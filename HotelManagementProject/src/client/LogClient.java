package client;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogClient {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 9999; 
    private static final String LOG_FILE = "log.txt";

    public static void send(String message) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            writer.println(message); 

            try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
                String time = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(new Date());
                fileWriter.write("[" + time + "] " + message);
                fileWriter.newLine();
            }

        } catch (IOException e) {
            System.err.println("Lỗi ghi log: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        while (true) {
            send(" Log test: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
