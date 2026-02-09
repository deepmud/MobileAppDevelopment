package uk.ac.tees.mad.E4621366.mobileappdevelopment.common

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [PhotoEntity::class,IndicatorEntity::class, IndicatorRecordEntity::class,CountryEntity::class],
    version = 19
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
    abstract fun indicatorRecordDao(): IndicatorRecordDao
    abstract fun indicatorDao(): IndicatorDao
    abstract fun countryDao(): CountryDao
}