
创建maven项目：添加参数
archetypeCataLog
internal

##最常用的配置
###改随机端口
思考：固定端口为什么不能用？为什么要改随机端口？
1.如果在同一台服务器上，多个服务如果用同一个端口会造成端口冲突。
2，在现实的微服务（spring cloud、dubbo）开发中，开发人员是不用记住ip和端口的。
因此，我们一般在真实的开发环境下，是设置一个随机端口，就不用去管理端口了。也不会造成端口冲突。
```
server.port=${random.int[1024,9999]}

```

##自定义属性配置


##yml
springboot配置文件有两种，一种是properties结尾，一种是yaml或yml结尾
1. application.properties
2. application.yml
默认情况下是properties结尾的配置文件
   配置文件放在src/main/resources目录或者类路径/config下
   
###语法
```
server:
port: 9091
servlet:
context-path: /
```

##spring boot日志框架
logback、log4j
slf4j
从springboot的底层框架spring-boot-starter-logging可以看出，它依赖了3个框架分别为：slf4j、logback、log4j
### 分析1：slf4j、logback、log4j区别？
1. logback、log4j：日志实现框架、就是实现怎么记录日志的。
2. slf4j：提供了java中所有的日志框架的简单抽象（日志的门面设计模式），说白了就是一个日志api（没有实现类），他不能
单独使用。因此必须结合logback或log4j日志框架来实现。

### 分析2：spring boot的日志搭配
springboot2.0默认采用了slf4j + logback的日志搭配。
在开发过程中，我们都是采用了slf4j的api去记录日志，底层的实现就是根据配置logback或log4j日志框架。

### 为什么控制台的日志只输出了 info、warn、error？
因为springboot默认是info级别的，需要修改配置文件
```
logging:
  level:
    com.example.boot: trace
```
### 配置日志的生成存储路径和日志名称
```
logging:
  file:
#    path: e:\idea\logs
    name: e:/idea/logs/springboot.log
    #以下配置效果为：项目根目录下/output/logs/spring.log,默认的日志名为spring.log
    #path: output/logs
    #file.path和file.name同时配置的情况，只有file.name会生效，若没有指定具体路径，则会在项目的根目录下生成name.log文件
    #只配置file.path则会在该路径下生成默认的spring.log文件
```

## lombok
### 步骤1：idea搜索lombok插件
打开idea的settings面板，并选择plugins选项，然后点击 browse repositories
### 步骤2：安装并重启idea

### lombok核心注解@data
#### 什么是@data注解
@data 注解在实体类上，自动生成javabean的getter/setter方法，写构造器、equals等方法

#### pom文件添加依赖包
```
  <dependency>
       <groupId>org.projectlombok</groupId>
       <artifactId>lombok</artifactId>
       <version>1.18.20</version>
   </dependency>
```
### lombok第二核心注解@Slf4j
注解@Slf4j的作用就是代替一下代码
```
public static final Logger logger = LoggerFactory.getLogger(HelloController.class);
```

springboot异步框架
### 1.课程目标
熟悉spring的异步框架，学会使用异步@Async注解

### 2.为什么要用异步框架，它解决什么问题？
在springboot的日常开发中，一般都是同步调用的，但经常有特殊业务需要做异步处理，例如：新用户注册送100积分，下单成功发送push消息等等。
就以新用户注册为例，为什么要异步处理？
1）容错性，如果送积分出现异常，不会因为送积分而导致用户注册失败
因为用户注册是主要功能，送积分是次要功能，即使送积分异常也要提示用户注册成功，然后后面再针对积分异常做补偿处理。
2）提升性能，例如注册用户花了20ms，送积分花了50ms，如果同步的话，总耗时70ms，用异步的话，无需等待积分，故耗时20ms。
因此：异步能解决2个问题，性能和容错性。

### 3.springboot异步调用
只需要使用@Async注解即可实现方法的异步调用。

### 4.@Async异步调用例子
#### 步骤1：开启异步任务
采用@EnableAsync来开启异步任务支持，另外需要加入@Configuration来把当前类加入springIOC容器中。
```
@Configuration
@EnableAsync
public class SyncConfiguration {
}
```

#### 步骤2：在方法上标记异步调用
增加一个service类，用来做积分处理
```
   @Async
    public void addScore() {
        // TODO 模拟睡5s，用于赠送积分处理
        try {
            Thread.sleep(5 * 1000);
            log.info("------------积分处理------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
```

