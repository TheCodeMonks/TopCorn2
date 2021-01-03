package com.theapache64.topcorn2.utils

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.theapache64.topcorn2.utils.calladapter.flow.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

/**
 * A super cool utility class to provide controlled data cache-ing
 */
abstract class NetworkBoundResource<DB, REMOTE> {

    @MainThread
    abstract fun fetchFromLocal(): Flow<DB>

    @MainThread
    abstract fun fetchFromRemote(): Flow<Resource<REMOTE>>

    @WorkerThread
    abstract fun saveRemoteData(data: REMOTE)

    @MainThread
    abstract fun shouldFetchFromRemote(data: DB): Boolean

    @ExperimentalCoroutinesApi
    fun asFlow() = flow<Resource<DB>> {


        val localData = fetchFromLocal().first()

        // checking if local data is staled
        if (shouldFetchFromRemote(localData)) {

            // need remote data
            fetchFromRemote()
                .collect { response ->
                    when (response) {

                        is Resource.Loading -> {
                            emit(Resource.Loading())
                        }
                        is Resource.Success -> {
                            val data = response.data!!
                            saveRemoteData(data)

                            // start watching it
                            emitLocalDbData()
                        }
                        is Resource.Error -> {
                            emit(Resource.Error(response.errorData))
                        }
                    }
                }

        } else {
            // valid cache, no need to fetch from remote.
            emitLocalDbData()
        }
    }

    @ExperimentalCoroutinesApi
    private suspend fun FlowCollector<Resource<DB>>.emitLocalDbData() {

        // sending loading status
        emit(Resource.Loading())

        emitAll(fetchFromLocal().map { dbData ->
            Resource.Success(null, dbData)
        })
    }
}