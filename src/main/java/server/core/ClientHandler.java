package server.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            System.out.println("[SERVER] Đã kết nối với Client: " + clientSocket.getInetAddress());

            String clientMessage;
            // Liên tục lắng nghe tin nhắn từ Client
            while ((clientMessage = in.readLine()) != null) {
                System.out.println("[SERVER] Nhận được từ Client: " + clientMessage);

                // Phản hồi lại Client
                out.println("Server đã xử lý thành công yêu cầu: " + clientMessage);
            }
        } catch (Exception e) {
            System.out.println("[SERVER] Lỗi kết nối với Client: " + e.getMessage());
        } finally {
            System.out.println("[SERVER] Client đã ngắt kết nối.");
        }
    }
}