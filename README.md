# dictionary
个人代码库

[TOC]

[运行说明](#运行说明)

[代码内容](#代码内容)

* [类型互转](#类型互转)
* [日期处理](#日期处理)
* [提供接口方式](#提供接口方式)
* [定时任务](#定时任务)

## 运行说明
- 语言版本 java10+tomcat9  spring boot最新版

- 编译器版本 intellij idea 最新版
    - lombok插件(Slf4j日志打印、实体类简写等)//很好用，强推
    - easyCode插件(根据数据库自动生成实体类)//个人觉得没啥卵用，尤其引入lombok不需要get、set方法
    - SonarLint插件(代码检查插件)//可以帮助找出未使用的jar包，让代码变得更美观合理。但不能识别自定义断言
    - .ignore插件(git提交忽略文件)//有点好用

- maven项目编译

    - 不建议使用idea自带的maven，下载依赖速度很慢

    - 版本3.5.3

    - 添加阿里镜像

        位置`apache-maven-3.5.3\conf\settings.xml` 在mirrors标签里面添加如下

        ```java
        <mirror>
              <id>alimaven</id>
              <name>aliyun maven</name>
              <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
              <mirrorOf>central</mirrorOf>        
        </mirror>
        ```

    - 修改idea相应配置

- spring boot 无需手动编译，可以直接运行。注意mysql数据库配置。先在本地运行/doc/数据结构.sql文件

- 运行启动成功之后访问http://localhost:4399/dictionary/

## 代码内容

### 类型互转

List2String，String2List etc.

Date2String，String2Date etc.

### 日期处理

获取当前时间、格式化日期、日期截取、日期增加减少年、月、日、时、分、秒、毫秒等

### 提供接口方式

实体类型类字段提供枚举接口
### 定时任务
多线程定时任务框架搭建

