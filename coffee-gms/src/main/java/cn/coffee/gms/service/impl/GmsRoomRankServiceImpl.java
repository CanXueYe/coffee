package cn.coffee.gms.service.impl;

import cn.coffee.common.exception.BizException;
import cn.coffee.gms.entity.GmsRoomJoin;
import cn.coffee.gms.entity.GmsRoomRank;
import cn.coffee.gms.mapper.GmsRoomRankMapper;
import cn.coffee.gms.service.IGmsRoomJoinService;
import cn.coffee.gms.service.IGmsRoomRankService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * 房间排名表-服务实现类
 *
 * @author lhz
 */
@Service
public class GmsRoomRankServiceImpl extends ServiceImpl<GmsRoomRankMapper,GmsRoomRank> implements  IGmsRoomRankService {

    @Inject
    private IGmsRoomJoinService roomJoinService;

    @Override
    public GmsRoomRank getRoom(long roomId) {
        GmsRoomRank rank=this.getOne(new QueryWrapper<GmsRoomRank>().eq("room_id",roomId));
        if(rank==null){
            throw new BizException("当前房间未结算，请稍后重试！");
        }
        return rank;
    }

    @Override
    public void settleRank(long roomId) {
        List<GmsRoomJoin> list=roomJoinService.getTopThree(roomId);
        GmsRoomRank rank=new GmsRoomRank();
        rank.setRoomId(roomId);
        rank.setCreateTime(new Date());
        if(list.size()>=1){
            rank.setRankNo1(list.get(0).getMemberId());
            rank.setRankNo1Points(list.get(0).getPoints());
            rank.setRankNo1Icon(list.get(0).getMemberIcon());
            rank.setRankNo1Nick(list.get(0).getMemberNick());
        }
        if(list.size()>=2){
            rank.setRankNo2(list.get(1).getMemberId());
            rank.setRankNo2Points(list.get(1).getPoints());
            rank.setRankNo2Icon(list.get(1).getMemberIcon());
            rank.setRankNo2Nick(list.get(1).getMemberNick());
        }
        if(list.size()>=3){
            rank.setRankNo3(list.get(2).getMemberId());
            rank.setRankNo3Points(list.get(2).getPoints());
            rank.setRankNo3Icon(list.get(2).getMemberIcon());
            rank.setRankNo3Nick(list.get(2).getMemberNick());
        }
        this.save(rank);
    }

    @Override
    public boolean isWinning(long roomId, long memberId) {
        GmsRoomRank rank=getRoom(roomId);
        boolean isWin=false;
        if(rank.getRankNo1()==memberId || rank.getRankNo2()==memberId || rank.getRankNo2()==memberId){
            isWin=true;
        }
        return isWin;
    }
}
