package itis.lisa.semestrovka.basicfeature.data.remote.api

import itis.lisa.semestrovka.basicfeature.data.remote.model.Response
import retrofit2.http.GET

interface NewsApi {

    @GET("latest-news")
    suspend fun getNews(): Response
}
