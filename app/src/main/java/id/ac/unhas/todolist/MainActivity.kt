package id.ac.unhas.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.unhas.todolist.entities.Todo
import id.ac.unhas.todolist.repositories.TodoRepository
import id.ac.unhas.todolist.viewmodels.TodoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.todo_item.*
import java.io.LineNumberReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerViews = rvTodos
        val adapter = TodoRecyclerAdapter(this) {todo, done ->
            todoViewModel.toggleDone(todo, done)
        }
        recyclerViews.adapter = adapter
        recyclerViews.layoutManager = LinearLayoutManager(this)

    }
    //in onCreate
    todoViewModel = ViewModelProvider.of(this).get(TodoViewModel::class.java)

    todoViewModel.todos.observe(this, Observer { todos ->
        todos?.let{
            adapter.setTodos(todos)
        }

    })

    todoViewModel.showBubble()
    fabAdd.secOnclikListener {
        if (!TextUtils.isEmpty(editTodo.text)){
            val todo = Todo(0, editTodo.text.toString(),"",false)
            todoViewModel.insert(todo)
        }
    }
}
