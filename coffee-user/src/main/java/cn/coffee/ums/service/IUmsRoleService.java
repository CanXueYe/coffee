package cn.coffee.ums.service;

import cn.coffee.ums.entity.UmsRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户角色表-服务类
 *
 * @author lhz
 */
public interface IUmsRoleService extends IService<UmsRole> {

    /**
     * 查询用户的角色
     * @param userId
     * @return
     */
    List<UmsRole> getRoleByUser(String userId);

}
