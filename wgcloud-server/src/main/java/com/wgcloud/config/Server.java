package com.wgcloud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.*;

@Configuration
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9888);
        Socket socket = server.accept();
        InputStream in = socket.getInputStream();
        DataInputStream dis = new DataInputStream(in);
        // 输出文件全路径
        String filePath = "F:\\Listener_log.txt";
        while (true) {
            String message = dis.readUTF();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
            bufferedWriter.write(message);
            System.out.println(message);
        }
    }

//    public static void init() {
//        try {
//            ServerSocket server = new ServerSocket(9888);
//            Socket socket = server.accept();
//            InputStream in = socket.getInputStream();
//            DataInputStream dis = new DataInputStream(in);
//            while (true) {
//                String message = dis.readUTF();
//                log.info("the serverListener get msg:{}", message);
//            }
//        } catch (IOException e) {
//            log.error("the Cilent.class-check: error:{}", e);
//        }
//    }
}