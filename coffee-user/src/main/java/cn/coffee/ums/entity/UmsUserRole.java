package cn.coffee.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


/**
 * 用户角色关联表-实体类
 * @author lhz
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ums_user_role")
public class UmsUserRole {

    @IsKey
    @TableId(type = IdType.ASSIGN_ID)
    @Column(name = "id",comment = "用户角色关联ID")
    private String id;

    @Column(name = "user_id",comment = "用户ID")
    private String userId;

    @Column(name = "role_id",comment = "角色ID")
    private String roleId;

    @Column(name = "create_time",comment = "创建时间")
    private Date createTime;


}
