package com.koreandroid.bookfavorites.ui.home

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel(), DefaultLifecycleObserver {

    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> = _searchText

    init {
        _searchText.value = ""
    }

    override fun onCreate(owner: LifecycleOwner) {
        clearSearchText()
    }

    fun setSearchText(text: String) {
        _searchText.value = text
    }

    fun clearSearchText() {
        setSearchText("")
    }
}