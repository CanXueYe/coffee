package cn.coffee.cron.service.impl;

import cn.coffee.cron.entity.SysTask;
import cn.coffee.cron.mapper.SysTaskMapper;
import cn.coffee.cron.service.ISysTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 定时任务表-服务实现类
 *
 * @author lhz
 */
@Service("cn.coffee.cron.service.impl")
public class SysTaskServiceImpl extends ServiceImpl<SysTaskMapper, SysTask> implements ISysTaskService {

}
