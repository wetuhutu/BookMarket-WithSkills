---
name: "backend-api-tester"
description: "Tests backend APIs, validates data formats against frontend specifications, and generates standardized test reports. Invoke when user needs to test backend interfaces, validate API responses, or generate API test reports."
---

# Backend API Tester

This skill performs comprehensive backend API testing, validates data formats against frontend specifications, and generates standardized test reports.

## ⚠️ Important Prerequisites

**Before using this skill, ensure the following:**

1. **Backend Server Must Be Running**: This skill **does NOT check if the backend is running**. It assumes the backend server is already running and accessible at the configured `backendUrl`. If the backend is not running, all test cases will fail with connection errors.

2. **Windows Users**: On Windows systems, this skill uses **PowerShell** for HTTP requests. The skill automatically detects the operating system and selects the appropriate HTTP client:
   - **Windows**: PowerShell (`Invoke-RestMethod`)
   - **Linux/Mac**: curl command
   - **Node.js**: axios (if available)

3. **Manual Backend Start**: You must manually ensure the backend server is running before invoking this skill. The skill will not attempt to start or restart the backend server.

## When to Use

Invoke this skill when:
- User wants to test backend API endpoints
- User needs to validate API response formats
- User wants to verify data consistency between backend and frontend
- User needs to generate API test reports
- User wants to check API response status codes, response times, and data structures
- User needs to compare backend responses with frontend interface definitions
- User says "测试后端接口" (test backend interface) - trigger phrase for backend API testing
- User says "生成测试报告" (generate test report) - trigger phrase for report generation

## Core Features

### 1. Backend API Testing (Automated)

Tests backend API endpoints to ensure they are functioning correctly:

- **Endpoint Availability**: Check if all API endpoints are accessible
- **HTTP Methods**: Verify GET, POST, PUT, DELETE methods work as expected
- **Response Format**: Validate response structure matches expected format
- **Status Codes**: Check for proper HTTP status codes (200, 201, 400, 401, 404, 500)
- **Response Time**: Measure and validate API response times
- **Error Handling**: Verify error messages are properly formatted
- **Cross-Platform Support**: Automatically detect OS and use appropriate HTTP client

### 2. Data Format Validation (Automated)

Validates data consistency between backend responses and frontend interface definitions:

- **Field Mapping**: Check if backend response fields match frontend expected fields
- **Data Types**: Verify data types (string, number, boolean, date) are correct
- **Date Formats**: Check date/time formats are consistent
- **Number Formats**: Verify price, quantity, and other numeric formats
- **Enum Values**: Validate enum values (status, condition, category) match
- **Required Fields**: Ensure all required fields are present
- **Null/Undefined Handling**: Validate handling of null and undefined values

### 3. Frontend Interface Comparison (Automated)

Automatically compares backend responses with frontend interface definitions:

- **Interface Definition Parsing**: Read and parse frontend API interface definitions
- **Schema Validation**: Compare backend response schema with frontend schema
- **Type Checking**: Verify data types match between backend and frontend
- **Field Name Mapping**: Handle camelCase vs snake_case conversions
- **Nested Structure Validation**: Validate nested objects and arrays

### 4. Test Report Generation (Automated)

Generates standardized test reports with comprehensive information:

- **Test Summary**: Overview of test results (pass/fail statistics)
- **Detailed Test Results**: Individual test case results with status
- **API Comparison Analysis**: Detailed comparison between backend and frontend
- **Issue Summary**: List of all issues found with severity levels
- **Performance Metrics**: Response times and performance analysis
- **Recommendations**: Actionable recommendations for fixes

### 5. Authentication Management (Automated)

Automatically manages authentication for protected endpoints:

- **Auto Login**: Automatically login with configured test accounts
- **Token Management**: Automatically obtain and cache JWT tokens
- **Token Refresh**: Automatically refresh expired tokens
- **Multi-User Support**: Support testing with different user roles

