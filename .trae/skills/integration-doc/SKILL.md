---
name: "integration-doc"
description: "Generates frontend-backend integration documentation by analyzing Vue frontend and Spring Boot backend codebases. Invoke when user asks to create API mapping, data model alignment, or integration docs between frontend and backend."
---

# Frontend-Backend Integration Documentation Generator

This skill generates comprehensive integration documentation by analyzing Vue 3 frontend projects and Spring Boot backend projects.

## When to Use

Invoke this skill when:
- User asks to generate frontend-backend integration documentation
- User needs API mapping between Vue components and Spring Boot controllers
- User requires data model alignment documentation
- User wants to understand how frontend connects to backend APIs
- User needs documentation for API endpoints, request/response formats, and database mappings

## Capabilities

This skill analyzes:

### Frontend Analysis (Vue 3)
- Vue components and their data requirements
- Vue Router routes and page structure
- API calls and data fetching patterns
- State management (Pinia/Vuex)
- Form inputs and data submission

### Backend Analysis (Spring Boot)
- REST API controllers and endpoints
- Request/Response DTOs
- Service layer and business logic
- Database entities and table structures
- MyBatis mappers and SQL queries

### Documentation Output

Generates documentation including:

1. **API Interface Mapping**
   - Frontend page → Backend API mapping
   - Request parameters and response formats
   - HTTP methods and endpoints
   - Authentication requirements

2. **Database Table Mapping**
   - Frontend data fields → Database columns
   - Data type conversions
   - Field usage status (used/unused)
   - Relationship mappings

3. **Missing API Detection**
   - Frontend features without backend support
   - Backend APIs not used by frontend
   - Recommended new endpoints

4. **Configuration Guide**
   - CORS configuration
   - Proxy settings
   - Environment variables
   - Authentication flow

5. **Development Priorities**
   - Phase 1: Core features
   - Phase 2: User features
   - Phase 3: Enhanced features

## Usage Workflow

1. **Analyze Frontend**
   - Scan Vue components in `src/views/` and `src/components/`
   - Identify API calls and data requirements
   - Extract routing structure

2. **Analyze Backend**
   - Scan Spring Boot controllers
   - Analyze entity classes and database tables
   - Review DTOs and service layer

3. **Generate Documentation**
   - Create comprehensive mapping tables
   - Identify gaps and missing features
   - Provide code examples and configuration

4. **Output Format**
   - Markdown documentation
   - Save to `docs/` directory
   - Include code snippets and examples

## Output Structure

The generated documentation follows this structure:

```markdown
# Frontend-Backend Integration Documentation

## 1. API Interface Mapping
## 2. Database Table Mapping
## 3. Missing Backend APIs
## 4. Frontend Implementation Guide
## 5. Configuration Instructions
## 6. Development Priorities
## 7. Notes and Best Practices
```

## Key Features

- **Automatic Detection**: Identifies API calls in frontend code
- **Data Mapping**: Maps frontend data structures to database schemas
- **Gap Analysis**: Highlights missing functionality
- **Code Examples**: Provides implementation samples
- **Priority Planning**: Suggests development phases

## Best Practices

1. Always analyze both frontend and backend codebases completely
2. Focus on existing frontend requirements first
3. Clearly mark missing backend APIs
4. Provide actionable recommendations
5. Include code examples for common patterns

## Example Output

For a Vue 3 + Spring Boot project, generates documentation similar to:

| Frontend Page | Backend API | Method | Status |
|---------------|-------------|--------|--------|
| Home.vue | GET /books/categories | GET | ✅ Available |
| Books.vue | GET /books | GET | ✅ Available |
| Publish.vue | POST /books | POST | ⚠️ Missing |

## Notes

- This skill focuses on Vue 3 and Spring Boot ecosystems
- Can be adapted for other frameworks (React, Express, etc.)
- Assumes standard REST API patterns
- Supports JWT authentication flows
