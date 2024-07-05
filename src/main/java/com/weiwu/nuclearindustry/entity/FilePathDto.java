package com.weiwu.nuclearindustry.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * @author Administrator
 */
@Getter
@Setter
public class FilePathDto {
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
