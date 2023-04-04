package cn.coffee.ums.service.impl;

import cn.coffee.ums.entity.UmsUserRole;
import cn.coffee.ums.mapper.UmsUserRoleMapper;
import cn.coffee.ums.service.IUmsUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户角色关联表-服务实现类
 *
 * @author lhz
 */
@Service
public class UmsUserRoleServiceImpl extends ServiceImpl<UmsUserRoleMapper,UmsUserRole> implements  IUmsUserRoleService {

}
