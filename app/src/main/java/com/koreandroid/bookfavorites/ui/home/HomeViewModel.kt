package com.koreandroid.bookfavorites.ui.home

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel(), DefaultLifecycleObserver {

    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> = _searchText

    private var isHomeFragmentViewRecreated: Boolean? = null

    init {
        _searchText.value = ""
    }

    override fun onCreate(owner: LifecycleOwner) {
        if (isHomeFragmentViewRecreated!!) {
            clearSearchText()
        }
    }

    fun setSearchText(text: String) {
        _searchText.value = text
    }

    fun clearSearchText() {
        setSearchText("")
    }

    fun updateIsHomeFragmentViewRecreated() {
        isHomeFragmentViewRecreated = isHomeFragmentViewRecreated?.let {
            true
        } ?: false
    }

    fun clearIsHomeFragmentViewRecreated() {
        isHomeFragmentViewRecreated = null
    }
}