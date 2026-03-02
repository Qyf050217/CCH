package com.zust.cch.common;

/**
 * 全局常量池
 */
public final class Constants {

    // 私有化构造方法，常量类不需要被实例化
    private Constants() {
    }

    // ==================== Redis Key 前缀 ====================

    /**
     * 邮箱验证码的 Redis Key 前缀
     */
    public static final String REDIS_KEY_MAIL_CODE = "cch:mail:code:";

    /**
     * CF 比赛排名数据的 Redis Key 前缀
     */
    public static final String REDIS_KEY_CF_STANDINGS = "cch:cf:standings:";

    // ==================== 业务默认值 ====================

    /**
     * 注册时默认的 Codeforces Handle
     */
    public static final String DEFAULT_CF_NAME = "未绑定";

    /**
     * 生成临时用户名时的前缀
     */
    public static final String TEMP_USER_PREFIX = "cch_user_";

    /**
     * 密码相关
     */
    public static final int passwordMaxLen = 20;
    public static final int passwordMinLen = 6;

    // ==================== Token 与安全验证 ====================

    /**
     * 前端请求头里存放 Token 的固定字段名
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * 验证码的有效期（单位：分钟）
     */
    public static final long MAIL_CODE_EXPIRE_MINUTES = 5L;
}