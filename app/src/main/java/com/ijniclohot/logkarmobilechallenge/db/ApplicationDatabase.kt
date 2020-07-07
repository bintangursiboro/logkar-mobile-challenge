package com.ijniclohot.logkarmobilechallenge.db

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ijniclohot.logkarmobilechallenge.dao.OrderDao
import com.ijniclohot.logkarmobilechallenge.models.OrderModel

@Database(entities = [OrderModel::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: ApplicationDatabase? = null
        private val LOCK = Any()

        operator fun invoke(application: Application) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(application).also {
                instance = it
            }
        }

        private fun buildDatabase(application: Application) = Room.databaseBuilder(
            application,
            ApplicationDatabase::class.java,
            "database.db"
        ).fallbackToDestructiveMigration().build()

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
            }
        }
    }

    abstract fun currentOrderDao(): OrderDao

}