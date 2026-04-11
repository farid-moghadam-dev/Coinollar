package com.faridev.coinollar

import android.app.Application
import com.faridev.coinollar.core.database.di.databaseModule
import com.faridev.coinollar.core.network.di.networkModule
import com.faridev.coinollar.data.di.dataModule
import com.faridev.coinollar.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class CoinollarApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@CoinollarApp)
            modules(
                networkModule,
                databaseModule,
                dataModule,
                viewModelModule
            )
        }
    }
}
