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
import com.koreandroid.bookfavorites.R
import com.koreandroid.bookfavorites.databinding.FragmentHomeBinding
import com.koreandroid.bookfavorites.util.hideSoftKeyboard
import com.koreandroid.bookfavorites.util.showSoftKeyboard

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var isViewRecreated: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (isViewRecreated) viewLifecycleOwner.lifecycle.addObserver(viewModel)

        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            with(insets.getInsets(WindowInsetsCompat.Type.systemBars())) {
                v.updatePadding(top = top * 71 / 84)
            }

            WindowInsetsCompat.CONSUMED
        }

        binding.setupSearchTextField()

        isViewRecreated = true
    }

    override fun onResume() {
        super.onResume()

        if (binding.etSearch.text.isNotEmpty()) binding.searchBar.callOnClick()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun FragmentHomeBinding.setupSearchTextField() {
        val defaultNavigationIcon: Drawable? = searchBar.navigationIcon
        val searchItem: MenuItem = searchBar.menu.findItem(R.id.search)!!

        searchBar.run {
            setOnClickListener {
                if (binding.etSearch.visibility == View.GONE) {
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
        }

        etSearch.run {
            viewModel.searchText.observe(viewLifecycleOwner) {
                if (it != text.toString()) setText(it)
            }

            doOnTextChanged { text, _, _, _ ->
                viewModel.setSearchText(text?.toString() ?: "")
            }

            setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    clSearch.updateLayoutParams {
                        height = resources.getDimension(R.dimen.size_large)
                            .toInt()
                    }

                    searchItem.isVisible = false

                    visibility = View.GONE
                    hideSoftKeyboard()

                    searchBar.isClickable = true
                    searchBar.navigationIcon = defaultNavigationIcon
                    searchBar.hint = getString(R.string.home_search_bar_hint)

                    viewModel.clearSearchText()
                }
            }
        }
    }
}