package io.github.hidou7.tolog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("logging_event_exception")
public class LoggingEventExceptionEntity {

    private String eventId;

    private Integer i;

    private String traceLine;
}
