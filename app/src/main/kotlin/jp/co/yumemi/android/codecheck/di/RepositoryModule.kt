package jp.co.yumemi.android.codecheck.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.codecheck.data.repository.GithubRepoRepository
import jp.co.yumemi.android.codecheck.data.repository.impl.GithubRepoRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindGithubRepoRepository(impl: GithubRepoRepositoryImpl): GithubRepoRepository
}