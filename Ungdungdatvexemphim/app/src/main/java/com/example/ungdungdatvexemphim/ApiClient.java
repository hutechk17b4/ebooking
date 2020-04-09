package com.example.ungdungdatvexemphim;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
// Ngày càng có nhiều ứng dụng hay độc đáo đáp ứng nhu cầu
// ngày càng cao của người dùng khó tính.
// Để đa dạng trong ứng dụng, bạn không thể bỏ qua việc
// cập nhật dữ liệu liên tục. Để làm được điều này
// thì dĩ nhiên ứng dụng của bạn phải kết nối internet.
// Kết nối dữ liệu tới Server sử dụng thư viện Retrofit trong Android
// để kết nối dữ liệu tới server cần thông qua REST API, Retrofit sẽ giúp bạn giải quyết một cách hiệu quả.
// Retrofit được giới thiệu bởi Square như là một http-client và được cộng đồng phát triển android sử dụng rộng rãi.
// Lưu ý: Để hiệu quả hơn trong việc lập trình, mình giới thiệu thêm 1 số thư viện hỗ trợ cho Retrofit,
// chọn 1 trong các gói sau theo nhu cầu --(theo mình thì dùng GSON là đáp ứng đủ nhu cầu)--
// CÁC THƯ VIỆN:
// Gson: com.squareup.retrofit2:converter-gson
//Jackson: com.squareup.retrofit2:converter-jackson
//Moshi: com.squareup.retrofit2:converter-moshi
//Protobuf: com.squareup.retrofit2:converter-protobuf
//Wire: com.squareup.retrofit2:converter-wire
//Simple XML: com.squareup.retrofit2:converter-simplexml
//Scalars (primitives, boxed, and String): com.squareup.retrofit2:converter-scalars
//Và giờ gradle file sẽ như sau:
public class ApiClient {
    // Tạo hằng BASE_URL ko thay đổi
    // Class định nghĩa các hằng đối tượng
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static ApiClient apiClient;
    private static Retrofit retrofit;


    private ApiClient(){

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ApiClient getInstance(){
        if (apiClient == null){
            apiClient = new ApiClient();
        }
        return apiClient;
    }

    public ApiInterface getApi(){

        return retrofit.create(ApiInterface.class);
    }
}
// Xong hết  // Tạo đối tượng gọi để thực hiện Request
// Bên NowPlayingFragment

