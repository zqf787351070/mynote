# Spring Security 原理分析

## 1. Spring Security 过滤器链

SpringSecurity 采用的是责任链的设计模式，它有一条很长的过滤器链。各个过滤器说明如下：
1. WebAsyncManagerIntegrationFilter：将 Security 上下文与 Spring Web 中用于处理异步请求映射的 WebAsyncManager 进行集成。
2. SecurityContextPersistenceFilter：在每一次请求处理之前将该请求相关的安全上下文信息加载到 SecurityContextHolder 中，然后在该次请求处理完成之后，将 SecurityContextHolder 中关于这次请求的信息存储到一个仓库中，完毕后清空 SecurityContextHolder。
3. HeaderWriterFilter：用于将头信息加入到响应中。
4. CsrfFilter：用于处理跨站请求伪造。
5. 