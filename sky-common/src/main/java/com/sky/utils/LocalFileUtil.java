package com.sky.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 本地文件存储工具类
 */
@Component
@Slf4j
public class LocalFileUtil {

    /**
     * 本地存储根目录
     * Windows 示例：D:/sky-images/
     * Linux/Mac 示例：/home/sky/images/
     */
    private static final String BASE_PATH = "D:\\workplace\\Java\\sky_images\\";

    /**
     * 上传文件到本地磁盘
     *
     * @param bytes      文件字节数组
     * @param objectName 文件名（UUID + 后缀）
     * @return 可访问的 URL
     */
    public String upload(byte[] bytes, String objectName) throws IOException {
        // 1. 确保目录存在，不存在就创建
        File dir = new File(BASE_PATH);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            log.info("创建目录 {}，结果：{}", BASE_PATH, created);
        }

        // 2. 写入文件
        File file = new File(BASE_PATH + objectName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
        }

        log.info("文件已保存到本地：{}", file.getAbsolutePath());

        // 3. 返回可访问的 URL
        // 对应 WebMvcConfiguration 里 /images/** 的映射
        return "http://localhost:8080/images/" + objectName;
    }
}