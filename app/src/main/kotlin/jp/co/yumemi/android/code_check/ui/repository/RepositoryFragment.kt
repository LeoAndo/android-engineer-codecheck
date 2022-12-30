/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.repository

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoryBinding

/**
 * リポジトリ詳細画面
 */
@AndroidEntryPoint
class RepositoryFragment : Fragment(R.layout.fragment_repository) {

    private val args: RepositoryFragmentArgs by navArgs()

    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRepositoryBinding.bind(view)

        val item = args.item

        binding.ownerIconView.load(item.ownerIconUrl)
        binding.nameView.text = item.name
        binding.languageView.text =
            requireContext().getString(R.string.written_language, item.language)
        binding.starsView.text =
            requireContext().getString(R.string.stargazers_count, item.stargazersCount)
        binding.watchersView.text =
            requireContext().getString(R.string.watchers_count, item.watchersCount)
        binding.forksView.text = requireContext().getString(R.string.forks_count, item.forksCount)
        binding.openIssuesView.text =
            requireContext().getString(R.string.open_issues_count, item.openIssuesCount)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
