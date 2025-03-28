package com.lihui.security_office_backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lihui.security_office_backend.common.BaseResponse;
import com.lihui.security_office_backend.common.ResultUtils;
import com.lihui.security_office_backend.exception.BusinessException;
import com.lihui.security_office_backend.exception.ErrorCode;
import com.lihui.security_office_backend.model.dto.content.ContentAddRequest;
import com.lihui.security_office_backend.model.dto.content.ContentDeleteRequest;
import com.lihui.security_office_backend.model.dto.content.ContentQueryRequest;
import com.lihui.security_office_backend.model.dto.content.ContentUpdataRequest;
import com.lihui.security_office_backend.model.entity.Content;
import com.lihui.security_office_backend.model.vo.ContentVO;
import com.lihui.security_office_backend.service.ContentService;
import com.lihui.security_office_backend.utils.FileUploadUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Resource
    private ContentService contentService;
    @Resource
    private FileUploadUtils fileUploadUtils;

    @PostMapping("/add")
    public BaseResponse<Boolean> addContent(
            ContentAddRequest contentAddRequest,
            @RequestParam("mainFile") MultipartFile mainFile,
            @RequestParam("coverFile") MultipartFile coverFile,
//            @RequestParam("title") String title,
//            @RequestParam("description") String description,
//            @RequestParam("score") Integer score,
//            @RequestParam("categoryId") Long categoryId,
            HttpServletRequest request) {

//        MultipartFile mainFile = contentAddRequest.getMainFile();
//        MultipartFile coverFile = contentAddRequest.getCoverFile();
        String title = contentAddRequest.getTitle();
        String description = contentAddRequest.getDescription();
        Integer score = contentAddRequest.getScore();
        Long categoryId = contentAddRequest.getCategoryId();
        try {
            String mainFileType = fileUploadUtils.inferFileType(mainFile);
            if ((!"video".equals(mainFileType) && !"article".equals(mainFileType))) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "文件类型错误");
//                return ResponseEntity.badRequest().body(false);
            }

            String mainFileUrl = fileUploadUtils.uploadFile(mainFile, mainFileType);
            String coverFileUrl = fileUploadUtils.uploadFile(coverFile, "cover");

            contentAddRequest.setTitle(title);
            contentAddRequest.setDescription(description);
            contentAddRequest.setType(mainFileType);
            contentAddRequest.setUrl(mainFileUrl);
            contentAddRequest.setCoverUrl(coverFileUrl);
            contentAddRequest.setScore(score);
            contentAddRequest.setCategoryId(categoryId);

            boolean result = contentService.addContent(contentAddRequest, request);
            return ResultUtils.success(result);
//            return ResponseEntity.ok(result);
        } catch (IOException | IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "文件上传失败");
//            return ResponseEntity.badRequest().body(false);
        }
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteContent(ContentDeleteRequest contentDeleteRequest) {
        Long id = contentDeleteRequest.getId();
        boolean result = contentService.deleteContent(id);
        return ResultUtils.success(result);
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> updateContent(ContentUpdataRequest contentUpdataRequest,
                                               @RequestParam(value = "mainFile", required = false) MultipartFile mainFile,
                                               @RequestParam(value = "coverFile", required = false) MultipartFile coverFile,
                                               HttpServletRequest request) {
        try {
            // 先检查要更新的内容是否存在
            if (contentUpdataRequest.getId() == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "要更新的内容不存在");
            }
            // 处理主文件更新
            if (mainFile != null && !mainFile.isEmpty()) {
                String mainFileType = fileUploadUtils.inferFileType(mainFile);
                if ((!"video".equals(mainFileType) && !"article".equals(mainFileType))) {
                    throw new BusinessException(ErrorCode.OPERATION_ERROR, "类型错误");
                }
                String mainFileUrl = fileUploadUtils.uploadFile(mainFile, mainFileType);
                contentUpdataRequest.setUrl(mainFileUrl);
                contentUpdataRequest.setType(mainFileType);
            }

            // 处理封面文件更新
            if (coverFile != null && !coverFile.isEmpty()) {
                String coverFileUrl = fileUploadUtils.uploadFile(coverFile, "cover");
                contentUpdataRequest.setCoverUrl(coverFileUrl);
            }

            // 调用服务层更新内容
            boolean result = contentService.updateContent(contentUpdataRequest, request);
            return ResultUtils.success(result);

//            return ResponseEntity.ok(result);
        } catch (IOException | IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "系统错误");
//            return ResponseEntity.badRequest().body(false);
        }
    }

    @GetMapping("/list/")
    public BaseResponse<List<ContentVO>> getContentList() {
        List<ContentVO> contentList = contentService.getContentList();
        return ResultUtils.success(contentList);
    }
    @PostMapping("/list/page")
        public BaseResponse<Page<Content>> getContentListByPage(@RequestBody ContentQueryRequest contentQueryRequest) {
        long current = contentQueryRequest.getCurrent();
        long size = contentQueryRequest.getPageSize();
        // 查数据库
        Page<Content> categoryPage = contentService.page(new Page<>(current, size),
                contentService.getQueryWrapper(contentQueryRequest));
        return ResultUtils.success(categoryPage);
    }


}