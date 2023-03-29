package com.wgcloud.upan;

import com.wgcloud.ClientUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CustomEventListener implements ApplicationListener<CustomEvent> {
    // 存放磁盘状态
    private static Map<String, Boolean> map = new LinkedHashMap<String, Boolean>();
    // 定义磁盘
    private static final String[] arr = new String[]{"C", "D", "E", "F", "G", "H", "I", "J", "U"};
    @Override
    public void onApplicationEvent(CustomEvent customEvent) {
        ClientUtil.init();
        ClientUtil.check();
        // 执行代码逻辑
        System.out.println("接收到自定义事件：" + customEvent.getMessage());
    }
}