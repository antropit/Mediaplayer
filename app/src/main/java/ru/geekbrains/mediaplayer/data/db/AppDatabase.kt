package ru.geekbrains.mediaplayer.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import ru.geekbrains.mediaplayer.data.MediaSourceData

@Database(
    version = 1,
    entities = [
        MediaSourceData::class
    ], exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        private var instance: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app.db").build()
            return instance!!
        }
    }

    abstract fun mediaDao(): MediaDao
}