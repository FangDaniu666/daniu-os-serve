package com.daniu.pms.util;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@UtilityClass
@Slf4j
public class Mp3MetadataExtractor {

    public static String extractArtistFromMp3(String mp3File) {
        Mp3File mp3 = getMp3File(mp3File);

        if (mp3.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3.getId3v2Tag();
            String artist = id3v2Tag.getArtist();
            return artist;
        } else {
            return "Unknown";
        }
    }

    public static String extractAndSaveCoverImage(String mp3File, String outputDirectory, String title) {
        Mp3File mp3 = getMp3File(mp3File);
        String fileName = "unknown.jpg";
        if (mp3.hasId3v2Tag()) {
            ID3v2 id3v2Tag = mp3.getId3v2Tag();
            byte[] imageData = id3v2Tag.getAlbumImage();

            if (imageData != null) {
                String mimeType = id3v2Tag.getAlbumImageMimeType();
                String extension = mimeType.split("/")[1]; // 获取图片文件扩展名

                fileName = title + "." + extension;
                String outputPath = outputDirectory + File.separator + fileName;

                try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                    fos.write(imageData); // 将图片数据写入文件
                } catch (FileNotFoundException e) {
                    log.error("文件未找到: {}", e.getMessage());
                } catch (IOException e) {
                    log.error("写入文件失败: {}", e.getMessage());
                }
            } else {
                log.error("没有找到封面图像");
            }
        } else {
            log.error("文件没有 ID3v2 标签");
        }
        return fileName;
    }


    private static Mp3File getMp3File(String mp3File) {
        Mp3File mp3 = null;
        try {
            mp3 = new Mp3File(mp3File);
        } catch (IOException e) {
            log.error("读取 MP3 文件失败: {}", e.getMessage());
        } catch (UnsupportedTagException e) {
            log.error("不支持的 MP3 标签: {}", e.getMessage());
        } catch (InvalidDataException e) {
            log.error("无效的 MP3 数据: {}", e.getMessage());
        }
        return mp3;
    }

}

