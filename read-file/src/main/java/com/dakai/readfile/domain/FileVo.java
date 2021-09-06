package com.dakai.readfile.domain;

import lombok.Data;

import java.util.List;

@Data
public class FileVo {

    private String name;
    private String path;
    private boolean type;
    private List<FileVo> child;
}
