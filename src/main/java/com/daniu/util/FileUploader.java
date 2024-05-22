package com.daniu.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@UtilityClass
@Slf4j
public class FileUploader {

    public static void saveMultipartFileToLocalFile(MultipartFile multipartFile, String destinationDirectory, String fileName) throws IOException {
        // 确保目标目录存在，如果不存在则创建
        File directory = new File(destinationDirectory);
        if (!directory.exists()) {
            directory.mkdirs(); // 创建目录及其父目录
        }
        // 构造本地文件路径
        String filePath = destinationDirectory + File.separator + fileName;

        // 将 MultipartFile 内容写入本地文件
        File file = new File(filePath);
        multipartFile.transferTo(file);

        log.info("文件保存成功: " + filePath);
    }

}