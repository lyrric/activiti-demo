package com.github.lyrric.model;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created on 2018/11/9.
 * 分页返回
 * @author wangxiaodong
 */
@Data
public class PageResult<T> {
    /**
     * 数据总数
     */
    @ApiModelProperty("数据总数")
    private long totalCount;
    /**
     * 页面总数
     */
    @ApiModelProperty("页面总数")
    private long totalPage;
    /**
     * 分页数据
     */
    @ApiModelProperty("分页数据")
    private List<T> data;

    public PageResult() {
    }

    public PageResult(Page<T> page) {
        data = page.getResult();
        totalCount = page.getTotal();
        totalPage = page.getPages();
    }
}
