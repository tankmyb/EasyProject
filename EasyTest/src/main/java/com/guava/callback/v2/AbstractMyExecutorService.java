package com.guava.callback.v2;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;


public abstract class AbstractMyExecutorService
    extends AbstractExecutorService implements MyExecutorService {

  @Override protected final <T> MyFuture<T> newTaskFor(Runnable runnable, T value) {
    return MyFuture.create(runnable, value);
  }

  @Override protected final <T> MyFuture<T> newTaskFor(Callable<T> callable) {
    return MyFuture.create(callable);
  }

  @Override public MyFuture<?> submit(Runnable task) {
    return (MyFuture<?>) super.submit(task);
  }

  @Override public <T> MyFuture<T> submit(Runnable task, T result) {
    return (MyFuture<T>) super.submit(task, result);
  }

  @Override public <T> MyFuture<T> submit(Callable<T> task) {
    return (MyFuture<T>) super.submit(task);
  }
}