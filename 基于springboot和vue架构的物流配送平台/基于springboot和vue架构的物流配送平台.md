# 基于Spring Boot + Vue架构的物流配送平台技术文档

## 文档信息

| 项目 | 内容 |
|------|------|
| 项目名称 | 物流配送平台 |
| 技术架构 | Spring Boot + Vue 3 |
| 文档版本 | V1.0 |
| 创建日期 | 2025年 |

---

## 1. 课题概述

### 1.1 项目定位

本项目是一款面向中小型物流企业的**Web端配送管理信息系统**，采用前后端分离架构设计，旨在实现物流配送全流程的数字化管理。系统涵盖订单管理、车辆调度、配送跟踪、用户管理等核心功能，为物流企业提供高效、便捷的运营管理工具。

### 1.2 技术架构概览

```
┌─────────────────────────────────────────────────────────────┐
│                        前端层 (Vue 3)                       │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐          │
│  │  Element    │  │   Vue       │  │   ECharts   │          │
│  │   Plus      │  │   Router    │  │   图表      │          │
│  └─────────────┘  └─────────────┘  └─────────────┘          │
├─────────────────────────────────────────────────────────────┤
│                        网关层 (Nginx)                        │
├─────────────────────────────────────────────────────────────┤
│                       后端层 (Spring Boot)                   │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐          │
│  │  Spring     │  │  Spring     │  │   MyBatis   │          │
│  │  Security   │  │  MVC        │  │   -Plus     │          │
│  └─────────────┘  └─────────────┘  └─────────────┘          │
├─────────────────────────────────────────────────────────────┤
│                        数据层 (MySQL)                        │
└─────────────────────────────────────────────────────────────┘
```

---

## 2. 课题背景与目标

### 2.1 行业背景

随着电子商务的蓬勃发展，物流配送行业面临前所未有的机遇与挑战。根据中国物流与采购联合会数据，2024年我国社会物流总额预计超过360万亿元，同比增长约5%。然而，传统物流企业普遍存在以下痛点：

- **信息孤岛**：订单、调度、跟踪等系统相互独立，数据无法互通
- **效率低下**：人工调度费时费力，车辆空驶率居高不下
- **体验不佳**：客户无法实时了解配送进度，投诉率偏高
- **决策困难**：缺乏数据分析支撑，经营决策缺乏数据依据

### 2.2 项目目标

本项目旨在构建一套完整的物流配送管理平台，具体目标如下：

| 目标类型 | 具体描述 | 量化指标 |
|----------|----------|----------|
| 业务目标 | 实现订单全流程管理 | 订单处理效率提升50% |
| 业务目标 | 优化车辆调度算法 | 车辆利用率提升30% |
| 业务目标 | 提供实时配送跟踪 | 客户满意度提升25% |
| 技术目标 | 掌握前后端分离开发 | 具备完整项目开发能力 |
| 技术目标 | 理解微服务架构思想 | 为微服务转型奠定基础 |
| 学习目标 | 熟练使用主流技术栈 | Spring Boot + Vue 3 + MySQL |

### 2.3 预期成果

1. **可运行的Web系统**：一套完整的物流配送管理平台
2. **技术文档**：需求分析报告、设计文档、测试报告
3. **源代码**：前后端完整代码，注释规范
4. **部署文档**：系统部署与配置指南

---

## 3. 市场调研与竞品分析

### 3.1 市场概况

物流配送管理市场主要分为以下几类：

| 市场类型 | 代表产品 | 特点 | 适用规模 |
|----------|----------|------|----------|
| 端到端物流平台 | 顺丰科技、京东物流 | 功能全面，定制化强 | 大型企业 |
| SaaS物流系统 | 菜鸟网络、G7 | 按需订阅，快速上线 | 中小企业 |
| 开源物流系统 | 本项目 | 免费开源，可定制 | 初创/学习 |
| 垂直领域方案 | 闪送、达达 | 专注即时配送 | O2O场景 |

### 3.2 竞品分析

#### 3.2.1 商业产品对比

| 对比维度 | 菜鸟网络 | G7物联网 | 本项目 |
|----------|----------|----------|--------|
| 价格 | 高昂订阅费 | 高昂订阅费 | 免费开源 |
| 定制能力 | 有限 | 有限 | 完全自主 |
| 技术栈 | Java/Go | Java | Spring Boot + Vue |
| 学习价值 | 封闭源码 | 封闭源码 | 完整开源 |
| 部署方式 | 云端 | 云端/私有 | 私有化部署 |

#### 3.2.2 开源项目对比

| 项目 | Star数 | 技术栈 | 特点 | 适用场景 |
|------|--------|--------|------|----------|
| RuoYi-Vue | 29,393 | Spring Boot + Vue | 权限管理系统，通用性强 | 后台管理模板 |
| JeecgBoot | 45,367 | Spring Boot + Vue | 低代码平台，功能丰富 | 快速开发 |
| 本项目 | - | Spring Boot + Vue | 物流垂直领域，专注配送 | 物流管理学习 |

### 3.3 市场机会

1. **教育市场**：高校计算机专业毕业设计首选方向
2. **初创企业**：低成本快速验证业务想法
3. **技术学习**：全栈开发实战训练最佳项目
4. **二次开发**：可作为企业级应用基础框架

---

## 4. 含金量分析

### 4.1 作为毕业设计的价值评估

