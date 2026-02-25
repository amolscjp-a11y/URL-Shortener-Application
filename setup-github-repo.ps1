# GitHub Private Repository Setup Script (PowerShell)
# This script automates the process of pushing code to a private GitHub repository

Write-Host ""
Write-Host "================================================" -ForegroundColor Cyan
Write-Host "   GitHub Private Repository Setup" -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Cyan
Write-Host ""

# Check if git is installed
try {
    $null = git --version
} catch {
    Write-Host "Error: Git is not installed. Please install git first." -ForegroundColor Red
    exit 1
}

# Get repository information
$githubUsername = Read-Host "Enter your GitHub username"
$repoName = Read-Host "Enter your repository name (default: URL-Shortener-Application)"
if ([string]::IsNullOrWhiteSpace($repoName)) {
    $repoName = "URL-Shortener-Application"
}

# Construct repository URL
$repoUrl = "https://github.com/$githubUsername/$repoName.git"

Write-Host ""
Write-Host "Repository URL: $repoUrl" -ForegroundColor Green
Write-Host ""

# Initialize git if needed
if (-not (Test-Path ".git")) {
    Write-Host "Initializing git repository..."
    git init
}

# Check if remote exists
$remoteExists = git remote | Select-String -Pattern "origin" -Quiet
if ($remoteExists) {
    Write-Host "Updating existing remote..."
    git remote set-url origin $repoUrl
} else {
    Write-Host "Adding GitHub remote..."
    git remote add origin $repoUrl
}

# Check branch name
$currentBranch = git rev-parse --abbrev-ref HEAD
if ($currentBranch -ne "main") {
    Write-Host "Renaming branch to 'main'..."
    git branch -M main
}

# Stage all files
Write-Host "Staging files..."
git add .

# Check if there are changes to commit
$status = git status --porcelain
if ($status.Count -eq 0) {
    Write-Host "No changes to commit. Repository is up to date." -ForegroundColor Yellow
} else {
    # Create commit
    Write-Host "Creating commit..."
    $commitMsg = Read-Host "Enter commit message (default: 'Initial commit: URL Shortener Application')"
    if ([string]::IsNullOrWhiteSpace($commitMsg)) {
        $commitMsg = "Initial commit: URL Shortener Application"
    }

    git commit -m $commitMsg

    # Push to GitHub
    Write-Host "Pushing to GitHub..."
    git push -u origin main

    Write-Host ""
    Write-Host "✓ Successfully pushed to GitHub!" -ForegroundColor Green
}

Write-Host ""
Write-Host "Repository is ready!" -ForegroundColor Green
Write-Host "URL: https://github.com/$githubUsername/$repoName" -ForegroundColor Cyan
Write-Host ""
Write-Host "Next steps:" -ForegroundColor Yellow
Write-Host "1. Go to the repository on GitHub"
Write-Host "2. Click Settings → Collaborators and teams"
Write-Host "3. Click 'Add people'"
Write-Host "4. Search for 'anju-infracloud'"
Write-Host "5. Select appropriate role and add"
Write-Host ""

# Offer to open GitHub page
$openGitHub = Read-Host "Open repository in browser? (y/n)"
if ($openGitHub -eq "y") {
    Start-Process "https://github.com/$githubUsername/$repoName"
}

