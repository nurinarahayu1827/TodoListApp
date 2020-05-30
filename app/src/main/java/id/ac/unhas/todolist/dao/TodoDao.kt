package id.ac.unhas.todolist.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import id.ac.unhas.todolist.entities.Todo

@Dao
interface TodoDao {
    @Query("SELECT * from todo_table order by done desc")
    fun getAllTodos() : LiveData<List<Todo>>

    @Insert
    suspend fun insert(todo : Todo)

    @Update
    suspend fun update(todo: Todo)

    @Query("DELETE FROM todo_table")
    fun deleteAll()
}