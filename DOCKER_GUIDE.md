# Docker Deployment Guide

This guide explains how to build and run the URL Shortener Application using Docker.

## Prerequisites

- Docker installed on your machine
- Docker version 20.10+ (or Docker Desktop)

## Building the Docker Image

### Option 1: Build from Source

```bash
# Navigate to the project directory
cd URL-Shortener-Application

# Build the Docker image
docker build -t url-shortener:latest .

# Or with a specific tag version
docker build -t url-shortener:1.0.0 .
```

### Option 2: Build with Custom Configuration

If you want to customize the base URL for production:

```bash
# Build and run with environment variables
docker build \
  --build-arg APP_BASE_URL=https://myshortener.com \
  -t url-shortener:prod .
```

## Running the Docker Container

### Basic Usage

```bash
# Run the container
docker run -p 8080:8080 url-shortener:latest

# The application will be available at: http://localhost:8080
```

### Advanced Usage

```bash
# Run with custom base URL
docker run -p 8080:8080 \
  -e APP_BASE_URL=https://myshortener.com \
  url-shortener:latest

# Run with custom port mapping
docker run -p 9090:8080 url-shortener:latest
# Access at: http://localhost:9090

# Run in detached mode
docker run -d \
  --name url-shortener \
  -p 8080:8080 \
  url-shortener:latest

# View logs
docker logs -f url-shortener

# Stop the container
docker stop url-shortener

# Remove the container
docker rm url-shortener
```

## Docker Compose (Optional)

Create a `docker-compose.yml` file for easier orchestration:

```yaml
version: '3.8'

services:
  url-shortener:
    build: .
    ports:
      - "8080:8080"
    environment:
      - APP_BASE_URL=http://localhost:8080
    container_name: url-shortener-app
```

Then run:

```bash
docker-compose up
docker-compose down
```

## Testing the Application in Docker

### 1. Create a shortened URL

```bash
curl -X POST http://localhost:8080/api/url/shorten \
  -H "Content-Type: application/json" \
  -d '{"originalUrl":"https://www.example.com/very/long/url"}'
```

### 2. Get top 3 domains

```bash
curl http://localhost:8080/api/metrics/top-domains
```

Expected output (example):
```json
{
  "youtube.com": 4,
  "example.com": 3,
  "stackoverflow.com": 2
}
```

### 3. Get total metrics

```bash
curl http://localhost:8080/api/metrics/total-urls
```

### 4. Get all shortened URLs

```bash
curl http://localhost:8080/api/url
```

### 5. Use PowerShell test script (on Windows)

If you're running Docker Desktop on Windows, you can use the PowerShell test script:

```powershell
.\test-endpoints.ps1
```

## Building and Pushing to Docker Registry

### Docker Hub

```bash
# Tag the image
docker tag url-shortener:latest yourusername/url-shortener:latest
docker tag url-shortener:latest yourusername/url-shortener:1.0.0

# Login to Docker Hub
docker login

# Push the image
docker push yourusername/url-shortener:latest
docker push yourusername/url-shortener:1.0.0

# Pull and run from Docker Hub
docker run -p 8080:8080 yourusername/url-shortener:latest
```

### AWS ECR (Elastic Container Registry)

```bash
# Get login token
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin <account-id>.dkr.ecr.us-east-1.amazonaws.com

# Tag the image
docker tag url-shortener:latest <account-id>.dkr.ecr.us-east-1.amazonaws.com/url-shortener:latest

# Push to ECR
docker push <account-id>.dkr.ecr.us-east-1.amazonaws.com/url-shortener:latest
```

### Google Container Registry (GCR)

```bash
# Authenticate with GCR
gcloud auth configure-docker

# Tag the image
docker tag url-shortener:latest gcr.io/my-project/url-shortener:latest

# Push to GCR
docker push gcr.io/my-project/url-shortener:latest
```

## Image Size Optimization

The current multi-stage Dockerfile is optimized for size:
- **Build stage**: Uses `maven:3.8.1-openjdk-8-slim` for compilation
- **Runtime stage**: Uses `eclipse-temurin:8-jre-alpine` (minimal JRE)

This approach reduces the final image size by ~70% compared to a single-stage build.

## Troubleshooting

### Container won't start
```bash
# Check the logs
docker logs <container-id>

# Verify the image was built correctly
docker images
```

### Port already in use
```bash
# Use a different port
docker run -p 9090:8080 url-shortener:latest

# Or find and stop the conflicting container
docker ps
docker stop <container-id>
```

### DNS/Network issues
```bash
# Run with explicit network settings
docker run --network bridge -p 8080:8080 url-shortener:latest
```

## Production Deployment

For production deployment:

1. Use a reverse proxy (nginx/traefik) for SSL/TLS
2. Set appropriate base URL: `-e APP_BASE_URL=https://yourdomain.com`
3. Use environment-specific configuration files
4. Implement health checks
5. Use container orchestration (Kubernetes, Docker Swarm)
6. Set resource limits (CPU, memory)

Example with resource limits:

```bash
docker run -p 8080:8080 \
  --memory=512m \
  --cpus="1.0" \
  --health-cmd='curl -f http://localhost:8080/api/metrics/total-urls || exit 1' \
  --health-interval=30s \
  url-shortener:latest
```

## Cleanup

```bash
# Remove unused images
docker image prune

# Remove all containers
docker container prune

# Remove all unused volumes
docker volume prune

# Full cleanup (use with caution)
docker system prune -a
```

## References

- [Docker Official Documentation](https://docs.docker.com/)
- [Docker Best Practices](https://docs.docker.com/develop/dev-best-practices/)
- [Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)

