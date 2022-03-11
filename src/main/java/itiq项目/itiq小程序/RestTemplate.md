# 1. RestTemplate 简介

RestTemplate 是 Spring 框架提供的一个调用 Restful 服务的抽象层。

它是 Spring 对 httpClient 的再封装工具类，采用模板模式抽象出来，类似于 jdbcTemplate。

从 Spring5.0 开始，非阻塞的、反应式的 org.springframework.web.reactive.client.WebClient 提供了 RestTemplate 的现代替代方案，它对同步和异步以及流式处理场景都提供了高效的支持。RestTemplate 将在将来的版本中被弃用，并且不会添加主要的新特性。有关更多详细信息和示例代码，请参阅 Spring 框架参考文档的WebClient部分。

# 2. RestTemplate 的实例化

## 2.1 基于默认设置创建 RestTemplate

使用默认设置创建 RestTemplate 的新实例。默认 HttpMessageConverters 已初始化。

```java
	public RestTemplate() {
		this.messageConverters.add(new ByteArrayHttpMessageConverter());
		this.messageConverters.add(new StringHttpMessageConverter());
		this.messageConverters.add(new ResourceHttpMessageConverter(false));
		try {
			this.messageConverters.add(new SourceHttpMessageConverter<>());
		}
		catch (Error err) {
			// Ignore when no TransformerFactory implementation is available
		}
		this.messageConverters.add(new AllEncompassingFormHttpMessageConverter());

		if (romePresent) {
			this.messageConverters.add(new AtomFeedHttpMessageConverter());
			this.messageConverters.add(new RssChannelHttpMessageConverter());
		}

		if (jackson2XmlPresent) {
			this.messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
		}
		else if (jaxb2Present) {
			this.messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
		}

		if (jackson2Present) {
			this.messageConverters.add(new MappingJackson2HttpMessageConverter());
		}
		else if (gsonPresent) {
			this.messageConverters.add(new GsonHttpMessageConverter());
		}
		else if (jsonbPresent) {
			this.messageConverters.add(new JsonbHttpMessageConverter());
		}

		if (jackson2SmilePresent) {
			this.messageConverters.add(new MappingJackson2SmileHttpMessageConverter());
		}
		if (jackson2CborPresent) {
			this.messageConverters.add(new MappingJackson2CborHttpMessageConverter());
		}

		this.uriTemplateHandler = initUriTemplateHandler();
	}
```

## 2.2 基于给定的 ClientHttpRequestFactory 创建 RestTemplate

需要传入要使用的 HTTP 请求工厂：requestFactory

```java
	public RestTemplate(ClientHttpRequestFactory requestFactory) {
		this();
		setRequestFactory(requestFactory);
	}
```

常用的请求工厂：

1. SimpleClientHttpRequestFactory

ClientHttpRequestFactory 使用标准 JDK 工具实现