### 5.为什么要给@Async自定义线程池？
@Async注解，在默认情况下用的是SimpleAsyncTaskExecutor线程池，该线程池不是真正意义上你的线程池，因为线程不重用，每次调用都会新建一条线程。
可以通过控制台日志输出查看，每次打印的线程名都是[task-1]、[task-2]、[task-33]、[task-4]...递增的。
@Async注解异步框架提供多种线程
SimpleAsyncTaskExecutor：不是真的线程池，这个类不重用线程，每次调用都会创建一个新的线程。
SyncTaskExecutor：这个类没有实现异步调用，只是一个同步操作，只适用于不需要多线程的地方。
ConcurrentTaskExecutor：Executor的适配类，不推荐使用。如果ThreadPoolTaskEexecutor不满足要求时才会考虑使用这个类。
ThreadPoolTaskScheduler：可以使用cron（正则）表达式。
ThreadPoolTaskExecutor：最常使用，推荐。其实质是对java.util.concurrent.ThreadPoolExecutor的包装。

### 6.为@Async实现一个自定义线程池
#### 步骤1：配置线程池
```
 @Bean(name = "scorePoolTaskExecutor")
 public ThreadPoolTaskExecutor getScorePoolTaskExecutor() {
     ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
     // 核心线程数
     threadPoolTaskExecutor.setCorePoolSize(10);
     // 线程池维护线程的最大数量，只有在缓冲队列满了之后才会申请超过核心线程数的线程
     threadPoolTaskExecutor.setMaxPoolSize(100);
     // 缓存队列
     threadPoolTaskExecutor.setQueueCapacity(50);
     // 空闲时间，当超过了核心线程数之外的线程空闲时间到达之后会被销毁
     threadPoolTaskExecutor.setKeepAliveSeconds(200);
     // 异步方法内部线程名称
     threadPoolTaskExecutor.setThreadNamePrefix("score-");
     /*
     *   当线程池的任务缓存队列已满并且线程池中的线程数目达到maximumPoolSize，如果还有任务到来就会采取任务拒绝策略
     *   通常有以下四种策略：
     *   ThreadPlloExecutor.AbortPolicy：丢弃任务并抛出RejectedExecutionException异常。
     *   ThreadPlloExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
     *   ThreadPlloExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）。
     *   ThreadPlloExecutor.CallerRunsPolicy：重试添加当前的任务，自动重复调用execute()方法，直到成功。
     * */
     threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
     threadPoolTaskExecutor.initialize();

     return threadPoolTaskExecutor;
 }
```

#### 步骤2：为@Async指定线程池
```
@Async("scorePoolTaskExecutor")
 public void addScore2() {
     // TODO 模拟睡5s，用于赠送积分处理
     try {
         Thread.sleep(5 * 1000);
         log.info("------------积分处理------------");
     } catch (InterruptedException e) {
         e.printStackTrace();
     }
 }
```
### 练习
在现实互联网项目开发中，针对高并发的请求，一般的做法是高并发接口单独线程池隔离处理。
假设现在有2个高并发接口：
一个是修改用户的信息接口，刷新redis缓存
一个是下订单接口，发送app_push信息
方案：
设计2个线程池，分别用户刷新redis缓存、发送app_push信息。

## springboot集成swagger
###  一、目标
1. 弄清楚，为什么要用swagger，它解决了什么问题？
2. 编码实现2个springboot接口，让swagger自动生成接口文档

###  二、为什么要用swagger，它解决了什么问题？
随着springboot、springcloud等微服务的流行，在微服务的设计下，小公司微服务小的几十，大公司大的几百上万的微服务。
这么多的微服务必定产生了大量的接口调用，而接口的调用就必定要写接口文档。
在微服务盛行下，成千上万的接口文档编写，不可能靠人力来缩写，因此swagger就产生了，它采用自动化实现并解决了人力编写接口文档的问题，
它通过在接口及实体上添加几个注解的方式就能在项目启动后自动化生成接口文档。

swagger提供了一个全新的维护API文档的方式，有4大有点：
1. 自动生成文档：只需要少量的注解，swagger就可以根据代码自动生成API文档，很好的保证了文档的时效性。
2. 跨语言性，支持40多种语言。
3. swagger ui 呈现出来的是一份可交互式的API文档，我们可以直接在文档页面尝试API的调用，省去了准备复杂的调用参数的过程。
4. 还可以将文档规范导入相关的工具（例如SoapUI），这些工具将会为我们自动的创建自动化测试。

