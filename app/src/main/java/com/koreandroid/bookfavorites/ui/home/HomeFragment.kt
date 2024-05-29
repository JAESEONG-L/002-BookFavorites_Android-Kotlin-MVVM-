package com.koreandroid.bookfavorites.ui.home

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.search.SearchBar
import com.koreandroid.bookfavorites.R
import com.koreandroid.bookfavorites.databinding.FragmentHomeBinding
import com.koreandroid.bookfavorites.util.hideSoftKeyboard
import com.koreandroid.bookfavorites.util.showSoftKeyboard

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.provideFactory(::initializeViewState)
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycle.addObserver(viewModel)

        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            with(insets.getInsets(WindowInsetsCompat.Type.systemBars())) {
                v.updatePadding(top = top * 71 / 84)
            }

            WindowInsetsCompat.CONSUMED
        }

        binding.setupSearchTextField()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeViewState(searchText: String) {
        binding.etSearch.setText(searchText)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun FragmentHomeBinding.setupSearchTextField() {
        val defaultNavigationIcon: Drawable? = searchBar.navigationIcon
        val searchItem: MenuItem = searchBar.menu.findItem(R.id.search)!!

        searchBar.setOnClickListener {
            if (etSearch.visibility == View.GONE) with(it as SearchBar) {
                isClickable = false
                navigationIcon = resources.getDrawable(R.drawable.shape_empty, null)
                hint = null

                etSearch.visibility = View.VISIBLE
                etSearch.showSoftKeyboard()

                searchItem.isVisible = true

                clSearch.updateLayoutParams {
                    height = resources.getDimension(R.dimen.height_app_bar_large)
                        .toInt()
                }
            }
        }

        etSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.searchText = text?.toString() ?: ""
        }

        etSearch.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                clSearch.updateLayoutParams {
                    height = resources.getDimension(R.dimen.size_large)
                        .toInt()
                }

                searchItem.isVisible = false

                v.visibility = View.GONE
                v.hideSoftKeyboard()

                searchBar.isClickable = true
                searchBar.navigationIcon = defaultNavigationIcon
                searchBar.hint = getString(R.string.home_search_bar_hint)
            }
        }
    }
}