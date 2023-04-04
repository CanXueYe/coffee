package cn.coffee.ums.controller;

import cn.coffee.common.utils.R;
import cn.coffee.common.validation.ValidationUtil;
import cn.coffee.ums.entity.UmsRole;
import cn.coffee.ums.entity.UmsUserRole;
import cn.coffee.ums.service.IUmsRoleService;
import cn.coffee.ums.service.IUmsUserRoleService;
import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * 用户角色关联表-管理层
 * @author Tomato
 */
@Log4j2
@RestController
@RequestMapping("/umsUserRole")
public class UmsUserRoleController {

    @Inject
    private IUmsUserRoleService umsUserRoleService;
    @Inject
    private IUmsRoleService roleService;

    /**
     * 查询用户的角色集
     * @return
     */
    @SaCheckLogin
    @SaCheckRole("administrator")
    @GetMapping("/queryByUser/{userId}")
    public R queryByUser(@PathVariable("userId") String userId){
        List<UmsRole> roles=roleService.getRoleByUser(userId);
        return R.success().data(roles);
    }

    /**
     * 保存用户关联角色信息
     * @return
     */
    @SaCheckLogin
    @SaCheckRole("administrator")
    @PostMapping("/save")
    public R save(@RequestBody UmsUserRole userRole){
        if(ValidationUtil.isEmpty(userRole.getUserId()) || ValidationUtil.isEmpty(userRole.getUserId())){
            return R.error().message("角色绑定角色信息不可为空，请核实后重试！");
        }
        umsUserRoleService.saveOrUpdate(userRole);
        return R.success().message("保存用户关联角色成功！");
    }

    /**
     * 删除用户关联角色信息
     * @return
     */
    @SaCheckLogin
    @SaCheckRole("administrator")
    @PostMapping("/delete")
    public R delete(@PathVariable("id") String id){
        umsUserRoleService.removeById(id);
        return R.error().message("删除用户关联角色信息成功！");
    }

}