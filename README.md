# daniu-os-serve

daniu-os的后端实现，基于 Spring Boot 框架。

## 技术栈

- **Sa-token**：国产轻量级权限验证框架，官方文档请查阅 [sa-token.cc](https://sa-token.cc/index.html)。
   - **JWT 支持**：通过 `sa-token-jwt` 进行 JWT 令牌管理，增强系统安全性。

- **文档生成工具**：结合 Knife4j，可以方便地生成 API 文档。

- **数据库操作**：使用 Mybatis Plus 提供便捷的数据库操作。

- **实用工具类**：使用 Hutool 提供各种实用工具类库；使用 Lombok 简化代码。

- **对象映射**：使用 MapStruct 进行 Java 对象之间的映射转换。

- **AOP 支持**：使用 Spring AOP 进行面向切面编程。

- **参数校验**：使用 Hibernate Validator 进行参数校验。

- **MP3 操作**：使用 mp3agic 库进行 MP3 文件的操作。

## 使用 Dockerfile 部署项目

本文档将指导您如何使用提供的 Dockerfile 来部署项目。项目根目录包含用于构建 Docker 镜像的 [Dockerfile](https://github.com/daniu-os/daniu-os-serve/Dockerfile)。

### 构建 Docker 镜像

1. 确保在项目的根目录下有 `daniu-os-serve.jar` 文件，通过 `mvn clean package` 进行构建。
2. 打开终端并导航到项目的根目录。
3. 执行以下命令来构建 Docker 镜像：

   ```sh
   docker build -t daniu-os-serve:latest .
   ```

### 运行 Docker 容器

构建镜像完成后，可以使用以下命令来运行 Docker 容器：

```sh
docker run -d -p 8085:8085 --name daniu-os-serve daniu-os-serve:latest
```

部署完成后，可以通过 `http://ip:8085/api` 来调用 API。