package com.wgcloud.thread;

import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class ClientSocketThread implements Runnable{
    // 存放磁盘状态
    private static Map<String, Boolean> map = new LinkedHashMap<String, Boolean>();
    // 定义磁盘
    private static final String[] arr = new String[]{"C", "D", "E", "F", "G", "H", "I", "J", "U"};
    private String host;

    private Integer port;

    public ClientSocketThread(String host, int port){
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        File file;
        for (String str : arr) {
            file = new File(str + ":\\");
            map.put(str, file.exists());
        }
        try {
            Socket socket = new Socket(host, port);
            OutputStream out = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(out);
//        File[] roots = File.listRoots();
//        File uDisk = new File("F:\\");
            File file1;
            while (true) {
//

                for (String str : arr) {
                    file1 = new File(str + ":\\");
//                    try {
//                        long start = System.currentTimeMillis();
//                        Stream<Path> walk = Files.walk(Paths.get(file.getAbsolutePath()));
//                        List<String> fileNames = new ArrayList<>();
//                        walk.filter(Files::isRegularFile).forEach(path -> fileNames.add(String.valueOf(path.getFileName())));
////                        List<Path> paths = walk.filter(Files::isRegularFile).collect(Collectors.toList());
////                        List<Path> paths = walk.collect(Collectors.toList());
//                        long end = System.currentTimeMillis();
//                        dos.writeUTF("插入的U盘/移动硬盘的所有可访问文件有：" + JSONUtil.toJsonStr(fileNames));
//                        dos.writeUTF("读取文件信息所花费时间为：" + (end - start) + "ms");
//                    } catch (IOException e) {
//                        log.error("the Cilent.class-check: error:{}", e);
//                    }

                    // 如果磁盘现在存在，并且以前不存在
                    // 则表示刚插上U盘，返回
                    if (file1.exists() && !map.get(str)) {
                        dos.writeUTF("U盘/移动硬盘插入：" + str);
                        File[] files = file1.listFiles();
                    }

                    if (!file1.exists() && map.get(str)) {
                        dos.writeUTF("U盘拔出：" + str);
//                    Stream<Path> walk = Files.walk(Paths.get(file.getAbsolutePath()));
//                    List<Path> paths = walk.filter(Files::isRegularFile).collect(Collectors.toList());
//                    dos.writeUTF("插入的U盘/移动硬盘的所有可访问文件有：" + JSONUtil.toJsonStr(paths));
                    }

                    // 需要每次状态改变时，更新保存的状态
                    // 如果刚检测的状态和原来的状态不一样，则重新更新状态
                    // 必须放上面的if语句下面
                    if (file1.exists() != map.get(str)) {
                        map.put(str, file1.exists());
                    }
                }

                try {
                    Thread.sleep(1 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            log.error("the Client.class-check: error:{}", e);
        }
    }
}
