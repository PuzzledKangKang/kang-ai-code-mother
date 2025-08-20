package net.pkk.kangaicodemother.service;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @author 林子康
 * @version 1.0
 * @since 2025/8/20 9:33
 */
public interface ProjectDownloadService {

    /**
     * 下载项目为压缩包
     *
     * @param projectPath        项目路径
     * @param downloadFileName   下载文件名
     * @param httpServerResponse http 响应
     */
    void downloadProjectAsZip(String projectPath, String downloadFileName, HttpServletResponse httpServerResponse);
}
