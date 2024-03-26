package pama1234.server.game.net;

public enum ServerIntention{
  /** 服务端没有获得玩家的意图指令，玩家需要发送或重新发送指令 */
  IntentionRequired,
  /** 玩家的意图需要登陆才能执行 */
  LoginRequired,
  /** 发送房间基本信息 */
  SendRoomInfo,
  /** 发送游戏中玩家可获得的信息（视野内的粒子位置和速度等） */
  SendGameCache,
  /** 发送服务器的类型（单房间或多房间） */
  SendServerType,
  /** 新玩家加入游戏 */
  PlayerJoinRoomEvent,
  /** 房间中的玩家离开游戏 */
  PlayerLeaveRoomEvent,
  /** 发送当前房间中的玩家列表 */
  SendRoomPlayerList,
  /** 发送玩家的角色信息 */
  SendAvatar,

  /** 确认（我也不知道到底确认了些什么） */
  Confirm,
  /** 中断，让服务端停止发送正在发送的东东 */
  Interrupt
}