package com.hidou7.tolog.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PageDto {

    /**
     * 页数
     */
    @NotNull
    private Integer pageNum;

    /**
     * 页大小
     */
    @NotNull
    private Integer pageSize;

    public Page getPage(){
        return new Page<>(this.pageNum, this.pageSize);
    }
}
