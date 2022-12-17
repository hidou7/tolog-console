package io.github.hidou7.tolog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("logging_event")
public class LoggingEventEntity {

    private String eventId;

    private Long timestmp;

    private String formattedMessage;

    private String loggerName;

    private String levelString;

    private String threadName;

    private String callerFilename;

    private String callerClass;

    private String callerMethod;

    private String callerLine;

    private String appName;

    @TableField(exist = false)
    private List<String> traceLines;

    @TableField(exist = false)
    private String happenTime;
}
