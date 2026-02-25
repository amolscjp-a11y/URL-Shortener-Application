# URL Shortener Application

A Spring Boot REST API application that provides URL shortening functionality with metrics tracking.

## Features

- **URL Shortening**: Convert long URLs into short, easy-to-share codes
- **URL Redirection**: Redirect from short codes to original URLs
- **Metrics Tracking**: Track the most popular domains
- **Base62 Encoding**: Efficient URL encoding using Base62 algorithm
- **Error Handling**: Global exception handling with meaningful error responses
- **Input Validation**: Request validation for data integrity

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

