package com.hidou7.tolog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hidou7.tolog.dto.LogSearchDto;
import com.hidou7.tolog.entity.LoggingEventEntity;
import com.hidou7.tolog.entity.LoggingEventExceptionEntity;
import com.hidou7.tolog.mapper.LoggingEventExceptionMapper;
import com.hidou7.tolog.mapper.LoggingEventMapper;
import com.hidou7.tolog.vo.LogSearchVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LogService {

    @Autowired
    private LoggingEventMapper loggingEventMapper;
    @Autowired
    private LoggingEventExceptionMapper loggingEventExceptionMapper;

    public Page<LogSearchVo> search(LogSearchDto dto) {
        List<LogSearchVo> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
        Page<LoggingEventEntity> page = this.loggingEventMapper.selectPage(dto.getPage(), this.getLoggingEventWrapper(dto));
        List<Long> eventIds = page.getRecords().stream().map(LoggingEventEntity::getEventId).collect(Collectors.toList());
        if(eventIds.size() > 0){
            List<LoggingEventExceptionEntity> exceptions = this.loggingEventExceptionMapper.selectList(new QueryWrapper<LoggingEventExceptionEntity>().in("event_id", eventIds));
            Map<Long, List<LoggingEventExceptionEntity>> exceptionMap = exceptions.stream().collect(Collectors.groupingBy(LoggingEventExceptionEntity::getEventId, Collectors.toList()));
            for (LoggingEventEntity record : page.getRecords()) {
                String error = sdf.format(new Date(record.getTimestmp())) + " #"
                        + record.getAppName() + " "
                        + record.getLevelString() + " --- ["
                        + record.getThreadName() + "] "
                        + record.getCallerClass() + "."
                        + record.getCallerMethod() + ": "
                        + record.getFormattedMessage();
                List<LoggingEventExceptionEntity> list = exceptionMap.getOrDefault(record.getEventId(), new ArrayList<>());
                list.sort(Comparator.comparing(LoggingEventExceptionEntity::getI));
                List<String> traceLines = list.stream().map(LoggingEventExceptionEntity::getTraceLine).collect(Collectors.toList());
                result.add(new LogSearchVo(error, traceLines));
            }
        }
        Page<LogSearchVo> p = new Page<>();
        p.setRecords(result);
        p.setTotal(page.getTotal());
        return p;
    }

    private QueryWrapper<LoggingEventEntity> getLoggingEventWrapper(LogSearchDto dto){
        return new QueryWrapper<LoggingEventEntity>()
            .eq(StringUtils.isNotBlank(dto.getLevel()), "level_string", dto.getLevel())
            .eq(StringUtils.isNotBlank(dto.getAppName()), "app_name", dto.getAppName())
            .like(StringUtils.isNotBlank(dto.getErrorMsg()), "formatted_message", dto.getErrorMsg())
            .ge(dto.getStartTimestamp() != null, "timestmp", dto.getStartTimestamp())
            .le(dto.getEndTimestamp() != null, "timestmp", dto.getEndTimestamp())
            .orderByAsc("event_id");
    }

    public List<String> getAppNames(){
        return this.loggingEventMapper.getAppNames();
    }
}