## Testing Workflow

### Phase 1: Configuration and Setup

1. **Load Test Configuration**
   - Read test configuration from `.trae/config/api-test-config.json`
   - Parse test cases and validation rules
   - Load test accounts for authentication
   - Load frontend interface definitions

2. **Configuration File Format** (`.trae/config/api-test-config.json`):
   ```json
   {
     "backendUrl": "http://localhost:8080/api",
     "testAccounts": [
       {
         "name": "普通用户",
         "account": "wstest",
         "password": "123456"
       },
       {
         "name": "管理员",
         "account": "admin",
         "password": "admin123"
       }
     ],
     "testCases": [
       {
         "name": "Get Book Categories",
         "endpoint": "/books/categories",
         "method": "GET",
         "expectedStatus": 200,
         "maxResponseTime": 200,
         "requiresAuth": false,
         "validationRules": {
           "responseStructure": {
             "code": "number",
             "message": "string",
             "data": "array"
           },
           "dataFields": [
             {
               "name": "string",
               "id": "string",
               "icon": "string",
               "count": "number"
             }
           ]
         }
       }
     ],
     "successThreshold": 0.8,
     "reportFormat": "markdown",
     "reportPath": "./docs/api-test-report.md"
   }
   ```

### Phase 2: Authentication Setup (Automated)

1. **Initialize Authentication Manager**
   - Load test accounts from configuration
   - Create AuthManager instance
   - Prepare authentication headers

2. **Auto Login for Required Endpoints**
   - For each test case that requires authentication:
     - Select appropriate test account
     - Execute login request automatically
     - Cache JWT token
     - Prepare authorization headers

3. **Token Management**
   - Store tokens in memory for the test session
   - Check token expiration before each request
   - Refresh expired tokens automatically
   - Handle authentication errors gracefully

### Phase 3: Backend API Testing (Automated)

1. **Execute Test Cases**
   - For each test case in configuration:
     - Detect OS and select appropriate HTTP client
     - Prepare request (headers, body, query params)
     - Send HTTP request to backend endpoint
     - Measure response time
     - Capture response status code
     - Capture response body

2. **Cross-Platform HTTP Request**

   **Windows (PowerShell)**:
   ```powershell
   $headers = @{"Content-Type"="application/json"; "Authorization"="Bearer $token"}
   $body = '{"key":"value"}'
   $response = Invoke-RestMethod -Uri "$backendUrl$endpoint" -Method $method -Headers $headers -Body $body
   $responseTime = (Measure-Command { $response }).TotalMilliseconds
   ```

   **Linux/Mac (curl)**:
   ```bash
   curl -X $method "$backendUrl$endpoint" \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer $token" \
     -d '{"key":"value"}' \
     -w "\n%{time_total}" \
     -s
   ```

   **Node.js (axios)**:
   ```javascript
   const axios = require('axios');
   const response = await axios({
     method: method,
     url: `${backendUrl}${endpoint}`,
     headers: headers,
     data: body
   });
   const responseTime = response.headers['x-response-time'] || 0;
   ```

3. **Validate Responses (Automated)**
   - Check response status code matches expected
   - Check response time is within threshold
   - Validate response structure matches expected schema
   - Validate data types of all fields
   - Validate required fields are present
   - Validate field values against constraints
   - Detect null/undefined values in required fields
   - Compare with frontend interface definitions

4. **Store Test Results**
   - Save individual test case results
   - Track pass/fail status
   - Record error messages and details
   - Store response time metrics
   - Capture full response for analysis

### Phase 4: Frontend Interface Comparison (Automated)

1. **Load Frontend Interface Definitions**
   - Read frontend API interface definitions from `src/api/` directory
   - Parse TypeScript/JavaScript interface definitions
   - Extract expected data structures and types
   - Build schema map for comparison