| 评估维度 | 评分 | 说明 |
|----------|------|------|
| 技术难度 | ⭐⭐⭐⭐ | 前后端分离架构，涉及认证、权限、业务逻辑 |
| 业务复杂度 | ⭐⭐⭐⭐ | 订单、调度、跟踪、统计等多模块协同 |
| 创新空间 | ⭐⭐⭐ | 可在调度算法、UI交互、数据可视化创新 |
| 完整性要求 | ⭐⭐⭐⭐ | 需涵盖需求、设计、实现、测试、部署全流程 |
| 文档工作量 | ⭐⭐⭐⭐ | 需求、设计、测试等多种文档 |

**综合评分：⭐⭐⭐⭐（4/5）**

作为毕业设计，本项目能够充分展示开发者对以下能力的掌握：
- 前后端分离架构设计能力
- 关系型数据库设计与优化
- RESTful API设计与实现
- 前端框架使用与组件开发
- 项目文档编写与汇报

### 4.2 作为学习项目的价值评估

| 技能方向 | 掌握内容 | 学习价值 |
|----------|----------|----------|
| 后端开发 | Spring Boot、Spring Security、JWT、MyBatis-Plus | ⭐⭐⭐⭐⭐ |
| 前端开发 | Vue 3、Composition API、Element Plus、Vite | ⭐⭐⭐⭐⭐ |
| 数据库 | MySQL、数据库设计、SQL优化 | ⭐⭐⭐⭐ |
| 项目管理 | Git、Maven/Gradle、开发规范 | ⭐⭐⭐⭐ |
| 部署运维 | Linux、Docker、Nginx配置 | ⭐⭐⭐ |

**综合评分：⭐⭐⭐⭐⭐（5/5）**

本项目是全栈开发的绝佳学习路径，覆盖了Java后端开发和Vue前端开发的核心技术点。

---

## 5. 可行性分析

### 5.1 技术可行性

| 技术点 | 成熟度 | 学习曲线 | 替代方案 |
|--------|--------|----------|----------|
| Spring Boot | 成熟稳定 | 中等 | Spring MVC |
| Spring Security | 成熟稳定 | 较陡 | Shiro |
| JWT | 成熟稳定 | 平缓 | Session |
| Vue 3 | 成熟稳定 | 中等 | React |
| Element Plus | 活跃维护 | 平缓 | Ant Design Vue |
| MySQL | 成熟稳定 | 平缓 | PostgreSQL |
| MyBatis-Plus | 活跃维护 | 平缓 | JPA/MyBatis |

**结论：技术方案成熟，所有技术均有成熟社区支持，技术可行性高。**

### 5.2 资源可行性

| 资源类型 | 需求 | 解决方案 |
|----------|------|----------|
| 开发环境 | JDK 1.8+、Node.js 16+、MySQL 5.7+ | 免费开源 |
| 服务器 | 2核4G云服务器 | 学生优惠/本地测试 |
| 时间 | 3-4个月 | 合理规划里程碑 |
| 人员 | 1-2人 | 个人/小组项目 |

### 5.3 风险评估与应对

| 风险类型 | 风险描述 | 应对措施 |
|----------|----------|----------|
| 技术风险 | 新技术学习成本高 | 预留充足学习时间，参考开源项目 |
| 进度风险 | 功能scope蔓延 | 明确MVP边界，控制迭代范围 |
| 数据风险 | 测试数据不足 | 编写数据生成脚本 |
| 部署风险 | 环境配置复杂 | Docker容器化部署 |

---

## 6. 用户场景与使用边界

### 6.1 目标用户

| 用户角色 | 使用场景 | 核心诉求 |
|----------|----------|----------|
| 管理员 | 系统配置、用户管理、数据查看 | 全局掌控、安全管理 |
| 调度员 | 订单分配、车辆调度 | 高效排单、减少空驶 |
| 配送员 | 接单、状态更新、轨迹记录 | 操作便捷、任务清晰 |
| 客户 | 下单、查看物流、评价 | 体验流畅、信息透明 |

### 6.2 典型使用场景

#### 场景一：客户下单

1. 客户登录系统
2. 填写寄件信息（地址、联系人、货物详情）
3. 选择配送类型（当日达/次日达/普通）
4. 提交订单并支付
5. 系统生成订单号，客户可追踪物流状态

#### 场景二：智能调度

1. 调度员查看待分配订单
2. 系统根据订单位置、车辆位置、配送时效推荐方案
3. 调度员确认或调整分配
4. 配送员收到推送通知

#### 场景三：配送执行

1. 配送员接单，查看订单详情
2. 到达取货点，扫描取件，更新状态
3. 配送途中，更新位置轨迹
4. 送达签收，拍照确认
5. 客户确认收货，订单完成

### 6.3 使用边界

| 边界维度 | 明确限制 |
|----------|----------|
| 用户规模 | 支持1000用户并发 |
| 数据规模 | 支持10万级订单数据 |
| 部署方式 | 单机部署，不支持集群 |
| 移动端 | 仅支持响应式Web，暂不开发原生App |
| 第三方集成 | 暂不支持地图API、短信、支付集成 |

---

## 7. 功能目标与非目标

### 7.1 功能目标（Must Have）

#### 7.1.1 用户管理模块

| 功能 | 描述 | 优先级 |
|------|------|--------|
| 用户注册 | 客户自主注册账号 | P0 |
| 用户登录 | JWT无状态登录 | P0 |
| 密码修改 | 忘记密码/修改密码 | P1 |
| 角色管理 | 管理员/调度员/配送员/客户 | P0 |
| 权限控制 | 基于角色的功能权限 | P0 |

#### 7.1.2 订单管理模块

