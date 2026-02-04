---
name: "integration-testing"
description: "Performs frontend-backend integration testing including API validation, data format verification, and authentication flow testing. Invoke when user needs to test API connectivity, verify frontend-backend integration, or validate data formats."
---

# Frontend-Backend Integration Testing

This skill performs comprehensive testing for Vue 3 frontend and Spring Boot backend integration.

## When to Use

Invoke this skill when:
- User wants to test if backend APIs are working correctly
- User needs to verify frontend-backend integration
- User wants to validate data formats between frontend and backend
- User needs to test authentication and authorization flows
- User wants to check API responses match frontend expectations
- User is preparing for frontend-backend joint debugging
- User says "测试接口" (test interface) - this is a trigger phrase for API testing

## Testing Capabilities

### 1. API Interface Testing

Tests backend API endpoints to ensure they are functioning correctly:

- **Endpoint Availability**: Check if all API endpoints are accessible
- **HTTP Methods**: Verify GET, POST, PUT, DELETE methods work as expected
- **Response Format**: Validate response structure matches expected format
- **Status Codes**: Check for proper HTTP status codes (200, 201, 400, 401, 404, 500)
- **Error Handling**: Verify error messages are properly formatted

### 2. Frontend Integration Testing

Tests if frontend components can successfully communicate with backend:

- **API Calls**: Verify frontend API calls reach backend correctly
- **Data Binding**: Check if frontend receives and displays data properly
- **State Management**: Test if Pinia/Vuex stores update correctly with API data
- **Error Handling**: Verify frontend handles API errors gracefully
- **Loading States**: Check loading indicators display during API requests

### 3. Authentication Flow Testing

Tests user authentication and authorization:

- **Login Flow**: Test login API and token storage
- **Token Management**: Verify JWT tokens are stored and sent correctly
- **Protected Routes**: Check if protected pages require authentication
- **Token Refresh**: Test token refresh mechanism (if implemented)
- **Logout Flow**: Verify logout clears tokens and redirects correctly

### 4. Data Format Validation

Validates data consistency between frontend and backend:

- **Field Mapping**: Check if frontend fields match backend response fields
- **Data Types**: Verify data types (string, number, boolean, date) are correct
- **Date Formats**: Check date/time formats are consistent
- **Number Formats**: Verify price, quantity, and other numeric formats
- **Enum Values**: Validate enum values (status, condition, category) match

## Testing Workflow

### Phase 1: Backend API Testing

1. **Verify Backend is Running** (Assumed to be running by default)
   - Confirm backend service is available at `http://localhost:8080/api`
   - No need to start backend server manually

2. **Test API Endpoints**
   - Test authentication endpoints (`/auth/login`, `/auth/register`)
   - Test book endpoints (`/books`, `/books/{id}`, `/books/categories`)
   - Test user endpoints (if available)
   - Test order endpoints (`/users/orders`)
   - Test review endpoints (`/books/{id}/reviews`)

3. **Validate Responses**
   - Check response structure: `{ code, message, data }`
   - Verify data types match expectations
   - Check for required fields

### Phase 2: Frontend Integration Testing

1. **Start Frontend Server**
   ```bash
   npm run dev
   ```

2. **Configure API Base URL**
   - Set `baseURL` to `http://localhost:8080/api`
   - Configure proxy in `vite.config.js` if needed

3. **Test Each Page**
   - **Home Page**: Load categories and hot books
   - **Books Page**: Search, filter, and sort functionality
   - **Book Detail Page**: Load book details, seller info, reviews
   - **Publish Page**: Form submission (if backend API exists)
   - **Profile Page**: User data, orders, favorites (if backend API exists)

4. **Browser Console Check**
   - Check for network errors
   - Verify API requests are sent
   - Check response data is received

### Phase 3: Authentication Testing

1. **Login Flow**
   - Test valid credentials
   - Test invalid credentials
   - Verify token is stored in localStorage
   - Check if token is sent in subsequent requests

2. **Protected Routes**
   - Access protected pages without login (should redirect)
   - Access protected pages with valid token (should load)
   - Test token expiration handling

3. **Logout Flow**
   - Verify token is cleared
   - Check redirect to home/login page

### Phase 4: Data Format Validation

1. **Compare Data Structures**
   - Frontend expected fields vs Backend response fields
   - Data type consistency
   - Field naming conventions (camelCase vs snake_case)

