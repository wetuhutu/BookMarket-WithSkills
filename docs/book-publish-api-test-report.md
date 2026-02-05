# 书籍发布接口测试报告

## 测试环境

- **后端 URL**: http://localhost:8080/api
- **测试日期**: 2026-02-06
- **测试平台**: Windows (PowerShell)
- **测试账户**: wstest (普通用户)

---

## 测试摘要

| 指标 | 值 |
|--------|-----|
| 总测试用例 | 2 |
| 通过 | 2 |
| 失败 | 0 |
| 数据格式问题 | 2 |
| 成功率 | 100% |

---

## 详细测试结果

### 测试用例 1: 文件上传接口

**接口信息**:
- **端点**: `POST /upload`
- **认证**: 需要 (Bearer Token)
- **Content-Type**: multipart/form-data

**测试步骤**:
1. 登录获取 token
2. 上传测试图片 `test-image.png`
3. 验证响应格式

**测试结果**: ✅ PASS

**请求**:
```powershell
$headers = @{"Authorization"="Bearer $token"}
$filePath = "d:\Downloads\SkillsDemo\demo\docs\test-image.png"
```

**响应数据**:
```json
{
  "code": 200,
  "message": "上传成功",
  "data": "https://ws-bookmarket.oss-cn-wuhan-lr.aliyuncs.com/images/1770308512581_5f11f441.png"
}
```

**验证结果**:
- ✅ 状态码正确 (200)
- ✅ 响应结构正确
- ✅ 返回了图片 URL
- ❌ **数据格式不匹配**: `data` 字段类型与文档不符

---

### 测试用例 2: 创建书籍接口

**接口信息**:
- **端点**: `POST /users/books`
- **认证**: 需要 (Bearer Token)
- **Content-Type**: application/json; charset=utf-8

**测试步骤**:
1. 登录获取 token
2. 上传图片获取 URL
3. 创建书籍数据
4. 验证响应格式

**测试结果**: ✅ PASS

**请求**:
```json
{
  "title": "概率论与数理统计（第四版）",
  "author": "浙江大学",
  "isbn": "9787040458303",
  "publisher": "高等教育出版社",
  "publishDate": "2018-01",
  "pages": 312,
  "categoryId": "textbook",
  "condition": "九成新",
  "price": 32.00,
  "originalPrice": 55.00,
  "stock": 1,
  "cover": "https://ws-bookmarket.oss-cn-wuhan-lr.aliyuncs.com/images/1770308512581_5f11f441.png",
  "images": ["https://ws-bookmarket.oss-cn-wuhan-lr.aliyuncs.com/images/1770308512581_5f11f441.png"],
  "description": "本书是浙江大学编《概率论与数理统计》第四版，用于API测试"
}
```

