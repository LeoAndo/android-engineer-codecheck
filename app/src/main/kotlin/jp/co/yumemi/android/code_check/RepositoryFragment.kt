/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoryBinding

/**
 * リポジトリ詳細画面
 */
@AndroidEntryPoint
class RepositoryFragment : Fragment(R.layout.fragment_repository) {

    private val args: RepositoryFragmentArgs by navArgs()

    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding!!
    // private val viewModel by navGraphViewModels<RepositoriesViewModel>(R.id.nav_graph)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Log.d("検索した日時", viewModel.lastSearchDate?.toString() ?: "no date")

        _binding = FragmentRepositoryBinding.bind(view)

        val item = args.item

        binding.ownerIconView.load(item.ownerIconUrl)
        binding.nameView.text = item.name
        binding.languageView.text =
            requireContext().getString(R.string.written_language, item.language)
        binding.starsView.text = "${item.stargazersCount} stars"
        binding.watchersView.text = "${item.watchersCount} watchers"
        binding.forksView.text = "${item.forksCount} forks"
        binding.openIssuesView.text = "${item.openIssuesCount} open issues"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
