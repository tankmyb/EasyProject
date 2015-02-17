package com.disruptor.example.disruptor.immutable;

public interface EventAccessor<T>
{
    T take(long sequence);
}