```java
public class SimpleClientHttpRequestFactory implements ClientHttpRequestFactory, AsyncClientHttpRequestFactory {

	private static final int DEFAULT_CHUNK_SIZE = 4096;


	/**
	* 此请求工厂使用的代理
	*/
	@Nullable
	private Proxy proxy;

	/**
	* 指示此请求工厂是否在内部缓冲请求主体，默认为 true
	* 通过 POST 或 PUT 发送大量数据时，建议将此属性设置为 false，以免内存不足
	*/
	private boolean bufferRequestBody = true;

	/**
	* 设置不在本地缓冲请求主体时写入每个块的字节数
	* 请注意，此参数仅在bufferRequestBody设置为false时使用，并且内容长度事先未知
	*/
	private int chunkSize = DEFAULT_CHUNK_SIZE;

	/**
	* 设置基础URLConnection的连接超时（以毫秒为单位）
	* 超时值为0指定无限超时
	* 默认值是系统的默认超时
	*/
	private int connectTimeout = -1;

	/**
	* 设置基础URLConnection的读取超时（以毫秒为单位）
	* 超时值为0指定无限超时
	* 默认值是系统的默认超时
	*/
	private int readTimeout = -1;

	/**
	* 设置是否可以将基础URLConnection设置为“输出流”模式，默认值为true
	* 启用输出流时，无法自动处理身份验证和重定向
	*/
	private boolean outputStreaming = true;

	/**
	* 为此请求工厂设置任务执行器
	* 创建异步请求需要设置此属性
	*/
	@Nullable
	private AsyncListenableTaskExecutor taskExecutor;

	// 上述属性的 GET / SET 方法省略 ...

	/**
	* 创建 HTTP 请求客户端
	*/
	@Override
	public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
		// 开启一个 HttpURLConnection
		HttpURLConnection connection = openConnection(uri.toURL(), this.proxy);
		// 设置超时时间、请求方法等一些参数到 connection
		prepareConnection(connection, httpMethod.name());

		if (this.bufferRequestBody) {
			return new SimpleBufferingClientHttpRequest(connection, this.outputStreaming);
		}
		else {
			return new SimpleStreamingClientHttpRequest(connection, this.chunkSize, this.outputStreaming);
		}
	}

	/**
	 * 创建异步的 HTTP 请求客户端
	 */
	@Override
	public AsyncClientHttpRequest createAsyncRequest(URI uri, HttpMethod httpMethod) throws IOException {
		Assert.state(this.taskExecutor != null, "Asynchronous execution requires TaskExecutor to be set");

		HttpURLConnection connection = openConnection(uri.toURL(), this.proxy);
		prepareConnection(connection, httpMethod.name());

		if (this.bufferRequestBody) {
			return new SimpleBufferingAsyncClientHttpRequest(
					connection, this.outputStreaming, this.taskExecutor);
		}
		else {
			return new SimpleStreamingAsyncClientHttpRequest(
					connection, this.chunkSize, this.outputStreaming, this.taskExecutor);
		}
	}

	/**
	 * 打开并返回到给定URL的连接
	 * 默认实现使用给定的代理（如果存在）来打开连接
	 */
	protected HttpURLConnection openConnection(URL url, @Nullable Proxy proxy) throws IOException {
		URLConnection urlConnection = (proxy != null ? url.openConnection(proxy) : url.openConnection());
		if (!HttpURLConnection.class.isInstance(urlConnection)) {
			throw new IllegalStateException("HttpURLConnection required for [" + url + "] but got: " + urlConnection);
		}
		return (HttpURLConnection) urlConnection;
	}

	/**
	 * 用于准备给定HttpURLConnection的模板方法
	 * 默认实现为输入和输出准备连接，并设置HTTP方法
	 */
	protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
		if (this.connectTimeout >= 0) {
			connection.setConnectTimeout(this.connectTimeout);
		}
		if (this.readTimeout >= 0) {
			connection.setReadTimeout(this.readTimeout);
		}

		connection.setDoInput(true);

		if ("GET".equals(httpMethod)) {
			connection.setInstanceFollowRedirects(true);
		}
		else {
			connection.setInstanceFollowRedirects(false);
		}

		if ("POST".equals(httpMethod) || "PUT".equals(httpMethod) ||
				"PATCH".equals(httpMethod) || "DELETE".equals(httpMethod)) {
			connection.setDoOutput(true);
		}
		else {
			connection.setDoOutput(false);
		}

		connection.setRequestMethod(httpMethod);
	}

}
```

2. HttpComponentsClientHttpRequestFactory

clienthttpprequestfactory 使用 Apache HttpComponents HttpClient 创建请求

允许使用预先配置的 HttpClient 实例，可能包括身份验证、HTTP连接池等

