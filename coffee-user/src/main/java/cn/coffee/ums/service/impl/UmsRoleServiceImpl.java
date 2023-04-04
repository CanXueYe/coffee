package cn.coffee.ums.service.impl;

import cn.coffee.ums.entity.UmsRole;
import cn.coffee.ums.entity.UmsUserRole;
import cn.coffee.ums.mapper.UmsRoleMapper;
import cn.coffee.ums.service.IUmsRoleService;
import cn.coffee.ums.service.IUmsUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色表-服务实现类
 *
 * @author lhz
 */
@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper,UmsRole> implements  IUmsRoleService {

    @Inject
    private IUmsUserRoleService userRoleService;

    @Override
    public List<UmsRole> getRoleByUser(String userId) {
        List<UmsUserRole> userRoles=userRoleService.list(new QueryWrapper<UmsUserRole>().eq("user_id",userId));
        List<String> roleIds=userRoles.stream().map(ur->ur.getRoleId()).collect(Collectors.toList());
        List<UmsRole> roles=this.listByIds(roleIds);
        return roles;
    }
}
