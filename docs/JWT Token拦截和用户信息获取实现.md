# JWT Token 拦截和用户信息获取实现

## 修改时间
2026-02-03

## 一、修改内容

### 1.1 创建 JwtAuthenticationFilter

**文件**: `backend-code/src/main/java/com/bookmarket/filter/JwtAuthenticationFilter.java`

**功能**: JWT Token 拦截器，从请求头中提取 token 并验证，将用户信息设置到 Spring Security 上下文中。

**核心代码**:
```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String token = getTokenFromRequest(request);
        
        if (token != null && jwtUtil.validateToken(token)) {
            String username = jwtUtil.getUsernameFromToken(token);
            Long userId = jwtUtil.getUserIdFromToken(token);
            
            if (username != null) {
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                
                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
```

**工作流程**:
1. 从请求头 `Authorization` 中提取 token
2. 使用 `JwtUtil.validateToken()` 验证 token 有效性
3. 从 token 中解析用户名和用户ID
4. 创建 `UsernamePasswordAuthenticationToken` 并设置到 Security 上下文
5. 继续执行过滤器链

### 1.2 扩展 JwtUtil

**文件**: `backend-code/src/main/java/com/bookmarket/utils/JwtUtil.java`

**新增方法**:

#### 1. validateToken

```java
public boolean validateToken(String token) {
    try {
        Claims claims = parseJwt(token);
        return claims != null && !isTokenExpired(claims);
    } catch (Exception e) {
        return false;
    }
}
```

**功能**: 验证 token 是否有效（格式正确且未过期）

#### 2. getUsernameFromToken

```java
public String getUsernameFromToken(String token) {
    Claims claims = parseJwt(token);
    return claims != null ? claims.getSubject() : null;
}
```

**功能**: 从 token 中获取用户名

#### 3. getUserIdFromToken

```java
public Long getUserIdFromToken(String token) {
    Claims claims = parseJwt(token);
    if (claims != null && claims.get("userId") != null) {
        return claims.get("userId", Long.class);
    }
    return null;
}
```

**功能**: 从 token 中获取用户ID

#### 4. isTokenExpired

```java
private boolean isTokenExpired(Claims claims) {
    Date expiration = claims.getExpiration();
    return expiration != null && expiration.before(new Date());
}
```

**功能**: 检查 token 是否已过期

#### 5. 改进 parseJwt

```java
public Claims parseJwt(String token) {
    try {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    } catch (Exception e) {
        return null;
    }
}
```

**改进**: 添加异常处理，解析失败时返回 null

### 1.3 更新 SecurityConfig

**文件**: `backend-code/src/main/java/com/bookmarket/config/SecurityConfig.java`

**修改内容**:

#### 1. 注入 JwtAuthenticationFilter

```java
@Autowired
private JwtAuthenticationFilter jwtAuthenticationFilter;
```

