package io.github.hidou7.tolog.vo;

import lombok.Data;

import java.util.List;

@Data
public class LogSearchVo {

    private String error;

    private List<String> traceLines;

    public LogSearchVo() {
    }

    public LogSearchVo(String error, List<String> traceLines) {
        this.error = error;
        this.traceLines = traceLines;
    }
}