| 功能 | 描述 | 优先级 |
|------|------|--------|
| 订单创建 | 在线填写订单信息 | P0 |
| 订单列表 | 多条件查询、筛选、排序 | P0 |
| 订单详情 | 查看订单完整信息 | P0 |
| 订单取消 | 未开始配送前可取消 | P1 |
| 订单跟踪 | 查看物流状态流转 | P0 |

#### 7.1.3 车辆调度模块

| 功能 | 描述 | 优先级 |
|------|------|--------|
| 车辆管理 | 车辆信息维护（车牌、司机、容量） | P0 |
| 司机管理 | 司机信息、状态管理 | P0 |
| 任务分配 | 手动分配订单给车辆/司机 | P0 |
| 调度看板 | 可视化展示当日任务 | P1 |
| 路径规划 | 简单路径优化建议 | P2 |

#### 7.1.4 配送跟踪模块

| 功能 | 描述 | 优先级 |
|------|------|--------|
| 状态更新 | 配送员更新各节点状态 | P0 |
| 轨迹记录 | 记录配送员位置变化 | P1 |
| 异常处理 | 配送异常登记与处理 | P1 |
| 签收确认 | 客户确认收货 | P0 |

#### 7.1.5 统计报表模块

| 功能 | 描述 | 优先级 |
|------|------|--------|
| 订单统计 | 订单量、成交额统计 | P1 |
| 配送统计 | 配送时效、完成率统计 | P1 |
| 报表导出 | Excel导出功能 | P2 |
| 数据可视化 | ECharts图表展示 | P1 |

### 7.2 功能目标（Should Have）

| 功能 | 描述 | 优先级 |
|------|------|--------|
| 消息通知 | 订单状态变更通知 | P2 |
| 评价系统 | 客户评价配送服务 | P2 |
| 操作日志 | 关键操作审计记录 | P2 |

### 7.3 非目标（Out of Scope）

| 功能 | 原因 |
|------|------|
| 移动端App | 超出项目范围 |
| 地图导航集成 | 需要第三方API付费 |
| 支付功能 | 需要资质认证 |
| 短信通知 | 需要短信通道 |
| 智能算法调度 | 算法复杂度过高 |
| 多人协同配送 | 业务场景不明确 |
| 客服系统 | 功能过于独立 |

---

## 8. 技术方案总览

### 8.1 整体架构

```
┌─────────────────────────────────────────────────────────────────┐
│                        客户端 (Browser)                         │
│                     http/https 请求                              │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                        Nginx 反向代理                            │
│                     静态资源 / 负载均衡                           │
└────────────────────────────┬────────────────────────────────────┘
                             │
              ┌──────────────┴──────────────┐
              │                             │
              ▼                             ▼
┌─────────────────────────┐    ┌─────────────────────────┐
│    后端服务 (8080)      │    │    后端服务 (8081)      │
│    Spring Boot         │    │    (预留扩展)           │
└─────────────────────────┘    └─────────────────────────┘
              │
              ▼
┌─────────────────────────────────────────────────────────────────┐
│                      MySQL 数据库 (3306)                        │
│                    主从复制 (可选)                                │
└─────────────────────────────────────────────────────────────────┘
```

### 8.2 技术选型总表

| 层次 | 技术 | 版本 | 说明 |
|------|------|------|------|
| 后端框架 | Spring Boot | 2.7.18 | 主流版本，稳定可靠 |
| 安全框架 | Spring Security | 5.7.x | 与Spring Boot集成 |
| 认证方案 | JWT | 0.11.x | 无状态token认证 |
| ORM框架 | MyBatis-Plus | 3.5.x | 国产优秀ORM |
| 数据库 | MySQL | 8.0 | 关系型数据库 |
| 前端框架 | Vue | 3.3.x | 渐进式框架 |
| UI组件库 | Element Plus | 2.4.x | Vue 3组件库 |
| 构建工具 | Vite | 4.x | 快速构建 |
| 路由 | Vue Router | 4.x | SPA路由 |
| 状态管理 | Pinia | 2.x | Vue 3推荐 |
| HTTP客户端 | Axios | 1.x | HTTP请求 |
| 图表 | ECharts | 5.x | 数据可视化 |
| 代码规范 | ESLint + Prettier | - | 前端规范 |

### 8.3 项目结构

#### 8.3.1 后端项目结构

```
logistics-backend/
├── src/main/java/com/logistics/
│   ├── config/              # 配置类
│   │   ├── SecurityConfig.java
│   │   ├── CORSConfig.java
│   │   └── MyBatisPlusConfig.java
│   ├── controller/         # 控制器
│   │   ├── auth/
│   │   ├── order/
│   │   ├── vehicle/
│   │   └── statistics/
│   ├── service/            # 业务逻辑
│   │   ├── impl/
│   │   └── IXXXService.java
│   ├── mapper/             # 数据访问
│   │   └── XXXMapper.java
│   ├── entity/             # 实体类
│   │   ├── User.java
│   │   ├── Order.java
│   │   └── Vehicle.java
│   ├── dto/                # 数据传输对象
│   ├── vo/                 # 视图对象
│   ├── common/             # 公共组件
│   │   ├── Result.java     # 统一响应
│   │   ├── JwtUtil.java   # JWT工具
│   │   └── JwtAuthFilter.java
│   └── LogisticsApplication.java
├── src/main/resources/
│   ├── mapper/             # MyBatis XML
│   ├── application.yml    # 应用配置
│   └── application-dev.yml
└── pom.xml
```

#### 8.3.2 前端项目结构

