package com.hidou7.tolog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hidou7.tolog.entity.LoggingEventEntity;

import java.util.List;

public interface LoggingEventMapper extends BaseMapper<LoggingEventEntity> {

    List<String> getAppNames();
}
