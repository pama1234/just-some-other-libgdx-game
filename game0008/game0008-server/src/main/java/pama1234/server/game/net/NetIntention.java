package pama1234.server.game.net;

import pama1234.util.Annotations.RedundantCache;

@RedundantCache
public enum NetIntention{
  /** 确认（我也不知道到底确认了些什么） */
  Confirm,
  /** 中断，让服务端停止发送正在发送的东东 */
  Interrupt
}