```
logistics-vue/
├── public/                 # 静态资源
├── src/
│   ├── api/               # API接口
│   │   ├── auth.js
│   │   ├── order.js
│   │   └── vehicle.js
│   ├── assets/            # 资源文件
│   ├── components/       # 公共组件
│   │   ├── Header.vue
│   │   ├── Sidebar.vue
│   │   └── Pagination.vue
│   ├── layouts/           # 布局组件
│   │   └── BasicLayout.vue
│   ├── router/            # 路由配置
│   │   └── index.js
│   ├── stores/            # Pinia状态管理
│   │   ├── user.js
│   │   └── app.js
│   ├── views/             # 页面视图
│   │   ├── login/
│   │   ├── order/
│   │   ├── vehicle/
│   │   └── statistics/
│   ├── App.vue
│   └── main.js
├── index.html
├── vite.config.js
├── .eslintrc.js
└── package.json
```

---

## 9. 技术选型与理由

### 9.1 后端技术选型

#### 9.1.1 Spring Boot 2.7.x

**选型理由：**

| 考量维度 | 说明 |
|----------|------|
| 市场主流 | 国内Java后端开发事实标准，企业招聘需求大 |
| 生态完善 | 与Spring Security、Spring Data等无缝集成 |
| 学习资源 | 教程丰富，遇到问题易解决 |
| 版本稳定 | 2.7.x是LTS版本，维护周期长 |
| 兼容性好 | 对主流中间件、数据库支持完善 |

**不选其他方案的理由：**
- Spring MVC：需手动配置，繁琐
- Quarkus/ Micronaut：国内就业市场认可度较低

#### 9.1.2 Spring Security + JWT

**选型理由：**

| 考量维度 | 说明 |
|----------|------|
| 官方方案 | Spring家族亲儿子，与Spring Boot深度集成 |
| 功能完善 | 认证、授权、CSRF防护、会话管理等开箱即用 |
| 可扩展 | 灵活定制认证逻辑 |
| JWT优势 | 无状态、可扩展、支持移动端 |

**不选Shiro的理由：**
- Spring Security与Spring Boot更原生
- 社区活跃度更高
- 文档更完善

#### 9.1.3 MyBatis-Plus

**选型理由：**

| 考量维度 | 说明 |
|----------|------|
| 国产精品 | 中文文档，国内社区活跃 |
| 效率提升 | 内置CRUD、Wrapper、条件构造器 |
| 兼容性强 | 支持MyBatis所有特性 |
| 自动填充 | 创建时间/更新时间自动填充 |
| 乐观锁 | 内置版本号乐观锁支持 |

**不选JPA的理由：**
- SQL可控性强，适合复杂业务
- 学习曲线更平缓
- 适合需要优化SQL的场景

### 9.2 前端技术选型

#### 9.2.1 Vue 3

**选型理由：**

| 考量维度 | 说明 |
|----------|------|
| 渐进式 | 可渐进使用，学习曲线平缓 |
| Composition API | 代码组织更灵活，复用性强 |
| 性能提升 | 比Vue 2性能提升显著 |
| 生态丰富 | Vue Router、Pinia、Vite配套完善 |
| 市场需求 | 国内前端开发需求大 |

#### 9.2.2 Element Plus

**选型理由：**

| 考量维度 | 说明 |
|----------|------|
| Vue 3原生 | 专为Vue 3打造，TypeScript支持好 |
| 组件丰富 | 表单、表格、导航等组件齐全 |
| 文档友好 | 中文文档，上手快 |
| 设计规范 | 符合企业后台管理系统审美 |
| 社区活跃 | 维护积极，问题响应快 |

**不选其他UI库的理由：**
- Ant Design Vue：更适合大型企业项目，体积较大
- Vant：更适合移动端
- Naive UI：生态相对较小

#### 9.2.3 Vite

**选型理由：**

| 考量维度 | 说明 |
|----------|------|
| 极速启动 | 开发服务器秒开 |
| HMR | 修改文件即时更新，无需刷新 |
| 生产优化 | 内置Rollup打包优化 |
| 配置简单 | 约定优于配置 |

#### 9.2.4 Pinia

**选型理由：**

| 考量维度 | 说明 |
|----------|------|
| 官方推荐 | Vue官方推荐的状态管理 |
| TypeScript | 原生支持TypeScript |
| 简洁API | 比Vuex更简单的API设计 |
| 模块化 | 天然模块化支持 |

### 9.3 数据可视化选型

#### 9.3.1 ECharts

**选型理由：**

| 考量维度 | 说明 |
|----------|------|
| 国产开源 | 百度开源，中文文档 |
| 图表丰富 | 折线、柱状、饼图、地图等几十种图表 |
| 性能优秀 | 支持大数据量渲染 |
| 社区活跃 | 国内使用广泛，问题解决方案多 |

---

## 10. 系统/模块设计

### 10.1 模块架构