2. **Compare Backend Responses with Frontend Definitions**
   - For each API endpoint tested:
     - Compare backend response structure with frontend interface
     - Check field names match (considering naming conventions)
     - Verify data types match
     - Check for missing or extra fields
     - Validate nested structures
     - Detect type mismatches (e.g., decimal vs number)
     - Identify null values in required fields

3. **Generate Comparison Report (Automated)**
   - List matching fields
   - List mismatched fields with details
   - List missing fields
   - List extra fields
   - Provide recommendations for fixes
   - Calculate match percentage

### Phase 5: Performance Analysis (Automated)

1. **Collect Performance Metrics**
   - Response time for each endpoint
   - Min, Max, Average response times
   - P50, P95, P99 percentiles
   - Response time distribution
   - Request success rate

2. **Analyze Performance**
   - Identify slow endpoints
   - Compare against thresholds
   - Detect performance degradation
   - Generate performance recommendations

### Phase 6: Test Report Generation (Automated)

1. **Generate Test Summary**
   - Total test cases executed
   - Pass count and percentage
   - Fail count and percentage
   - Average response time
   - Performance metrics

2. **Generate Detailed Test Results**
   - Table of all test cases with status
   - Detailed results for each test case
   - Error messages and stack traces (if any)
   - Response time for each endpoint
   - Validation results

3. **Generate API Comparison Analysis**
   - Summary of comparison results
   - Detailed field-by-field comparison
   - Type mismatches
   - Missing fields
   - Extra fields
   - Match percentage

4. **Generate Issue Summary (Automated)**
   - List of all issues found
   - Severity levels (Critical, High, Medium, Low)
   - Impact analysis
   - Recommendations for fixes
   - Categorize issues by type (data, performance, security)

5. **Save Test Report**
   - Write report to specified path
   - Use specified format (markdown, HTML, JSON)
   - Include timestamp and test environment info
   - Add performance charts (if HTML format)

## Test Report Format

Generate a comprehensive test report following this structure:

```markdown
# Backend API Test Report

## Test Environment

- **Backend URL**: http://localhost:8080/api
- **Test Date**: YYYY-MM-DD HH:mm:ss
- **Test Duration**: X minutes
- **Total Test Cases**: N
- **Test Account**: [Account Name]

---

## Test Summary

| Metric | Value |
|--------|-------|
| Total Test Cases | N |
| Passed | M (X%) |
| Failed | K (Y%) |
| Average Response Time | Zms |
| Success Rate | X% |
| Performance Score | A/B/C |

---

## Detailed Test Results

### API Endpoint Tests

| Endpoint | Method | Status | Response Time | Notes |
|----------|--------|--------|---------------|-------|
| /books/categories | GET | ✅ PASS | 85ms | - |
| /books/hot | GET | ✅ PASS | 92ms | - |
| /books | GET | ❌ FAIL | - | 500 Internal Server Error |

#### Test Case Details

**Test Case: Get Book Categories**
- **Endpoint**: `/books/categories`
- **Method**: GET
- **Expected Status**: 200
- **Actual Status**: 200
- **Response Time**: 85ms
- **Status**: ✅ PASS

**Response Data**:
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {"name": "教育", "id": "education", "icon": "📚", "count": 2}
  ]
}
```

**Validation Results**:
- ✅ Status code matches expected
- ✅ Response time within threshold (200ms)
- ✅ Response structure matches expected
- ✅ Data types are correct
- ✅ Required fields are present
- ✅ No null values in required fields

---

## Frontend Interface Comparison

### Comparison Summary

| API Endpoint | Fields Matched | Fields Mismatched | Missing Fields | Extra Fields | Match % | Status |
|--------------|----------------|-------------------|----------------|--------------|---------|--------|
| /books/categories | 4 | 0 | 0 | 0 | 100% | ✅ PASS |
| /books/hot | 8 | 2 | 1 | 0 | 73% | ⚠️ PARTIAL |

### Detailed Comparison

**API Endpoint: `/books/categories`**

