package uk.ac.tees.mad.E4621366.mobileappdevelopment

import android.app.Application
import androidx.room.Room
import uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.common.AppDatabase


class MyApp : Application() {
    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "app_db"
        ).build()
    }
}