### 三、案例实战：把springboot的接口自动生成接口文档
#### 步骤1：po'm文件加入依赖包
```
   <!-- swagger -->
   <dependency>
       <groupId>io.springfox</groupId>
       <artifactId>springfox-swagger2</artifactId>
       <version>2.10.5</version>
   </dependency>
   <dependency>
       <groupId>io.springfox</groupId>
       <artifactId>springfox-swagger-ui</artifactId>
       <version>2.10.5</version>
   </dependency>
```

#### 步骤2：修改配置文件
1. 配置文件application.yml
   ```
   #表示是否开启swagger，一般线上环境是关闭的
   SPRING:
      SWAGGER2:
         ENABLED: true
   ```

2. 增加一个swagger配置类
```
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${SPRING.SWAGGER2.ENABLED}")
    private boolean swaggerEnabled;

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(swaggerEnabled)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.boot"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("springboot整合swagger")
                .termsOfServiceUrl("127.0.0.1:9090/test/user/update")
                .version("1.0")
                .build();
    }

}
```
以上注意点：
1. createRestApi() 这个方法一定要写上你的包名，代表需要生成接口文档的目录包

体验地址：http://127.0.0.1:9090/swagger-ui.html

### swagger常用注解
注解                                        用途                                               注解位置
@Api                                  描述类的作用                                  注解 于类上
@ApiOperation                 描述类的方法的作用                        注解于方法上
@ApiParam                        描述类方法参数的作用                    注解于方法的参数上
@ApiImplicitParams          描述类方法参数的作用                    注解于请求的方法上，包含一组参数说明
@ApiImplicitParam            描述类方法参数的作用                    注解于请求的方法上，对单个参数的说明
@ApiModel                        描述对象的作用                             注解于请求对象或者返回结果对象上
@ApiModelProperty          描述对象里字段的作用                    注解于请求对象或者返回结果对象里的字段上

#### 实例
```
@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation("根据id修改用户数据")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "用户主键", required = true, paramType = "path")
    })
    @GetMapping(value = "/u/{id}")
    public UserVo findById(@PathVariable Long id){
        UserVo userVo = new UserVo();
        userVo.setId(id);
        userVo.setName("swagger");
        userVo.setPassword("123456");
        return userVo;
    }

    @ApiOperation("添加用户")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserVo createUser(@RequestBody UserVo userVo){
        return userVo;
    }

}

@Data // lombok注解
@ApiModel(description = "用户对象")
public class UserVo {
    @ApiModelProperty("用户id")
    private Long id;
    @ApiModelProperty("用户昵称")
    private String name;
    @ApiModelProperty("用户密码")
    private String password;
}
```

#response统一格式
## 一、目标
1）弄清楚为什么要对springboot所有controller的response做统一格式封装？
2）学会用ResponseBodyAdvice接口和@ControllerAdvice注解

## 二、为什么要对springboot的接口返回值统一标准格式？
首先来看下，springboot默认情况下的response是什么格式的
### 第一种格式：response为String

### 第二种格式：response为Object

### 第三种格式：response为void

### 第四种格式：response为异常

以上4中情况，如果你和客户端（app，H5等前端）开发人员联调接口，他们会很懵逼，因为你给他们的接口没有一个统一的格式，客户端开发人员不知道如何处理返回值。
因此，我们应该统一response的标准格式。

## 三、定义response的标准格式
一般的response的标准格式包含3部分：
1. status状态值：代表本次请求response的状态结果。
2. response描述：对本次状态码的描述。
3. data数据：本次返回的数据。

## 四、初级程序员对response代码封装
对response的统一封装，是有一定的技术含量的，我们先来看下，初级程序员的封装，网上很多教程都是这么写的。
### 步骤1：把标准格式转化为代码
common.Result
### 步骤2：把状态码存在枚举类里面
common.ResultCodeEnum
### 步骤3：加一个体验类

结论：看到这里，应该能看到这样封装代码有很大弊端。
因为今后你每写一个接口，都要手工指定Result.suc()这行代码，多累啊。
如果你写这种代码推广给整个公司用，然后硬性规定代码必须这么写，所有程序员都会吐槽鄙视。

## 五、高级程序员对response代码封装
如果你在公司推广你的编码规范，为了避免被吐槽，我们必须优化代码。
优化的目标：不要每个接口都手动指定Result返回值。

