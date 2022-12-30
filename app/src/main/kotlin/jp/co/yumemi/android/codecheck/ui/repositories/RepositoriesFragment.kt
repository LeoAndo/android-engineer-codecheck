/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck.ui.repositories

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.codecheck.R
import jp.co.yumemi.android.codecheck.data.ErrorResult
import jp.co.yumemi.android.codecheck.databinding.FragmentRepositoriesBinding
import jp.co.yumemi.android.codecheck.ui.extentions.hideKeyboard
import jp.co.yumemi.android.codecheck.ui.extentions.showToast
import jp.co.yumemi.android.codecheck.model.RepositorySummary
import kotlinx.coroutines.launch
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
        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }

        binding.searchInputText.setOnEditorActionListener { editText, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                Log.d("RepositoriesFragment", "検索日時 ${Date()}")
                viewModel.searchResults(editText.text.toString())
                hideKeyboard()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.fabUp.setOnClickListener {
            binding.recyclerView.scrollToPosition(0)
        }

        binding.buttonReload.setOnClickListener {
            viewModel.searchResults(binding.searchInputText.text.toString())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(
                viewLifecycleOwner.lifecycle,
                Lifecycle.State.CREATED
            ).collect { uiState ->
                val isLoading = uiState is UiState.Loading
                binding.recyclerView.isVisible = !isLoading
                binding.progress.isVisible = isLoading
                binding.errorLayout.isVisible = (uiState is UiState.Error)
                when (uiState) {
                    UiState.Initial, UiState.Loading -> {}
                    is UiState.Data -> {
                        adapter.submitList(uiState.repositories)
                        if (uiState.repositories.isEmpty()) {
                            requireContext().showToast(getString(R.string.empty_message))
                        }
                    }
                    is UiState.Error -> {
                        adapter.submitList(emptyList())
                        val defaultErrorMessage = uiState.throwable.localizedMessage
                            ?: getString(R.string.default_error_msg)
                        val message = if (uiState.throwable is ErrorResult) {
                            when (uiState.throwable) {
                                ErrorResult.NetworkError -> getString(R.string.network_error_message)
                                is ErrorResult.ForbiddenError, is ErrorResult.UnAuthorizedError,
                                is ErrorResult.UnprocessableEntity, is ErrorResult.UnexpectedError -> {
                                    defaultErrorMessage
                                }
                            }
                        } else {
                            defaultErrorMessage
                        }
                        binding.errorMessage.text = message
                    }
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