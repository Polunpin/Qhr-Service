package com.tencent.config;
import org.springframework.boot.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 启用虚拟线程处理请求与异步任务，提升IO场景吞吐。
 */
@Configuration
public class VirtualThreadConfig {

  @Bean(destroyMethod = "close")
  public ExecutorService virtualThreadExecutor() {
    return Executors.newVirtualThreadPerTaskExecutor();
  }

  /** 把 Web 请求改成虚拟线程 */
  @Bean
  public TomcatProtocolHandlerCustomizer<?> tomcatVirtualThreadCustomizer(ExecutorService virtualThreadExecutor) {
    return protocolHandler -> protocolHandler.setExecutor(virtualThreadExecutor);
  }

  /** 为 @Async / 任务执行提供虚拟线程 */
  @Bean
  public TaskExecutor applicationTaskExecutor(ExecutorService virtualThreadExecutor) {
    return new TaskExecutorAdapter(virtualThreadExecutor);
  }
}
