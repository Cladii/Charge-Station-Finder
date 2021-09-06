package com.example.g52010mobg5.app.database.email

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Email::class], version = 1, exportSchema = false)
abstract class EmailDatabase : RoomDatabase() {

    abstract val emailDatabaseDao: EmailDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: EmailDatabase? = null

        fun getInstance(context: Context): EmailDatabase {
            synchronized(this) {
                var instance =
                    INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EmailDatabase::class.java,
                        "email_history_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}