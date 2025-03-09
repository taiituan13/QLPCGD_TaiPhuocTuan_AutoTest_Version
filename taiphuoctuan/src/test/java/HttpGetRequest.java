import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetRequest {
    public static void main(String[] args) {
        try {
            String apiUrl = "https://cntttest.vanlanguni.edu.vn:18081/Phancong02/AcademicDegree/GetData?_=1741342050583";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Thiết lập phương thức GET
            connection.setRequestMethod("GET");

            // Thiết lập headers
            connection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
            connection.setRequestProperty("Accept-Language", "vi-VN,vi;q=0.9,en-US;q=0.8,en;q=0.7");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36");
            connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            connection.setRequestProperty("Referer", "https://cntttest.vanlanguni.edu.vn:18081/Phancong02/AcademicDegree");
            connection.setRequestProperty("Cookie", "__RequestVerificationToken_L1BoYW5jb25nMDI1=..."); // Thay bằng cookie thật của bạn
            connection.setRequestProperty("sec-fetch-mode", "cors");
            connection.setRequestProperty("sec-fetch-site", "same-origin");

            // Bỏ qua SSL nếu cần (cho kết nối HTTPS không an toàn)
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            // Đọc phản hồi
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // In kết quả
            System.out.println("Response: " + response.toString());

            // Đóng kết nối
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
