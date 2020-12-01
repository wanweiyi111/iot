package com.hzyw.basic.common.function;

@FunctionalInterface
public interface CacheSelector<T> {
    T select();
}