```
┌─────────────────────────────────────────────────────────────────┐
│                         应用层 (Controller)                      │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐    │
│  │用户模块 │ │订单模块 │ │车辆模块 │ │配送模块 │ │统计模块 │    │
│  └────┬────┘ └────┬────┘ └────┬────┘ └────┬────┘ └────┬────┘    │
├───────┼──────────┼──────────┼──────────┼──────────┼───────────┤
│                         服务层 (Service)                         │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐    │
│  │ 认证    │ │ 业务    │ │ 调度    │ │ 轨迹    │ │ 分析    │    │
│  │ 服务    │ │ 服务    │ │ 服务    │ │ 服务    │ │ 服务    │    │
│  └────┬────┘ └────┬────┘ └────┬────┘ └────┬────┘ └────┬────┘    │
├───────┼──────────┼──────────┼──────────┼──────────┼───────────┤
│                        数据层 (Mapper)                           │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐    │
│  │用户Mapper│ │订单Mapper│ │车辆Mapper│ │配送Mapper│ │统计Mapper│    │
│  └────┬────┘ └────┬────┘ └────┬────┘ └────┬────┘ └────┬────┘    │
├───────┼──────────┼──────────┼──────────┼──────────┼───────────┤
│                         实体层 (Entity)                          │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐    │
│  │  User   │ │  Order  │ │ Vehicle │ │Delivery │ │ Report  │    │
│  └─────────┘ └─────────┘ └─────────┘ └─────────┘ └─────────┘    │
└─────────────────────────────────────────────────────────────────┘
```

### 10.2 核心模块设计

#### 10.2.1 用户认证模块

**设计要点：**

1. **认证流程**
   - 用户登录 → 验证用户名密码 → 生成JWT Token → 返回Token → 前端存储
   - 后续请求 → 携带Token → Filter验证Token → 解析用户信息 → 授权访问

2. **权限架构**
   - 基于RBAC（Role-Based Access Control）模型
   - 角色：ADMIN（管理员）、DISPATCHER（调度员）、COURIER（配送员）、CUSTOMER（客户）
   - 权限：菜单权限、按钮权限、API权限

3. **安全措施**
   - 密码BCrypt加密存储
   - Token有效期设置（默认24小时）
   - 接口防刷（可选）
   - CORS配置

#### 10.2.2 订单管理模块

**设计要点：**

1. **订单状态机**
   - PENDING（待分配） → ASSIGNED（已分配） → PICKED_UP（已取件）
   - → IN_TRANSIT（配送中） → DELIVERED（已送达） → COMPLETED（已完成）
   - ↓
   - CANCELLED（已取消）

2. **订单数据结构**
   - 订单基本信息：订单号、客户信息、寄件/收件地址
   - 货物信息：重量、体积、类型
   - 配送信息：配送类型、时效要求、费用
   - 状态信息：当前状态、状态变更历史

#### 10.2.3 车辆调度模块

**设计要点：**

1. **调度策略**
   - 手动分配：调度员手动选择车辆和司机
   - 智能推荐：根据距离、车辆容量、配送时效推荐最优方案
   - 优先级：考虑订单紧急程度、距离远近

2. **调度看板**
   - 今日订单概览
   - 车辆分布地图（简化版）
   - 待分配订单列表
   - 配送员当前位置（模拟）

#### 10.2.4 配送跟踪模块

**设计要点：**

1. **轨迹记录**
   - 关键节点状态更新
   - 位置更新（定时上报，可模拟）
   - 时间戳记录

2. **状态流转**
   - 配送员端操作触发状态变更
   - 自动记录变更时间
   - 推送通知给客户（可选）

---

## 11. 数据结构/接口/文件结构约定

### 11.1 数据库表设计

#### 11.1.1 用户表 (sys_user)

```sql
CREATE TABLE sys_user (
    id              BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '用户ID',
    username        VARCHAR(50)     NOT NULL                 COMMENT '用户名',
    password        VARCHAR(100)    NOT NULL                 COMMENT '密码',
    nickname        VARCHAR(50)     DEFAULT NULL             COMMENT '昵称',
    phone           VARCHAR(20)     DEFAULT NULL             COMMENT '手机号',
    email           VARCHAR(100)    DEFAULT NULL             COMMENT '邮箱',
    avatar          VARCHAR(255)    DEFAULT NULL             COMMENT '头像',
    role            VARCHAR(20)     NOT NULL DEFAULT 'CUSTOMER'  COMMENT '角色',
    status          TINYINT         NOT NULL DEFAULT 1       COMMENT '状态 0禁用 1正常',
    create_time     DATETIME        DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted         TINYINT         DEFAULT 0                COMMENT '删除标记',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username),
    KEY idx_phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
```

#### 11.1.2 订单表 (order_info)

