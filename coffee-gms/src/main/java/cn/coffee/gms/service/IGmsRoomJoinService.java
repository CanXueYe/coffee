package cn.coffee.gms.service;

import cn.coffee.gms.entity.GmsRoomJoin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 房间参加人员表-服务类
 *
 * @author lhz
 */
public interface IGmsRoomJoinService extends IService<GmsRoomJoin> {

    /**
     * 获取用户在房间里的信息
     * @param roomId
     * @param memberId
     * @return
     */
    GmsRoomJoin getMemberInRoom(long roomId,long memberId);

    /**
     * 获取房间参与的所有人员--按积分排序
     * @param roomId
     * @return
     */
    List<GmsRoomJoin> queryByRoom(long roomId);

    /**
     * 获取前三名用户
     * @return
     */
    List<GmsRoomJoin> getTopThree(long roomId);

    /**
     * 用户参加房间--兼容保存点数
     * @param model
     */
    void joinRoom(GmsRoomJoin model);

}
