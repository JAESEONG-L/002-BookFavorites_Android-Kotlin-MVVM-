package com.koreandroid.bookfavorites.ui.shared

import androidx.lifecycle.ViewModel
import com.koreandroid.bookfavorites.data.BibliographyRepository
import com.koreandroid.bookfavorites.data.FavoritesRepository
import com.koreandroid.bookfavorites.data.NotesRepository

class MobileNavigationViewModel : ViewModel() {

    internal val bibliographyRepository = BibliographyRepository()

    internal val favoritesRepository = FavoritesRepository()

    internal val notesRepository = NotesRepository()
}