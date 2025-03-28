package com.lihui.security_office_backend.utils;

import com.lihui.security_office_backend.config.FileTypeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class FileUploadUtils {

    @Autowired
    private FileTypeConfig fileTypeConfig;

    private static final Map<String, String[]> ALLOWED_EXTENSIONS = new HashMap<>();
    private static final Map<String, String[]> ALLOWED_MIME_TYPES = new HashMap<>();

    @PostConstruct
    public void init() {
        ALLOWED_EXTENSIONS.put("video", new String[]{"mp4", "avi"});
        ALLOWED_MIME_TYPES.put("video", new String[]{"video/mp4", "video/x-msvideo"});

        ALLOWED_EXTENSIONS.put("article", new String[]{"pdf", "doc", "docx"});
        ALLOWED_MIME_TYPES.put("article", new String[]{"application/pdf", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"});

        ALLOWED_EXTENSIONS.put("cover", new String[]{"jpg", "jpeg", "png"});
        ALLOWED_MIME_TYPES.put("cover", new String[]{"image/jpeg", "image/png"});
    }

    public String uploadFile(MultipartFile file, String fileType) throws IOException {
        String uploadDir = fileTypeConfig.getDirectoryByType(fileType);
        ensureDirectoryExists(uploadDir);

        if (!isValidFileType(file, fileType)) {
            throw new IllegalArgumentException("无效的文件类型: " + fileType);
        }

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), filePath);

        return "/" + uploadDir.replace("\\", "/") + "/" + fileName;
    }

    public String inferFileType(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
        for (Map.Entry<String, String[]> entry : ALLOWED_EXTENSIONS.entrySet()) {
            if (Arrays.asList(entry.getValue()).contains(extension)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean isValidFileType(MultipartFile file, String fileType) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
        String mimeType = file.getContentType();
        return Arrays.asList(ALLOWED_EXTENSIONS.getOrDefault(fileType, new String[0])).contains(extension)
                && Arrays.asList(ALLOWED_MIME_TYPES.getOrDefault(fileType, new String[0])).contains(mimeType);
    }

    private void ensureDirectoryExists(String directory) {
        File dir = new File(directory);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException("无法创建目录: " + directory);
            }
        }
    }
}    