package finaledit;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogServer {
 public static void main(String[] args) throws IOException {
     ServerSocket serverSocket = new ServerSocket(9999);
     System.out.println("Log server đang chạy...");

     while (true) {
         Socket client = serverSocket.accept();
         new Thread(() -> {
             try (
                 BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                 BufferedWriter writer = new BufferedWriter(new FileWriter("logs/log.txt", true));
             ) {
                 String line;
                 while ((line = in.readLine()) != null) {
                     String timestamp = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date());
                     writer.write(timestamp + " " + line);
                     writer.newLine();
                     writer.flush();
                     System.out.println(timestamp + " " + line);
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }).start();
     }
 }
}

