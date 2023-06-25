package com.hidou7.tolog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("logging_event_exception")
public class LoggingEventExceptionEntity {

    private Long eventId;

    private Integer i;

    private String traceLine;
}
