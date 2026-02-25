# URL Shortener Application

A Spring Boot REST API application that provides URL shortening functionality with metrics tracking.

## 🎯 Key Features

✨ **Complete URL Shortening System**
- **URL Shortening**: Convert long URLs into short, easy-to-share codes
- **URL Redirection**: Redirect from short codes to original URLs
- **URL Management**: Update and delete shortened URLs
- **Metrics Tracking**: Track the most popular domains
- **Base62 Encoding**: Efficient URL encoding using Base62 algorithm
- **Error Handling**: Global exception handling with meaningful error responses
- **Input Validation**: Request validation for data integrity

🔝 **Top 3 Domains Metrics (NEW!)**
- **Endpoint**: `GET /api/metrics/top-domains`
- Returns the top 3 domain names that have been shortened the most
- Perfect for analyzing user behavior and website popularity
- **Example**: If users shortened 6 YouTube, 4 StackOverflow, 2 Wikipedia URLs → Returns:
  ```json
  {
    "youtube.com": 6,
    "stackoverflow.com": 4,
    "wikipedia.org": 2
  }
  ```

🐳 **Docker Support**
- Production-ready multi-stage Dockerfile
- Docker Compose configuration included
- Optimized image size (~100MB)
- Automated CI/CD with GitHub Actions

📚 **Complete Documentation**
- API Testing Guide with examples
- Docker Deployment Guide
- GitHub & Docker Hub setup instructions
- Quick start scripts for Windows/Linux/Mac

## 🚀 Quick Start

### Using Maven
```bash
mvn spring-boot:run
```

### Using Docker
```bash
docker build -t url-shortener:latest .
docker run -p 8080:8080 url-shortener:latest
```

### Using Docker Compose
```bash
docker-compose up
```

### Using Quick Start Scripts
- **Windows**: `.\quickstart.bat`
- **Linux/Mac**: `./quickstart.sh`

Then test the **Top 3 Domains endpoint**:
```bash
curl http://localhost:8080/api/metrics/top-domains
```

---

## 📖 Documentation

| Document | Purpose |
|----------|---------|
| [API_TESTING_GUIDE.md](./API_TESTING_GUIDE.md) | Complete guide to test all endpoints including the top 3 domains metrics |
| [DOCKER_GUIDE.md](./DOCKER_GUIDE.md) | Docker deployment, image building, and production setup |
| [GITHUB_DEPLOYMENT_GUIDE.md](./GITHUB_DEPLOYMENT_GUIDE.md) | GitHub repository and Docker Hub setup with CI/CD |

---

## Project Structure

```
url-shortener-java/
├── src/
│   └── main/
│       ├── java/com/example/urlshortener/
│       │   ├── UrlShortenerApplication.java      # Spring Boot entry point
│       │   ├── controller/
│       │   │   ├── UrlController.java            # URL shortening endpoints
│       │   │   └── MetricsController.java        # Metrics endpoints
│       │   ├── service/
│       │   │   └── UrlService.java               # Business logic
│       │   ├── model/
│       │   │   ├── UrlRequest.java               # Request DTO
│       │   │   ├── UrlResponse.java              # Response DTO
│       │   │   └── ErrorResponse.java            # Error response model
│       │   ├── exception/
│       │   │   ├── GlobalExceptionHandler.java   # Exception handling
│       │   │   └── UrlNotFoundException.java     # Custom exception
│       │   └── util/
│       │       └── Base62Encoder.java            # URL encoding utility
│       └── resources/
│           └── application.yml                   # Application configuration
├── pom.xml                                       # Maven dependencies
├── .gitignore                                    # Git ignore file
└── README.md                                     # This file
```

## Technologies Used

- **Java 11+**
- **Spring Boot 2.x**
- **Spring MVC**
- **Maven**
- **Spring Validation**

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- Git

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/url-shortener-java.git
cd url-shortener-java
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080` by default.

## Configuration

Edit `src/main/resources/application.yml` to configure:

```yaml
app:
  base-url: http://localhost:8080

server:
  port: 8080
```

## API Endpoints

### URL Management

#### 1. Create Shortened URL
- **Endpoint**: `POST /api/url/shorten`
- **Request Body**:
```json
{
  "originalUrl": "https://www.example.com/very/long/url"
}
```
- **Response** (201 Created):
```json
{
  "shortUrl": "http://localhost:8080/api/url/abc123"
}
```

#### 2. Redirect to Original URL
- **Endpoint**: `GET /api/url/{shortCode}`
- **Response**: HTTP 302 Redirect to original URL
- **Example**: `GET /api/url/abc123` → Redirects to original URL

#### 3. Get URL Details
- **Endpoint**: `GET /api/url/{shortCode}/details`
- **Response** (200 OK):
```json
{
  "shortCode": "abc123",
  "originalUrl": "https://www.example.com/very/long/url",
  "createdAt": "2026-02-25T10:30:00",
  "clickCount": 5
}
```

#### 4. Update URL
- **Endpoint**: `PUT /api/url/{shortCode}`
- **Request Body**:
```json
{
  "originalUrl": "https://www.new-example.com/url"
}
```
- **Response** (200 OK):
```json
{
  "shortUrl": "http://localhost:8080/api/url/abc123"
}
```

#### 5. Delete URL
- **Endpoint**: `DELETE /api/url/{shortCode}`
- **Response**: 204 No Content

#### 6. Get All URLs
- **Endpoint**: `GET /api/url`
- **Response** (200 OK):
```json
[
  {
    "shortCode": "abc123",
    "originalUrl": "https://www.example.com/url1",
    "createdAt": "2026-02-25T10:30:00",
    "clickCount": 5
  },
  {
    "shortCode": "def456",
    "originalUrl": "https://www.example.com/url2",
    "createdAt": "2026-02-25T11:00:00",
    "clickCount": 2
  }
]
```

### Metrics

#### 1. Get Top Domains
- **Endpoint**: `GET /api/metrics/top-domains`
- **Response** (200 OK):
```json
{
  "example.com": 45,
  "github.com": 32,
  "stackoverflow.com": 28
}
```

#### 2. Get Total URLs
- **Endpoint**: `GET /api/metrics/total-urls`
- **Response** (200 OK):
```json
{
  "totalUrls": 150,
  "totalClicks": 1250,
  "averageClicksPerUrl": 8.33
}
```

#### 3. Get Click Count for Specific URL
- **Endpoint**: `GET /api/metrics/click-count/{shortCode}`
- **Response** (200 OK):
```json
{
  "shortCode": "abc123",
  "clickCount": 25,
  "lastClicked": "2026-02-25T14:30:00"
}
```

## Error Handling

The API returns appropriate HTTP status codes and error messages:

- **400 Bad Request**: Invalid input or validation error
- **404 Not Found**: URL not found
- **500 Internal Server Error**: Server error

### Error Response Format
```json
{
  "timestamp": "2026-02-25T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "URL not found with short code: abc123"
}
```

## Usage Examples

### Using cURL

#### Create a shortened URL
```bash
curl -X POST http://localhost:8080/api/url/shorten \
  -H "Content-Type: application/json" \
  -d '{"originalUrl":"https://www.example.com/very/long/url"}'
