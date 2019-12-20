package com.mahipal.rxjava.mvvm.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mahipal.rxjava.mvvm.model.GithubProfile
import com.mahipal.rxjava.network.GitHubApiRepository
import io.reactivex.Observable

class GitHubViewModel(application: Application) : AndroidViewModel(application) {

    private var gitHubApiRepository: GitHubApiRepository? = null

    init {
        gitHubApiRepository = GitHubApiRepository()
    }

    fun getGitHubProfileByUserName(userName: String) : Observable<GithubProfile>? {
        return gitHubApiRepository?.getGitHubProfileByUserName(userName)
    }
}