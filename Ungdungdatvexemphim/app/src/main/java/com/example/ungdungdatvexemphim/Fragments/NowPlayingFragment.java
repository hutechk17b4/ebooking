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

import com.example.ungdungdatvexemphim.Adapters.NowPlayingMovieAdapter;
import com.example.ungdungdatvexemphim.ApiClient;
import com.example.ungdungdatvexemphim.Models.MovieResponse;
import com.example.ungdungdatvexemphim.Models.NowPlaying;
import com.example.ungdungdatvexemphim.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// NƠI XỬ LÝ GIAO DIỆN NOWPLAYING
public class NowPlayingFragment extends Fragment {

    NowPlayingMovieAdapter nowPlayingMovieAdapter;
    RecyclerView recyclerView;
    List<NowPlaying> nowPlayings;
    String apiKey = "1ccffc87918c1ca19fc2e8d2eb7dd6a2";


    // Hàm tạo view cho Fragment_now_playing
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing,container,false);


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager( getContext()));

        getMovieData(apiKey);

        return view;
    }

// Hàm lấy dữ liệu Phim và
    // Thực hiện Request đến API và xử lý kết quả trả về :
    public void getMovieData(String apiKey){
        // Tạo đối tượng gọi để thực hiện Request
        Call<MovieResponse> call = ApiClient.getInstance().getApi().getPlayingMovies(apiKey);
        // Enqueue: phương thức thực hiện request bất đồng bộ
        // và thông báo cho ứng dụng khi có phản hồi từ server.
        call.enqueue(new Callback<MovieResponse>() {
            // Nếu có phản hồi thì làm gì...
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    nowPlayings = response.body().getResults();
                    nowPlayingMovieAdapter = new NowPlayingMovieAdapter(getContext(),nowPlayings);
                    recyclerView.setAdapter(nowPlayingMovieAdapter);
                }
            }
            // Nếu thất bại
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}