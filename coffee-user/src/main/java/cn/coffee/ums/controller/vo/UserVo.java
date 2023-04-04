package cn.coffee.ums.controller.vo;

import cn.coffee.ums.entity.UmsUser;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class UserVo {

    private String id;

    private String name; // 姓名

    private String nick;

    private String mobile;

    private String email; // Email地址

    private String wechat; // 微信号

    private String qq; // QQ号

    private String avatar; // 头像

    private Integer gender; // 性别：0-未知；1-男；2-女
    private Date birthday; // 出生日期
    private String inviter; // 邀请人

    private String code; // 唯一编码
    private Timestamp register; // 注册时间
    private Integer grade; // 等级：>=90为管理员；99为超级管理员
    private Integer state; // 状态：0-禁用；1-正常；2-删除

    private String token;

    public static UserVo build(UmsUser user, String token){
        UserVo vo=new UserVo();
        vo.setId(user.getId());
        vo.setNick(user.getNick());
        vo.setMobile(user.getMobile());
        vo.setToken(token);
        return vo;
    }

    public static UserVo build(UmsUser user){
        UserVo vo=new UserVo();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }
}
