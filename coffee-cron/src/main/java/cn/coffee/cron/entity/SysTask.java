package cn.coffee.cron.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import lombok.*;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;


import java.util.Date;

/**
 * 定时任务实体类
 * @author lhz
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_task")
public class SysTask implements Serializable {
    private static final long serialVersionUID = -1L;


    @IsKey
    @TableId(type = IdType.ASSIGN_UUID)
    @Column(name = "id",comment = "定时任务ID")
    private String id;

    @Column(name = "task_name",comment = "定时任务名称")
    private String taskName;

    @Column(name = "task_code",comment = "定时任务编码")
    private String taskCode;

    @Column(name = "cron",comment = "定时任务规则")
    private String cron;

    @Column(name = "class_name",comment = "实例化类名")
    private String className;

    @Column(name = "method_name",comment = "执行的方法")
    private String methodName;

    @Column(name = "remark",comment = "备注")
    private String remark;

    @Column(name = "create_time",comment = "创建时间")
    private Date createTime;

    @Column(name = "create_user",comment = "创建用户")
    private String createUser;

    @Column(name = "state",comment = "状态 0：无效  1：有效")
    private String state;


}
