package com.gczx.application.common.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * UUID生成类，用于生成32位的UUID
 * @author leifeijin
 */
public final class UUIDUtils {
    private static Random random;

    static {
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private UUIDUtils() {
    }

    /**
     * 获取自动生成32位的UUID,对应数据库会员表的主键
     *
     * @return 32位的UUID
     */
    public static String getPkUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 自动生成11位的UUID
     * @return 11位的UUID
     */
    public static String getUserUuid() {
        return getUuid(11);
    }

    /**
     * 生成指定位数的UUID
     * @param length 指定位数
     * @return String
     */
    public static String getUuid(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            // 首字母不能为0
            result.append(random.nextInt(9) + 1);
        }
        return result.toString();
    }
}