```

#### Get top domains
```bash
curl http://localhost:8080/api/metrics/top-domains
```

#### Redirect to original URL
```bash
curl -L http://localhost:8080/api/url/abc123
```

### Using Postman

1. Import the API endpoints into Postman
2. Set the base URL to `http://localhost:8080`
3. Create requests for each endpoint
4. Test and save your collection

## Development

### Running Tests

```bash
mvn test
```

### Building WAR File

```bash
mvn clean package
```

The WAR file will be generated in `target/url-shortener.war`

### IDE Setup

For IntelliJ IDEA:
1. Open the project
2. Maven will automatically sync dependencies
3. Run `UrlShortenerApplication.java` as Spring Boot Application

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Git Workflow

```bash
# Check status
git status

# Stage changes
git add .

# Commit
git commit -m "Descriptive commit message"

# Push to remote
git push origin main
```

## Troubleshooting

### Application won't start
- Check if port 8080 is available
- Ensure Java 11+ is installed: `java -version`
- Clear Maven cache: `mvn clean`

### URL not found error
- Verify the short code is correct
- Check if the URL has been deleted

### CORS Issues
- Add `@CrossOrigin` annotation to controllers if needed
- Configure CORS in application.yml if required

## Future Enhancements

- [ ] Database persistence (JPA/Hibernate)
- [ ] User authentication and authorization
- [ ] Custom short codes
- [ ] URL expiration
- [ ] Analytics dashboard
- [ ] QR code generation
- [ ] Bulk URL shortening
- [ ] API rate limiting

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For issues and questions:
1. Check existing GitHub issues
2. Create a new GitHub issue with detailed description
3. Contact the maintainers

## Changelog

### Version 1.0.0 (2026-02-25)
- Initial release
- Basic URL shortening functionality
- Metrics tracking
- REST API endpoints

## Quick Local Run (if Maven is not installed locally)

If you don't have Maven installed on your machine, you have a few options:

- Build the JAR on a machine that has Maven (or in CI), then copy the artifact here and run it with Java:

  1. On a machine with Maven installed, run:
     mvn clean package
  2. Copy the generated JAR from `target/` (for example `url-shortener-0.0.1-SNAPSHOT.jar`) into this project directory.
  3. Run the application with Java:
     java -jar target\url-shortener-0.0.1-SNAPSHOT.jar

- Or open the project in IntelliJ IDEA or another IDE and run `UrlShortenerApplication` as a Spring Boot application.

- Test script: A PowerShell helper `test-endpoints.ps1` is included in the project root to exercise the endpoints once the app is running.

## Docker Deployment

### Build the Docker Image

```bash
# Navigate to the project directory
cd URL-Shortener-Application

# Build the Docker image
docker build -t url-shortener:latest .
```

### Run the Application in Docker

```bash
# Run the container
docker run -p 8080:8080 url-shortener:latest

# The application will be available at: http://localhost:8080
```

### Run with Custom Configuration

```bash
# Run with custom base URL for production
docker run -p 8080:8080 \
  -e APP_BASE_URL=https://myshortener.com \
  url-shortener:latest

# Run in detached mode
docker run -d --name url-shortener -p 8080:8080 url-shortener:latest
```

### Test the Docker Container

```bash
# Test the top 3 domains endpoint
curl http://localhost:8080/api/metrics/top-domains

# Create a shortened URL
curl -X POST http://localhost:8080/api/url/shorten \
  -H "Content-Type: application/json" \
  -d '{"originalUrl":"https://www.youtube.com/watch?v=example"}'
```

### Push to Docker Registry

```bash
# Tag and push to Docker Hub
docker tag url-shortener:latest yourusername/url-shortener:latest
docker push yourusername/url-shortener:latest

# Run from Docker Hub
docker run -p 8080:8080 yourusername/url-shortener:latest
```

For comprehensive Docker documentation including Docker Compose setup, AWS ECR, Google GCR, and troubleshooting, see [DOCKER_GUIDE.md](./DOCKER_GUIDE.md).

### Docker Image Details

- **Base Build Image**: `maven:3.8.1-openjdk-8-slim` (for compilation)
- **Runtime Image**: `eclipse-temurin:8-jre-alpine` (minimal JRE, ~100MB)
- **Multi-stage Build**: Optimized for size (~70% reduction compared to single-stage)
- **Exposed Port**: 8080

