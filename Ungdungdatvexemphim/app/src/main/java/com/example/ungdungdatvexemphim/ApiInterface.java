package com.example.ungdungdatvexemphim;

import com.example.ungdungdatvexemphim.Models.MovieResponse;
import com.example.ungdungdatvexemphim.Models.MovieResponse1;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// Xử lý API tứ máy chủ web
public interface ApiInterface {

    // Lấy API key trả về giao diện
    @GET("movie/now_playing")
    Call<MovieResponse> getPlayingMovies(
            @Query("api_key") String apiKey
    );
    @GET("movie/upcoming")
    Call<MovieResponse1> getUpcomingMovies(
            @Query("api_key") String apiKey
    );


}
