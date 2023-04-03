package com.wgcloud.upan;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;

import com.wgcloud.CommonConfig;
import com.wgcloud.OshiUtil;
import com.wgcloud.RestUtil;
import com.wgcloud.entity.LogInfo;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Slf4j
public class CustomEventListener implements ApplicationListener<ContextRefreshedEvent> {
    // 存放磁盘状态
    private static Map<String, Boolean> map = new LinkedHashMap<String, Boolean>();
    // 定义磁盘
    private static final String[] arr = new String[]{"C", "D", "E", "F", "G", "H", "I", "J", "U"};
    @Autowired
    private RestUtil restUtil;
    @Autowired
    private CommonConfig commonConfig;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent customEvent) {
        log.info("CustomEventListener-onApplicationEvent run");
        init();
        check();
        // 执行代码逻辑
        System.out.println("接收到自定义事件：");
    }

    // 初始化磁盘状态，存在true， 否则false
    public void init() {
        File file;
        for (String str : arr) {
            file = new File(str + ":\\");
            map.put(str, file.exists());
        }
    }

    public void check() {
        LogInfo logInfo = new LogInfo();
        logInfo.setCreateTime(new Date());
        logInfo.setState("2");
        logInfo.setHostname(OshiUtil.getHostIp() + "  mac:" + OshiUtil.getHostMac());
        JSONObject jsonObject = new JSONObject();
        File file;
        while (true) {
            for (String str : arr) {
                file = new File(str + ":\\");
                // 如果磁盘现在存在，并且以前不存在
                // 则表示刚插上U盘，返回
                if (file.exists() && !map.get(str)) {
                    String msg = "检测到U盘/移动硬盘插入：" + str;
                    logInfo.setInfoContent(msg + "， 事件名称：检测U盘/硬盘插入！！！");
                    log.info("CustomEventListener-check.info:{}", JSONUtil.toJsonStr(logInfo));
                    jsonObject.put("loginfo", logInfo);
                    restUtil.post(commonConfig.getServerUrl() + "/wgcloud/log/save", jsonObject);
                }
                if (!file.exists() && map.get(str)) {
                    String msg = "检测到U盘/移动硬盘拔出：" + str;
                    logInfo.setInfoContent(msg + "， 事件名称：检测U盘/硬盘拔出！");
                    log.info("CustomEventListener-check.info:{}", JSONUtil.toJsonStr(logInfo));
                    jsonObject.put("loginfo", logInfo);
                    restUtil.post(commonConfig.getServerUrl() + "/wgcloud/log/save", jsonObject);
                }
                // 需要每次状态改变时，更新保存的状态
                // 如果刚检测的状态和原来的状态不一样，则重新更新状态
                // 必须放上面的if语句下面
                if (file.exists() != map.get(str)) {
                    map.put(str, file.exists());
                }
            }
        }
    }
}