package client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    @FXML
    void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // 1. Kiểm tra rỗng
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!");
            return;
        }

        // Xóa thông báo lỗi cũ, vô hiệu hóa nút bấm tránh spam click
        errorLabel.setText("");
        loginButton.setDisable(true);

        /* Giai đoạn Gửi (Client Sends Request)
           Tạm thời dùng String ghép. Sau này khi thêm GSON,
           bạn thay bằng: String messageToSend = gson.toJson(new NetworkMessage("LOGIN", new LoginRequest(...)))
        */
        String messageToSend = "LOGIN_REQUEST_" + username + "_" + password;

        // 2. Mở một luồng (Thread) chạy ngầm để không làm đơ giao diện JavaFX
        new Thread(() -> {
            try (Socket socket = new Socket("127.0.0.1", 8080);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                // Gửi dữ liệu qua TCP/IP
                out.println(messageToSend);

                // Chờ và đọc phản hồi từ Server
                String response = in.readLine();

                // 3. Giai đoạn Cập nhật giao diện (Bắt buộc dùng Platform.runLater)
                Platform.runLater(() -> {
                    if (response != null && response.contains("thành công")) {
                        errorLabel.setTextFill(Color.GREEN);
                        errorLabel.setText("Đăng nhập thành công! Đang chuyển trang...");

                        // TODO: Viết code chuyển sang màn hình LobbyView.fxml tại đây

                    } else {
                        errorLabel.setTextFill(Color.RED);
                        errorLabel.setText("Đăng nhập thất bại: " + response);
                        loginButton.setDisable(false); // Mở lại nút bấm
                    }
                });

            } catch (Exception e) {
                // Xử lý khi Server chưa mở hoặc lỗi mạng
                Platform.runLater(() -> {
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setText("Lỗi kết nối tới máy chủ! Vui lòng thử lại.");
                    loginButton.setDisable(false);
                });
            }
        }).start();
    }
}