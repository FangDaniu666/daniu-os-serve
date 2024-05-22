package com.daniu.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
@Slf4j
public class MarkdownProcessor {

    public static Map<String, Object> processMarkdownFile(String filePath, String copyTargetDir) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            log.error("文件未找到或不是有效的文件: " + filePath);
            return Map.of("name", " ", "path", " ", "lastedittime", " ");
        }
        String header = "desktop"; // 如果需要，可以在此处提供标题字符串
        String fileNameWithoutExt = removeExtension(file.getName());
        return processMarkdownContent(file, header, fileNameWithoutExt, copyTargetDir);
    }

    private static Map<String, Object> processMarkdownContent(File file, String header, String fileNameWithoutExt, String copyTargetDir) {
        Map<String, Object> fileInfo = new HashMap<>();
        fileInfo.put("name", file.getName());
        fileInfo.put("path", header + "_" + fileNameWithoutExt + ".json");
        fileInfo.put("lastedittime", getModifiedTime(file.toPath()));
        String title = "";
        String abstract_ = "";
        try {
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            boolean titleFound = false;
            boolean abstractFound = false;
            for (String line : lines) {
                if (titleFound && !abstractFound) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#") || line.startsWith("!") || line.startsWith("[") || line.startsWith("]") || line.startsWith("(") || line.startsWith(")") || line.startsWith("-") || line.startsWith(".") || line.startsWith(":")) {
                        continue;
                    } else if (line.length() > 120) {
                        abstract_ = line.substring(0, 120) + "...";
                        abstractFound = true;
                    } else {
                        abstract_ = line;
                        abstractFound = true;
                    }
                } else if (line.startsWith("# ")) {
                    title = line.substring(2).trim();
                    titleFound = true;
                }
                if (titleFound && abstractFound) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileInfo.put("title", title);
        fileInfo.put("abstract", abstract_);
        fileInfo.put("size", file.length());
        saveFileContent(file, header, fileNameWithoutExt, copyTargetDir);
        return fileInfo;
    }

    private static long getModifiedTime(Path path) {
        try {
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            return attrs.lastModifiedTime().toMillis();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static String removeExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            return fileName.substring(0, dotIndex);
        }
        return fileName;
    }

    private static void saveFileContent(File file, String header, String fileNameWithoutExt, String copyTargetDir) {
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            String content = new String(bytes, StandardCharsets.UTF_8);
            content = Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));

            String outputFilePath = copyTargetDir + File.separator + header + "_" + fileNameWithoutExt + ".json";
            FileWriter writer = new FileWriter(outputFilePath, StandardCharsets.UTF_8);
            writer.write("{\"data\":\"" + content + "\"}");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*private static void saveMapJson(Map<String, Object> fileInfo, String copyTargetDir) {
        try {
            FileWriter writer = new FileWriter(copyTargetDir + File.separator + "map.json");
            writer.write(convertToJson(fileInfo));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /*private static String convertToJson(Map<String, Object> fileInfo) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        for (Map.Entry<String, Object> pair : fileInfo.entrySet()) {
            jsonBuilder.append("\"").append(pair.getKey()).append("\":");
            Object value = pair.getValue();
            if (value instanceof String) {
                jsonBuilder.append("\"").append(value).append("\",");
            } else if (value instanceof Long) {
                jsonBuilder.append(value).append(",");
            }
        }
        jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }*/
}