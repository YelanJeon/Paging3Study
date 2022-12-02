package com.monkey.pagingtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this, PagingViewModel.Factory(PagingRepository(SampleBackendService())))[PagingViewModel::class.java]
    }
    private val recyclerView by lazy {
        findViewById<RecyclerView>(R.id.rcv_list)
    }
    private val btnRefresh by lazy {
        findViewById<Button>(R.id.btn_refresh)
    }
    private val emptyView by lazy {
        findViewById<TextView>(R.id.empty)
    }
    private val adapter by lazy {
        MyAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //recyclerView 설정
        adapter.addLoadStateListener {
            if(adapter.itemCount == 0) {
                emptyView.visibility = View.VISIBLE
            }else{
                emptyView.visibility = View.GONE
            }
        }
        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            PagingLoadStateAdapter { adapter.retry() },
            PagingLoadStateAdapter { adapter.retry() }
        )
        recyclerView.layoutManager = LinearLayoutManager(baseContext)
        recyclerView.addItemDecoration(DividerItemDecoration(baseContext, RecyclerView.VERTICAL))

        lifecycleScope.launch {
            viewModel.pagingData.collectLatest {
                adapter.submitData(it)
            }
        }

        btnRefresh.setOnClickListener {
            adapter.refresh()
        }

    }
}