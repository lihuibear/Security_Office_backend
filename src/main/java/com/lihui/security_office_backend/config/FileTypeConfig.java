package com.lihui.security_office_backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "file.upload")
public class FileTypeConfig {

    private String baseDir;
    private String videoDir;
    private String articleDir;
    private String coverDir;

    private final Map<String, String> directoryMap = new HashMap<>();

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public void setVideoDir(String videoDir) {
        this.videoDir = videoDir;
    }

    public void setArticleDir(String articleDir) {
        this.articleDir = articleDir;
    }

    public void setCoverDir(String coverDir) {
        this.coverDir = coverDir;
    }

    @PostConstruct
    public void init() {
        directoryMap.put("video", baseDir + "/" + videoDir);
        directoryMap.put("article", baseDir + "/" + articleDir);
        directoryMap.put("cover", baseDir + "/" + coverDir);
    }

    public String getDirectoryByType(String fileType) {
        String directory = directoryMap.get(fileType);
        if (directory == null) {
            throw new IllegalArgumentException("无效的文件类型: " + fileType);
        }
        return directory;
    }
}