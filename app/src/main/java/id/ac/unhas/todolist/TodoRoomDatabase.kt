package id.ac.unhas.todolist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ac.unhas.todolist.dao.TodoDao
import id.ac.unhas.todolist.entities.Todo
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import java.security.AccessControlContext

@Database(entities = [Todo::class], version = 1)
abstract class TodoRoomDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    
    companion object {
        @Volatile
        private var INSTANCE : TodoRoomDatabase? = null
        
        @InternalCoroutinesApi
        fun getDatabase(context: Context) : TodoRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoRoomDatabase::class.java,
                    "todo database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}