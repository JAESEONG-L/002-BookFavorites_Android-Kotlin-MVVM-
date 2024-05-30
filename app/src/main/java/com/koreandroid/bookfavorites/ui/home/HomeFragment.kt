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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Adding viewModel as a LifecycleObserver for the View Lifecycle.
        viewLifecycleOwner.lifecycle.addObserver(viewModel)

        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            with(insets.getInsets(WindowInsetsCompat.Type.systemBars())) {
                v.updatePadding(top = top * 71 / 84)
            }

            WindowInsetsCompat.CONSUMED
        }

        binding.setupSearchTextField()

        viewModel.updateIsHomeFragmentViewRecreated()
    }

    override fun onResume() {
        super.onResume()

        if (binding.etSearch.text.isNotEmpty()) binding.searchBar.callOnClick()
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }

    override fun onDestroy() {
        viewModel.clearIsHomeFragmentViewRecreated()

        super.onDestroy()
    }

    private fun FragmentHomeBinding.setupSearchTextField() {
        val defaultNavigationIcon: Drawable? = searchBar.navigationIcon
        val searchItem: MenuItem = searchBar.menu.findItem(R.id.search)!!

        searchBar.run {
            setOnClickListener {
                if (showSearchTextField()) searchItem.isVisible = true
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
                if (hasFocus) return@setOnFocusChangeListener

                searchItem.isVisible = false
                hideSearchTextField()
                searchBar.navigationIcon = defaultNavigationIcon

                viewModel.clearSearchText()
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showSearchTextField(): Boolean = binding.etSearch.run {
        if (visibility == View.GONE) {
            with(binding.searchBar) {
                isClickable = false
                navigationIcon = resources.getDrawable(R.drawable.shape_empty, null)
                hint = null
            }

            visibility = View.VISIBLE
            showSoftKeyboard()

            binding.clSearch.updateLayoutParams {
                height = resources.getDimension(R.dimen.height_app_bar_large)
                    .toInt()
            }

            true
        } else false
    }

    private fun hideSearchTextField() {
        binding.etSearch.run {
            binding.clSearch.updateLayoutParams {
                height = resources.getDimension(R.dimen.size_large)
                    .toInt()
            }

            visibility = View.GONE
            hideSoftKeyboard()

            with(binding.searchBar) {
                isClickable = true
                hint = getString(R.string.home_search_bar_hint)
            }
        }
    }
}