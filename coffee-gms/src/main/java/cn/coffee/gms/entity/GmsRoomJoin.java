package cn.coffee.gms.entity;

import lombok.*;

import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;


import java.util.Date;

/**
 * 房间参加人员表-实体类
 * @author lhz
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("gms_room_join")
public class GmsRoomJoin implements Serializable {
    private static final long serialVersionUID = -1L;


    @TableId(type = IdType.ASSIGN_UUID)
    /**
     * 房间参加人员ID
     */
    @TableField("id")
    private long id;

    /**
     * 房间ID
     */
    @TableField("room_id")
    private long roomId;

    /**
     * 用户ID
     */
    @TableField("member_id")
    private long memberId;

    /**
     * 用户头像
     */
    @TableField("member_icon")
    private String memberIcon;

    /**
     * 用户昵称
     */
    @TableField("member_nick")
    private String memberNick;

    /**
     * 点数
     */
    @TableField("points")
    private int points;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;


}
