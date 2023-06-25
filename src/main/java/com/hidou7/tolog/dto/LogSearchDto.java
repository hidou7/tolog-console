package com.hidou7.tolog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LogSearchDto extends PageDto{

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
