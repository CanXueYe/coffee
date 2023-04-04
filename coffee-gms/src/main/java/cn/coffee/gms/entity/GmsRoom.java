package cn.coffee.gms.entity;

import lombok.*;

import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;


import java.util.Date;

/**
 * 骰子房间表-实体类
 * @author lhz
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("gms_room")
public class GmsRoom implements Serializable {
    private static final long serialVersionUID = -1L;


    @TableId(type = IdType.AUTO)
    /**
     * 房间ID
     */
    @TableField("id")
    private long id;

    /**
     * 房间编号
     */
    @TableField("code")
    private String code;

    /**
     * 房间标题
     */
    @TableField("title")
    private String title;

    /**
     * 房主用户ID
     */
    @TableField("home_member_id")
    private long homeMemberId;

    /**
     * 消耗积分
     */
    @TableField("consume_integral")
    private int consumeIntegral;

    /**
     * 参加人数
     */
    @TableField("join_num")
    private int joinNum;

    /**
     * 状态  0：未开始   1：已开始   2：已结算   3：已作废
     */
    @TableField("status")
    private int status;

    /**
     * 房间时间（分钟）
     */
    @TableField("duration")
    private int duration;

    /**
     * 开始时间
     */
    @TableField("begin_time")
    private Date beginTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private Date endTime;

    /**
     * 结束时间
     */
    @TableField("settle_time")
    private Date settleTime;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


}
