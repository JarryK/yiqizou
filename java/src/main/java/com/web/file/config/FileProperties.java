package com.web.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "file")
public class FileProperties {

    /** 文件大小限制 单位KB*/
    private Long maxSize;

    /** 头像大小限制 单位KB*/
    private Long avatarMaxSize;

    private ElPath mac;

    private ElPath linux;

    private ElPath windows;

    public String getPath(){
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")) {
            return windows.path;
        } else if(os.toLowerCase().startsWith("mac")){
            return mac.path;
        }
        return linux.path;
    }
    
    public String getAvatarPath(){
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")) {
            return windows.avatar;
        } else if(os.toLowerCase().startsWith("mac")){
            return mac.avatar;
        }
        return linux.avatar;
    }

    @Data
    public static class ElPath{

    	// 基础路径
        private String path;

        // 头像路径
        private String avatar;
    }
}