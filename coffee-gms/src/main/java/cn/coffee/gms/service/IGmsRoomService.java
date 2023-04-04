package cn.coffee.gms.service;

import cn.coffee.gms.entity.GmsRoom;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 骰子房间表-服务类
 *
 * @author lhz
 */
public interface IGmsRoomService extends IService<GmsRoom> {

    /**
     * 创建房间
     * @param room
     */
    void openRoom(GmsRoom room);

    /**
     * 结算房间
     * @param id
     */
    void settleRoom(long id);


}
