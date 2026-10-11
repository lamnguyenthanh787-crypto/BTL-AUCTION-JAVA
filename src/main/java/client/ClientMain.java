package client;

import client.controllers.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cài đặt thông số cơ bản cho cửa sổ
        primaryStage.setTitle("Hệ thống Đấu giá Trực tuyến");
        primaryStage.setResizable(false); // Không cho phóng to thu nhỏ

        // Khởi tạo SceneManager và đưa cho nó quản lý cái Stage (cửa sổ gốc) này
        SceneManager.getInstance().setPrimaryStage(primaryStage);

        // Ra lệnh cho SceneManager load màn hình Đăng nhập đầu tiên
        SceneManager.getInstance().switchScene("/views/LoginView.fxml");
    }

    public static void main(String[] args) {
        // Khởi động JavaFX
        launch(args);
    }
}