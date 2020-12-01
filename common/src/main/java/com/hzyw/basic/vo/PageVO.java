package com.hzyw.basic.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author chenling
 */
public class PageVO<T> {
    @ApiModelProperty("返回code")
    private Integer code;
    @ApiModelProperty("返回信息")
    private String message;
    @ApiModelProperty("当前页")
    private Integer currentPage;
    @ApiModelProperty("每页显示的总条数")
    private Integer pageSize;
    @ApiModelProperty("总条数")
    private Integer totalNum;
    @ApiModelProperty("是否有下一页")
    private Integer isMore;
    @ApiModelProperty("总页数")
    private Integer totalPage;
    @ApiModelProperty("开始序列")
    private Integer startIndex;
    @ApiModelProperty("分页结果")
    private List<T> data;

    public void setCode(Integer code)
    {
        this.code = code;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setCurrentPage(Integer currentPage)
    {
        this.currentPage = currentPage;
    }

    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public void setTotalNum(Integer totalNum)
    {
        this.totalNum = totalNum;
    }

    public void setIsMore(Integer isMore)
    {
        this.isMore = isMore;
    }

    public void setTotalPage(Integer totalPage)
    {
        this.totalPage = totalPage;
    }

    public void setStartIndex(Integer startIndex)
    {
        this.startIndex = startIndex;
    }

    public void setData(List<T> data)
    {
        this.data = data;
    }

    public Integer getCode()
    {
        return this.code;
    }

    public String getMessage()
    {
        return this.message;
    }

    public Integer getCurrentPage()
    {
        return this.currentPage;
    }

    public Integer getPageSize()
    {
        return this.pageSize;
    }

    public Integer getTotalNum()
    {
        return this.totalNum;
    }

    public Integer getIsMore()
    {
        return this.isMore;
    }

    public Integer getTotalPage()
    {
        return this.totalPage;
    }

    public Integer getStartIndex()
    {
        return this.startIndex;
    }

    public List<T> getData()
    {
        return this.data;
    }

    public PageVO(Integer currentPage, Integer pageSize, Integer totalNum)
    {
        if ((currentPage != null) && (currentPage.intValue() > 0)) {
            this.currentPage = currentPage;
        }
        if ((pageSize != null) && (pageSize.intValue() > 0)) {
            this.pageSize = pageSize;
        }
        this.totalNum = totalNum;
        this.totalPage = Integer.valueOf((this.totalNum.intValue() + this.pageSize.intValue() - 1) / this.pageSize.intValue());
        this.startIndex = Integer.valueOf((this.currentPage.intValue() - 1) * this.pageSize.intValue());
        this.isMore = Integer.valueOf(this.currentPage.intValue() >= this.totalPage.intValue() ? 0 : 1);
    }

    public PageVO() {}
}