| Field | Backend Type | Frontend Type | Match? | Notes |
|-------|--------------|---------------|---------|-------|
| name | string | string | ✅ YES | - |
| id | string | string | ✅ YES | - |
| icon | string | string | ✅ YES | - |
| count | number | number | ✅ YES | - |

**API Endpoint: `/books/hot`**

| Field | Backend Type | Frontend Type | Match? | Notes |
|-------|--------------|---------------|---------|-------|
| id | number | number | ✅ YES | - |
| title | string | string | ✅ YES | - |
| author | string | string | ✅ YES | - |
| condition | string | string | ✅ YES | - |
| price | decimal | number | ⚠️ NO | Type mismatch |
| originalPrice | decimal | number | ⚠️ NO | Type mismatch |
| cover | string | string | ✅ YES | - |
| sellerName | string | string | ✅ YES | - |
| sellerLevel | string | string | ✅ YES | - |
| sellerRating | number | number | ✅ YES | - |
| isVerified | number | boolean | ⚠️ NO | Type mismatch |

**Missing Fields**:
- None

**Extra Fields**:
- None

---

## Issues Found

### Critical Issues (P0)

| Issue | API Endpoint | Impact | Recommendation |
|-------|--------------|--------|----------------|
| 500 Internal Server Error | /books | All book listing features fail | Check backend logs and fix error |

### High Priority Issues (P1)

| Issue | API Endpoint | Impact | Recommendation |
|-------|--------------|--------|----------------|
| Type mismatch: price field | /books/hot | Price display may have precision issues | Convert decimal to number in frontend or backend |
| Type mismatch: originalPrice field | /books/hot | Price display may have precision issues | Convert decimal to number in frontend or backend |
| Type mismatch: isVerified field | /books/hot | Boolean logic may fail | Convert number to boolean in frontend or backend |

### Medium Priority Issues (P2)

| Issue | API Endpoint | Impact | Recommendation |
|-------|--------------|--------|----------------|
| Response time exceeds threshold | /books | Slow user experience | Optimize backend query or add caching |

### Low Priority Issues (P3)

| Issue | API Endpoint | Impact | Recommendation |
|-------|--------------|--------|----------------|
| None | - | - | - |

---

## Performance Metrics

| Metric | Value |
|--------|-------|
| Average Response Time | 95ms |
| Min Response Time | 45ms |
| Max Response Time | 250ms |
| P50 Response Time | 85ms |
| P95 Response Time | 180ms |
| P99 Response Time | 230ms |

### Response Time Distribution

| Range | Count | Percentage |
|-------|-------|-------------|
| < 100ms | 15 | 75% |
| 100-200ms | 4 | 20% |
| 200-300ms | 1 | 5% |
| > 300ms | 0 | 0% |

### Performance Grade

| Endpoint | Response Time | Grade |
|----------|--------------|-------|
| /books/categories | 85ms | A |
| /books/hot | 92ms | A |
| /books | 250ms | C |

---

## Recommendations

### Backend Improvements

1. **Fix Critical Error**
   - Investigate 500 error on `/books` endpoint
   - Check backend logs for error details
   - Implement proper error handling

2. **Data Type Consistency**
   - Standardize decimal to number conversion
   - Ensure boolean fields use correct type
   - Document data types in API documentation

3. **Performance Optimization**
   - Add database indexing for slow queries
   - Implement caching for frequently accessed data
   - Consider pagination for large datasets

### Frontend Improvements

1. **Type Conversion**
   - Add utility functions for type conversion
   - Handle decimal to number conversion
   - Handle number to boolean conversion

2. **Error Handling**
   - Implement proper error handling for API failures
   - Show user-friendly error messages
   - Add retry logic for transient errors

3. **Data Validation**
   - Validate API responses before using
   - Handle null/undefined values gracefully
   - Implement data sanitization

---

## Appendix

### A. Test Configuration

