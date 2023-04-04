package cn.coffee.ums.controller;

import cn.coffee.common.utils.R;
import cn.coffee.common.validation.ValidationUtil;
import cn.coffee.ums.entity.UmsRole;
import cn.coffee.ums.service.IUmsRoleService;
import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * 用户角色表-管理层
 * @author Tomato
 */
@Log4j2
@RestController
@RequestMapping("/role")
public class UmsRoleController {

    @Inject
    private IUmsRoleService umsRoleService;

    /**
     * 获取角色列表
     * @return
     */
    @SaCheckLogin
    //@SaCheckRole("administrator")
    @GetMapping("/query")
    public R query(){
        List<UmsRole> roles=umsRoleService.list();
        return R.success().data(roles);
    }

    /**
     * 保存角色信息
     * @return
     */
    @SaCheckLogin
    @SaCheckRole("administrator")
    @PostMapping("/save")
    public R save(@RequestBody UmsRole role){
        if(ValidationUtil.isEmpty(role.getRoleName()) || ValidationUtil.isEmpty(role.getRoleCode())){
            return R.error().message("角色信息不可为空，请核实后重试！");
        }
        umsRoleService.saveOrUpdate(role);
        return R.success().message("保存角色成功！");
    }

    /**
     * 删除角色信息
     * @return
     */
    @SaCheckLogin
    @SaCheckRole("administrator")
    @GetMapping("/delete/{id}")
    public R delete(@PathVariable("id") String id){
        umsRoleService.removeById(id);
        return R.success().message("删除角色成功！");
    }

}