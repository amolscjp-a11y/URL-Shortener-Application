# Complete API Testing Guide

This guide demonstrates how to test all endpoints of the URL Shortener Application, with a focus on the **Top 3 Domains** metrics endpoint.

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Starting the Application](#starting-the-application)
3. [Testing Top 3 Domains Endpoint](#testing-top-3-domains-endpoint)
4. [Complete Endpoint Testing Sequence](#complete-endpoint-testing-sequence)
5. [Testing with Tools](#testing-with-tools)

---

## Prerequisites

- Java 8+ installed
- Maven installed (or Docker)
- curl or Postman (for testing)
- Application running on `http://localhost:8080`

---

## Starting the Application

### Option 1: Using Maven
```bash
mvn spring-boot:run
```

### Option 2: Using Java (pre-built JAR)
```bash
java -jar target/url-shortener-0.0.1-SNAPSHOT.jar
```

### Option 3: Using Docker
```bash
docker build -t url-shortener:latest .
docker run -p 8080:8080 url-shortener:latest
```

---

## Testing Top 3 Domains Endpoint

### What is the Top 3 Domains Endpoint?

The **Top 3 Domains** endpoint (`GET /api/metrics/top-domains`) returns the domain names that users have shortened the most frequently. This helps you understand which websites are most popular among your users.

### Example Scenario

If users shortened:
- 6 YouTube video links
- 4 StackOverflow code snippets
- 2 Wikipedia articles
- 1 GitHub repository link

The endpoint would return:

```json
{
  "youtube.com": 6,
  "stackoverflow.com": 4,
  "wikipedia.org": 2
}
```

### Testing Steps

#### Step 1: Create Multiple Shortened URLs (Different Domains)

```bash
# Shorten a YouTube URL
curl -X POST http://localhost:8080/api/url/shorten \
  -H "Content-Type: application/json" \
  -d '{"originalUrl":"https://www.youtube.com/watch?v=dQw4w9WgXcQ"}'

# Shorten another YouTube URL
curl -X POST http://localhost:8080/api/url/shorten \
  -H "Content-Type: application/json" \
  -d '{"originalUrl":"https://www.youtube.com/watch?v=jNxD3xZDEAQ"}'

# Shorten a StackOverflow URL
curl -X POST http://localhost:8080/api/url/shorten \
  -H "Content-Type: application/json" \
  -d '{"originalUrl":"https://stackoverflow.com/questions/20031029/java-streams-to-map"}'

# Shorten a Wikipedia URL
curl -X POST http://localhost:8080/api/url/shorten \
  -H "Content-Type: application/json" \
  -d '{"originalUrl":"https://en.wikipedia.org/wiki/Java_(programming_language)"}'
```

#### Step 2: Query the Top 3 Domains Endpoint

```bash
curl http://localhost:8080/api/metrics/top-domains
```

**Expected Output:**
```json
{
  "youtube.com": 2,
  "stackoverflow.com": 1,
  "wikipedia.org": 1
}
```

#### Step 3: Add More URLs and Check the Updated Results

```bash
# Add more YouTube URLs
curl -X POST http://localhost:8080/api/url/shorten \
  -H "Content-Type: application/json" \
  -d '{"originalUrl":"https://www.youtube.com/watch?v=kJmhKr-"}'

curl -X POST http://localhost:8080/api/url/shorten \
  -H "Content-Type: application/json" \
  -d '{"originalUrl":"https://www.youtube.com/watch?v=9bZkp7q19f0"}'

curl -X POST http://localhost:8080/api/url/shorten \
  -H "Content-Type: application/json" \
  -d '{"originalUrl":"https://www.youtube.com/watch?v=Gw-tDz8xpT4"}'

# Add more StackOverflow URLs
curl -X POST http://localhost:8080/api/url/shorten \
  -H "Content-Type: application/json" \
  -d '{"originalUrl":"https://stackoverflow.com/questions/4316551"}'

curl -X POST http://localhost:8080/api/url/shorten \
  -H "Content-Type: application/json" \
  -d '{"originalUrl":"https://stackoverflow.com/questions/3010840"}'

# Check the updated results
curl http://localhost:8080/api/metrics/top-domains
```

**Expected Output:**
```json
{
  "youtube.com": 5,
  "stackoverflow.com": 3,
  "wikipedia.org": 1
}
```

---

## Complete Endpoint Testing Sequence

### 1. Create Shortened URLs

```bash
# YouTube
curl -X POST http://localhost:8080/api/url/shorten \
  -H "Content-Type: application/json" \
  -d '{"originalUrl":"https://www.youtube.com/watch?v=dQw4w9WgXcQ"}'

# Response: {"shortUrl":"http://localhost:8080/api/url/abc123"}
```

Save the short code (e.g., `abc123`) for the next steps.

### 2. Get URL Details

```bash
curl http://localhost:8080/api/url/abc123/details
```

**Response:**
```json
{
  "shortCode": "abc123",
  "originalUrl": "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
  "createdAt": "2026-02-25T10:30:00",
  "clickCount": 0
}
```

### 3. Redirect (Click the Short URL)

```bash
# Follow the redirect
curl -L http://localhost:8080/api/url/abc123
```

### 4. Get Updated Details (Click Count)

```bash
curl http://localhost:8080/api/url/abc123/details
```

**Response** (note `clickCount` is now 1):
```json
{
  "shortCode": "abc123",
  "originalUrl": "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
  "createdAt": "2026-02-25T10:30:00",
  "clickCount": 1
}
```

### 5. List All URLs

```bash
curl http://localhost:8080/api/url
```

### 6. Get All Metrics

```bash
curl http://localhost:8080/api/metrics/total-urls
```

**Response:**
```json
{
  "totalUrls": 10,
  "totalClicks": 25,
  "averageClicksPerUrl": 2.5
}
```

### 7. Get Top 3 Domains

```bash
curl http://localhost:8080/api/metrics/top-domains
```

### 8. Update a URL

```bash
curl -X PUT http://localhost:8080/api/url/abc123 \
  -H "Content-Type: application/json" \
  -d '{"originalUrl":"https://www.youtube.com/watch?v=newid"}'
```

### 9. Delete a URL

```bash
curl -X DELETE http://localhost:8080/api/url/abc123
```

### 10. Verify Deletion

```bash
curl http://localhost:8080/api/url
```

---

## Testing with Tools

### Using Postman

#### Setup
1. Download and install [Postman](https://www.postman.com/downloads/)
2. Create a new collection called "URL Shortener"

#### Create Requests

**Request 1: Shorten URL**
- Method: `POST`
- URL: `http://localhost:8080/api/url/shorten`
- Body (JSON):
```json
{
  "originalUrl": "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
}
```

**Request 2: Get Top 3 Domains**
- Method: `GET`
- URL: `http://localhost:8080/api/metrics/top-domains`

**Request 3: Get All Metrics**
- Method: `GET`
- URL: `http://localhost:8080/api/metrics/total-urls`

**Request 4: Get URL Details**
- Method: `GET`
- URL: `http://localhost:8080/api/url/{{shortCode}}/details`
  (Replace `{{shortCode}}` with actual short code from Request 1)

**Request 5: List All URLs**
- Method: `GET`
- URL: `http://localhost:8080/api/url`

### Using PowerShell (Windows)

A test script `test-endpoints.ps1` is provided in the project root:

```powershell
.\test-endpoints.ps1
```

This script automatically:
1. Creates a shortened URL
2. Gets details
3. Tests redirects
4. Updates the URL
5. Tests updated redirect
6. Deletes the URL
7. Lists all remaining URLs

### Using HTTPie (Cross-platform)

```bash
# Install HTTPie
pip install httpie

# Create shortened URL
http POST http://localhost:8080/api/url/shorten originalUrl="https://www.youtube.com/watch?v=dQw4w9WgXcQ"

# Get top domains
http http://localhost:8080/api/metrics/top-domains

# Get metrics
http http://localhost:8080/api/metrics/total-urls
```

### Using JavaScript/Fetch API

```javascript
// Create shortened URL
fetch('http://localhost:8080/api/url/shorten', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ originalUrl: 'https://www.youtube.com/watch?v=dQw4w9WgXcQ' })
})
.then(r => r.json())
.then(data => console.log(data));

// Get top 3 domains
fetch('http://localhost:8080/api/metrics/top-domains')
  .then(r => r.json())
  .then(data => console.log('Top 3 Domains:', data));

// Get metrics
fetch('http://localhost:8080/api/metrics/total-urls')
  .then(r => r.json())
  .then(data => console.log('Metrics:', data));
```

---

## Summary

| Endpoint | Method | Purpose |
|----------|--------|---------|
| `/api/url/shorten` | POST | Create shortened URL |
| `/api/url/{code}` | GET | Redirect to original URL |
| `/api/url/{code}/details` | GET | Get URL details and click count |
| `/api/url/{code}` | PUT | Update URL |
| `/api/url/{code}` | DELETE | Delete shortened URL |
| `/api/url` | GET | List all URLs |
| **`/api/metrics/top-domains`** | **GET** | **Get top 3 domains by shortening count** ⭐ |
| `/api/metrics/total-urls` | GET | Get overall metrics |
| `/api/metrics/click-count/{code}` | GET | Get click count for specific URL |

---

## Troubleshooting

### Connection Refused
```bash
# Ensure the application is running
# Check if port 8080 is in use
lsof -i :8080  # On macOS/Linux
netstat -ano | findstr :8080  # On Windows
```

### URL Not Found
```bash
# Make sure the short code exists
# List all URLs to verify
curl http://localhost:8080/api/url
```

### CORS Issues
If you're testing from a browser frontend, CORS might be an issue. The application can be configured to allow specific origins.

---

## Next Steps

1. Deploy the application using Docker
2. Set up a frontend dashboard to visualize the top domains
3. Implement persistence with a database
4. Add user authentication
5. Create scheduled reports for domain statistics


