package com.mahipal.rxjava.network

import android.util.Log
import com.mahipal.rxjava.mvvm.model.GithubProfile
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class GitHubApiRepository{

    private val LOG_TAG = GitHubApiRepository::class.java.simpleName

    fun getGitHubProfileByUserName(userName: String) : Observable<GithubProfile>? {
        val apiCall = RetrofitInstance.getInstance()
        return apiCall?.fetchGithubProfileByUserName(userName)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
    }
}