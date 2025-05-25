/**
 * 用户信息接口
 */
export interface User {
  /**
   * 用户唯一ID (UUID)
   */
  uid: string;

  /**
   * 登录用户名
   */
  username: string;

  /**
   * 昵称
   */
  nickname: string;

  /**
   * 加密后的密码（BCrypt）
   */
  password: string;

  /**
   * 电子邮箱
   */
  email: string;

  /**
   * 手机号
   */
  phone: string;

  /**
   * 性别：0男 1女 2未知
   */
  sex: string;

  /**
   * IP地址
   */
  ip: string;

  /**
   * 头像URL
   */
  avatarUrl: string;

  /**
   * 用户状态
   */
  status: string;

  /**
   * 最后登录时间
   */
  lastLogin: string | null; // ISO 8601 格式字符串

  /**
   * 创建时间
   */
  createdAt: string; // ISO 8601 格式字符串

  /**
   * 更新时间
   */
  updatedAt: string; // ISO 8601 格式字符串
}
