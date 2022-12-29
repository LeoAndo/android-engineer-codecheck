/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoriesBinding
import jp.co.yumemi.android.code_check.extentions.hideKeyboard
import jp.co.yumemi.android.code_check.extentions.showToast
import jp.co.yumemi.android.code_check.model.RepositorySummary

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
                viewModel.searchResults(editText.text.toString())
                requireActivity().hideKeyboard()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }

        viewModel.results.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            requireContext().showToast(it.localizedMessage)
        }
    }

    private fun gotoRepositoryFragment(item: RepositorySummary) {
        val action =
            RepositoriesFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item = item)
        findNavController().navigate(action)
    }
}
