package com.hidou7.tolog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("logging_event")
public class LoggingEventEntity {

    private Long eventId;

    private String appName;

    private Long timestmp;

    private String formattedMessage;

    private String loggerName;

    private String levelString;

    private String threadName;

    private String callerFilename;

    private String callerClass;

    private String callerMethod;

    private String callerLine;
}
