package com.example.netflixclone.data.source.retrofit

import com.example.netflixclone.data.model.Movie
import com.example.netflixclone.data.model.MoviesDetailResponse
import com.example.netflixclone.data.model.MoviesResponse
import com.example.netflixclone.data.model.SeriesDetailResponse
import com.example.netflixclone.data.model.SeriesResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface MovieService {

    @GET("get_products")
    suspend fun getMovies(@Header("store") store: String = "filmsin"): Response<MoviesResponse>

    @GET("get_sale_products")
    suspend fun getComingSoonMovies(@Header("store") store: String = "filmsin"): Response<MoviesResponse>

    @GET("get_product_detail")
    suspend fun getMoviesDetail(
        @Query("id") id: Int,
        @Header("store") store: String = "filmsin",
    ): Response<MoviesDetailResponse>

    @GET("get_products")
    suspend fun getSeries(@Header("store") store: String = "dizisin"): Response<SeriesResponse>

    @GET("get_product_detail")
    suspend fun getSeriesDetail(
        @Query("id") id: Int,
        @Header("store") store: String = "dizisin",
    ): Response<SeriesDetailResponse>

    @GET("search_product")
    suspend fun searchProduct(
        @Query("query") query: String,
        @Header("store") store: String = "filmsin"
    ): Response<MoviesResponse>
}