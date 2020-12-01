package com.hzyw.basic.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PageVO<T> {
    @ApiModelProperty(value = "返回code")
    private Integer code;
    @ApiModelProperty(value = "返回信息")
    private String message;
    @ApiModelProperty(value = "当前页")
    private Integer currentPage;
    @ApiModelProperty(value = "每页显示的总条数")
    private Integer pageSize;
    @ApiModelProperty(value = "总条数")
    private Integer totalNum;
    @ApiModelProperty(value = "是否有下一页")
    private Integer isMore;
    @ApiModelProperty(value = "总页数")
    private Integer totalPage;
    @ApiModelProperty(value = "开始索引")
    private Integer startIndex;
    @ApiModelProperty(value = "分页结果")
    private List<T> data;

    public PageVO() {

    }

    public PageVO(Integer currentPage, Integer pageSize, Integer totalNum) {
        if (currentPage != null && currentPage > 0) {
            this.currentPage = currentPage;
        }
        if (pageSize != null && pageSize > 0) {
            this.pageSize = pageSize;
        }
        this.totalNum = totalNum;
        this.totalPage = (this.totalNum + this.pageSize - 1) / this.pageSize;
        this.startIndex = (this.currentPage - 1) * this.pageSize;
        this.isMore = this.currentPage >= this.totalPage ? 0 : 1;
    }
}