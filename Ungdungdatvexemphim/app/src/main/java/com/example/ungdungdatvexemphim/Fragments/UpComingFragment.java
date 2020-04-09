package com.example.ungdungdatvexemphim.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungdatvexemphim.Adapters.UpComingMovieAdapter;
import com.example.ungdungdatvexemphim.ApiClient;
import com.example.ungdungdatvexemphim.Models.MovieResponse1;
import com.example.ungdungdatvexemphim.Models.Upcoming;
import com.example.ungdungdatvexemphim.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpComingFragment extends Fragment {

    UpComingMovieAdapter upcomingMovieAdapter;
    RecyclerView recyclerView;
    List<Upcoming> upcomings;
    String apiKey = "1ccffc87918c1ca19fc2e8d2eb7dd6a2";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming,container,false);


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager( getContext()));

        getMovieData(apiKey);

        return view;
    }
    // function lấy dữ liệu phim dữ liệu truyền vào là một đối tượng keyAPI
    public void getMovieData(String apiKey){
        // tạo đối tượng gọi về lấy API lấy phim sắp chiếu thông qua keyAPI
        Call<MovieResponse1> call = ApiClient.getInstance().getApi().getUpcomingMovies(apiKey);
        // Enqueue: phương thức thực hiện request bất đồng bộ
        // và thông báo cho ứng dụng khi có phản hồi từ server.
        call.enqueue(new Callback<MovieResponse1>() {
            // Nếu có phản hồi thì làm gì...
            @Override
            public void onResponse(Call<MovieResponse1> call, Response<MovieResponse1> response) {
                if (response.isSuccessful() && response.body() != null){
                    upcomings = response.body().getResults();
                    upcomingMovieAdapter = new UpComingMovieAdapter(getContext(),upcomings);
                    recyclerView.setAdapter(upcomingMovieAdapter);
                }
            }
            // Nếu thất bại
            @Override
            public void onFailure(Call<MovieResponse1> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
