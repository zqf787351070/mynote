itiq 小程序项目

# 1. Swagger 依赖

```xml
<!-- swagger -->
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger2 -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>

<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
<dependency>
    <groupId>commons-beanutils</groupId>
    <artifactId>commons-beanutils</artifactId>
    <version>1.7.0</version>
</dependency>
``` 

# 2. Swagger 的配置

创建配置类 SwaggerConfig
```java
@Configuration
@EnableSwagger2 // 开启Swagger
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class SwaggerConfig implements WebMvcConfigurer {

    /**
     * 显示swagger-ui.html文档展示页，必须注入swagger资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 配置Swagger的Bean实例
     * @return
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(true) // 是否启动swagger
                .groupName("Leo Zhu")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)) // 配置包扫描的方式
                .paths(PathSelectors.any()) // 配置过滤路径(允许的路径，配合分组使用)
                .build();
    }

    /**
     * 配置Swagger信息apiInfo
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("接口手册").description("by Leo Zhu")
                .version("1.0.0").build();
    }

}
```

# 3. Swagger 的常用注解

1. `@Api(tags = "")`：标注在 Controller 层的类上，用于指示该 Controller 的描述
2. `@ApiOperation(value = "")`：标注在方法上，用于指示该方法的描述
3. `@ApiImplicitParams(
   {@ApiImplicitParam(name = "", value = "", required = true),
   @ApiImplicitParam(name = "", value = "", required = true)})`：标注在方法上，用于指示该方法所需参数的描述
4. `@ApiModel(description = "")`：标注在实体类上，用于描述该实体类（一般是接收参数的实体类）
5. `@ApiModelProperty(value = "", required = true)`：标注在实体类的属性上，用于描述该属性

# 4. 使用示例 -- itiq 小程序项目