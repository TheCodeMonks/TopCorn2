package com.theapache64.topcorn2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.theapache64.topcorn2.data.local.daos.MoviesDao
import com.theapache64.topcorn2.data.remote.Movie

@Database(entities = [Movie::class], version = 3)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MoviesDao
}