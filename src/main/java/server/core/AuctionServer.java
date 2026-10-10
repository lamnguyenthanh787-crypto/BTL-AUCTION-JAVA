package server.core;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuctionServer {
    private static final int PORT = 8080;
    // Sử dụng CachedThreadPool để tự động cấp phát luồng cho mỗi Client
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        System.out.println("[SERVER] Đang khởi động...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[SERVER] Đang lắng nghe tại cổng " + PORT);

            while (true) {
                // Chờ và chấp nhận kết nối từ Client mới
                Socket clientSocket = serverSocket.accept();

                // Giao Socket của Client này cho một luồng (Thread) riêng xử lý
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                threadPool.execute(clientHandler);
            }
        } catch (Exception e) {
            System.err.println("[SERVER] Lỗi máy chủ: " + e.getMessage());
        }
    }
}