```java
public class HttpComponentsClientHttpRequestFactory implements ClientHttpRequestFactory, DisposableBean {

	private HttpClient httpClient;

	@Nullable
	private RequestConfig requestConfig;

	private boolean bufferRequestBody = true;


	/**
	 * 使用基于系统默认的 HttpClient 创建 HttpComponentsClientHttpRequestFactory 的新实例。
	 */
	public HttpComponentsClientHttpRequestFactory() {
		this.httpClient = HttpClients.createSystem();
	}

	/**
	 * 使用开发人员给定的 HttpClient 实例创建 HttpComponentsClientHttpRequestFactory 的新实例。
	 */
	public HttpComponentsClientHttpRequestFactory(HttpClient httpClient) {
		this.httpClient = httpClient;
	}


	public void setHttpClient(HttpClient httpClient) {
		Assert.notNull(httpClient, "HttpClient must not be null");
		this.httpClient = httpClient;
	}

	public HttpClient getHttpClient() {
		return this.httpClient;
	}

	/**
	 * 设置 RequestConfig 的连接超时
	 * 超时值为0指定无限超时
	 */
	public void setConnectTimeout(int timeout) {
		Assert.isTrue(timeout >= 0, "Timeout must be a non-negative value");
		this.requestConfig = requestConfigBuilder().setConnectTimeout(timeout).build();
	}

	/**
	 * 设置 RequestConfig 从连接管理器请求连接时使用的超时（以毫秒为单位）
	 * 超时值为0指定无限超时
	 */
	public void setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.requestConfig = requestConfigBuilder()
				.setConnectionRequestTimeout(connectionRequestTimeout).build();
	}

	/**
	 * 设置 RequestConfig 套接字读取超时
	 * 超时值为0指定无限超时
	 */
	public void setReadTimeout(int timeout) {
		Assert.isTrue(timeout >= 0, "Timeout must be a non-negative value");
		this.requestConfig = requestConfigBuilder().setSocketTimeout(timeout).build();
	}

	public void setBufferRequestBody(boolean bufferRequestBody) {
		this.bufferRequestBody = bufferRequestBody;
	}

	@Override
	public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
		HttpClient client = getHttpClient();

		HttpUriRequest httpRequest = createHttpUriRequest(httpMethod, uri);
		postProcessHttpRequest(httpRequest);
		HttpContext context = createHttpContext(httpMethod, uri);
		if (context == null) {
			context = HttpClientContext.create();
		}

		// Request configuration not set in the context
		if (context.getAttribute(HttpClientContext.REQUEST_CONFIG) == null) {
			// Use request configuration given by the user, when available
			RequestConfig config = null;
			if (httpRequest instanceof Configurable) {
				config = ((Configurable) httpRequest).getConfig();
			}
			if (config == null) {
				config = createRequestConfig(client);
			}
			if (config != null) {
				context.setAttribute(HttpClientContext.REQUEST_CONFIG, config);
			}
		}

		if (this.bufferRequestBody) {
			return new HttpComponentsClientHttpRequest(client, httpRequest, context);
		}
		else {
			return new HttpComponentsStreamingClientHttpRequest(client, httpRequest, context);
		}
	}


	private RequestConfig.Builder requestConfigBuilder() {
		return (this.requestConfig != null ? RequestConfig.copy(this.requestConfig) : RequestConfig.custom());
	}

	@Nullable
	protected RequestConfig createRequestConfig(Object client) {
		if (client instanceof Configurable) {
			RequestConfig clientRequestConfig = ((Configurable) client).getConfig();
			return mergeRequestConfig(clientRequestConfig);
		}
		return this.requestConfig;
	}

	protected RequestConfig mergeRequestConfig(RequestConfig clientConfig) {
		if (this.requestConfig == null) {  // nothing to merge
			return clientConfig;
		}

		RequestConfig.Builder builder = RequestConfig.copy(clientConfig);
		int connectTimeout = this.requestConfig.getConnectTimeout();
		if (connectTimeout >= 0) {
			builder.setConnectTimeout(connectTimeout);
		}
		int connectionRequestTimeout = this.requestConfig.getConnectionRequestTimeout();
		if (connectionRequestTimeout >= 0) {
			builder.setConnectionRequestTimeout(connectionRequestTimeout);
		}
		int socketTimeout = this.requestConfig.getSocketTimeout();
		if (socketTimeout >= 0) {
			builder.setSocketTimeout(socketTimeout);
		}
		return builder.build();
	}

	protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
		switch (httpMethod) {
			case GET:
				return new HttpGet(uri);
			case HEAD:
				return new HttpHead(uri);
			case POST:
				return new HttpPost(uri);
			case PUT:
				return new HttpPut(uri);
			case PATCH:
				return new HttpPatch(uri);
			case DELETE:
				return new HttpDelete(uri);
			case OPTIONS:
				return new HttpOptions(uri);
			case TRACE:
				return new HttpTrace(uri);
			default:
				throw new IllegalArgumentException("Invalid HTTP method: " + httpMethod);
		}
	}

	protected void postProcessHttpRequest(HttpUriRequest request) {
	}

	@Nullable
	protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
		return null;
	}

	@Override
	public void destroy() throws Exception {
		HttpClient httpClient = getHttpClient();
		if (httpClient instanceof Closeable) {
			((Closeable) httpClient).close();
		}
	}

	private static class HttpDelete extends HttpEntityEnclosingRequestBase {

		public HttpDelete(URI uri) {
			super();
			setURI(uri);
		}

		@Override
		public String getMethod() {
			return "DELETE";
		}
	}

}
```

