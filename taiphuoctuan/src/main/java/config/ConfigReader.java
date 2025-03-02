package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    // Khởi tạo Properties khi class được load
    static {
        try {
            FileInputStream file = new FileInputStream("src/main/resources/config.properties"); // Đường dẫn đến file config
            properties = new Properties();
            properties.load(file);
            file.close(); // Đóng file sau khi đọc xong
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Không thể tải file config.properties");
        }
    }

    // Phương thức lấy giá trị theo key
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
