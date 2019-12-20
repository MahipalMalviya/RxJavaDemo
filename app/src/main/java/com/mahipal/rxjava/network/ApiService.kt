package com.mahipal.rxjava.network

import com.mahipal.rxjava.mvvm.model.GithubProfile
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users/{userName}")
    fun fetchGithubProfileByUserName(@Path("userName") userName: String) : Observable<GithubProfile>?
}