### 步骤1：采用ResponseBodyAdvice技术来实现response的统一格式
springboot提供了ResponseBodyAdvice来帮我们处理
ResponseBodyAdvice的作用：拦截Controller方法的返回值，统一处理返回值/响应体，一般用来做response的统一格式、加解密签名等
先来看下ResponseBodyAdvice这个接口的源码。
```
public interface ResponseBodyAdvice<T> {

	/**
	 * Whether this component supports the given controller method return type
	 * and the selected {@code HttpMessageConverter} type.
	 * @param returnType the return type
	 * @param converterType the selected converter type
	 * @return {@code true} if {@link #beforeBodyWrite} should be invoked;
	 * {@code false} otherwise
	 */
	boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType);

	/**
	 * Invoked after an {@code HttpMessageConverter} is selected and just before
	 * its write method is invoked.
	 * @param body the body to be written
	 * @param returnType the return type of the controller method
	 * @param selectedContentType the content type selected through content negotiation
	 * @param selectedConverterType the converter type selected to write to the response
	 * @param request the current request
	 * @param response the current response
	 * @return the body that was passed in or a modified (possibly new) instance
	 */
	@Nullable
	T beforeBodyWrite(@Nullable T body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response);

}
```

### 步骤2：写一个ResponseBodyAdvice实现类
```
@ControllerAdvice(basePackages = "com.example.boot")
public class ResponseHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof String){
            return JsonUtil.objectToJson(Result.OK(o));
        }
        return Result.OK(o);
    }
}
```
以上代码，有2个地方需要重点讲解:
#### 1. @ControllerAdvice注解
@ControllerAdvice这是一个非常有用的注解，它的作用是增强Controller的扩展功能类。
那@ControllerAdvice对Controller增强了哪些扩展功能呢？主要体现在2方面：
1. 对Controller全局数据统一处理，例如：对response统一封装
2. 对Controller全局异常统一处理

在使用@ControllerAdvice时，还要特别注意，加上**basePackages**，
@ControllerAdvice(basePackages = "com.example.boot")，因为如果不加的话，它可是对整个系统的Controller做了扩展功能。
它会对某些特殊功能产生冲突，例如：不加的话，在使用swagger时会出现空白页异常。

#### 2. beforeBodyWrite方法体的response类型判断
```
 if (o instanceof String){
            return JsonUtil.objectToJson(Result.OK(o));
        }
```

## 全局异常处理器
### 一、目标
1. 弄懂为什么springboot需要**全局异常处理器**
2. 编码实战一个springboot**全局异常处理器**
3. 把**全局异常处理器**集成进c

### springboot为什么需要**全局异常处理器**
1. 先讲下什么是**全局异常处理器** <br>
   **全局异常处理器**就是把整个系统的异常统一自动处理，程序员可以做到不用谢try...catch
2. 那为什么需要全局异常呢？
- 第一个原因：不用强制写try-catch，由全局异常处理器统一捕获处理
```
异常返回信息：
{
  "timestamp": "2021-08-16T08:40:42.179+00:00",
  "status": 500,
  "error": "Internal Server Error",
  "path": "/test/error1"
}
```
这样的返回信息，不能直接返回到客户端，需要接入《接口返回值统一标准格式》

- 第二个原因：自定义异常只能由全局异常来捕获
```
   @GetMapping("/error2")
    public void error2(){
        throw new RuntimeException("");
    }

```

- 第三个原因：JSR300规范的Validator参数校验器，参数校验不通过会抛出异常，是无法使用try-catch语句直接捕获的，
只能使用全局异常处理器了。
  