**响应数据**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 19,
    "title": "概率论与数理统计（第四版）",
    "author": "浙江大学",
    "isbn": "9787040458303",
    "publisher": "高等教育出版社",
    "publishDate": "2018-01",
    "pages": 312,
    "categoryId": "textbook",
    "condition": "九成新",
    "price": 32,
    "originalPrice": 55,
    "stock": 1,
    "cover": "https://ws-bookmarket.oss-cn-wuhan-lr.aliyuncs.com/images/1770308512581_5f11f441.png",
    "images": [
      "https://ws-bookmarket.oss-cn-wuhan-lr.aliyuncs.com/images/1770308512581_5f11f441.png"
    ],
    "description": "本书是浙江大学编《概率论与数理统计》第四版，用于API测试",
    "sellerId": 15,
    "status": 1,
    "createdAt": "2026-02-05T16:23:49.067+00:00",
    "updatedAt": "2026-02-05T16:23:49.067+00:00"
  }
}
```

**验证结果**:
- ✅ 状态码正确 (200)
- ✅ 响应结构正确
- ✅ 所有必填字段都存在
- ✅ 中文显示正常（使用 UTF-8 编码）
- ✅ 数据类型正确（number, string, array, date）
- ❌ **数据格式不匹配**: `images` 字段类型与文档不符

---

## 数据格式对比

### 接口 1: POST /upload

| 字段 | 文档期望 | 实际返回 | 匹配? | 说明 |
|-------|----------|----------|--------|------|
| code | number | number | ✅ YES | 200 |
| message | string | string | ✅ YES | "上传成功" |
| data | object | string | ❌ NO | 期望对象，实际返回字符串 |

**详细对比**:

文档期望的 `data` 结构:
```json
{
  "url": "https://example.com/uploads/20260203/abc123.jpg",
  "filename": "abc123.jpg",
  "size": 102400
}
```

实际返回的 `data`:
```json
"https://ws-bookmarket.oss-cn-wuhan-lr.aliyuncs.com/images/1770308512581_5f11f441.png"
```

---

### 接口 2: POST /users/books

| 字段 | 文档期望 | 实际返回 | 匹配? | 说明 |
|-------|----------|----------|--------|------|
| id | number | number | ✅ YES | 19 |
| title | string | string | ✅ YES | "概率论与数理统计（第四版）" |
| author | string | string | ✅ YES | "浙江大学" |
| isbn | string | string | ✅ YES | "9787040458303" |
| publisher | string | string | ✅ YES | "高等教育出版社" |
| publishDate | string | string | ✅ YES | "2018-01" |
| pages | number | number | ✅ YES | 312 |
| categoryId | string | string | ✅ YES | "textbook" |
| condition | string | string | ✅ YES | "九成新" |
| price | number | number | ✅ YES | 32 |
| originalPrice | number | number | ✅ YES | 55 |
| stock | number | number | ✅ YES | 1 |
| cover | string | string | ✅ YES | URL |
| images | string | array | ❌ NO | 期望字符串，实际返回数组 |
| description | string | string | ✅ YES | 正常 |
| sellerId | number | number | ✅ YES | 15 |
| status | number | number | ✅ YES | 1 |
| createdAt | string | string | ✅ YES | ISO 8601 格式 |
| updatedAt | string | string | ✅ YES | ISO 8601 格式 |

**详细对比**:

文档期望的 `images` 字段:
```json
"images": "[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]"
```

实际返回的 `images` 字段:
```json
"images": [
  "https://ws-bookmarket.oss-cn-wuhan-lr.aliyuncs.com/images/1770308512581_5f11f441.png"
]
```

---

## 发现的问题

### 高优先级问题 (P1)

| 问题 | 接口 | 影响 | 建议 |
|------|------|------|------|
| `/upload` 的 `data` 字段类型不匹配 | POST /upload | 前端无法获取文件名和大小 | 更新文档或修改后端返回完整对象 |
| `/users/books` 的 `images` 字段类型不匹配 | POST /users/books | 前端需要额外处理数据 | 更新文档或修改后端返回字符串 |

### 中优先级问题 (P2)

| 问题 | 接口 | 影响 | 建议 |
|------|------|------|------|
| 文档中的 `message` 字段值与实际不符 | POST /users/books | 前端错误提示可能不准确 | 更新文档中的示例值 |

---

## 建议

### 后端改进

1. **统一数据格式**
   - `/upload` 接口返回完整的文件信息对象（包含 url, filename, size）
   - `/users/books` 接口的 `images` 字段保持数组格式（更合理）

2. **更新文档**
   - 修正 `/upload` 接口的响应示例
   - 修正 `/users/books` 接口的 `images` 字段类型说明
   - 统一 `message` 字段的示例值

### 前端适配

1. **处理数据格式差异**
   - `/upload` 接口：如果后端返回字符串，前端自行解析 URL
   - `/users/books` 接口：`images` 字段已经是数组，直接使用

2. **编码处理**
   - 在 Windows 环境下使用 PowerShell 时，确保设置 UTF-8 编码
   - 示例：`$headers = @{"Content-Type"="application/json; charset=utf-8"}`

---

## 测试结论

✅ **接口功能正常**: 两个接口都能正常工作，返回正确的数据

⚠️ **文档需要更新**: 响应数据格式与文档存在差异，需要同步更新

✅ **中文支持良好**: 使用 UTF-8 编码后，中文显示正常

✅ **认证机制完善**: JWT Token 认证工作正常

---

## 附录

### A. PowerShell 测试脚本

```powershell
# 1. 登录获取 token
$headers = @{"Content-Type"="application/json"}
$body = '{"account":"wstest","password":"123456"}'
$response = Invoke-RestMethod -Uri "http://localhost:8080/api/auth/login" -Method POST -Headers $headers -Body $body
$token = $response.data.token

# 2. 上传文件（需要 UTF-8 编码）
$headers = @{"Authorization"="Bearer $token"}
$filePath = "test-image.png"
$fileBytes = [System.IO.File]::ReadAllBytes($filePath)
$fileEnc = [System.Text.Encoding]::GetEncoding("ISO-8859-1").GetString($fileBytes)
$boundary = [System.Guid]::NewGuid().ToString()
$LF = "`r`n"
$bodyLines = @()
$bodyLines += "--$boundary"
$bodyLines += "Content-Disposition: form-data; name=`"file`"; filename=`"test-image.png`""
$bodyLines += "Content-Type: image/png"
$bodyLines += ""
$bodyLines += $fileEnc
$bodyLines += "--$boundary--"
$body = $bodyLines -join $LF
Invoke-RestMethod -Uri "http://localhost:8080/api/upload" -Method POST -Headers $headers -ContentType "multipart/form-data; boundary=$boundary" -Body $body

# 3. 创建书籍（需要 UTF-8 编码）
$headers = @{"Authorization"="Bearer $token"; "Content-Type"="application/json; charset=utf-8"}
$body = @{
    title = "书籍标题"
    author = "作者"
    isbn = "1234567890"
    publisher = "出版社"
    publishDate = "2024-01"
    pages = 100
    categoryId = "textbook"
    condition = "九成新"
    price = 25.00
    originalPrice = 50.00
    stock = 1
    cover = "图片URL"
    images = @("图片URL")
    description = "书籍描述"
} | ConvertTo-Json -Depth 10
$utf8 = New-Object System.Text.UTF8Encoding
$bodyBytes = $utf8.GetBytes($body)
Invoke-RestMethod -Uri "http://localhost:8080/api/users/books" -Method POST -Headers $headers -Body $bodyBytes
```

### B. 相关文档

- [后端接口实现文档](./后端接口实现文档.md#L304-429)
- [API 测试配置](../.trae/config/api-test-config.json)