```json
{
  "backendUrl": "http://localhost:8080/api",
  "testAccounts": [...],
  "testCases": [...],
  "successThreshold": 0.8,
  "reportFormat": "markdown",
  "reportPath": "./docs/api-test-report.md"
}
```

### B. Test Commands

```bash
# Run all tests
npm run test:api

# Run specific test
npm run test:api -- --test-case="Get Book Categories"

# Generate report only
npm run test:api -- --report-only

# Use specific test account
npm run test:api -- --account="admin"
```

### C. Related Documents

- [Frontend API Definitions](../src/api/)
- [Backend API Documentation](./backend-api-doc.md)
- [Integration Test Report](./integration-test-report.md)
```

## Configuration Options

### Test Configuration Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `backendUrl` | string | `http://localhost:8080/api` | Base URL for backend API |
| `testAccounts` | array | `[]` | Array of test account configurations |
| `testCases` | array | `[]` | Array of test case configurations |
| `successThreshold` | number | `0.8` | Minimum success rate (0-1) |
| `reportFormat` | string | `markdown` | Report format (markdown, html, json) |
| `reportPath` | string | `./docs/api-test-report.md` | Path to save test report |
| `maxResponseTime` | number | `200` | Maximum acceptable response time (ms) |

### Test Account Configuration

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `name` | string | Yes | Test account name for reference |
| `account` | string | Yes | Username/account for login |
| `password` | string | Yes | Password for login |

### Test Case Configuration

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `name` | string | Yes | Test case name |
| `endpoint` | string | Yes | API endpoint path |
| `method` | string | Yes | HTTP method (GET, POST, PUT, DELETE) |
| `expectedStatus` | number | Yes | Expected HTTP status code |
| `maxResponseTime` | number | No | Maximum response time (ms) |
| `requiresAuth` | boolean | No | Whether endpoint requires authentication (default: false) |
| `useAccount` | string | No | Name of test account to use |
| `headers` | object | No | Request headers |
| `body` | object | No | Request body (for POST/PUT) |
| `queryParams` | object | No | Query parameters (for GET) |
| `validationRules` | object | No | Validation rules for response |

### Validation Rules

| Parameter | Type | Description |
|-----------|------|-------------|
| `responseStructure` | object | Expected structure of response |
| `dataFields` | array or object | Expected fields in data (array format for array data, object format for object data) |
| `requiredFields` | array | List of required fields |
| `typeChecks` | object | Type checks for specific fields |
| `valueConstraints` | object | Value constraints (min, max, enum) |

**Important Notes on `dataFields`**:
- When `data` is an **array**, use **array format** to describe fields in each array item:
  ```json
  "dataFields": [
    {
      "name": "string",
      "id": "string",
      "icon": "string",
      "count": "number"
    }
  ]
  ```
- When `data` is an **object**, use **object format** to describe fields in the object:
  ```json
  "dataFields": {
    "list": "array",
    "total": "number",
    "page": "number",
    "pageSize": "number"
  }
  ```

## Implementation Details

### Authentication Manager

The skill includes an automatic authentication manager:

```javascript
class AuthManager {
  constructor(config) {
    this.config = config;
    this.tokens = new Map();
  }

  async login(accountName) {
    const account = this.config.testAccounts.find(a => a.name === accountName);
    if (!account) {
      throw new Error(`Test account not found: ${accountName}`);
    }

    const response = await this.executeRequest('/auth/login', 'POST', {
      account: account.account,
      password: account.password
    });

    this.tokens.set(accountName, response.data.token);
    return response.data.token;
  }

  getToken(accountName) {
    return this.tokens.get(accountName);
  }

  getHeaders(accountName) {
    const token = this.getToken(accountName);
    return {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    };
  }
}
```

### Response Validator

The skill includes an automatic response validator:

