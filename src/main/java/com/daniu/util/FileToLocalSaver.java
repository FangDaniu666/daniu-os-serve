package com.daniu.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件保存工具类
 *
 * @author FangDaniu
 * @since 2024/05/25
 */
@UtilityClass
@Slf4j
public class FileToLocalSaver {

    public static void saveMultipartFileToLocalFile(MultipartFile multipartFile, String destinationDirectory, String fileName) throws IOException {
        // 确保目标目录存在，如果不存在则创建
        File directory = new File(destinationDirectory);
        if (!directory.exists()) {
            boolean mkdir = directory.mkdirs();// 创建目录及其父目录
            if (!mkdir) {
                throw new IOException("创建目录失败: " + destinationDirectory);
            }
        }
        // 构造本地文件路径
        String filePath = destinationDirectory + File.separator + fileName;

        // 将 MultipartFile 内容写入本地文件
        File file = new File(filePath);
        multipartFile.transferTo(file);

        log.info("文件保存成功: {}", filePath);
    }

}