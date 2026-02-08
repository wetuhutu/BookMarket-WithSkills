# 校园二手书交易平台

一个专为校园师生打造的二手书交易平台，让闲置书籍流动起来，传递知识与价值。

## 项目简介

本项目是一个前后端分离的二手书交易平台，解决校园里买书贵、卖书难的问题。用户可以发布闲置书籍、浏览他人书籍、在线交易、评价反馈，打造一个安全、便捷、高效的校园二手书交易环境。

## 技术栈

### 前端
- Vue 3 - 渐进式JavaScript框架
- Vue Router 4 - 官方路由管理器
- Pinia - Vue状态管理库
- Axios - HTTP客户端
- Tailwind CSS - 实用优先的CSS框架
- Vite - 下一代前端构建工具

### 后端
- Spring Boot 3.5.10 - Java应用开发框架
- MyBatis 3.0.5 - 持久层框架
- MySQL - 关系型数据库
- JWT - JSON Web Token认证
- 阿里云OSS - 对象存储服务

## 功能特性

### 用户功能
- 用户注册/登录
- 个人信息管理
- 头像上传
- 卖家认证

### 书籍功能
- 书籍发布
- 书籍浏览
- 分类筛选
- 价格筛选
- 搜索功能
- 排序功能
- 书籍详情
- 相似书籍推荐

### 交易功能
- 在线下单
- 订单管理
- 确认收货
- 取消订单


### 收藏功能
- 添加收藏
- 取消收藏
- 收藏列表

## 项目结构

```
demo/
├── src/                    # 前端源码
│   ├── api/              # API接口封装
│   ├── components/        # 公共组件
│   ├── router/           # 路由配置
│   ├── utils/            # 工具函数
│   └── views/           # 页面组件
├── backend-code/          # 后端源码
│   └── src/main/java/com/bookmarket/
│       ├── config/        # 配置类
│       ├── controller/    # 控制器
│       ├── service/       # 服务层
│       ├── mapper/        # 数据访问层
│       ├── dto/          # 数据传输对象
│       ├── vo/           # 视图对象
│       └── utils/        # 工具类
├── docs/                # 项目文档
└── logs/                # 开发日志
```

## 环境要求

### 前端
- Node.js >= 16.0.0
- npm >= 8.0.0

### 后端
- JDK >= 21
- Maven >= 3.6.0
- MySQL >= 8.0

## 本地运行

### 1. 克隆项目

```bash
git clone <repository-url>
cd demo
```

### 2. 配置数据库

创建MySQL数据库并执行SQL脚本：

```sql
-- 创建数据库
CREATE DATABASE bookmarket DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 导入数据表
USE bookmarket;
SOURCE docs/tables.sql;
```

### 3. 配置后端

修改 `backend-code/src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/bookmarket?useUnicode=true&characterEncoding=utf8
    username: your_username
    password: your_password
```

### 4. 启动后端

```bash
cd backend-code
mvn spring-boot:run
```

后端服务将在 http://localhost:8080/api 启动

### 5. 安装前端依赖

```bash
npm install
```

### 6. 启动前端

```bash
npm run dev
```

前端服务将在 http://localhost:5173 启动

### 7. 访问应用

打开浏览器访问：http://localhost:5173

## API文档

主要API接口：

- `GET /books/categories` - 获取书籍分类
- `GET /books/hot` - 获取热门书籍
- `GET /books/hot/top` - 获取TOP1热门书籍
- `GET /books` - 获取书籍列表（支持分页、筛选、排序）
- `GET /books/{id}` - 获取书籍详情
- `POST /auth/login` - 用户登录
- `POST /auth/register` - 用户注册
- `GET /users/profile` - 获取用户信息
- `PUT /users/profile` - 更新用户信息
- `POST /upload` - 文件上传

详细API文档请参考：[后端接口实现文档](docs/后端接口实现文档.md)

## 开发日志

- [第一周周志](logs/weekly/week1.md)
- [第二周周志](logs/weekly/week2.md)

## 测试报告

- [前后端联调测试报告](docs/前后端联调测试报告.md)
- [登录注册功能测试报告](docs/登录注册功能测试报告（更新版）.md)
- [获取我的书籍接口测试报告](docs/获取我的书籍接口测试报告.md)
- [获取收藏列表接口测试报告](docs/获取收藏列表接口测试报告.md)
- [获取热点书籍TOP1接口测试报告](docs/获取热点书籍TOP1接口测试报告.md)

## 常见问题

### 1. 后端启动失败

检查数据库连接配置是否正确，确保MySQL服务已启动。

### 2. 前端无法访问后端

检查后端服务是否正常运行，确认端口8080未被占用。

### 3. 跨域问题

后端已配置CORS，允许前端访问。如仍有问题，检查SecurityConfig配置。

### 4. 图片上传失败

检查阿里云OSS配置，确保AccessKey和SecretKey正确配置。

## 联系方式

如有问题或建议，欢迎提Issue或Pull Request。
