package com.hzyw.basic.common.function;

@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t);
}