```sql
CREATE TABLE order_info (
    id                  BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '订单ID',
    order_no            VARCHAR(32)     NOT NULL                 COMMENT '订单号',
    user_id             BIGINT          NOT NULL                 COMMENT '下单用户ID',
    courier_id         BIGINT          DEFAULT NULL             COMMENT '配送员ID',
    
    /* 寄件信息 */
    sender_name         VARCHAR(50)     NOT NULL                 COMMENT '寄件人姓名',
    sender_phone        VARCHAR(20)     NOT NULL                 COMMENT '寄件人电话',
    sender_address      VARCHAR(255)    NOT NULL                 COMMENT '寄件地址',
    sender_province     VARCHAR(50)     DEFAULT NULL             COMMENT '寄件省份',
    sender_city         VARCHAR(50)     DEFAULT NULL             COMMENT '寄件城市',
    sender_district     VARCHAR(50)     DEFAULT NULL             COMMENT '寄件区县',
    
    /* 收件信息 */
    receiver_name       VARCHAR(50)     NOT NULL                 COMMENT '收件人姓名',
    receiver_phone      VARCHAR(20)     NOT NULL                 COMMENT '收件人电话',
    receiver_address    VARCHAR(255)    NOT NULL                 COMMENT '收件地址',
    receiver_province  VARCHAR(50)     DEFAULT NULL             COMMENT '收件省份',
    receiver_city       VARCHAR(50)     DEFAULT NULL             COMMENT '收件城市',
    receiver_district   VARCHAR(50)     DEFAULT NULL             COMMENT '收件区县',
    
    /* 货物信息 */
    goods_type          VARCHAR(50)     DEFAULT NULL             COMMENT '货物类型',
    goods_weight        DECIMAL(10,2)   DEFAULT NULL             COMMENT '货物重量(kg)',
    goods_volume        DECIMAL(10,2)   DEFAULT NULL             COMMENT '货物体积(m³)',
    goods_value         DECIMAL(10,2)   DEFAULT NULL             COMMENT '货物价值',
    remark              VARCHAR(500)    DEFAULT NULL             COMMENT '备注',
    
    /* 配送信息 */
    delivery_type       VARCHAR(20)     NOT NULL                 COMMENT '配送类型',
    delivery_fee        DECIMAL(10,2)   NOT NULL DEFAULT 0       COMMENT '配送费用',
    expect_pickup_time  DATETIME        DEFAULT NULL             COMMENT '期望取件时间',
    expect_delivery_time DATETIME       DEFAULT NULL             COMMENT '期望送达时间',
    
    /* 状态信息 */
    status              VARCHAR(20)     NOT NULL DEFAULT 'PENDING'  COMMENT '订单状态',
    create_time         DATETIME        DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time         DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    pickup_time         DATETIME        DEFAULT NULL             COMMENT '实际取件时间',
    delivery_time       DATETIME        DEFAULT NULL             COMMENT '实际送达时间',
    complete_time       DATETIME        DEFAULT NULL             COMMENT '完成时间',
    cancel_time         DATETIME        DEFAULT NULL             COMMENT '取消时间',
    deleted             TINYINT         DEFAULT 0                COMMENT '删除标记',
    
    PRIMARY KEY (id),
    UNIQUE KEY uk_order_no (order_no),
    KEY idx_user_id (user_id),
    KEY idx_courier_id (courier_id),
    KEY idx_status (status),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';
```

#### 11.1.3 车辆表 (vehicle_info)

```sql
CREATE TABLE vehicle_info (
    id                  BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '车辆ID',
    vehicle_no          VARCHAR(20)     NOT NULL                 COMMENT '车牌号',
    vehicle_type        VARCHAR(20)     DEFAULT NULL             COMMENT '车辆类型',
    brand               VARCHAR(50)     DEFAULT NULL             COMMENT '品牌',
    model               VARCHAR(50)     DEFAULT NULL             COMMENT '型号',
    capacity_weight     DECIMAL(10,2)   DEFAULT NULL             COMMENT '载重(kg)',
    capacity_volume     DECIMAL(10,2)   DEFAULT NULL             COMMENT '容积(m³)',
    status              TINYINT         NOT NULL DEFAULT 1       COMMENT '状态 0维修 1空闲 2配送中',
    current_lat         DECIMAL(10,6)   DEFAULT NULL             COMMENT '当前位置纬度',
    current_lng         DECIMAL(10,6)   DEFAULT NULL             COMMENT '当前位置经度',
    driver_name         VARCHAR(50)     DEFAULT NULL             COMMENT '司机姓名',
    driver_phone        VARCHAR(20)     DEFAULT NULL             COMMENT '司机电话',
    create_time         DATETIME        DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time         DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted             TINYINT         DEFAULT 0                COMMENT '删除标记',
    PRIMARY KEY (id),
    UNIQUE KEY uk_vehicle_no (vehicle_no),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='车辆表';
```

#### 11.1.4 配送员表 (courier_info)

```sql
CREATE TABLE courier_info (
    id                  BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '配送员ID',
    user_id             BIGINT          NOT NULL                 COMMENT '关联用户ID',
    employee_no         VARCHAR(20)     NOT NULL                 COMMENT '员工编号',
    real_name           VARCHAR(50)     NOT NULL                 COMMENT '真实姓名',
    phone               VARCHAR(20)     NOT NULL                 COMMENT '联系电话',
    id_card             VARCHAR(20)     DEFAULT NULL             COMMENT '身份证号',
    status              TINYINT         NOT NULL DEFAULT 1       COMMENT '状态 0离职 1在职',
    current_lat         DECIMAL(10,6)   DEFAULT NULL             COMMENT '当前位置纬度',
    current_lng         DECIMAL(10,6)   DEFAULT NULL             COMMENT '当前位置经度',
    today_orders        INT             DEFAULT 0                 COMMENT '今日配送数',
    total_orders        INT             DEFAULT 0                 COMMENT '累计配送数',
    rating              DECIMAL(3,2)    DEFAULT 5.00             COMMENT '评分',
    create_time         DATETIME        DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time         DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted             TINYINT         DEFAULT 0                COMMENT '删除标记',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_id (user_id),
    UNIQUE KEY uk_employee_no (employee_no),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='配送员表';
```

#### 11.1.5 订单状态流转表 (order_status_log)

```sql
CREATE TABLE order_status_log (
    id                  BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '日志ID',
    order_id            BIGINT          NOT NULL                 COMMENT '订单ID',
    status              VARCHAR(20)     NOT NULL                 COMMENT '状态',
    operate_type        VARCHAR(20)     DEFAULT NULL             COMMENT '操作类型',
    operate_id          BIGINT          DEFAULT NULL             COMMENT '操作人ID',
    operate_name        VARCHAR(50)     DEFAULT NULL             COMMENT '操作人姓名',
    remark              VARCHAR(500)    DEFAULT NULL             COMMENT '备注',
    create_time         DATETIME        DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_order_id (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单状态日志表';
```

### 11.2 API接口设计

#### 11.2.1 统一响应格式