2. **Test Edge Cases**
   - Empty data responses
   - Null/undefined values
   - Large datasets (pagination)
   - Special characters in strings

## Test Report Format

Generate a comprehensive test report with:

```markdown
# Frontend-Backend Integration Test Report

## Test Environment
- Backend URL: http://localhost:8080/api
- Frontend URL: http://localhost:5173
- Test Date: YYYY-MM-DD HH:mm:ss

## API Endpoint Tests

| Endpoint | Method | Status | Response Time | Notes |
|----------|--------|--------|---------------|-------|
| /auth/login | POST | ✅ PASS | 120ms | - |
| /books | GET | ✅ PASS | 85ms | - |
| /books/{id} | GET | ❌ FAIL | - | 404 Not Found |

## Frontend Integration Tests

| Page | API Calls | Data Display | Status | Notes |
|------|-----------|--------------|--------|-------|
| Home | /books/categories, /books/hot | ✅ PASS | ✅ PASS | - |
| Books | /books | ✅ PASS | ❌ FAIL | Images not loading |

## Authentication Tests

| Test Case | Expected | Actual | Status |
|-----------|----------|---------|--------|
| Login with valid credentials | Success, token stored | Success, token stored | ✅ PASS |
| Login with invalid credentials | Error message | Error message | ✅ PASS |

## Data Format Validation

| Field | Frontend Type | Backend Type | Match? | Notes |
|-------|---------------|--------------|---------|-------|
| price | number | decimal | ✅ YES | - |
| createdAt | string | datetime | ⚠️ NO | Needs format conversion |

## Issues Found

1. [High] /books/{id} returns 404 for valid IDs
2. [Medium] Date format mismatch between frontend and backend
3. [Low] Images not loading due to CORS issue

## Recommendations

1. Fix book detail endpoint routing
2. Implement date format utility function
3. Configure CORS properly for image loading
```

## Common Issues and Solutions

### Issue 1: CORS Errors

**Symptom**: Browser shows CORS policy error

**Solution**:
- Check `CorsConfig.java` in backend
- Ensure frontend URL is in allowed origins
- Verify `@CrossOrigin` annotations on controllers

### Issue 2: 401 Unauthorized

**Symptom**: API returns 401 status

**Solution**:
- Check if token is stored in localStorage
- Verify token is sent in Authorization header
- Check if token has expired

### Issue 3: Data Not Displaying

**Symptom**: API call succeeds but data doesn't show

**Solution**:
- Check response data structure
- Verify field names match (camelCase vs snake_case)
- Check if data is nested in `data` property

### Issue 4: Form Submission Fails

**Symptom**: Form submit returns error

**Solution**:
- Check request format (JSON vs FormData)
- Verify required fields are included
- Check Content-Type header

## Testing Tools

### Backend Testing
- **Postman**: Manual API testing
- **curl**: Command-line API testing
- **Spring Boot Actuator**: Health checks

### Frontend Testing
- **Browser DevTools**: Network tab for API calls
- **Vue DevTools**: Component state inspection
- **Console**: Error and warning messages

### Automated Testing
- **Jest**: Frontend unit testing
- **Vitest**: Vue component testing
- **Supertest**: API endpoint testing

## Best Practices

1. **Test Early and Often**: Start testing as soon as APIs are available
2. **Test Real Scenarios**: Test with actual data, not just mock data
3. **Document Everything**: Keep detailed test reports
4. **Automate Repetitive Tests**: Create scripts for common test cases
5. **Test Edge Cases**: Don't just test happy paths
6. **Monitor Performance**: Track API response times
7. **Security Testing**: Test authentication and authorization thoroughly

## Quick Start Commands

```bash
# Start frontend
npm run dev

# Test API with curl (assuming backend is running)
curl http://localhost:8080/api/books/categories

# Test API with authentication (assuming backend is running)
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"account":"wstest","password":"123456"}'
```

## Notes

- This skill assumes Vue 3 frontend and Spring Boot backend
- Supports REST API architecture
- Works with JWT authentication
- Can be adapted for other frameworks
- IMPORTANT: Skip checking if backend is running - assume it's available by default (typically on http://localhost:8080)
- BUT still test actual backend API endpoints to verify functionality
- Focus on frontend implementation and API integration testing
