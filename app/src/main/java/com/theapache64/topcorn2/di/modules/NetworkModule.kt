package com.theapache64.topcorn2.di.modules

import com.squareup.moshi.Moshi
import com.theapache64.topcorn2.data.remote.ApiInterface
import com.theapache64.topcorn2.utils.calladapter.flow.FlowResourceCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRavenApi(moshi: Moshi): ApiInterface {
        return Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/theapache64/top250/master/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(FlowResourceCallAdapterFactory())
            .build()
            .create(ApiInterface::class.java)
    }

}