package com.example.util;

import com.example.exceptions.CheckException;

import java.util.stream.Stream;

/**
 * ==========================================
 * Created with IntelliJ IDEA.
 * User: 小破天
 * Date: 2018-05-30
 * Time: 0:50
 * 博客园：http://www.cnblogs.com/xiaopotian/
 * ===========================================
 */
public class CheckUtil {
    public static final String[] INVALID_NAMES = {"admin", "owner"};

    /**
     * 校验名字, 不成功抛出校验异常
     *
     * @param name
     */
    public static void checkName(String name) {
        Stream.of(INVALID_NAMES).filter(u -> u.equalsIgnoreCase(name))
                .findAny().ifPresent(u -> {
            throw new CheckException("name", name);
        });
    }
}
