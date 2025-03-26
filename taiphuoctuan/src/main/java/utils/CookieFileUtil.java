package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CookieFileUtil {
    public static String getCookieFromFile() {
        StringBuilder cookieValue = new StringBuilder();
        try (InputStream inputStream = CookieFileUtil.class.getClassLoader().getResourceAsStream("cookie.data");
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream == null) {
                System.err.println("⚠️ File cookie.data không tồn tại!");
                return ""; // Trả về chuỗi rỗng nếu file không tồn tại
            }

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(";");
                    if (cookieValue.length() > 0) {
                        cookieValue.append(";"); // Nối cookie đúng chuẩn
                    }
                    if (parts.length == 6) {
                        String name = parts[0];
                        String value = parts[1];
                        cookieValue.append(name).append("=").append(value);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cookieValue.toString();
    }
}
