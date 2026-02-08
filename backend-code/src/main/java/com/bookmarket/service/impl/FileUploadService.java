package com.bookmarket.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.bookmarket.utils.OssClientSingleton;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileUploadService {

    public String uploadImage(MultipartFile file, String folder) throws IOException {
        // 验证文件类型
        if (!isValidImageType(file.getContentType())) {
            throw new IllegalArgumentException("不支持的图片格式");
        }
        
        // 验证文件大小（5MB限制）
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("文件大小不能超过5MB");
        }
        
        // 生成唯一的文件名
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8) + extension;
        
        // 构建OSS对象键
        String objectKey = folder + "/" + fileName;
        
        // 获取OSS客户端实例
        OSS ossClient = OssClientSingleton.getInstance();
        
        try {
            // 创建PutObjectRequest对象
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                "ws-bookmarket", // Bucket名称
                objectKey,      // 对象键
                file.getInputStream() // 输入流
            );

            // 执行上传
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            
            // 返回访问URL
            String fileUrl = "https://ws-bookmarket.oss-cn-wuhan-lr.aliyuncs.com/" + objectKey;
            
            System.out.println("文件上传成功！ObjectKey: " + objectKey);
            System.out.println("ETag: " + result.getETag());
            
            return fileUrl;
            
        } catch (Exception e) {
            System.err.println("文件上传失败: " + e.getMessage());
            throw new RuntimeException("文件上传失败", e);
        }
    }
    
    /**
     * 验证图片文件类型
     */
    private boolean isValidImageType(String contentType) {
        if (contentType == null) {
            return false;
        }
        return contentType.equalsIgnoreCase("image/jpeg") ||
               contentType.equalsIgnoreCase("image/jpg") ||
               contentType.equalsIgnoreCase("image/png") ||
               contentType.equalsIgnoreCase("image/gif") ||
               contentType.equalsIgnoreCase("image/webp");
    }

}