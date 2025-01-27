package com.paper.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.paper.common.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;



/**
 * @author 林万奇
 * @since 2025-01-27
 */
@RestController
@RequestMapping("/files")
public class FileController {
    // 日志记录
    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    // 初始化文件路径：用户路径+files
    private static final String filePath = System.getProperty("user.dir") + "/files/";
    // 获取yml配置文件中的fileBaseUrl
    @Value("${fileBaseUrl:}")
    private String fileBaseUrl;


    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        try {
            // 没有目录则创建目录
            if (FileUtil.isDirectory(filePath)) {
                FileUtil.mkdir(filePath);
            }
            fileName = System.currentTimeMillis() + "-" + fileName;
            String realFilePath = filePath + fileName;
            // 文件存储形式：时间戳-文件名
            FileUtil.writeBytes(file.getBytes(), realFilePath);
        } catch (IOException e) {
            log.error("{}--文件上传失败", fileName, e);
        }
        String url = fileBaseUrl + "/files/download/" + fileName;
        return Result.success(url);
    }


    @GetMapping("/download/${fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) {
        OutputStream os;
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(filePath + fileName);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            log.warn("文件下载失败：" + fileName);
        }
    }
}



