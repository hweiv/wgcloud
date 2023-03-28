package com.wgcloud.upan;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Configuration
public class Client {
    // 存放磁盘状态
    private static Map<String, Boolean> map = new LinkedHashMap<String, Boolean>();
    // 定义磁盘
    private static final String[] arr = new String[]{"C", "D", "E", "F", "G", "H", "I", "J", "U"};
//    public static void main(String[] args) throws IOException {
//        init();
//        check();
//    }

    public static void check() {
        try {
            Socket socket = new Socket("127.0.0.1", 9888);
            OutputStream out = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(out);
//        File[] roots = File.listRoots();
//        File uDisk = new File("F:\\");
            File file;
            while (true) {
//            if (uDisk.exists()) {
//                dos.writeUTF("U盘插入：" + uDisk.getAbsolutePath());
//            } else {
//                dos.writeUTF("U盘拔出");
//            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

                for (String str : arr) {
                    file = new File(str + ":\\");

                    // 如果磁盘现在存在，并且以前不存在
                    // 则表示刚插上U盘，返回
                    if (file.exists() && !map.get(str)) {
//                    return;
                        dos.writeUTF("U盘/移动硬盘插入：" + str);
                        File[] files = file.listFiles();
                        // 时间太长
                    /*
                    try {
                        long start = System.currentTimeMillis();
                        Stream<Path> walk = Files.walk(Paths.get(file.getAbsolutePath()));
                        List<Path> paths = walk.filter(Files::isRegularFile).collect(Collectors.toList());
                        long end = System.currentTimeMillis();
                        dos.writeUTF("插入的U盘/移动硬盘的所有可访问文件有：" + JSONUtil.toJsonStr(paths));
                        dos.writeUTF("读取文件信息所花费时间为：" + (end - start) + "ms");
                    } catch (IOException e) {
                        log.error("the Cilent.class-check: error:{}", e);
                    }

                     */
                    }

                    if (!file.exists() && map.get(str)) {
                        dos.writeUTF("U盘拔出：" + str);
//                    Stream<Path> walk = Files.walk(Paths.get(file.getAbsolutePath()));
//                    List<Path> paths = walk.filter(Files::isRegularFile).collect(Collectors.toList());
//                    dos.writeUTF("插入的U盘/移动硬盘的所有可访问文件有：" + JSONUtil.toJsonStr(paths));
                    }

                    // 需要每次状态改变时，更新保存的状态
                    // 如果刚检测的状态和原来的状态不一样，则重新更新状态
                    // 必须放上面的if语句下面
                    if (file.exists() != map.get(str)) {
                        map.put(str, file.exists());
                    }
                }

                try {
                    Thread.sleep(1 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            log.error("the Cilent.class-check: error:{}", e);
        }
    }

    // 初始化磁盘状态，存在true， 否则false
    public static void init() {
        File file;
        for (String str : arr) {
            file = new File(str + ":\\");
            map.put(str, file.exists());
        }
    }
}