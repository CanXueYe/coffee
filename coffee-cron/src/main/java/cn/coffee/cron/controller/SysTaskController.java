package cn.coffee.cron.controller;

import cn.coffee.cron.config.SysTaskConfig;
import cn.coffee.cron.service.ISysTaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * 定时任务控制层
 * @author Tomato
 */
@RestController
@RequestMapping("task")
@Log4j2
public class SysTaskController {

    @Inject
    private ISysTaskService taskService;
    @Inject
    private SysTaskConfig sysTaskConfig;

    @GetMapping("/cancel")
    public String cancel(){
        sysTaskConfig.cancel();
        return "OK";
    }

    @GetMapping("/run")
    public String run(){
        sysTaskConfig.run();
        return "OK";
    }

}