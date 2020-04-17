package com.example.ungdungdatvexemphim.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ungdungdatvexemphim.Activities.SelectTimeStartActivity;
import com.example.ungdungdatvexemphim.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class DetailedFragment extends Fragment {
// Tạo đối tượng để ánh xạ
    // Ánh xạ: Lấy đối tượng đã tạo dùng nó để làm gì....
    TextView tvTitle,tvDesc,tvRating,tvReleaseDate;
    Button btnTicketBooking;
    ImageView movieImage;
    Button buttonbuyticket;

    String movieTitle, movieRating, movieReleaseDate, movieDesc, movieImagePath,IDmovie;

    String urlCheckLichTrinh="http://192.168.1.9/php_ebooking/checkLichTrinh.php";

    // Tạo View từ bản vẽ fragment_detailed.xml
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed, container, false);
        // Lấy đối tượng đã tạo để Ánh xạ
        tvTitle = view.findViewById(R.id.movieTitle);
        tvDesc = view.findViewById(R.id.movieDesc);
        tvRating = view.findViewById(R.id.movieRating);
        tvReleaseDate = view.findViewById(R.id.movieReleaseDate);
        btnTicketBooking = view.findViewById(R.id.btnBuyTicket);
        movieImage = view.findViewById(R.id.imgPoster);

// Bundle trong Android là một đối tượng dữ liệu được tạo ra nhằm mục dích đóng gói
// các dữ liệu cần được truyền qua lại giữa các Intent trong Android
        final Bundle bundle = getArguments();
//  Lấy ra được gói tin trong Bundle
        movieTitle = bundle.getString("movieTitle");
        movieRating = bundle.getString("movieRating");
        movieDesc = bundle.getString("movieDesc");
        movieReleaseDate = bundle.getString("movieReleaseDate");
        movieImagePath = bundle.getString("moviePosterPath");
        IDmovie=bundle.getString("IDmovie");
        Toast.makeText(getContext(),IDmovie,Toast.LENGTH_SHORT).show();

// đặt tên cho đối tượng đã tạo
        tvTitle.setText(movieTitle);
        tvRating.setText(movieRating);
        tvDesc.setText(movieDesc);
        tvReleaseDate.setText(movieReleaseDate);
// Khi phát triển ứng dụng có nhiều ảnh hoặc
// phải load và hiển thị ảnh từ server vậy
// làm sao cho app không bị đơ UI, khi chờ phải load ảnh
// nếu để load ảnh tuần tự thì chắc để chờ đợi xong load ảnh
// mà chưa kịp load xong đã chuyển màn hình thì sẽ đơ luôn app
// Picasso thư viện chuyên về download ảnh từ server về cho app android
// Muốn dùng Picasso thêm đoạn mã sau vào trong file build.gradle
//  compile 'com.squareup.picasso:picasso:2.5.2' (này cũ rồi nên update)
// --> Load ảnh từ Internet (server) vào View (trong app của mình)
        // Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
        Picasso.with(getContext()).load(movieImagePath).into(movieImage);
        // Xử lý sự kiện cho nút Book
        btnTicketBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ChooseSeatActivity chooseSeatActivity=new ChooseSeatActivity();
//                Intent intent=new Intent(getContext(), chooseSeatActivity.getClass());
//                startActivity(intent);
                CheckLichTrinh();
                // truyền qua cả tên phim


            }
        });
        return view;
    }

    public void CheckLichTrinh()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlCheckLichTrinh,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                        if(response.equals("success"))
                        {
                            SelectTimeStartActivity selectTimeStartActivity=new SelectTimeStartActivity();
                            Intent intent=new Intent(getContext(),selectTimeStartActivity.getClass());
                            Bundle bundle=new Bundle();
                            bundle.putString("TENPHIM",movieTitle);
                            bundle.putString("IDPHIM",IDmovie);
                            intent.putExtra("DuLieuTenPhim",bundle);
                            getContext().startActivity(intent);




                        }
                        else {
                            Toast.makeText(getContext(),"ko có lịch chiếu",Toast.LENGTH_SHORT).show();
                        }




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("IDphimPost",IDmovie);


                return params;
            }
        };
        requestQueue.add(stringRequest);

    }



}
