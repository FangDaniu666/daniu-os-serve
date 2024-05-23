package com.daniu.domain.dto;

import lombok.Data;

@Data
public class MusicDto {

    private Integer id;

    private String title;

    private String artist;

    private String src;

    private String pic;

    private boolean isInsert;
}
