package cn.coffee.ums.service.impl;

import cn.coffee.ums.mapper.UmsUserMapper;
import cn.coffee.ums.service.IUmsUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.coffee.ums.entity.UmsUser;
import org.springframework.stereotype.Repository;

@Repository("cn.coffee.ums.service.impl")
public class UmsUserServiceImpl extends ServiceImpl<UmsUserMapper, UmsUser> implements IUmsUserService {
}
