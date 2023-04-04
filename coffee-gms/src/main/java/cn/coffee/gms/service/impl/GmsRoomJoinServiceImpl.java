package cn.coffee.gms.service.impl;

import cn.coffee.common.exception.BizException;
import cn.coffee.common.validation.ValidationUtil;
import cn.coffee.gms.entity.GmsRoomJoin;
import cn.coffee.gms.mapper.GmsRoomJoinMapper;
import cn.coffee.gms.service.IGmsRoomJoinService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 房间参加人员表-服务实现类
 *
 * @author lhz
 */
@Service
public class GmsRoomJoinServiceImpl extends ServiceImpl<GmsRoomJoinMapper,GmsRoomJoin> implements  IGmsRoomJoinService {

    @Override
    public GmsRoomJoin getMemberInRoom(long roomId, long memberId) {
        return this.getOne(new QueryWrapper<GmsRoomJoin>().eq("room_id",roomId).eq("member_id",memberId));
    }

    @Override
    public List<GmsRoomJoin> queryByRoom(long roomId) {
        return this.list(new QueryWrapper<GmsRoomJoin>().eq("room_id",roomId).orderByDesc("points"));
    }

    @Override
    public List<GmsRoomJoin> getTopThree(long roomId) {
        return this.page(new Page<>(0,3),new QueryWrapper<GmsRoomJoin>().eq("room_id",roomId).orderByDesc("points")).getRecords();
    }

    @Override
    public void joinRoom(GmsRoomJoin model) {
        if(ValidationUtil.isEmpty(model.getMemberId()) || ValidationUtil.isEmpty(model.getRoomId()) ||
        ValidationUtil.isEmpty(model.getMemberNick()) || ValidationUtil.isEmpty(model.getMemberIcon())){
            throw new BizException("参数获取失败，请稍后重试！");
        }
        GmsRoomJoin join=getMemberInRoom(model.getRoomId(),model.getMemberId());
        if(join==null){
            join=new GmsRoomJoin();
            join.setRoomId(model.getRoomId());
            join.setMemberId(model.getMemberId());
            join.setMemberNick(model.getMemberNick());
            join.setMemberIcon(model.getMemberIcon());
            join.setPoints(0);
        }
        join.setPoints(join.getPoints()+(ValidationUtil.isEmpty(model.getPoints()) ? 0 : model.getPoints()));
        join.setUpdateTime(new Date());
        this.saveOrUpdate(join);
    }
}
