package com.lihui.security_office_backend.controller;

import com.lihui.security_office_backend.annotation.AuthCheck;
import com.lihui.security_office_backend.constant.UserConstant;
import com.lihui.security_office_backend.utils.FileUploadUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 文件上传控制器
 */
@Deprecated
@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Resource
    private FileUploadUtils fileUploadUtils;

    /**
     * 上传单个文件，并指定文件类型
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String fileTypeStr) {
        try {
            String fileUrl = fileUploadUtils.uploadFile(file, fileTypeStr);
            return ResponseEntity.ok("文件上传成功，访问路径：" + fileUrl);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("无效的文件类型：" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("文件上传失败：" + e.getMessage());
        }
    }

    /**
     * 上传多个文件，并指定文件类型
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)

    @PostMapping("/upload-multiple")
    public ResponseEntity<String[]> uploadMultipleFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("type") String fileTypeStr) {
        try {
            String[] fileUrls = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                fileUrls[i] = fileUploadUtils.uploadFile(files[i], fileTypeStr);
            }
            return ResponseEntity.ok(fileUrls);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new String[]{"无效的文件类型：" + e.getMessage()});
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new String[]{"文件上传失败：" + e.getMessage()});
        }
    }
}    