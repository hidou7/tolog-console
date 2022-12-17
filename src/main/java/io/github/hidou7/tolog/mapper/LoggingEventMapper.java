package io.github.hidou7.tolog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.hidou7.tolog.entity.LoggingEventEntity;

import java.util.List;

public interface LoggingEventMapper extends BaseMapper<LoggingEventEntity> {

    List<String> getAppNames();
}
