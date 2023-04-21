package com.example.chatgptapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatgptapp.databinding.ActivityMainBinding
import com.example.chatgptapp.model.Message
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: ChatGptViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ChatGptViewModel::class.java]
        val llm = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = llm

        viewModel.messageList.observe(this) {
            val adapter = ChatGptAdapter(it)
            binding.recyclerView.adapter = adapter
        }
        binding.sendBtn.setOnClickListener {
            val edit = binding.messageEditText.text.toString()
            viewModel.AddTochat(edit, Message.SENT_BY_ME, viewModel.getTimeNow())
            binding.messageEditText.setText("")
            viewModel.callapi(edit)
        }


    }

    private fun getTimeNow(): String {
        return SimpleDateFormat("yy-mm-dd", Locale.US).format(Date())
    }
}