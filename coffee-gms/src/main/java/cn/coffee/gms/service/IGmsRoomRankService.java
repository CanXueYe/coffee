package cn.coffee.gms.service;

import cn.coffee.gms.entity.GmsRoomRank;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 房间排名表-服务类
 *
 * @author lhz
 */
public interface IGmsRoomRankService extends IService<GmsRoomRank> {

    /**
     * 获取房间的排名（已结算才有）
     * @param roomId
     * @return
     */
    GmsRoomRank getRoom(long roomId);

    /**
     * 结算排名
     * @param roomId
     */
    void settleRank(long roomId);

    /**
     * 判断用户是否中奖
     * @param roomId
     * @param memberId
     * @return
     */
    boolean isWinning(long roomId,long memberId);

}
