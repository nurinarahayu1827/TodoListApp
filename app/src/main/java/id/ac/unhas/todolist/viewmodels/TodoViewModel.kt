package id.ac.unhas.todolist.viewmodels

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.ac.unhas.todolist.NotificationHelper
import id.ac.unhas.todolist.TodoRoomDatabase
import id.ac.unhas.todolist.entities.Todo
import id.ac.unhas.todolist.repositories.TodoRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

@RequiresApi(Build.VERSION_CODES.M)
@InternalCoroutinesApi
class TodoViewModel (application: Application) : AndroidViewModel(application){
    private val todoRepository : TodoRepository
    val todos : LiveData<List<Todo>>

    private var job = Job()
    private val coroutineContext: CoroutineContext get() = job + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    val notificationHelper = NotificationHelper(getApplication())

    init {
        val todoDao =
            TodoRoomDatabase.getDatabase(application).todoDao()
        todoRepository = TodoRepository(todoDao)
        todos = todoRepository.todos
        notificationHelper.setUpNotificationChannels()
    }

    fun insert(todo: Todo) = scope.launch(Dispatchers.IO) {
        todoRepository.insert(todo)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun showBubble() = scope.launch(Dispatchers.Default){
        if (notificationHelper.canBubble()) {
            notificationHelper.showNotification(false)
        }
    }

    fun toggleDone(todo: Todo, checked : Boolean) =
        scope.launch(Dispatchers.IO){
            todo.done = checked
            todoRepository.update(todo)
        }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}