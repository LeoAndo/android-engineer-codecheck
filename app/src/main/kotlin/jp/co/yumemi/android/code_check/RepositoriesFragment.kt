/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoriesBinding
import jp.co.yumemi.android.code_check.model.Item

/**
 * リポジトリ検索画面
 */
class RepositoriesFragment : Fragment(R.layout.fragment_repositories) {
    private val viewModel by navGraphViewModels<RepositoriesViewModel>(R.id.nav_graph)
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
                viewModel.searchResults(editText.text.toString()).let { adapter.submitList(it) }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
    }

    private fun gotoRepositoryFragment(item: Item) {
        val action =
            RepositoriesFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item = item)
        findNavController().navigate(action)
    }
}
