//package cn.coffee.ums.config;
//
//
//import cn.coffee.ums.entity.UmsRole;
//import cn.coffee.ums.service.IUmsRoleService;
//import cn.dev33.satoken.stp.StpInterface;
//import com.google.common.collect.Lists;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import javax.inject.Inject;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@EnableWebMvc
//@Component
//public class authorGive implements StpInterface {
//
//    @Inject
//    private IUmsRoleService umsRoleService;
//
//    @Override
//    public List<String> getPermissionList(Object o, String s) {
//        return null;
//    }
//
//    @Override
//    public List<String> getRoleList(Object o, String s) {
//        //获取用户角色信息
//        List<UmsRole> roles = umsRoleService.getRoleByUser(o.toString());
//        List<String> roleCodes = Lists.newArrayList();
//        if(roles.size()>0){
//            roleCodes=roles.stream().map(r->r.getRoleCode()).collect(Collectors.toList());
//        }
//        return roleCodes;
//    }
//}
