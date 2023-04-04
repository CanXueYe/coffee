package cn.coffee.ums.controller;

import cn.coffee.common.exception.BizException;
import cn.coffee.common.utils.R;
import cn.coffee.common.utils.RedisUtil;
import cn.coffee.common.validation.ValidationUtil;
import cn.coffee.ums.controller.vo.UserRegVo;
import cn.coffee.ums.controller.vo.UserVo;
import cn.coffee.ums.entity.UmsUser;
import cn.coffee.ums.service.IUmsUserService;
import cn.coffee.ums.util.AESUtil;
import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Random;

/**
 * 用户控制类
 */
@Log4j2
@RestController
@RequestMapping("/user")
public class UmsUserController {

    @Inject
    private IUmsUserService userService;
    @Inject
    private RedisUtil redisUtil;

    /**
     * 获取用户信息
     * @return
     */
    @SaCheckLogin
    @GetMapping("/getUser")
    public R getUser(){
        try {
            System.out.println(StpUtil.getRoleList());
            System.out.println(StpUtil.hasRole("test"));;
            //校验是否登入
            if(!StpUtil.isLogin()) return R.noLogin();
            //获取用户ID
            String id=StpUtil.getLoginIdAsString();
            UmsUser user=userService.getById(id);
            return R.success().data(UserVo.build(user));
        }catch (Exception e){
            log.error(e.toString());
            throw new BizException("请联系管理员！");
        }
    }

    /**
     * 用户注册--密码验证
     * @param vo
     * @return
     */
    @PostMapping("/registeredByPwd")
    public R registeredByPwd(@RequestBody UserRegVo vo){
        if(ValidationUtil.isEmpty(vo.getMobile()) || ValidationUtil.isEmpty(vo.getPassword())){
            return R.error().message("手机号、密码不可为空，请验证后重试~");
        }
        String cipherPwd=AESUtil.cipherPwd(vo.getPassword());
        UmsUser user= UmsUser.builder().mobile(vo.getMobile()).password(cipherPwd).build();
        if(ValidationUtil.isNotEmpty(vo.getNickName())){
            user.setNick(vo.getNickName());
        }
        user.setCode(getCode());
        user.setRegister(new Timestamp(System.currentTimeMillis()));
        try{
            userService.save(user);
        }catch (Exception e){
            log.error(e.toString());
            return R.error().message("注册失败，请稍后重试~");
        }
        return R.success().message("注册成功~");
    }

    /**
     * 用户密码登入
     * @param model
     * @return
     */
    @PostMapping("/loginByPwd")
    public R loginByPwd(@RequestBody UmsUser model){
        if(ValidationUtil.isEmpty(model.getMobile())||ValidationUtil.isEmpty(model.getPassword())){
            return R.error().message("手机号和密码不可为空，请核实后重试~");
        }
        UmsUser user=userService.getOne(new QueryWrapper<UmsUser>().eq("mobile",model.getMobile()));
        if(user==null) return R.noRegistered();
        if(!AESUtil.checkPwd(model.getPassword(),user.getPassword())){
            return R.error().message("密码或手机号错误，请核实后重试~");
        }
        //用户登入
        StpUtil.login(user.getId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        //获取当前用户token
        String token=tokenInfo.getTokenValue();
        return R.success().data(UserVo.build(user,token));
    }

    /**
     * 用户登出
     * @return
     */
    @GetMapping("/logout")
    public R logout(){
        if(StpUtil.isLogin()){
            StpUtil.logout();
        }
        return R.success().message("成功退出登入~");
    }

    /**
     * 注销用户下线
     * @param id
     * @return
     */
    @GetMapping("/logoutById")
    public R logoutById(@RequestParam("id") String id){
        StpUtil.logout(id);
        return R.success().message("成功注销用户下线~");
    }

    /**
     * 踢用户下线
     * @param id
     * @return
     */
    @GetMapping("/kickOutById")
    public R kickOutById(@RequestParam("id") String id){
        StpUtil.kickout(id);
        return R.success().message("成功踢用户下线~");
    }


    @GetMapping("/getTokenToId/{token}")
    public R getTokenToId(@PathVariable("token") String token){
        String id= (String) StpUtil.getLoginIdByToken(token);
        return R.success().data(id);
    }


    /**
     * 获取邀请码
     */
    public static String getCode(){
        String SYMBOLS="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";//11 数字和26个字母组成
        Random RANDOM = new SecureRandom();
        char[] nonceChars = new char[6];//指定长度为6位/自己可以要求设置
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }
}