#### 2. 配置过滤器链

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/books/**").permitAll()
                    .requestMatchers("/categories/**").permitAll()
                    .requestMatchers("/upload").permitAll()
                    .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
}
```

**配置说明**:
- `.csrf(csrf -> csrf.disable())`: 禁用 CSRF 保护
- `.requestMatchers("/auth/**").permitAll()`: 登录/注册接口无需认证
- `.requestMatchers("/books/**").permitAll()`: 书籍列表/详情接口无需认证
- `.requestMatchers("/categories/**").permitAll()`: 分类接口无需认证
- `.requestMatchers("/upload").permitAll()`: 文件上传接口无需认证
- `.anyRequest().authenticated()`: 其他所有接口都需要认证
- `.addFilterBefore(jwtAuthenticationFilter, ...)`: 在用户名密码过滤器之前添加 JWT 过滤器
- `.sessionManagement(...STATELESS)`: 使用无状态会话管理

## 二、工作原理

### 2.1 Token 验证流程

```
客户端请求
  ↓
携带 Authorization: Bearer {token}
  ↓
JwtAuthenticationFilter 拦截
  ↓
提取 token
  ↓
JwtUtil.validateToken(token)
  ↓
解析 token (userId, username, expiration)
  ↓
检查 token 是否过期
  ↓
创建 Authentication 对象
  ↓
设置到 Security 上下文
  ↓
继续执行后续过滤器
  ↓
到达 Controller
  ↓
从 Security 上下文获取用户信息
  ↓
处理业务逻辑
```

### 2.2 获取用户信息的方式

#### 方式1: 从 Security 上下文获取（推荐）

```java
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
if (authentication != null && authentication.isAuthenticated()) {
    String username = authentication.getName();
}
```

#### 方式2: 从 token 直接获取

```java
String token = request.getHeader("Authorization");
if (token != null) {
    String username = jwtUtil.getUsernameFromToken(token);
    Long userId = jwtUtil.getUserIdFromToken(token);
}
```

#### 方式3: 创建工具类（推荐）

```java
@Component
public class UserContext {
    
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        String token = getTokenFromRequest();
        return jwtUtil.getUserIdFromToken(token);
    }
    
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getName();
    }
}
```

## 三、使用示例

### 3.1 Controller 中获取用户信息

```java
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/profile")
    public Result<User> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userService.getByUsername(username);
        return Result.success(user);
    }
}
```

### 3.2 Service 中获取用户信息

```java
@Service
public class BookServiceImpl implements BookService {

    @Override
    public Book createBook(Book book) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userService.getByUsername(username);
        book.setSellerId(user.getId());
        
        return bookMapper.insert(book);
    }
}
```

### 3.3 创建 UserContext 工具类

```java
@Component
public class UserContext {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    public Long getCurrentUserId() {
        HttpServletRequest request = 
            ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String token = getTokenFromRequest(request);
        return jwtUtil.getUserIdFromToken(token);
    }
    
    public String getCurrentUsername() {
        HttpServletRequest request = 
            ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String token = getTokenFromRequest(request);
        return jwtUtil.getUsernameFromToken(token);
    }
    
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
```

**使用示例**:
```java
Long userId = UserContext.getCurrentUserId();
String username = UserContext.getCurrentUsername();
```

## 四、前端对接

### 4.1 请求头格式

前端需要在所有需要认证的请求中携带 token：

```javascript
// src/api/request.js
const request = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000
})

request.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})
```

### 4.2 Token 过期处理

```javascript
request.interceptors.response.use(
  response => response,
  async error => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      router.push('/login')
    }
    return Promise.reject(error)
  }
)
```

## 五、测试验证

### 5.1 测试步骤

1. 登录获取 token
2. 使用 token 访问需要认证的接口
3. 验证用户信息是否正确获取

### 5.2 测试命令

```bash
# 1. 登录获取 token
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"account":"newuser","password":"123456"}'

# 2. 使用 token 访问用户信息
curl -X GET http://localhost:8080/api/users/profile \
  -H "Authorization: Bearer {token}"

# 3. 使用 token 创建书籍
curl -X POST http://localhost:8080/api/books \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{"title":"测试书籍","author":"测试作者",...}'
```

### 5.3 预期结果

- ✅ 无 token 访问受保护接口返回 401
- ✅ 有效 token 访问受保护接口返回 200
- ✅ 过期 token 访问受保护接口返回 401
- ✅ Controller 可以正确获取用户信息

## 六、注意事项

### 6.1 安全性

1. **Token 密钥**: 使用强密钥，不要使用默认密钥
2. **Token 过期**: 设置合理的过期时间（建议 7 天）
3. **HTTPS**: 生产环境必须使用 HTTPS 传输 token
4. **Token 存储**: 前端使用 localStorage 存储，注意 XSS 攻击

### 6.2 性能优化

1. **缓存解析**: 可以缓存 token 解析结果，避免重复解析
2. **异步验证**: 如果需要验证数据库，使用异步方式
3. **过滤器顺序**: JWT 过滤器应该在用户名密码过滤器之前

### 6.3 错误处理

1. **Token 格式错误**: 返回 401 Unauthorized
2. **Token 过期**: 返回 401 Unauthorized
3. **Token 无效**: 返回 401 Unauthorized
4. **用户不存在**: 返回 404 Not Found

## 七、常见问题

### 7.1 Token 无法解析

**问题**: `parseJwt` 抛出异常

**原因**: 
- Token 格式错误
- Token 签名不匹配
- Token 已损坏

**解决**: 
- 检查 secret 配置是否正确
- 检查 token 是否被篡改

### 7.2 Token 验证失败

**问题**: `validateToken` 返回 false

**原因**:
- Token 已过期
- Token 格式错误

**解决**: 
- 前端重新登录获取新 token
- 检查 expiration 配置是否合理

### 7.3 无法获取用户信息

**问题**: Security 上下文中没有 Authentication

**原因**:
- Token 验证失败
- 过滤器未正确执行

**解决**:
- 检查 SecurityConfig 配置
- 检查 JwtAuthenticationFilter 是否正确注册

## 八、总结

### 8.1 完成的工作

✅ 创建 `JwtAuthenticationFilter` 过滤器
✅ 扩展 `JwtUtil` 工具类，添加验证和获取方法
✅ 更新 `SecurityConfig`，配置 JWT 拦截
✅ 配置无状态会话管理
✅ 配置公开和受保护接口

### 8.2 工作原理

1. **拦截器**: 在所有请求之前执行
2. **Token 提取**: 从 `Authorization` 请求头中提取
3. **Token 验证**: 验证 token 有效性
4. **用户信息**: 从 token 中解析用户名和用户ID
5. **Security 上下文**: 将用户信息设置到 Spring Security 上下文
6. **业务使用**: Controller/Service 可以从 Security 上下文获取用户信息

### 8.3 优势

1. **无状态**: 不依赖 session，适合分布式系统
2. **可扩展**: 可以轻松添加自定义 claims
3. **安全性**: 使用 JWT 标准，安全性高
4. **性能**: 避免每次请求都查询数据库

## 九、相关文件

### 9.1 新增文件

- `backend-code/src/main/java/com/bookmarket/filter/JwtAuthenticationFilter.java` - JWT 过滤器

### 9.2 修改文件

- `backend-code/src/main/java/com/bookmarket/utils/JwtUtil.java` - 添加验证和获取方法
- `backend-code/src/main/java/com/bookmarket/config/SecurityConfig.java` - 配置 JWT 拦截

### 9.3 配置文件

- `.gitignore` - 添加 backend-code/ 到忽略列表
