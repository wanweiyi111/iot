package com.hzyw.basic.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hzyw.basic.dos.PermissionDO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeVO<T> extends PermissionDO{

    private List<TreeVO<T>> children;

    private List<TreeVO<T>> buttonList;

    private Boolean hasParent = false;

    private Boolean hasChildren = false;

    public void initChildren(){
        this.children = new ArrayList<>();
    }

    public void initButtonList(){
        this.buttonList = new ArrayList<>();
    }

}
