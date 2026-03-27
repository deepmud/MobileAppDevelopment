package uk.ac.tees.mad.E4621366.mobileappdevelopment

import android.app.Application
import androidx.room.Room
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.AppDatabase
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.RetrofitClient
import uk.ac.tees.mad.E4621366.mobileappdevelopment.repositories.IndicatorRepository


class MyApp : Application() {
    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
                this,
                AppDatabase::class.java,
                "app_db"
            ).fallbackToDestructiveMigration(true).build()
    }
    // Singleton repository exposing all required DAOs
    val indicatorRepository by lazy {
        IndicatorRepository(
            api = RetrofitClient.api,
            dao = database.indicatorRecordDao(),
            dao2 = database.indicatorDao(),
            dao3 = database.countryDao()
        )
    }
}