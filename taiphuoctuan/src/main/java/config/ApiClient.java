package config;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.time.Duration;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import utils.CookieFileUtil;

public class ApiClient {
    private final HttpClient httpClient;
    private final String baseUrl = "https://cntttest.vanlanguni.edu.vn:18081";
    private final Gson gson = new Gson();

    public ApiClient() {
        httpClient = createHttpClient();
    }

    /**
     * Tạo HttpClient bỏ qua xác thực SSL
     */
    private HttpClient createHttpClient() {
        try {
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

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCertificates, new SecureRandom());

            return HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Không thể tạo HttpClient với SSL tùy chỉnh", e);
        }
    }

    /**
     * Phương thức chung để gọi API
     * 
     * @param endpoint     Đường dẫn API
     * @param responseType Kiểu dữ liệu mong muốn (dùng TypeToken của Gson)
     * @return Dữ liệu API trả về
     */
    public <T> T callApi(String endpoint, TypeToken<T> responseType) throws IOException, InterruptedException {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String url = baseUrl + endpoint + "?_=" + timestamp;
        String cookieValue = CookieFileUtil.getCookieFromFile();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json, text/javascript, */*; q=0.01")
                .header("cookie", cookieValue)
                .header("referer", baseUrl + endpoint)
                .header("user-agent",
                        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36")
                .header("x-requested-with", "XMLHttpRequest")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return gson.fromJson(response.body(), responseType.getType());
        } else {
            System.out.println("API call failed: " + response.statusCode());
            System.out.println("Response: " + response.body());
            throw new IOException("API call failed with status code: " + response.statusCode());
        }
    }

    /**
     * Gọi API lấy dữ liệu học vị
     */
    public List<AcademicDegree> getAcademicDegreeData() throws IOException, InterruptedException {
        return callApi("/Phancong02/AcademicDegree/GetData", new TypeToken<List<AcademicDegree>>() {
        });
    }

    /**
     * Gọi API lấy dữ liệu giảng viên
     */
    public List<Lecturer> getLecturerData() throws IOException, InterruptedException {
        return callApi("/Phancong02/Lecturer/GetData", new TypeToken<List<Lecturer>>() {
        });
    }

    public List<Term> getTermData() throws IOException, InterruptedException {
        return callApi("/Phancong02/Term/GetData", new TypeToken<List<Term>>() {
        });
    }

    // Định nghĩa lớp AcademicDegree
    public static class AcademicDegree {
        private String id;
        private String name;
        private Number level;
        private boolean isActive;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Number getLevel() {
            return level;
        }

        public boolean isActive() {
            return isActive;
        }

        @Override
        public String toString() {
            return "{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", Level='" + level + '\'' +
                    ", isActive=" + isActive +
                    '}';
        }
    }

    // Định nghĩa lớp Lecturer (Giảng viên)
    public static class Lecturer {
        private String id;
        private String fullName;
        private String department;

        public String getId() {
            return id;
        }

        public String getFullName() {
            return fullName;
        }

        public String getDepartment() {
            return department;
        }

        @Override
        public String toString() {
            return "Lecturer{" +
                    "id=" + id +
                    ", fullName='" + fullName + '\'' +
                    ", department='" + department + '\'' +
                    '}';
        }
    }

    // Định nghĩa lớp Term (Học kỳ)
    public static class Term {
        private String id;
        private int start_year;
        private int end_year;
        private int max_class;
        private int max_lesson;
        private String start_date;
        private int start_week;
        private boolean status;

        public String getId() {
            return id;
        }

        public int getStartYear() {
            return start_year;
        }

        public int getEndYear() {
            return end_year;
        }

        public int getMaxClass() {
            return max_class;
        }

        public int getMaxLesson() {
            return max_lesson;
        }

        public String getStartDate() {
            return start_date;
        }

        public int getStartWeek() {
            return start_week;
        }

        public boolean isStatus() {
            return status;
        }

        @Override
        public String toString() {
            return "{" +
                    "id=" + id +
                    ", startYear=" + start_year +
                    ", endYear=" + end_year +
                    ", maxClass=" + max_class +
                    ", maxLesson=" + max_lesson +
                    ", startDate='" + start_date + '\'' +
                    ", startWeek=" + start_week +
                    ", status=" + status +
                    '}';
        }
    }

    // Hàm main để thử nghiệm
    // public static void main(String[] args) {
    //     try {
    //         ApiClient client = new ApiClient();

    //         // // Gọi API lấy danh sách học vị
    //         List<AcademicDegree> degrees = client.getAcademicDegreeData();
    //         System.out.println("\n📌 Danh sách học vị:");
    //         degrees.forEach(System.out::println);

    //         // // Gọi API lấy danh sách giảng viên
    //         List<Lecturer> lecturers = client.getLecturerData();
    //         System.out.println("\n📌 Danh sách giảng viên:");
    //         lecturers.forEach(System.out::println);

    //         // Gọi API lấy danh sách học kỳ
    //         List<Term> terms = client.getTermData();
    //         System.out.println("\n📌 Danh sách học kỳ:");
    //         terms.forEach(System.out::println);

    //     } catch (Exception e) {
    //         System.err.println("❌ Lỗi khi gọi API: " + e.getMessage());
    //         e.printStackTrace();
    //     }
    // }
}
