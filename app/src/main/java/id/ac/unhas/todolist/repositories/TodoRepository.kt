package id.ac.unhas.todolist.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import id.ac.unhas.todolist.dao.TodoDao
import id.ac.unhas.todolist.entities.Todo

class TodoRepository (private val todoDao: TodoDao) {

    val todos : LiveData<List<Todo>> = todoDao.getAllTodos()

    @WorkerThread
    suspend fun insert(todo: Todo){
        todoDao.insert(todo)
    }
    @WorkerThread
    suspend fun update(todo: Todo){
        todoDao.update(todo)
    }
}