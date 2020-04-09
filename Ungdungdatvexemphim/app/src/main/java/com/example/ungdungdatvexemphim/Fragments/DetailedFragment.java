package com.example.ungdungdatvexemphim.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ungdungdatvexemphim.R;
import com.squareup.picasso.Picasso;

public class DetailedFragment extends Fragment {
// Tạo đối tượng để ánh xạ
    // Ánh xạ: Lấy đối tượng đã tạo dùng nó để làm gì....
    TextView tvTitle,tvDesc,tvRating,tvReleaseDate;
    Button btnTicketBooking;
    ImageView movieImage;
    Button buttonbuyticket;

    String movieTitle, movieRating, movieReleaseDate, movieDesc, movieImagePath;

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
        Bundle bundle = getArguments();
//  Lấy ra được gói tin trong Bundle
        movieTitle = bundle.getString("movieTitle");
        movieRating = bundle.getString("movieRating");
        movieDesc = bundle.getString("movieDesc");
        movieReleaseDate = bundle.getString("movieReleaseDate");
        movieImagePath = bundle.getString("moviePosterPath");

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
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragmentContainer, new BuyticketFragment());
                fr.commit();
            }
        });
//        btnTicketBooking.getText(tvTitle.)
        return view;
    }



}