### 案例实战：编码实现一个springboot**全局异常处理器**
#### 步骤1：封装异常内容，统一存储在枚举类中
把所有的未知运行异常都用`SYSTEM_ERROR(10000, "系统异常，请稍后再试");`来提示
```
public enum ResultCodeEnum {

    OK(200, "OK"),
    SYSTEM_ERROR(10000, "系统异常，请稍后再试");

    private Integer status;
    private String msg;

    private ResultCodeEnum(int status, String msg) {
        this.setStatus(status);
        this.setMsg(msg);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
```
步骤2：封装Controller异常结果
```
@Data
public class ErrorResult {
    /**
     * 异常状态码
     */
    private Integer status;
    /**
     * 用户看得见的异常，例如：用户名重复
     */
    private String message;
    /**
     * 异常名称
     */
    private String exception;
    /**
     * 异常的堆栈信息
     */
    private String errors;

    /**
     * 对异常提示语进行封装
     * @param resultCode
     * @param e
     * @param message
     * @return
     */
    public static ErrorResult fail(ResultCodeEnum resultCode, Throwable e, String message){
         ErrorResult result = ErrorResult.fail(resultCode, e);
         result.setMessage(message);
         return result;
    }

    /**
     * 对异常枚举进行封装
     * @param resultCode
     * @param e
     * @return
     */
    public static ErrorResult fail(ResultCodeEnum resultCode, Throwable e){
        ErrorResult result = new ErrorResult();
        result.setStatus(resultCode.getStatus());
        result.setException(e.getClass().getName());
        result.setMessage(resultCode.getMsg());
//        result.setErrors(e.getStackTrace().toString());
        return result;
    }

}
```
步骤3：加个全局异常处理器，对异常进行处理
```
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ErrorResult handleThrowable(Throwable e, HttpServletRequest request){
        // TODO 运行时异常，可以在这里记录，用于发异常邮件通知
        ErrorResult errorResult = ErrorResult.fail(ResultCodeEnum.SYSTEM_ERROR, e);
        log.error("URL:{}, 系统异常", request.getRequestURI(), e );
        return errorResult;
    }
}
```
handleThrowable方法的作用是：捕获运行时异常，并把异常统一封装为ErrorResult对象。
有几个细节需要注意：
1. @ControllerAdvice在《response统一格式封装》课程时讲过，它是增强Controller的扩展功能。而全局异常处理器，就是扩展功能之一。
2. @ExceptionHandler统一处理某一类异常，从而能够减少代码重复率和复杂度，@ExceptionHandler(Throwable.class)指处理Throwable的异常。
3. @ResponseStatus指定客户端收到的http状态码，这里配置500错误。客户端就显示500错误。

步骤4：体验效果
```

```
结果
```

```
### 封装一个自定义异常集成进**全局异常处理器**
#### 步骤1：封装一个自定义异常
自定义异常通常是集成RuntimeException
```
@Data
public class CustomException extends RuntimeException {

    protected Integer code;
    protected String message;

    public CustomException(ResultCodeEnum resultCode) {
        this.code = resultCode.getStatus();
        this.message = resultCode.getMsg();
    }

}
```
#### 步骤2：把自定义异常集成进**全局异常处理器**

```
/**
     *  自定义异常处理器
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public ErrorResult handleCustomException(CustomException e, HttpServletRequest request){
        ErrorResult errorResult = ErrorResult.builder().status(e.code)
                .message(e.message)
                .exception(e.getClass().getName())
                .build();
        log.warn("URL: {}, 业务异常{}", request.getRequestURL(), errorResult);
        return errorResult;
    }
```
#### 步骤3：体验效果
```
    @GetMapping("/error3")
    public void error3(){
        throw new CustomException(ResultCodeEnum.USER_EXISTED);
    }
```
结果
```
{
  "status": 20001,
  "message": "   用户已存在",
  "exception": "CustomException",
  "errors": null
}
```

### 四、案例实战：把**全局异常处理器**集成进**接口返回值统一标准格式**
目标：把**全局异常处理器**的json格式转换为**接口返回值统一标准格式**
```
{
  "status": 20001,
  "message": "   用户已存在",
  "exception": "CustomException",
  "errors": null
}
```
转换为
```
{
  "status": 20001,
  "message": "   用户已存在",
  "data": null,
}
```
#### 步骤1：ResponseHandler
```

    /**
     * 处理response的具体业务方法
     * @param o
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof ErrorResult) {
            ErrorResult errorResult = (ErrorResult) o;
            return Result.fail(errorResult.getStatus(), errorResult.getMessage());
        } else if (o instanceof String) {
            return JsonUtil.objectToJson(Result.OK(o));
        }
        return Result.OK(o);
    }
```
在 《接口返回值统一封装标准格式》基础上增加了
```
if (o instanceof ErrorResult) {
            ErrorResult errorResult = (ErrorResult) o;
            return Result.fail(errorResult.getStatus(), errorResult.getMessage());
        }
```

#### 步骤2：体验效果
```
{
  "status": 20002,
  "msg": "登录失败！",
  "data": null
}
```

## springboot整合redis
