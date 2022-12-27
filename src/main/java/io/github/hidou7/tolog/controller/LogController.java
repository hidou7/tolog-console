package io.github.hidou7.tolog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.hidou7.tolog.annotation.Auth;
import io.github.hidou7.tolog.dto.LogSearchDto;
import io.github.hidou7.tolog.service.LogService;
import io.github.hidou7.tolog.vo.LogSearchVo;
import io.github.hidou7.tolog.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("log")
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * 分页查询
     */
    @GetMapping("search")
    @Auth
    public R<List<LogSearchVo>> search(@Valid LogSearchDto dto){
        Page<LogSearchVo> p = this.logService.search(dto);
        return R.page(p);
    }

    @GetMapping("getAppNames")
    @Auth
    public R<List<String>> getAppNames(){
        List<String> list = this.logService.getAppNames();
        return R.ok(list);
    }
}
