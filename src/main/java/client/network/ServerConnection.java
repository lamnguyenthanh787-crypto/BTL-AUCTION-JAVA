package client.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerConnection {
    private static final String SERVER_IP = "127.0.0.1"; // Kết nối tới máy cục bộ (localhost)
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("[CLIENT] Đã kết nối tới Server Đấu giá thành công!");

            // Giả lập luồng gửi request: Người dùng gõ lệnh, hệ thống đẩy qua Socket
            System.out.print("[CLIENT] Nhập dữ liệu để gửi lên Server (VD: LOGIN_REQUEST): ");
            String messageToSend = scanner.nextLine();

            out.println(messageToSend); // Gửi tới Server

            // Chờ nhận phản hồi từ Server
            String response = in.readLine();
            System.out.println("[CLIENT] Phản hồi từ Server: " + response);

        } catch (Exception e) {
            System.err.println("[CLIENT] Không thể kết nối tới Server: " + e.getMessage());
        }
    }
}
