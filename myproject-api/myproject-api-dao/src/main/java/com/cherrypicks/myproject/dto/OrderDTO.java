package com.cherrypicks.myproject.dto;

import com.cherrypicks.myproject.dto.BaseObject;

public class OrderDTO extends BaseObject {

    private static final long serialVersionUID = 4306886912275263259L;

    private Integer column;

    private String dir;

    public Integer getColumn() {
        return column;
    }

    public void setColumn(final Integer column) {
        this.column = column;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(final String dir) {
        this.dir = dir;
    }

}
