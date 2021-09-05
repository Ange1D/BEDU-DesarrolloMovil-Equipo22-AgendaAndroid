package com.equipo22.agenda.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Tarea::class), version = 1)
abstract class TareaDB: RoomDatabase() {
    companion object {
        private var dbInstance: TareaDB? = null
        const val DB_NAME = "Tareas_DB"

        fun getInstance(context: Context): TareaDB? {
            if (dbInstance == null) {
                synchronized(TareaDB::class) {
                    dbInstance = Room.databaseBuilder(
                        context,
                        TareaDB::class.java,
                        DB_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return dbInstance
        }
    }

    abstract fun tareaDAO():TareaDAO
}