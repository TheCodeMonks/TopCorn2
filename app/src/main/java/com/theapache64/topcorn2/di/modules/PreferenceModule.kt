package com.theapache64.topcorn2.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * Created by theapache64 : Jan 04 Mon,2021 @ 00:31
 */
@Module
@InstallIn(ApplicationComponent::class)
object PreferenceModule {
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("topcorn2_pref", Context.MODE_PRIVATE)
    }
}