```javascript
class ResponseValidator {
  constructor(expectedSchema) {
    this.expectedSchema = expectedSchema;
  }

  validate(actualResponse) {
    const issues = [];

    // Validate status code
    if (actualResponse.code !== this.expectedSchema.code) {
      issues.push({
        field: 'code',
        expected: this.expectedSchema.code,
        actual: actualResponse.code,
        severity: 'high'
      });
    }

    // Validate data structure
    this.validateStructure(actualResponse.data, this.expectedSchema.data, issues);

    // Validate data types
    this.validateTypes(actualResponse.data, this.expectedSchema.data, issues);

    // Validate required fields
    this.validateRequiredFields(actualResponse.data, this.expectedSchema.requiredFields, issues);

    return issues;
  }

  validateStructure(actual, expected, issues) {
    // Recursive structure validation
  }

  validateTypes(actual, expected, issues) {
    // Type checking
  }

  validateRequiredFields(actual, requiredFields, issues) {
    // Required field validation
  }
}
```

### Performance Monitor

The skill includes automatic performance monitoring:

```javascript
class PerformanceMonitor {
  constructor() {
    this.metrics = [];
  }

  async measureRequest(endpoint, method, headers, data) {
    const startTime = Date.now();
    const response = await this.executeRequest(endpoint, method, headers, data);
    const endTime = Date.now();
    const responseTime = endTime - startTime;

    this.metrics.push({
      endpoint,
      method,
      responseTime,
      timestamp: new Date().toISOString()
    });

    return {
      response: response,
      metrics: { responseTime }
    };
  }

  analyzeMetrics() {
    const responseTimes = this.metrics.map(m => m.responseTime);
    return {
      average: this.calculateAverage(responseTimes),
      min: Math.min(...responseTimes),
      max: Math.max(...responseTimes),
      p50: this.calculatePercentile(responseTimes, 50),
      p95: this.calculatePercentile(responseTimes, 95),
      p99: this.calculatePercentile(responseTimes, 99)
    };
  }

  calculateAverage(values) {
    return values.reduce((a, b) => a + b, 0) / values.length;
  }

  calculatePercentile(values, percentile) {
    const sorted = [...values].sort((a, b) => a - b);
    const index = Math.ceil((percentile / 100) * sorted.length) - 1;
    return sorted[index];
  }
}
```

## Implementation Steps

When this skill is invoked:

1. **Check Configuration**
   - Look for `.trae/config/api-test-config.json`
   - If not found, create default configuration
   - Load and parse configuration
   - Validate configuration format

2. **Initialize Components**
   - Create AuthManager instance
   - Create ResponseValidator instance
   - Create PerformanceMonitor instance
   - Detect OS and select HTTP client

3. **Load Frontend Interface Definitions**
   - Scan `src/api/` directory for interface definitions
   - Parse TypeScript/JavaScript interfaces
   - Build schema map for comparison

4. **Execute Test Cases**
   - For each test case:
     - Check if authentication required
     - Auto-login if needed
     - Send HTTP request
     - Measure response time
     - Validate response
     - Store results

5. **Compare with Frontend**
   - For each API tested:
     - Compare with frontend interface
     - Identify mismatches
     - Generate comparison report

6. **Analyze Performance**
   - Calculate performance metrics
   - Generate performance report
   - Identify performance issues

7. **Generate Test Report**
   - Compile all results
   - Generate report in specified format
   - Save to specified path

8. **Return Summary**
   - Provide summary to user
   - Highlight critical issues
   - Suggest next steps

## Integration with Other Skills

### Document Generation Skill

After completing API testing, this skill can invoke document generation skills to create additional documentation:

- **Integration Documentation**: Generate API integration docs
- **Test Summary**: Create concise test summary
- **API Reference**: Generate API reference documentation

### Integration Testing Skill

This skill can work with the integration-testing skill to:

- Validate frontend-backend integration
- Test authentication flows
- Verify data binding

## Best Practices

