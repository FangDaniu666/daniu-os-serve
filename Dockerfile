FROM openjdk:17-jdk-slim

LABEL authors="FangDaniu"

WORKDIR /app

# cat /etc/timezone
# timedatectl
# 设置时区为上海（Asia/Shanghai）
# ENV TZ=Asia/Shanghai
# RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 安装字体库和常用工具
# RUN apt-get update && \
#     apt-get install -y --no-install-recommends \
#     fonts-dejavu \
#     fontconfig \
#     && rm -rf /var/lib/apt/lists/*

# 设置APT镜像
RUN sed -i 's/deb.debian.org/mirrors.ustc.edu.cn/g' /etc/apt/sources.list && \
    sed -i 's/security.debian.org/mirrors.ustc.edu.cn/g' /etc/apt/sources.list

# 更新APT源并安装一个软件包，例如vim
RUN apt-get update
# RUN apt-get install -y vim
RUN apt-get install -y curl
RUN apt-get install -y fonts-dejavu
RUN apt-get install -y fontconfig

# 清理APT缓存以减小镜像体积
RUN apt-get clean && rm -rf /var/lib/apt/lists/*

# 在运行时自动挂载 /tmp 目录为匿名卷
VOLUME /tmp

# 将构建的 Spring Boot 可执行 JAR 复制到容器中，重命名为 app.jar
COPY daniu-os-serve.jar /app.jar

# 指定容器启动时执行的命令
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]

# 暴露容器的端口
EXPOSE 8085