## 2.3 使用 HttpMessageConverter 的列表创建 RestTemplate 的新实例

```java
	public RestTemplate(List<HttpMessageConverter<?>> messageConverters) {
		Assert.notEmpty(messageConverters, "At least one HttpMessageConverter required");
		this.messageConverters.addAll(messageConverters);
		this.uriTemplateHandler = initUriTemplateHandler();
	}
```

# 3. RestTemplate 的使用

## 3.1 创建请求工厂类

HTTP 请求
```java
public class HttpClientRequestFactory extends SimpleClientHttpRequestFactory {
}
```

HTTPS 请求
```java
public class HttpsClientRequestFactory extends SimpleClientHttpRequestFactory {

    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) {
        try {
            if (!(connection instanceof HttpsURLConnection)) {
                throw new RuntimeException("An instance of HttpsURLConnection is expected");
            }

            HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;

            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }
                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }

                    }
            };
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            httpsConnection.setSSLSocketFactory(new MyCustomSSLSocketFactory(sslContext.getSocketFactory()));

            httpsConnection.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });

            super.prepareConnection(httpsConnection, httpMethod);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * We need to invoke sslSocket.setEnabledProtocols(new String[] {"SSLv3"});
     * see http://www.oracle.com/technetwork/java/javase/documentation/cve-2014-3566-2342133.html (Java 8 section)、
     * SSLSocketFactory用于创建 SSLSockets
     */
    private static class MyCustomSSLSocketFactory extends SSLSocketFactory {

        private final SSLSocketFactory delegate;

        public MyCustomSSLSocketFactory(SSLSocketFactory delegate) {
            this.delegate = delegate;
        }

        // 返回默认启用的密码套件。除非一个列表启用，对SSL连接的握手会使用这些密码套件。
        // 这些默认的服务的最低质量要求保密保护和服务器身份验证
        @Override
        public String[] getDefaultCipherSuites() {
            return delegate.getDefaultCipherSuites();
        }

        // 返回的密码套件可用于SSL连接启用的名字
        @Override
        public String[] getSupportedCipherSuites() {
            return delegate.getSupportedCipherSuites();
        }


        @Override
        public Socket createSocket(final Socket socket, final String host, final int port,
                                   final boolean autoClose) throws IOException {
            final Socket underlyingSocket = delegate.createSocket(socket, host, port, autoClose);
            return overrideProtocol(underlyingSocket);
        }


        @Override
        public Socket createSocket(final String host, final int port) throws IOException {
            final Socket underlyingSocket = delegate.createSocket(host, port);
            return overrideProtocol(underlyingSocket);
        }

        @Override
        public Socket createSocket(final String host, final int port, final InetAddress localAddress,
                                   final int localPort) throws
                IOException {
            final Socket underlyingSocket = delegate.createSocket(host, port, localAddress, localPort);
            return overrideProtocol(underlyingSocket);
        }

        @Override
        public Socket createSocket(final InetAddress host, final int port) throws IOException {
            final Socket underlyingSocket = delegate.createSocket(host, port);
            return overrideProtocol(underlyingSocket);
        }

        @Override
        public Socket createSocket(final InetAddress host, final int port, final InetAddress localAddress,
                                   final int localPort) throws
                IOException {
            final Socket underlyingSocket = delegate.createSocket(host, port, localAddress, localPort);
            return overrideProtocol(underlyingSocket);
        }

        private Socket overrideProtocol(final Socket socket) {
            if (!(socket instanceof SSLSocket)) {
                throw new RuntimeException("An instance of SSLSocket is expected");
            }
            ((SSLSocket) socket).setEnabledProtocols(new String[]{"TLSv1"});
            return socket;
        }
    }

}
```