1. **Test Early and Often**: Run API tests frequently during development
2. **Test Real Scenarios**: Test with actual data, not just mock data
3. **Monitor Performance**: Track response times and performance metrics
4. **Document Everything**: Keep detailed test reports and logs
5. **Automate Tests**: Integrate with CI/CD pipeline
6. **Version Control**: Keep test configurations in version control
7. **Review Regularly**: Update test cases as APIs evolve
8. **Use Multiple Test Accounts**: Test with different user roles
9. **Test Authentication**: Verify token management and refresh
10. **Monitor Security**: Test for security vulnerabilities

## Common Issues and Solutions

### Issue 2: Authentication Required

**Symptom**: 401 Unauthorized errors

**Solution**:
- Ensure test accounts are configured
- Check if auto-login is enabled
- Verify token is being sent correctly

### Issue 3: Data Type Mismatches

**Symptom**: Type validation failures

**Solution**:
- Update validation rules to match actual types
- Add type conversion utilities
- Document expected types in API spec

### Issue 4: Token Expiration

**Symptom**: 401 errors after some time

**Solution**:
- Enable automatic token refresh
- Check token expiration time
- Implement token refresh logic

## Quick Start

```bash
# Create default configuration
npm run api-test:init

# Run all tests
npm run api-test

# Run specific test
npm run api-test -- --test="Get Book Categories"

# Generate report only
npm run api-test -- --report

# Use specific test account
npm run api-test -- --account="admin"

# Custom configuration
npm run api-test -- --config=./custom-config.json
```

## Automation Features

### What's Automated

- ✅ Cross-platform HTTP request execution
- ✅ Automatic authentication and token management
- ✅ Automatic response validation
- ✅ Automatic data type checking
- ✅ Automatic frontend interface comparison
- ✅ Automatic performance monitoring
- ✅ Automatic issue detection and reporting
- ✅ Automatic test report generation

### What Requires Manual Configuration

- ⚠️ Test account credentials (one-time setup)
- ⚠️ Test case definitions (one-time setup)
- ⚠️ Validation rules (one-time setup)
- ⚠️ Backend URL (environment-specific)

### Automation Level

| Feature | Automation Level |
|---------|----------------|
| HTTP Request Execution | 100% |
| Authentication Management | 100% |
| Response Validation | 100% |
| Performance Monitoring | 100% |
| Issue Detection | 100% |
| Report Generation | 100% |
| Test Case Creation | 0% (manual) |
| Validation Rules | 0% (manual) |
| **Overall** | **85%** |

## Notes

### Important Prerequisites

- **Backend Status**: This skill **does NOT check if backend is running** before testing. It assumes the backend server is already running and accessible at the configured `backendUrl`. If the backend is not running, all test cases will fail with connection errors.
- **Windows Users**: On Windows systems, this skill uses **PowerShell** for HTTP requests. The skill automatically detects the operating system and selects the appropriate HTTP client (PowerShell for Windows, curl for Linux/Mac, or Node.js/axios if available).
- **Manual Backend Start**: You must ensure the backend server is running before invoking this skill. The skill will not attempt to start or restart the backend server.

### Platform-Specific Behavior

| Platform | HTTP Client | Command Used |
|----------|-------------|--------------|
| Windows | PowerShell | `Invoke-RestMethod` |
| Linux/Mac | curl | `curl` command |
| Node.js | axios | `axios` library (if available) |

### Error Handling

- If backend is not running: All tests will fail with connection refused or timeout errors
- If authentication fails: Check test account credentials in configuration
- If CORS errors occur: Ensure backend CORS configuration allows the test origin

### Additional Notes

- Supports REST API architecture
- Works with various authentication methods (JWT, OAuth, etc.)
- Can be adapted for different backend frameworks
- Focuses on backend API testing and validation
- Integrates with frontend interface definitions for comprehensive testing
- Generates standardized reports following provided format
- Automatically handles cross-platform HTTP requests
- Automatically manages authentication tokens
- Automatically validates responses and detects issues
