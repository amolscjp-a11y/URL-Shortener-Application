#!/bin/bash

# GitHub Private Repository Setup Script
# This script automates the process of pushing code to a private GitHub repository

set -e  # Exit on error

echo ""
echo "================================================"
echo "   GitHub Private Repository Setup"
echo "================================================"
echo ""

# Check if git is installed
if ! command -v git &> /dev/null; then
    echo "Error: Git is not installed. Please install git first."
    exit 1
fi

# Get repository information
read -p "Enter your GitHub username: " github_username
read -p "Enter your repository name (default: URL-Shortener-Application): " repo_name
repo_name=${repo_name:-URL-Shortener-Application}

# Construct repository URL
repo_url="https://github.com/$github_username/$repo_name.git"

echo ""
echo "Repository URL: $repo_url"
echo ""

# Initialize git if needed
if [ ! -d .git ]; then
    echo "Initializing git repository..."
    git init
fi

# Check if remote exists
if git remote | grep -q origin; then
    echo "Updating existing remote..."
    git remote set-url origin "$repo_url"
else
    echo "Adding GitHub remote..."
    git remote add origin "$repo_url"
fi

# Check branch name
current_branch=$(git rev-parse --abbrev-ref HEAD)
if [ "$current_branch" != "main" ]; then
    echo "Renaming branch to 'main'..."
    git branch -M main
fi

# Stage all files
echo "Staging files..."
git add .

# Check if there are changes to commit
if git diff --cached --quiet; then
    echo "No changes to commit. Repository is up to date."
else
    # Create commit
    echo "Creating commit..."
    read -p "Enter commit message (default: 'Initial commit: URL Shortener Application'): " commit_msg
    commit_msg=${commit_msg:-"Initial commit: URL Shortener Application"}

    git commit -m "$commit_msg"

    # Push to GitHub
    echo "Pushing to GitHub..."
    git push -u origin main

    echo ""
    echo "✓ Successfully pushed to GitHub!"
fi

echo ""
echo "Repository is ready!"
echo "URL: https://github.com/$github_username/$repo_name"
echo ""
echo "Next steps:"
echo "1. Go to the repository on GitHub"
echo "2. Click Settings → Collaborators and teams"
echo "3. Click 'Add people'"
echo "4. Search for 'anju-infracloud'"
echo "5. Select appropriate role and add"
echo ""

