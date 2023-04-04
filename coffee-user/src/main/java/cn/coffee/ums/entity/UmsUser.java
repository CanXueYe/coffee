package cn.coffee.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ums_user")
public class UmsUser {

    @IsKey
    @TableId(type = IdType.ASSIGN_ID)
    @Column(name = "id",comment = "id")
    private String id;

    @TableField("password")
    @Column(name = "password",comment = "密码")
    private String password;

    @Column(name = "gesture",comment = "手势密码")
    private String gesture;

    @Column(name = "secret",comment = "安全密码")
    private String secret;

    @Column(name = "destroy",comment = "注销密码")
    private String destroy;

    @Column(name = "id_card",comment = "身份证号")
    private String idCard;

    @Column(name = "name",comment = "姓名")
    private String name;

    @Column(name = "nick",comment = "昵称")
    private String nick;

    @Column(name = "mobile",comment = "手机号")
    private String mobile;

    @Column(name = "email",comment = "Email地址")
    private String email;

    @Column(name = "wechat",comment = "微信号")
    private String wechat;

    @Column(name = "qq",comment = "QQ号")
    private String qq;

    @Column(name = "avatar",comment = "头像")
    private String avatar;

    @Column(name = "gender",comment = "性别：0-未知；1-男；2-女")
    private Integer gender;

    @Column(name = "birthday",comment = "出生日期")
    private Date birthday;

    @Column(name = "inviter",comment = "邀请人")
    private String inviter;

    @Column(name = "invite_count",comment = "邀请人数")
    private Integer inviteCount;

    @TableField("code")
    @Column(name = "code",comment = "唯一编码")
    private String code;

    @Column(name = "register",comment = "注册时间")
    private Timestamp register;

    @Column(name = "grade",comment = "等级：>=90为管理员；99为超级管理员")
    private Integer grade;

    @TableField("state")
    @Column(name = "state",comment = "状态：0-禁用；1-正常；2-删除")
    private Integer state;

    @TableField("from")
    @Column(name = "from",comment = "来源")
    private String from;

}
