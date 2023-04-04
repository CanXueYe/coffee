package cn.coffee.gms.service.impl;

import cn.coffee.common.exception.BizException;
import cn.coffee.common.utils.DateTimeUtil;
import cn.coffee.common.utils.StringUtil;
import cn.coffee.common.validation.ValidationUtil;
import cn.coffee.gms.entity.GmsRoom;
import cn.coffee.gms.mapper.GmsRoomMapper;
import cn.coffee.gms.service.IGmsRoomRankService;
import cn.coffee.gms.service.IGmsRoomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;

/**
 * 骰子房间表-服务实现类
 *
 * @author lhz
 */
@Service
public class GmsRoomServiceImpl extends ServiceImpl<GmsRoomMapper,GmsRoom> implements  IGmsRoomService {

    @Inject
    private IGmsRoomRankService roomRankService;

    @Override
    public void openRoom(GmsRoom room) {
        if(ValidationUtil.isEmpty(room.getTitle()) || ValidationUtil.isEmpty(room.getConsumeIntegral()) ||
                ValidationUtil.isEmpty(room.getDuration()) ||ValidationUtil.isEmpty(room.getBeginTime()) ||
                ValidationUtil.isEmpty(room.getHomeMemberId())){
            throw new BizException("参数缺失，请联系管理员！");
        }
        //初始化参数 -计算结束时间 -判断状态 -获取六位随机数
        Date time=new Date();
        room.setEndTime(DateTimeUtil.getMinuteAfter(room.getBeginTime(), room.getDuration()));
        room.setStatus(time.getTime()<room.getBeginTime().getTime() ? 0 : 1);
        room.setCode(StringUtil.getRandomString_A1(6));
        room.setCreateTime(time);
        room.setJoinNum(1);
        this.save(room);
    }

    @Override
    public void settleRoom(long id) {
        GmsRoom room = this.getById(id);
        if(room==null){
            throw new BizException("房间不存在，请稍后重试！");
        }
        if(room.getStatus()==0){
            throw new BizException("未开启房间不允许关闭！");
        }
        if(room.getStatus()!=1){
            return;
        }
        room.setStatus(2);
        room.setSettleTime(new Date());
        //保存数据
        this.updateById(room);
        //统计排名
        roomRankService.settleRank(id);
    }

}
