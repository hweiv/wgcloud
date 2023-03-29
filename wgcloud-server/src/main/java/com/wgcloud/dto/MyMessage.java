package com.wgcloud.dto;

import lombok.Data;

/**
 * @Author: JCccc
 * @Date: 2022-07-23 9:05
 * @Description:
 */
@Data
public class MyMessage {
 
    private String type;
 
    private String content;
 
    private String from;
 
    private String to;
 
    private String channel;
}