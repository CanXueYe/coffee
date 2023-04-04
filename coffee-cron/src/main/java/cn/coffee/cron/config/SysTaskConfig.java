package cn.coffee.cron.config;


import cn.coffee.common.utils.SpringUtil;
import cn.coffee.cron.entity.SysTask;
import cn.coffee.cron.service.ISysTaskService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;


@Component
@Configuration
@EnableScheduling
@ComponentScan("cn.coffee.cron")
public class SysTaskConfig implements SchedulingConfigurer {

    protected static Logger logger = LoggerFactory.getLogger(SysTaskConfig.class);

    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    volatile ScheduledFuture<?> future;
    @Inject
    private ISysTaskService taskService;

    //从数据库里取得所有要执行的定时任务
    private List<SysTask> getAllTasks() {
        List<SysTask> list=taskService.list(new QueryWrapper<SysTask>().eq("state",1));
        return list;
    }

    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }

    public void run(){
        ScheduledTaskRegistrar taskRegistrar=new ScheduledTaskRegistrar();
        configureTasks(taskRegistrar);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        List<SysTask> tasks=getAllTasks();
        logger.info("=========定时任务启动中=========");
        logger.info("定时任务启动,预计启动任务数量="+tasks.size()+"; time="+sdf.format(new Date()));

        //校验数据（这个步骤主要是为了打印日志，可以省略）
        checkDataList(tasks);

        //通过校验的数据执行定时任务
        int count = 0;
        if(tasks.size()>0) {
            for (int i = 0; i < tasks.size(); i++) {
                try {
                    taskRegistrar.addTriggerTask(getRunnable(tasks.get(i)), getTrigger(tasks.get(i)));
                    logger.info("===定时任务："+tasks.get(i).getRemark()+"启动成功，状态：true");
                    count++;
                } catch (Exception e) {
                    logger.error("===定时任务："+tasks.get(i).getRemark()+"启动失败，状态：false");
                    logger.error("定时任务启动错误:" + tasks.get(i).getClassName() + ";" + tasks.get(i).getMethodName() + ";" + e.getMessage());
                }
            }
        }
        logger.info("定时任务实际启动数量="+count+"; time="+sdf.format(new Date()));
        logger.info("=========定时任务启动完成=========");
    };


    private Runnable getRunnable(SysTask task){
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Class cal = Class.forName(task.getClassName());
                    Object obj = SpringUtil.getBean(cal);
                    Method method = obj.getClass().getMethod(task.getMethodName(),null);
                    method.invoke(obj);
                } catch (InvocationTargetException e) {
                    logger.error("定时任务启动错误，反射异常:"+task.getClassName()+";"+task.getMethodName()+";"+ e.getMessage());
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        };
    }

    private Trigger getTrigger(SysTask task){
        return new Trigger() {
            public Date nextExecutionTime(TriggerContext triggerContext){
                //将Cron 0/1 * * * * ? 输入取得下一次执行的时间
                CronTrigger trigger = new CronTrigger(task.getCron());
                Date nextExec = trigger.nextExecutionTime(triggerContext);
                return nextExec;
            }

            @Override
            public Instant nextExecution(TriggerContext triggerContext) {
                //将Cron 0/1 * * * * ? 输入取得下一次执行的时间
                CronTrigger trigger = new CronTrigger(task.getCron());
                Instant nextExec = trigger.nextExecution(triggerContext);
                return nextExec;
            }
        };

    }

    private List<SysTask> checkDataList(List<SysTask> list) {
        String errMsg="";
        for(int i=0;i<list.size();i++){
            if(!checkOneData(list.get(i)).equalsIgnoreCase("success")){
                errMsg+=list.get(i).getTaskName()+";";
                list.remove(list.get(i));
                i--;
            };
        }
        if(!StringUtils.isBlank(errMsg)){
            errMsg="未启动的任务:"+errMsg;
            logger.error(errMsg);
        }
        return list;
    }

    private String checkOneData(SysTask task){
        String result="success";
        Class cal= null;
        try {
            cal = Class.forName(task.getClassName());

            Object obj =SpringUtil.getBean(cal);
            Method method = obj.getClass().getMethod(task.getMethodName(),null);
            String cron=task.getCron();
            if(StringUtils.isBlank(cron)){
                result="定时任务启动错误，无cron:"+task.getTaskName();
                logger.error(result);
            }
        } catch (ClassNotFoundException e) {
            result="定时任务启动错误，找不到类:"+task.getClassName()+ e.getMessage();
            logger.error(result);
        } catch (NoSuchMethodException e) {
            result="定时任务启动错误，找不到方法,方法必须是public:"+task.getClassName()+";"+task.getMethodName()+";"+ e.getMessage();
            logger.error(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }


}