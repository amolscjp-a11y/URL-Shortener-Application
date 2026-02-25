# GitHub & Docker Hub Deployment Guide

Complete guide for hosting the URL Shortener Application on GitHub with Docker image on Docker Hub or other registries.

## Table of Contents
1. [GitHub Setup](#github-setup)
2. [Docker Hub Setup](#docker-hub-setup)
3. [GitHub Actions CI/CD](#github-actions-cicd)
4. [Automated Docker Image Builds](#automated-docker-image-builds)
5. [Pulling and Running the Docker Image](#pulling-and-running-the-docker-image)

---

## GitHub Setup

### Step 1: Create a GitHub Repository

1. Go to [GitHub](https://github.com) and log in
2. Click the "+" icon and select "New repository"
3. Fill in the details:
   - **Repository name**: `URL-Shortener-Application` (or your preferred name)
   - **Description**: `A Spring Boot REST API for URL shortening with metrics tracking`
   - **Visibility**: Public (to share the Docker image easily)
   - **Initialize with**: Add README (optional, we have one)
   - Click **Create repository**

### Step 2: Push Your Local Code to GitHub

```bash
# Navigate to your project directory
cd URL-Shortener-Application

# Initialize git (if not already done)
git init

# Add all files
git add .

# Initial commit
git commit -m "Initial commit: URL Shortener Application with metrics API and Docker support"

# Add remote repository
git remote add origin https://github.com/YOUR_USERNAME/URL-Shortener-Application.git

# Rename branch to main (if using master)
git branch -M main

# Push to GitHub
git push -u origin main
```

### Step 3: GitHub Links to Share

After pushing, share these links:

- **Source Code**: `https://github.com/YOUR_USERNAME/URL-Shortener-Application`
- **Clone URL**: `https://github.com/YOUR_USERNAME/URL-Shortener-Application.git`
- **SSH Clone**: `git@github.com:YOUR_USERNAME/URL-Shortener-Application.git`

Example:
```bash
# Others can clone your repository
git clone https://github.com/YOUR_USERNAME/URL-Shortener-Application.git
```

---

## Docker Hub Setup

### Step 1: Create Docker Hub Account

1. Go to [Docker Hub](https://hub.docker.com)
2. Click "Sign Up"
3. Fill in details and verify email
4. Complete registration

### Step 2: Create a Repository on Docker Hub

1. Log in to Docker Hub
2. Click "Create Repository"
3. Fill in details:
   - **Name**: `url-shortener`
   - **Description**: `Spring Boot URL Shortener with metrics tracking for top 3 domains`
   - **Visibility**: Public
   - Click **Create**

### Step 3: Build and Push Docker Image

```bash
# Log in to Docker Hub
docker login

# Navigate to project directory
cd URL-Shortener-Application

# Build the Docker image
docker build -t YOUR_USERNAME/url-shortener:latest .
docker build -t YOUR_USERNAME/url-shortener:1.0.0 .

# Push to Docker Hub
docker push YOUR_USERNAME/url-shortener:latest
docker push YOUR_USERNAME/url-shortener:1.0.0
```

### Step 4: Docker Hub Links to Share

After pushing:

- **Docker Hub Repository**: `https://hub.docker.com/r/YOUR_USERNAME/url-shortener`
- **Pull Command**: 
```bash
docker pull YOUR_USERNAME/url-shortener:latest
```

---

## GitHub Actions CI/CD

### Automated Testing and Building

Create `.github/workflows/build.yml`:

```yaml
name: Build and Test

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 8
      uses: actions/setup-java@v3
      with:
        java-version: '8'
        distribution: 'temurin'
    
    - name: Build with Maven
      run: mvn clean package
    
    - name: Run Tests
      run: mvn test
    
    - name: Upload Build Artifact
      uses: actions/upload-artifact@v3
      with:
        name: url-shortener-jar
        path: target/*.jar
```

### Automated Docker Image Build and Push

Create `.github/workflows/docker-build.yml`:

```yaml
name: Build and Push Docker Image

on:
  push:
    branches: [ main ]
    tags: [ 'v*' ]
  pull_request:
    branches: [ main ]

jobs:
  docker:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up Docker Buildx
      uses: docker/setup-buildx-action@v2
    
    - name: Login to Docker Hub
      if: github.event_name != 'pull_request'
      uses: docker/login-action@v2
      with:
        username: ${{ secrets.DOCKER_HUB_USERNAME }}
        password: ${{ secrets.DOCKER_HUB_TOKEN }}
    
    - name: Extract metadata
      id: meta
      uses: docker/metadata-action@v4
      with:
        images: |
          ${{ secrets.DOCKER_HUB_USERNAME }}/url-shortener
        tags: |
          type=ref,event=branch
          type=semver,pattern={{version}}
          type=semver,pattern={{major}}.{{minor}}
          type=sha
    
    - name: Build and push
      uses: docker/build-push-action@v4
      with:
        context: .
        push: ${{ github.event_name != 'pull_request' }}
        tags: ${{ steps.meta.outputs.tags }}
        labels: ${{ steps.meta.outputs.labels }}
```

### Setting up GitHub Secrets

1. Go to your GitHub repository
2. Click **Settings** → **Secrets and variables** → **Actions**
3. Click **New repository secret**
4. Add:
   - **DOCKER_HUB_USERNAME**: Your Docker Hub username
   - **DOCKER_HUB_TOKEN**: Your Docker Hub access token (create at hub.docker.com → Account Settings → Security)

### Generate Docker Hub Token

1. Log in to Docker Hub
2. Click your profile icon → **Account Settings**
3. Go to **Security**
4. Click **New Access Token**
5. Name it `GitHub Actions`
6. Copy the token and add it to GitHub Secrets

---

## Automated Docker Image Builds

### Using Docker Hub Automated Builds

1. Go to Docker Hub Repository Settings
2. Click **Builds**
3. Connect your GitHub account
4. Select the repository
5. Configure build rules:
   - **Source Type**: Branch
   - **Source**: main
   - **Docker Tag**: latest, 1.0.0
6. Save and the build will trigger automatically on push

---

## Pulling and Running the Docker Image

### For Users/Testers

```bash
# Pull the image
docker pull YOUR_USERNAME/url-shortener:latest

# Run the container
docker run -p 8080:8080 YOUR_USERNAME/url-shortener:latest

# Run with custom configuration
docker run -p 8080:8080 \
  -e APP_BASE_URL=https://myshortener.com \
  YOUR_USERNAME/url-shortener:latest

# Run with Docker Compose
docker-compose up
```

### Using from docker-compose.yml

```yaml
version: '3.8'
services:
  url-shortener:
    image: YOUR_USERNAME/url-shortener:latest
    ports:
      - "8080:8080"
    environment:
      - APP_BASE_URL=http://localhost:8080
```

---

## Complete Deployment URLs

### GitHub
- **Repository**: https://github.com/YOUR_USERNAME/URL-Shortener-Application
- **Clone**: `git clone https://github.com/YOUR_USERNAME/URL-Shortener-Application.git`
- **Issues**: https://github.com/YOUR_USERNAME/URL-Shortener-Application/issues
- **Actions/CI-CD**: https://github.com/YOUR_USERNAME/URL-Shortener-Application/actions

### Docker
- **Docker Hub**: https://hub.docker.com/r/YOUR_USERNAME/url-shortener
- **Docker Pull**: `docker pull YOUR_USERNAME/url-shortener:latest`
- **Dockerfile**: https://github.com/YOUR_USERNAME/URL-Shortener-Application/blob/main/Dockerfile

### Documentation
- **README**: https://github.com/YOUR_USERNAME/URL-Shortener-Application/blob/main/README.md
- **Docker Guide**: https://github.com/YOUR_USERNAME/URL-Shortener-Application/blob/main/DOCKER_GUIDE.md
- **API Testing**: https://github.com/YOUR_USERNAME/URL-Shortener-Application/blob/main/API_TESTING_GUIDE.md
- **Deployment**: https://github.com/YOUR_USERNAME/URL-Shortener-Application/blob/main/GITHUB_DEPLOYMENT_GUIDE.md

---

## Example README Badges

Add these to your GitHub README:

```markdown
# URL Shortener Application

[![Build and Test](https://github.com/YOUR_USERNAME/URL-Shortener-Application/workflows/Build%20and%20Test/badge.svg)](https://github.com/YOUR_USERNAME/URL-Shortener-Application/actions)
[![Docker Image Size](https://img.shields.io/docker/image-size/YOUR_USERNAME/url-shortener/latest)](https://hub.docker.com/r/YOUR_USERNAME/url-shortener)
[![Docker Pulls](https://img.shields.io/docker/pulls/YOUR_USERNAME/url-shortener)](https://hub.docker.com/r/YOUR_USERNAME/url-shortener)

```

---

## Troubleshooting

### Docker Login Issues
```bash
# If you get "denied: requested access to the resource is denied"
# Your token may be expired or incorrect
docker login -u YOUR_USERNAME
# Enter password/token when prompted
```

### Build Failure on GitHub Actions
```bash
# Check the workflow logs
# Go to Actions tab → Click the failed workflow → See detailed logs
# Common issues:
# 1. Java version not matching
# 2. Maven cache issues
# 3. Incorrect secrets configuration
```

### Docker Image Push Fails
```bash
# Ensure you're logged in
docker login

# Ensure correct naming format
docker tag url-shortener:latest YOUR_USERNAME/url-shortener:latest

# Then push
docker push YOUR_USERNAME/url-shortener:latest
```

---

## Next Steps

1. ✅ Push code to GitHub
2. ✅ Create Docker Hub account
3. ✅ Build and push Docker image
4. ✅ Set up GitHub Actions CI/CD
5. ✅ Share the GitHub and Docker Hub links
6. Share links in your project documentation
7. Create releases on GitHub
8. Set up branch protection rules
9. Configure code quality tools (optional)
10. Monitor build status and Docker image downloads

---

## Example Final Links to Share

**GitHub Repository:**
```
https://github.com/YOUR_USERNAME/URL-Shortener-Application
```

**Docker Hub Image:**
```
https://hub.docker.com/r/YOUR_USERNAME/url-shortener
```

**Quick Start:**
```bash
git clone https://github.com/YOUR_USERNAME/URL-Shortener-Application.git
docker pull YOUR_USERNAME/url-shortener:latest
docker run -p 8080:8080 YOUR_USERNAME/url-shortener:latest
```

**Test the Top 3 Domains Endpoint:**
```bash
curl http://localhost:8080/api/metrics/top-domains
```


