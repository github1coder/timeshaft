# 该镜像需要依赖的基础镜像
FROM fabric8/java-alpine-openjdk8-jre
# 调整时区
RUN rm -f /etc/localtime \
&& ln -sv /usr/share/zoneinfo/Asia/Beijing /etc/localtime \
&& echo "Asia/Beijing" > /etc/timezone
# 将当前目录下的jar包复制到docker容器的/目录下
ADD target/after_end-0.0.1.jar after_end.jar
# 指定docker容器启动时运行jar包
ENTRYPOINT ["nohup", "java", "-jar","after_end.jar", ">nohup.out" , "&"]