```json
// 成功响应
{
    "code": 200,
    "message": "success",
    "data": {},
    "timestamp": 1699500000000
}

// 失败响应
{
    "code": 400,
    "message": "参数错误",
    "data": null,
    "timestamp": 1699500000000
}
```

#### 11.2.2 认证接口

| 方法 | 路径 | 描述 | 权限 |
|------|------|------|------|
| POST | /api/auth/login | 用户登录 | 公开 |
| POST | /api/auth/register | 用户注册 | 公开 |
| POST | /api/auth/logout | 退出登录 | 登录 |
| GET | /api/auth/info | 获取当前用户信息 | 登录 |

#### 11.2.3 订单接口

| 方法 | 路径 | 描述 | 权限 |
|------|------|------|------|
| GET | /api/orders | 订单列表 | 登录 |
| POST | /api/orders | 创建订单 |

#### 11.2.4 车辆接口

| 方法 | 路径 | 描述 | 权限 |
|------|------|------|------|
| GET | /api/vehicles | 车辆列表 | 登录 |
| POST | /api/vehicles | 添加车辆 | 管理员 |
| GET | /api/vehicles/{id} | 车辆详情 | 登录 |
| PUT | /api/vehicles/{id} | 更新车辆 | 管理员 |
| DELETE | /api/vehicles/{id} | 删除车辆 | 管理员 |

### 11.3 文件结构约定

#### 11.3.1 后端代码规范

```
# 包命名
com.logistics.模块名

# 类命名
- Controller: XxxController.java
- Service: IXxxService.java, XxxServiceImpl.java
- Mapper: XxxMapper.java
- Entity: Xxx.java
- VO: XxxVO.java
- DTO: XxxDTO.java

# 方法命名
- 查询: getXxx, listXxx, pageXxx
- 新增: saveXxx, addXxx
- 更新: updateXxx
- 删除: removeXxx, deleteXxx
```

#### 11.3.2 前端代码规范

```
# 组件命名
- 页面组件: XxxView.vue (如 OrderListView.vue)
- 公共组件: Xxx.vue (如 Pagination.vue)
- 布局组件: XxxLayout.vue

# 目录结构
- api/: 接口函数
- components/: 公共组件
- views/: 页面视图
- stores/: 状态管理
- utils/: 工具函数
- assets/: 静态资源
```

---

## 12. 开发步骤建议

### 12.1 阶段一：环境搭建（第1-2周）

| 任务 | 描述 | 交付物 |
|------|------|--------|
| 开发环境安装 | JDK、Node.js、MySQL、IDE配置 | 开发环境可用 |
| 项目初始化 | Spring Boot + Vue项目创建 | 基础项目骨架 |
| 依赖配置 | Maven/Gradle依赖配置 | 可运行的基础项目 |
| 数据库搭建 | MySQL创建、连接配置 | 数据库可用 |

### 12.2 阶段二：基础功能（第3-5周）

| 任务 | 描述 | 交付物 |
|------|------|--------|
| 用户模块 | 登录、注册、权限控制 | 用户管理功能 |
| 公共组件 | 布局、表格、表单组件 | 可复用的前端组件 |
| 统一响应 | 封装后端统一响应格式 | Result类 |
| 异常处理 | 全局异常捕获 | GlobalExceptionHandler |

### 12.3 阶段三：核心业务（第6-10周）

| 任务 | 描述 | 交付物 |
|------|------|--------|
| 订单管理 | 订单CRUD、状态流转 | 订单管理模块 |
| 车辆调度 | 车辆管理、任务分配 | 调度模块 |
| 配送跟踪 | 状态更新、轨迹记录 | 跟踪模块 |
| 统计分析 | 数据统计、图表展示 | 统计模块 |

### 12.4 阶段四：完善与测试（第11-13周）

| 任务 | 描述 | 交付物 |
|------|------|--------|
| 接口优化 | 参数校验、安全加固 | 优化后的API |
| 前端优化 | 加载性能、交互体验 | 优化后的前端 |
| 单元测试 | 核心业务测试 | 测试报告 |
| 集成测试 | 前后端联调 | 可运行的系统 |

### 12.5 阶段五：部署与文档（第14-16周）

| 任务 | 描述 | 交付物 |
|------|------|--------|
| 部署配置 | Nginx、域名配置 | 部署文档 |
| 性能优化 | 数据库优化、缓存 | 优化报告 |
| 文档编写 | 设计文档、用户手册 | 完整文档 |
| 项目答辩 | PPT、演示 | 答辩材料 |

---

## 13. 测试方案

### 13.1 测试策略

| 测试类型 | 覆盖范围 | 工具 |
|----------|----------|------|
| 单元测试 | 业务逻辑类、方法 | JUnit 5 |
| 集成测试 | Controller、Service层 | Spring Boot Test |
| 前端测试 | 组件、工具函数 | Vitest |
| E2E测试 | 关键业务流程 | 手动测试 |

### 13.2 测试用例设计

#### 13.2.1 用户认证测试

| 用例ID | 用例描述 | 预期结果 |
|--------|----------|----------|
| TC001 | 正确账号密码登录 | 登录成功，返回Token |
| TC002 | 错误密码登录 | 登录失败，提示错误 |
| TC003 | Token过期访问 | 返回401，重新登录 |
| TC004 | 无权限访问 | 返回403，禁止访问 |

#### 13.2.2 订单管理测试

