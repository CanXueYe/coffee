package cn.coffee.gms.entity;

import lombok.*;

import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;


import java.util.Date;

/**
 * 房间排名表-实体类
 * @author lhz
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("gms_room_rank")
public class GmsRoomRank implements Serializable {
    private static final long serialVersionUID = -1L;


    @TableId(type = IdType.ASSIGN_UUID)
    /**
     * 房间排名ID
     */
    @TableField("id")
    private long id;

    /**
     * 房间ID
     */
    @TableField("room_id")
    private long roomId;

    /**
     * 第一名用户ID
     */
    @TableField("rank_no1")
    private long rankNo1;

    /**
     * 第一名点数
     */
    @TableField("rank_no1_points")
    private int rankNo1Points;

    /**
     * 第一名用户头像
     */
    @TableField("rank_no1_icon")
    private String rankNo1Icon;

    /**
     * 第一名用户昵称
     */
    @TableField("rank_no1_nick")
    private String rankNo1Nick;

    /**
     * 第二名用户ID
     */
    @TableField("rank_no2")
    private long rankNo2;

    /**
     * 第二名点数
     */
    @TableField("rank_no2_points")
    private int rankNo2Points;

    /**
     * 第二名用户头像
     */
    @TableField("rank_no1_icon")
    private String rankNo2Icon;

    /**
     * 第二名用户昵称
     */
    @TableField("rank_no1_nick")
    private String rankNo2Nick;

    /**
     * 第三名用户ID
     */
    @TableField("rank_no3")
    private long rankNo3;

    /**
     * 第三名点数
     */
    @TableField("rank_no3_points")
    private int rankNo3Points;

    /**
     * 第三名用户头像
     */
    @TableField("rank_no3_icon")
    private String rankNo3Icon;

    /**
     * 第三名用户昵称
     */
    @TableField("rank_no3_nick")
    private String rankNo3Nick;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


}
