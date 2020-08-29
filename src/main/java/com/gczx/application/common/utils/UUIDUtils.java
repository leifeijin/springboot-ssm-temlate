package com.gczx.application.common.utils;

import java.util.Random;
import java.util.UUID;

/**
 * UUID生成类，用于生成32位的UUID
 * @author guofu
 */
public final class UUIDUtils {
    public UUIDUtils() {}

    /**
     * 获取自动生成32位的UUID,对应数据库会员表的主键
     * @return 32位的UUID
     */
    public static String getPKUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 自动生成11位的UUID
     * @return 11位的UUID
     */
    public static String getUserUUID() {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < 11; i++) {
            // 首字母不能为0
            result += (random.nextInt(9) + 1);
        }
        return result;
    }
}
