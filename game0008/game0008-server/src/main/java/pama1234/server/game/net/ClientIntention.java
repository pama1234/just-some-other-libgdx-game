package pama1234.server.game.net;

public enum ClientIntention{
  /** 玩家登录自己的账号 */
  Login,
  /** 玩家退出登录自己的账号 */
  Logout,
  /** 创建账号 */
  CreateAccount,
  /** 加入现有房间 */
  JoinRoom,
  /** 离开现有房间 */
  LeaveRoom,
  /** 在服务端创建房间（公开版本应当设置限制，例如每个账号每天的次数和时常限制） */
  CreateRemoteRoom,
  /** 将玩家创建的本地房间链接到服务端上 */
  LinkLocalRoom,
  /** 发送游戏中的控制信息（按键鼠标等） */
  SendGameCtrlCache,
  /** 获取服务端的类型 */
  RequireServerType,
  /** 让服务端生成一个角色 */
  RequireAvatar,

  /** 确认（我也不知道到底确认了些什么） */
  Confirm,
  /** 中断，让服务端停止发送正在发送的东东 */
  Interrupt
}