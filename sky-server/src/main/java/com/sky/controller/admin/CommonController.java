package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.LocalFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口
 */
@RestController
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {
    @Autowired
    private LocalFileUtil localFileUtil;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}", file);
        try {
            // 1. 拿到原始文件名，如 "abc.jpg"
            String originalFilename = file.getOriginalFilename();

            // 2. 截取后缀，如 ".jpg"
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // 3. 生成新文件名，防止重名覆盖
            String objectName = UUID.randomUUID().toString() + extension;

            // 4. 上传到本地，返回 URL
            String url = localFileUtil.upload(file.getBytes(), objectName);

            // 5. 返回 URL
            return Result.success(url);
        } catch (IOException e) {
            log.error("文件上传失败：{}", e);
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
