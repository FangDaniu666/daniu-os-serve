package com.daniu.util;

import lombok.experimental.UtilityClass;

/**
 * 文件名工具类
 *
 * @author FangDaniu
 * @since 2024/05/25
 */
@UtilityClass
public class FileNameUtils {

    public static String removeFileExtension(String fileName) {
        // 查询最后一个点的索引位置
        int extensionIndex = fileName.lastIndexOf('.');
        if (extensionIndex == -1) {
            // 如果找不到点，则返回原始文件名
            return fileName;
        }
        return fileName.substring(0, extensionIndex);
    }

}
