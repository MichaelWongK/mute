 # 基础镜像
FROM openjdk:8-jre
 # 维护者信息
MAINTAINER micheal.wang
# 挂载/home/myshop目录到主机
# VOLUME 指向了一个/tmp的目录，由于 Spring Boot 使用内置的Tomcat容器，Tomcat 默认使用/tmp作为工作目录。
# 这个命令的效果是：在宿主机的/var/lib/docker目录下创建一个临时文件并把它链接到容器中的/tmp目录
# 可在启动容器时 通过 -v 挂载目录 或在docker-compose中挂载
VOLUME /home/myshop
# 设置jar包版本
ENV APP_VERSION 0.0.1-SNAPSHOT
 # 设置时区
RUN echo "Asia/Shanghai" > /etc/timezone
# 新建app目录
RUN mkdir /app
# 复制打包完成后的jar文件到/app目录下
COPY provider-admin-service-$APP_VERSION.jar /app/app.jar
# 启动容器时执行
# 为了缩短 Tomcat 的启动时间，添加java.security.egd的系统属性指向/dev/urandom作为 ENTRYPOINT
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar", "--spring.profiles.active=prod"]
# 映射20880端口
EXPOSE 20880
