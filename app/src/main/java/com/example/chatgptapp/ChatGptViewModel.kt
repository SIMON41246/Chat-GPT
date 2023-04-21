package com.example.chatgptapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatgptapp.model.CompletionRequest
import com.example.chatgptapp.model.CompletionResponse
import com.example.chatgptapp.model.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ChatGptViewModel : ViewModel() {
    private val _messageList = MutableLiveData<MutableList<Message>>()
    val messageList: LiveData<MutableList<Message>> get() = _messageList

    init {
        _messageList.value = mutableListOf()
    }

    fun AddTochat(mesage: String, sentBy: String, time: String) {
        val currentList = messageList.value ?: mutableListOf()
        currentList.add(Message(mesage, sentBy, time))
        _messageList.postValue(currentList)
    }

    fun AddResponse(response: String) {
        messageList.value?.removeAt(messageList.value?.size?.minus(1) ?: 0)
        AddTochat(response, SENT_BY_ROBOT, getTimeNow())
    }

    fun callapi(question: String) {
        AddTochat("typing....", SENT_BY_ROBOT, getTimeNow())
        val completionRequest = CompletionRequest(
            "text-davinci-003",
            question,
            4000
        )
        viewModelScope.launch {
            try {
                val response = API.result.getApi(completionRequest)
                HandleApi(response)
            } catch (_: java.lang.Exception) {

            }
        }
    }

    private suspend fun HandleApi(response: Response<CompletionResponse>) {
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                response.body()?.let {
                    val result = it.choices.firstOrNull()?.text
                    if (result != null) {
                        AddResponse(result.trim())
                    } else {
                        AddResponse("No chpoices Found ")
                    }
                }

            } else {
                AddResponse("NO choices")
            }
        }

    }

    fun getTimeNow(): String {
        return SimpleDateFormat("hh mm a", Locale.getDefault()).format(Date())
    }


    companion object {
        const val SENT_BY_ME = "sentbyme"
        const val SENT_BY_ROBOT = "sentbyrobot"
    }
}