package com.example.ungdungdatvexemphim.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdungdatvexemphim.Activities.MainActivity;
import com.example.ungdungdatvexemphim.Models.NowPlaying;
import com.example.ungdungdatvexemphim.R;
import com.example.ungdungdatvexemphim.Fragments.DetailedFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

// Adapter sẽ làm gần như tất cả các công việc cho RecyclerView
// nó kết nối Datasource với các View item.
// View item là một dòng (row) riêng lẻ trong RecyclerView nơi mà dữ liệu sẽ được hiển thị.
// Bất kỳ dữ liệu nào trong View item chỉ được hiển thị thông qua View item. Có thể coi View item như là một nhóm cuộn của các View item.
// Để vẽ được danh sách trên màn hình ,RecyclerView sẽ hỏi Adapter sẽ có tổng cộng bao nhiêu item.
// Và Adapter của chúng ta sẽ trả lời thông tin này ở trong hàm getItemCount().
// Bất cứ khi nào RecyclerView quyết định nó cần tạo một ViewHolder và lưu trong bộ nhớ,
// nó sẽ gọi onCreateViewHolder (). Trong phương thức này, Adapter trả về (bố cục xml) .
// Mỗi khi ViewHolder được tạo trước đó được sử dụng lại, RecyclerView sẽ bảo Adapter cập nhật dữ liệu của nó.
// Bạn tùy chỉnh quy trình này bằng cách ghi đè lên BindViewHolder().
public class NowPlayingMovieAdapter extends RecyclerView.Adapter<NowPlayingMovieAdapter.ViewHolder> {

    Context context;
    List<NowPlaying> nowPlayings;

    public NowPlayingMovieAdapter(Context context, List<NowPlaying> nowPlayings) {
        this.context = context;
        this.nowPlayings = nowPlayings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.now_playing_movie_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
// Nếu bạn khai báo một biến là final, bạn không thể thay đổi giá trị của biến final (Nó sẽ là Hằng Số).
        final NowPlaying playing = nowPlayings.get(position);

        String img_base_url = "https://image.tmdb.org/t/p/w185_and_h278_bestv2/";

// Nếu bạn khai báo một biến là final, bạn không thể thay đổi giá trị của biến final (Nó sẽ là Hằng Số).
        final String imgUrl = img_base_url + playing.getPoster_path();

// --> Load ảnh từ Internet (server) vào View (trong app của mình)
        Picasso.with(context).load(imgUrl).into(holder.imageView);

// Nếu bạn khai báo một biến là final, bạn không thể thay đổi giá trị của biến final (Nó sẽ là Hằng Số).
        final String rating = playing.getVote_average()+"/10";

        holder.tvRating.setText(rating);
        holder.tvTitle.setText(playing.getTitle());

        holder.btnBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tạo mới một đối tượng detailedFragment
                DetailedFragment detailedFragment = new DetailedFragment();
// Bundle trong Android là một đối tượng dữ liệu được tạo ra nhằm mục dích đóng gói
// các dữ liệu cần được truyền qua lại giữa các Intent trong Android
                Bundle bundle = new Bundle();
//  Đóng gói dữ liệu kiểu string
                bundle.putString("movieTitle",playing.getTitle());
                bundle.putString("movieRating",rating);
                bundle.putString("movieReleaseDate",playing.getRelease_date());
                bundle.putString("movieDesc",playing.getOverview());
                bundle.putString("moviePosterPath",imgUrl);
// Đóng gói bundle vào detailedFragment
                detailedFragment.setArguments(bundle);
                ((MainActivity)context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer,detailedFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return nowPlayings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvRating,tvTitle;
        ImageView imageView;
        Button btnBuyTicket,bookmark;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRating = itemView.findViewById(R.id.movieRating);
            tvTitle = itemView.findViewById(R.id.movieTitle);
            imageView = itemView.findViewById(R.id.imgPoster);
            bookmark = itemView.findViewById(R.id.btnBookmark);
            btnBuyTicket = itemView.findViewById(R.id.btnBuyTicket);

        }
    }
}
