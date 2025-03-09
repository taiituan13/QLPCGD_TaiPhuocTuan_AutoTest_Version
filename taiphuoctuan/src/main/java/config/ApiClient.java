package config;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.Duration;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class ApiClient {
    private final HttpClient httpClient;
    private final String baseUrl = "https://cntttest.vanlanguni.edu.vn:18081";
    private final Gson gson = new Gson();
    
    public ApiClient() {
        // Khởi tạo HttpClient với cấu hình bỏ qua xác thực SSL
        httpClient = createHttpClient();
    }
    
    /**
     * Tạo HttpClient bỏ qua xác thực SSL
     */
    private HttpClient createHttpClient() {
        try {
            // Tạo TrustManager bỏ qua xác thực chứng chỉ
            TrustManager[] trustAllCertificates = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                    
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }
                    
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
            };
            
            // Tạo SSLContext với TrustManager tùy chỉnh
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCertificates, new SecureRandom());
            
            // Trả về HttpClient với cấu hình SSL
            return HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
                    
        } catch (Exception e) {
            throw new RuntimeException("Không thể tạo HttpClient với SSL tùy chỉnh", e);
        }
    }
    
    /**
     * Lấy dữ liệu học vị từ API
     * @return Danh sách dữ liệu học vị
     * @throws IOException
     * @throws InterruptedException
     */
    public List<AcademicDegree> getAcademicDegreeData() throws IOException, InterruptedException {
        // Tạo timestamp tương tự như trong cURL
        String timestamp = String.valueOf(System.currentTimeMillis());
        String endpoint = "/Phancong02/AcademicDegree/GetData?_=" + timestamp;
        
        // Tạo request với các header từ cURL
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + endpoint))
                .header("accept", "application/json, text/javascript, */*; q=0.01")
                .header("accept-language", "vi-VN,vi;q=0.9,en-US;q=0.8,en;q=0.7,zh-CN;q=0.6,zh;q=0.5")
                .header("cookie", "__RequestVerificationToken_L1BoYW5jb25nMDI1=QiISmVJhRzbjH98AXVXduEd5t50RpWyXLf3u0VaD-JqOLoohv-N8EhmQfL0QsQXTX3y50a5QGdBsOCkEUCR8UiSqfV-71R8rbwy4RPvjE001; .AspNet.Cookies=V0Q-q99sNUVc5-Tmw543K3sR6yXEEt-VXqR9BFCMVhbm5WiWI7oWhoXBvxY3Fo65ZjckR_Ckg7zORF6vfqfK-_8Q5WVOdtg7RLFckRtQ3lQwjLtQmRz7uOzzadLp3ovfPwWx2n_fVuE6QKVizyxI7SfmtqhS_OQhTB193nsCOXmyB1vtm721Q6fuhcvygs1fWl1zWoGBjXcu0AKHv3RKr9Df8rqtKG-n6smvmIpjy_B0S8ddyZcLS6a2iY7Bptx_FRXjnxZbDMMWNkBs3Cj-rmMSnufusFaS6YF9_1veyD7FUd9_1eFPpvJU_7diwDX2ZYXeRatIAw5qg6CczWP2o02UFFFfCHwASUc_ZWLp9uA7HTBCdNkS9D_eG7scDoTnzhW86xQF0ZJC_VMYzkjaEO_ktGr29ZlAb6Pi97GLik_De4YxDs2wgevzg_mogOTPz-7Qc6wEK9jB2OMv1gG1AN5zKiQFCPWHRmVbkQFSnPQ1aAKzq2-xm0krczi_yNLL59WhkHQRTyQEufbS5c4BGdR1KV_orEc1h-jpn2RIPHz9EgsfAsUH2qGaGZHSkhdt9owddBAQnRzEgjllUTv-TdQwgakTja8bMIO0mIkeQPV8X3d6Y3g084U6X2RiXtl6Mhnc-FGWvA-7l4-QApq_wYkWbl7M7_48MNSU-Q4R_bU8VRdbV_wh7X18ecf9L_aDcojxBpJ9J60bSZTM59bmEZeh87zK3YfAdWwOrtf1YZ4GOyPu6xQ-JFvmNMMlJ2BANfBcQbcNtxp-NtWOfPjpv-cZjxwc7HeqqWy33sGTHp3yAa8ls0HRQXtc_M4-OCbKt7G5y_L99E0Ebk1ipC_gGPfGyG5sAm57C99kH97Sv1A2N6xg5EhhdXAC4xVrQUdZ0ly8H6uRHRD4WHG_kvTTPXHUMplU5uPREoxsTHACZpbgpp2q6s4NA6Vm-zEqYtG-vI00ts_OalunXHKunxSRoOS32BFUewLjynjkn6efYzgHpelzQJozYxX1_A7G2_IEWvItEHdoB0B0kvVN1fep7FhCLyv2JlprTj3ErrVdS9h3izos__V914yvTaEQ_EcGXK0ATd-KS2QqL2SbZq3azgPNRkqIGC3zL8QWb8TqKt6mjCzxizMMBPp4aBEav14hdl_8tK1MvPZprTxjKrgZ1O6KX5nm5AEftjBUo3qlbv8hng9G7ZHv6SbZSA6SSdk9MkL8xuV7ieSgpTvd7D2xKa0QXYVSaybTsemedX9gH6zQjGs4C32EpepSkla-jRl-i8gJ3XvTFWzQ03oDixmlwD4B03yYS8lKnqyfCki7od79NsQE_-OaFS84gbcrWlru1XhMQtsueFuNhLMzx2O9sw")
                .header("priority", "u=1, i")
                .header("referer", baseUrl + "/Phancong02/AcademicDegree")
                .header("sec-ch-ua", "\"Not A(Brand\";v=\"8\", \"Chromium\";v=\"132\", \"Google Chrome\";v=\"132\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "\"Linux\"")
                .header("sec-fetch-dest", "empty")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "same-origin")
                .header("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36")
                .header("x-requested-with", "XMLHttpRequest")
                .GET()
                .build();
        
        // Gửi request và nhận phản hồi
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        // Kiểm tra kết quả
        if (response.statusCode() == 200) {
            // Chuyển đổi JSON thành mảng đối tượng Java
            AcademicDegree[] degrees = gson.fromJson(response.body(), AcademicDegree[].class);
            return Arrays.asList(degrees);
        } else {
            throw new IOException("API call failed with status code: " + response.statusCode());
        }
    }
    
    // Class đại diện cho dữ liệu học vị
    public static class AcademicDegree {
        private String id;
        private String name;
        
        @SerializedName("ShortName")
        private String shortName;
        
        @SerializedName("IsActive")
        private boolean isActive;
        
        public String getId() {
            return id;
        }
        
        public String getName() {
            return name;
        }
        
        public String getShortName() {
            return shortName;
        }
        
        public boolean isActive() {
            return isActive;
        }
        
        @Override
        public String toString() {
            return "AcademicDegree{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", shortName='" + shortName + '\'' +
                    ", isActive=" + isActive +
                    '}';
        }
    }
    
    // Hàm main để thử nghiệm
    public static void main(String[] args) {
        try {
            ApiClient client = new ApiClient();
            List<AcademicDegree> degrees = client.getAcademicDegreeData();
            
            System.out.println("Danh sách học vị:");
            for (AcademicDegree degree : degrees) {
                System.out.println(degree.getId() + ": " + degree.getName() + 
                        " (" + degree.getShortName() + ") - Trạng thái: " + 
                        (degree.isActive() ? "Đang sử dụng" : "Không sử dụng"));
            }
        } catch (Exception e) {
            System.err.println("Đã xảy ra lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }
}