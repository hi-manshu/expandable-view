package com.example.cred

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cred.adapter.ExpandableAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val items = listOf("This is item 1", "This is item 2", "This is item 3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindRecyclerView()
    }

    private fun bindRecyclerView() {

        recyclerViewExpanded.layoutManager = LinearLayoutManager(this)
        recyclerViewExpanded.adapter = ExpandableAdapter(
            recyclerViewExpanded,
            items
        ) { value, position ->
            recyclerViewExpanded.smoothScrollToPosition(position)
        }
    }
}