package net.pkk.kangaicodemother.service;

/**
 * 截图服务
 *
 * @author 林子康
 * @version 1.0
 * @since 2025/8/19 11:33
 */
public interface ScreenshotService {

    /**
     * 通用的截图服务，可以得到访问地址
     *
     * @param webUrl 网页地址
     * @return 访问地址
     */
    String generateAndUpload(String webUrl);
}