## 3.2 创建配置类 RestTemplateConfig

在配置类中创建 RestTemplate 实例并放入容器

```java
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(new HttpsClientRequestFactory());
    }

    @Bean(name = "RestTemplateForHttp")
    public RestTemplate restTemplateForHttp() {
        return new RestTemplate(new HttpClientRequestFactory());
    }
}
```

## 3.3 使用实例化的 RestTemplate 对象完成业务

```java
    /**
     * 定时任务：更新数据库公司表(6.8add)
     * 每天 22 点执行一次
     */
    @Scheduled(cron = "0 0 22 * * ?") // 每天22点执行一次
    public void updateCompany() {
        String url = "http://101.37.64.23:8088/epiroc/open-api/machines-companies";
        ResponseEntity<ResultForRestTemplate> responseEntity = restTemplate.getForEntity(url, ResultForRestTemplate.class);
        ResultForRestTemplate result = responseEntity.getBody();
        Map<String, List<Map<String, String>>> data = (Map<String, List<Map<String, String>>>) result.getData();
        List<Map<String, String>> companyList = data.get("companyList");
        log.info("开始执行数据库公司表的更新");
        int addRow = 0;
        List<String> updateNameList = new ArrayList<>();
        for (Map<String, String> companyInfo: companyList) {
            Company company = new Company();
            String companyId = companyMapper.queryCompanyId(companyInfo.get("c_name"));
            if (companyId == null || companyId == "") {
                updateNameList.add(companyInfo.get("c_name"));
                addRow += 1;
                company.setCompanyCode(companyInfo.get("code"));
                company.setCompanyName(companyInfo.get("c_name"));
                company.setProvince(companyInfo.get("province"));
                company.setSalesman(companyInfo.get("salesman"));
                company.setChannel(companyInfo.get("channel"));
                company.setDealer(companyInfo.get("dealer"));
                company.setDealerType(companyInfo.get("dealer_type"));
                company.setProject(companyInfo.get("project"));
                company.setKeyAccount(companyInfo.get("key_account"));
                company.setRemark(companyInfo.get("remark"));
                company.setCreateBy("auto update");
                company.setCreateTime(DateUtils.getDate());
                company.setUpdateBy("auto update");
                company.setUpdateTime(DateUtils.getDate());
                companyMapper.insert(company);
            }
        }
        if (updateNameList.size() > 0) {
            for (String name: updateNameList) {
                log.info("公司：" + name + "  已添加至数据库");
            }
        }
        log.info("更新数据库完成，共添加" + addRow + "条新数据");
    }
```