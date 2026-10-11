package client.controllers; // Đổi tên package nếu bạn đặt ở chỗ khác

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneManager {
    // 1. Áp dụng Singleton Pattern (chỉ có duy nhất 1 instance tồn tại)
    private static SceneManager instance;
    private Stage primaryStage;

    private SceneManager() {
        // Private constructor để ngăn chặn việc tạo mới bằng từ khóa 'new'
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    // 2. Hàm khởi tạo (truyền cửa sổ gốc vào đây)
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    // 3. Hàm chuyển màn hình siêu tiện lợi
    public void switchScene(String fxmlPath) {
        try {
            URL fxmlLocation = getClass().getResource(fxmlPath);
            if (fxmlLocation == null) {
                System.err.println("Không tìm thấy file FXML tại đường dẫn: " + fxmlPath);
                return;
            }

            Parent root = FXMLLoader.load(fxmlLocation);
            Scene scene = new Scene(root); // Tạo cảnh mới với giao diện mới

            primaryStage.setScene(scene);
            primaryStage.centerOnScreen(); // Tự động căn giữa màn hình
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Lỗi khi load màn hình " + fxmlPath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}