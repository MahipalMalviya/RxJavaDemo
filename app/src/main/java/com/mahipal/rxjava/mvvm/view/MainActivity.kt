package com.mahipal.rxjava.mvvm.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mahipal.rxjava.R
import com.mahipal.rxjava.mvvm.model.GithubProfile
import com.mahipal.rxjava.mvvm.viewModel.GitHubViewModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val LOG_TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_search_user_github_profile.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(textView: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch()
                    return true
                }
                return false
            }
        })

    }

    @SuppressLint("SetTextI18n")
    private fun performSearch() {
        val userName = et_search_user_github_profile.text.toString().trim()
        val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        val observableGithubProfile = viewModel.create(GitHubViewModel::class.java)
            .getGitHubProfileByUserName(userName)

        var gitHub: GithubProfile? = null

        observableGithubProfile?.subscribe(object : Observer<GithubProfile> {

            override fun onComplete() {
                Log.d(LOG_TAG,"onComplete Invoked")
                setDataToView(gitHub)
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(LOG_TAG,"onSubscribe Invoked")
            }

            override fun onNext(gitHubProfile: GithubProfile) {
                Log.d(LOG_TAG,"onNext Invoked")
                gitHub = gitHubProfile
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                Log.d(LOG_TAG,"onError Invoked")
                Log.e(LOG_TAG,"Error When API Call -----> ${e.printStackTrace()}")
            }

        })

    }

    @SuppressLint("SetTextI18n")
    private fun setDataToView(gitHub: GithubProfile?) {
        gitHub?.let { gitHubProfile ->
                        Glide.with(this)
                .load(gitHubProfile.avatarUrl)
                .into(civ_github_profile_pic)

            tv_github_profile_name.text = gitHubProfile.name?:""
            tv_user_repo.text = "Public Repo :- ${gitHubProfile.publicRepos}"
            tv_user_bio.text = "Bio :- ${gitHubProfile.bio?:""}"
            tv_user_location.text = "Location :- ${gitHubProfile.location?:""}"
        }
    }
}