| 用例ID | 用例描述 | 预期结果 |
|--------|----------|----------|
| TC101 | 创建有效订单 | 订单创建成功 |
| TC102 | 创建订单-必填项为空 | 提示必填项错误 |
| TC103 | 查询自己的订单 | 返回当前用户订单 |
| TC104 | 查看他人订单 | 返回空或无权提示 |

#### 13.2.3 调度分配测试

| 用例ID | 用例描述 | 预期结果 |
|--------|----------|----------|
| TC201 | 分配订单给空闲车辆 | 分配成功，状态更新 |
| TC202 | 分配订单给忙碌车辆 | 提示车辆忙碌 |
| TC203 | 超容量分配 | 提示超出承载能力 |

### 13.3 测试数据准备

```sql
-- 测试用户数据
INSERT INTO sys_user (username, password, role) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'ADMIN'),
('dispatcher1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'DISPATCHER'),
('courier1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'COURIER'),
('customer1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'CUSTOMER');

-- 密码均为: 123456
```

---

## 14. 验收标准

### 14.1 功能验收

| 模块 | 验收标准 | 优先级 |
|------|----------|--------|
| 用户管理 | 支持登录、注册、角色管理、权限控制 | P0 |
| 订单管理 | 支持订单创建、查询、跟踪、取消 | P0 |
| 车辆调度 | 支持车辆管理、手动分配、调度看板 | P0 |
| 配送跟踪 | 支持状态更新、轨迹记录 | P0 |
| 统计分析 | 支持订单统计、图表展示 | P1 |

### 14.2 性能验收

| 指标 | 标准 | 测试方法 |
|------|------|----------|
| 首页加载 | < 3秒 | 首次加载测试 |
| API响应 | < 500ms | 接口响应时间 |
| 并发支持 | 100用户 | 压力测试 |
| 错误率 | < 1% | 稳定性测试 |

### 14.3 代码验收

| 检查项 | 标准 |
|--------|------|
| 代码规范 | 符合阿里巴巴Java开发规范 |
| 注释覆盖率 | 核心类/方法 > 80% |
| 单元测试 | 覆盖率 > 50% |
| 安全检查 | 无SQL注入、XSS漏洞 |

### 14.4 文档验收

| 文档 | 要求 |
|------|------|
| 需求文档 | 包含功能清单、流程图 |
| 设计文档 | 包含数据库设计、API文档 |
| 测试报告 | 包含测试用例、测试结果 |
| 用户手册 | 包含操作说明 |

---

## 15. 风险、约束与待确认项

### 15.1 已识别风险

| 风险项 | 风险等级 | 应对措施 |
|--------|----------|----------|
| 第三方地图API限制 | 中 | 使用模拟数据展示 |
| 支付功能复杂度 | 高 | 简化为一期不上线 |
| 实时推送实现难度 | 中 | 改为轮询机制 |
| 移动端适配 | 低 | 优先Web响应式 |

### 15.2 项目约束

| 约束类型 | 具体约束 |
|----------|----------|
| 时间约束 | 总周期16周 |
| 人员约束 | 1-2人开发 |
| 技术约束 | 不使用微服务架构 |
| 资源约束 | 无付费第三方服务 |

### 15.3 待确认项

| 序号 | 待确认项 | 确认人 | 优先级 |
|------|----------|--------|--------|
| 1 | 配送员端是否需要App还是H5 | 产品确定 | P0 |
| 2 | 调度算法具体实现要求 | 技术确定 | P1 |
| 3 | 数据量预估与存储方案 | 架构确定 | P1 |
| 4 | 部署服务器配置 | 运维确定 | P2 |

---

## 16. 参考资料

### 16.1 技术文档

| 技术 | 官方文档 |
|------|----------|
| Spring Boot | https://spring.io/projects/spring-boot |
| Spring Security | https://spring.io/projects/spring-security |
| Vue 3 | https://vuejs.org/ |
| Element Plus | https://element-plus.org/ |
| MyBatis-Plus | https://baomidou.com/ |
| ECharts | https://echarts.apache.org/ |

### 16.2 学习资源

| 资源 | 链接 |
|------|------|
| Spring Boot实战 | 书籍 |
| Vue 3实战 | 书籍/视频课程 |
| MySQL必知必会 | 书籍 |
| 前后端分离开发实战 | 项目实战 |

---

## 17. 参考项目链接

### 17.1 开源项目

| 项目 | GitHub地址 | 说明 |
|------|------------|------|
| RuoYi-Vue | https://github.com/yangzongzhuan/RuoYi-Vue | Spring Boot + Vue权限管理系统 |
| JeecgBoot | https://github.com/jeecgboot/JeecgBoot | 低代码开发框架 |
| Springboot_Vue_ElementUI_Logistics_Delivery | - | 物流配送毕业设计参考 |
| Logistics-Management-System-Java | https://github.com/1024sparrow/Logistics-Management-System-Java | 物流管理系统 |

### 17.2 技术博客

| 博客 | 地址 |
|------|------|
| 掘金 | https://juejin.cn/ |
| CSDN | https://blog.csdn.net/ |
| 博客园 | https://www.cnblogs.com/ |

---

## 附录

### 附录A：数据库字典

（详细字段说明见11.1节）

### 附录B：API接口完整列表

（详见接口设计文档）

### 附录C：技术栈版本清单

| 技术 | 版本 |
|------|------|
| JDK | 1.8+ |
| Spring Boot | 2.7.18 |
| Spring Security | 5.7.x |
| MyBatis-Plus | 3.5.x |
| MySQL | 8.0 |
| Vue | 3.3.x |
| Element Plus | 2.4.x |
| Vite | 4.x |
| Node.js | 16.x+ |

---

**文档结束**
