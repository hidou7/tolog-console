package io.github.hidou7.tolog.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LogSearchDto {

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

    /**
     * 服务名称
     */
    private String appName;

    /**
     * 报错信息
     */
    private String errorMsg;

    /**
     * 日志级别
     */
    private String level;

    /**
     * 开始时间
     */
    private Long startTimestamp;

    /**
     * 结束时间
     */
    private Long endTimestamp;
}
