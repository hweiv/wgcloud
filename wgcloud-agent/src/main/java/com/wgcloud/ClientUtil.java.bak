package com.wgcloud;

import cn.hutool.json.JSONUtil;
import com.wgcloud.upan.CustomEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
public class ClientUtil {

    // 存放磁盘状态
    private static Map<String, Boolean> map = new LinkedHashMap<String, Boolean>();
    // 定义磁盘
    private static final String[] arr = new String[]{"C", "D", "E", "F", "G", "H", "I", "J", "U"};

    @Autowired
    private static ApplicationContext applicationContext;

    @Value("${target.service.host}")
    private static String host;

    @Value("${target.service.port}")
    private static Integer port;
    public static void check() {
        try {
            Socket socket = new Socket(host, port);
            OutputStream out = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(out);
            File file;
            while (true) {
                for (String str : arr) {
                    file = new File(str + ":\\");

                    // 如果磁盘现在存在，并且以前不存在
                    // 则表示刚插上U盘，返回
                    if (file.exists() && !map.get(str)) {
                        CustomEvent customEvent = new CustomEvent(this, "Hello, World!");
                        applicationContext.publishEvent(customEvent);
                        dos.writeUTF("U盘/移动硬盘插入：" + str);
                        File[] files = file.listFiles();
                    }

                    if (!file.exists() && map.get(str)) {
                        dos.writeUTF("U盘拔出：" + str);
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
            log.error("the Client.class-check: error:{}", e);
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
