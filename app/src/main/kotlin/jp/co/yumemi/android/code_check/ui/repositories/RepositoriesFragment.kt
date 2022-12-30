/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.repositories

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.ErrorResult
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoriesBinding
import jp.co.yumemi.android.code_check.ui.extentions.hideKeyboard
import jp.co.yumemi.android.code_check.ui.extentions.showToast
import jp.co.yumemi.android.code_check.model.RepositorySummary
import java.util.*

/**
 * リポジトリ検索画面
 */
@AndroidEntryPoint
class RepositoriesFragment : Fragment(R.layout.fragment_repositories) {
    private val viewModel by viewModels<RepositoriesViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRepositoriesBinding.bind(view)

        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        val adapter = RepositoriesListAdapter(onItemClick = { item ->
            gotoRepositoryFragment(item)
        })

        binding.searchInputText.setOnEditorActionListener { editText, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                Log.d("RepositoriesFragment", "検索日時 ${Date()}")
                viewModel.searchResults(editText.text.toString())
                hideKeyboard()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            val isLoading = uiState is UiState.Loading
            binding.recyclerView.isVisible = !isLoading
            binding.progress.isVisible = isLoading
            when (uiState) {
                UiState.Initial, UiState.Loading -> {}
                is UiState.Data -> {
                    adapter.submitList(uiState.repositories)
                    if (uiState.repositories.isEmpty()) {
                        requireContext().showToast(getString(R.string.empty_message))
                    }
                }
                is UiState.Error -> {
                    val defaultErrorMessage = uiState.throwable.localizedMessage
                        ?: getString(R.string.default_error_message)
                    val message = if (uiState.throwable is ErrorResult) {
                        when (uiState.throwable) {
                            ErrorResult.ForbiddenError -> getString(R.string.forbidden_error_message)
                            ErrorResult.NetworkError -> getString(R.string.network_error_message)
                            ErrorResult.NotFoundError -> getString(R.string.page_not_found_message)
                            ErrorResult.UnAuthorizedError -> getString(R.string.unauthorized_error_message)
                            is ErrorResult.UnexpectedError -> defaultErrorMessage
                        }
                    } else {
                        defaultErrorMessage
                    }
                    requireContext().showToast(message)
                }
            }
        }
    }

    private fun gotoRepositoryFragment(item: RepositorySummary) {
        val action =
            RepositoriesFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item = item)
        findNavController().navigate(action)
    }
}
