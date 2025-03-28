package com.lihui.security_office_backend.controller;

import com.lihui.security_office_backend.model.dto.content.ContentAddRequest;
import com.lihui.security_office_backend.service.ContentService;
import com.lihui.security_office_backend.utils.FileUploadUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Resource
    private ContentService contentService;
    @Resource
    private FileUploadUtils fileUploadUtils;

    @PostMapping("/add")
    public ResponseEntity<Boolean> addContent(
            @RequestParam("mainFile") MultipartFile mainFile,
            @RequestParam("coverFile") MultipartFile coverFile,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("score") Integer score,
            @RequestParam("categoryId") Long categoryId,
            HttpServletRequest request) {
        try {
            String mainFileType = fileUploadUtils.inferFileType(mainFile);
            if (mainFileType == null || (!"video".equals(mainFileType) && !"article".equals(mainFileType))) {
                return ResponseEntity.badRequest().body(false);
            }

            String mainFileUrl = fileUploadUtils.uploadFile(mainFile, mainFileType);
            String coverFileUrl = fileUploadUtils.uploadFile(coverFile, "cover");

            ContentAddRequest contentAddRequest = new ContentAddRequest();
            contentAddRequest.setTitle(title);
            contentAddRequest.setDescription(description);
            contentAddRequest.setType(mainFileType);
            contentAddRequest.setUrl(mainFileUrl);
            contentAddRequest.setCoverUrl(coverFileUrl);
            contentAddRequest.setScore(score);
            contentAddRequest.setCategoryId(categoryId);

            boolean result = contentService.addContent(contentAddRequest, request);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(false);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
}