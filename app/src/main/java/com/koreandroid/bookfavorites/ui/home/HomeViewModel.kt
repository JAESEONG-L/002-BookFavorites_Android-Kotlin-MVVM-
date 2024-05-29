package com.koreandroid.bookfavorites.ui.home

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeViewModel(
    private val onCreateCallback: (String) -> Unit
) : ViewModel(), DefaultLifecycleObserver {

    var searchText: String = ""

    override fun onCreate(owner: LifecycleOwner) {
        clearSearchText()

        onCreateCallback(searchText)
    }

    private fun clearSearchText() {
        searchText = ""
    }

    companion object {

        fun provideFactory(onCreateCallback: (String) -> Unit): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HomeViewModel(onCreateCallback) as T
                }
            }
    }
}