package cn.coffee.ums.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import lombok.*;

import com.baomidou.mybatisplus.annotation.*;
import java.util.Date;

/**
 * 用户角色表实体类
 * @author lhz
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ums_role")
public class UmsRole {

    @IsKey
    @TableId(type = IdType.ASSIGN_ID)
    @Column(name = "id",comment = "用户角色ID")
    private String id;

    @Column(name = "role_name",comment = "角色名称")
    private String roleName;

    @Column(name = "role_code",comment = "角色编码")
    private String roleCode;

    @Column(name = "create_time",comment = "创建时间")
    private Date createTime;

    @Column(name = "update_time",comment = "修改时间")
    private Date updateTime;


}
