package com.bookmarket.controller;

import com.bookmarket.dto.response.Result;
import com.bookmarket.service.impl.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
public class UploadController {

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 通用文件上传接口
     */
    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam(value = "type", defaultValue = "images") String type) {
        try {
            // 验证文件
            if (file.isEmpty()) {
                return Result.error("请选择要上传的文件");
            }

            // 根据类型确定存储目录
            String folder = getFileFolder(type);

            // 上传文件
            String fileUrl = fileUploadService.uploadImage(file, folder);

            return Result.success("上传成功",fileUrl);

        } catch (IOException e) {
            System.err.println("文件上传IO异常: " + e.getMessage());
            return Result.error("文件上传失败：" + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("文件验证失败: " + e.getMessage());
            return Result.error(e.getMessage());
        } catch (Exception e) {
            System.err.println("文件上传异常: " + e.getMessage());
            return Result.error("文件上传失败：" + e.getMessage());
        }
    }

    /**
     * 根据文件类型确定存储目录
     */
    private String getFileFolder(String type) {
        return switch (type.toLowerCase()) {
            case "image", "images" -> "images";
            case "avatar", "avatars" -> "avatars";
            case "cover", "covers" -> "covers";
            case "document", "documents" -> "documents";
            default -> "files";
        };
    }
}