# GitHub Private Repository & Collaborator Setup Guide

Complete step-by-step guide to create a private GitHub repository and add `anju-infracloud` as a collaborator.

## Table of Contents
1. [Create a Private GitHub Repository](#create-a-private-github-repository)
2. [Push Your Code to the Repository](#push-your-code-to-the-repository)
3. [Add Collaborator](#add-collaborator)
4. [Configure Collaborator Permissions](#configure-collaborator-permissions)
5. [Verify Setup](#verify-setup)
6. [Troubleshooting](#troubleshooting)

---

## Create a Private GitHub Repository

### Step 1: Log in to GitHub

1. Go to [GitHub.com](https://github.com) and log in to your account
2. Make sure you're logged in as the repository owner

### Step 2: Create New Private Repository

1. Click the "+" icon in the top-right corner
2. Select "New repository"
3. Fill in the repository details:
   - **Repository name**: `URL-Shortener-Application` (or your preferred name)
   - **Description**: `A Spring Boot REST API for URL shortening with metrics tracking and Docker support`
   - **Visibility**: Select **PRIVATE** ⚠️ (Important!)
   - **Initialize this repository with**:
     - ✓ Add a README file (optional, you already have one)
     - ✓ Add .gitignore (select "Java")
     - ✓ Choose a license (optional, e.g., MIT)
4. Click **Create repository**

### Step 3: Get Your Repository URL

After creating, you'll see your repository URL. Copy it:
- **HTTPS**: `https://github.com/YOUR_USERNAME/URL-Shortener-Application.git`
- **SSH**: `git@github.com:YOUR_USERNAME/URL-Shortener-Application.git`

---

## Push Your Code to the Repository

### Option A: If Repository Already Has README

```bash
# Navigate to your project directory
cd URL-Shortener-Application

# Initialize git (if not already done)
git init

# Add GitHub remote
git remote add origin https://github.com/YOUR_USERNAME/URL-Shortener-Application.git

# Rename branch to main
git branch -M main

# Add all files
git add .

# Create initial commit
git commit -m "Initial commit: URL Shortener Application with metrics API and Docker support"

# Push to GitHub
git push -u origin main
```

### Option B: If Repository is Empty

```bash
# Navigate to your project directory
cd URL-Shortener-Application

# Clone the empty repository first
cd ..
git clone https://github.com/YOUR_USERNAME/URL-Shortener-Application.git temp-repo
cd temp-repo

# Copy your project files into the cloned directory
# Then:
git add .
git commit -m "Initial commit: URL Shortener Application with metrics API and Docker support"
git push -u origin main
```

### Option C: Force Push to Empty Repository

```bash
cd URL-Shortener-Application

# Initialize and add remote
git init
git remote add origin https://github.com/YOUR_USERNAME/URL-Shortener-Application.git

# Create initial commit if needed
git add .
git commit -m "Initial commit: URL Shortener Application"

# Push with --force-with-lease (safer than --force)
git push -u origin main --force-with-lease
```

---

## Add Collaborator

### Step 1: Access Repository Settings

1. Go to your repository: `https://github.com/YOUR_USERNAME/URL-Shortener-Application`
2. Click the **Settings** tab (you must be the owner)
3. On the left sidebar, click **Collaborators and teams** (or just **Collaborators**)

### Step 2: Add New Collaborator

1. Click **Add people** button
2. In the search box, type: `anju-infracloud`
3. Wait for the search results to appear
4. Click on the `anju-infracloud` user
5. Select the role:
   - **Pull only**: Can view and pull code (read-only)
   - **Triage**: Can read, pull, and manage issues/PRs
   - **Push**: Can read, pull, and push code (developer access) ⭐ **Recommended**
   - **Maintain**: Can manage without access to delete
   - **Admin**: Full repository access
6. Click **Add [username] to this repository**

### Alternative: Invite via Email

If the username isn't found:

1. Go to **Collaborators and teams**
2. Click **Add people**
3. Instead of searching, paste the email address if you know it
4. Follow the same role selection process

---

## Configure Collaborator Permissions

### Recommended Permission Levels

For `anju-infracloud`, choose based on the role:

**Developer Access** (Recommended for team members)
- Role: **Push**
- Can: Read, clone, push code, manage branches
- Cannot: Delete repository, change settings, manage access

**Read-Only Access** (For stakeholders/reviewers)
- Role: **Pull**
- Can: Read and clone code only
- Cannot: Push changes or access settings

**Full Access** (For co-owners)
- Role: **Admin**
- Can: Everything including delete and settings
- Cannot: None (full control)

### Step-by-Step to Set Permissions

1. Go to **Settings** → **Collaborators and teams**
2. Find `anju-infracloud` in the collaborators list
3. Click the role dropdown next to their name
4. Select desired role
5. Confirm the change

---

## Verify Setup

### Check Collaborator Was Added

```bash
# View all collaborators (in repository settings)
# Go to: Settings → Collaborators and teams
# You should see anju-infracloud listed
```

### Verify Collaborator Can Access

Send `anju-infracloud` a message with:

1. **Repository URL**: `https://github.com/YOUR_USERNAME/URL-Shortener-Application`
2. **Instructions to clone**:
   ```bash
   git clone https://github.com/YOUR_USERNAME/URL-Shortener-Application.git
   cd URL-Shortener-Application
   ```
3. **Test if they can pull**:
   ```bash
   git pull
   ```

### Collaborator Cloning Instructions

```bash
# If using HTTPS (simpler, uses GitHub token)
git clone https://github.com/YOUR_USERNAME/URL-Shortener-Application.git

# If using SSH (requires SSH key setup)
git clone git@github.com:YOUR_USERNAME/URL-Shortener-Application.git

# Navigate to project
cd URL-Shortener-Application

# Verify they can see all files
ls -la
```

---

## Complete Workflow for Collaborators

### First Time Setup (For `anju-infracloud`)

```bash
# Clone the private repository
git clone https://github.com/YOUR_USERNAME/URL-Shortener-Application.git
cd URL-Shortener-Application

# Configure git (if first time)
git config user.name "Anju Name"
git config user.email "anju@infracloud.io"

# Create a feature branch
git checkout -b feature/my-feature

# Make changes...

# Stage and commit
git add .
git commit -m "Add my feature"

# Push to origin
git push origin feature/my-feature
```

### Creating Pull Requests

1. After pushing to a feature branch, go to the repository on GitHub
2. Click **Compare & pull request**
3. Add title and description
4. Request code review from the owner
5. After approval, merge to main

---

## Troubleshooting

### "Repository not found" Error

**Problem**: `fatal: repository not found`

**Solutions**:
```bash
# 1. Verify the repository URL
git remote -v

# 2. Update remote if wrong
git remote set-url origin https://github.com/YOUR_USERNAME/URL-Shortener-Application.git

# 3. Verify you have access (check GitHub for the repo)
# 4. If using HTTPS, ensure you have a valid GitHub token:
# On Windows: Check Credential Manager
# On Mac: Check Keychain
# On Linux: Check ~/.git-credentials
```

### "Permission denied" Error

**Problem**: `Permission denied (publickey) fatal: Could not read from remote repository`

**Solutions**:
```bash
# For SSH, generate or check SSH keys
ssh-keygen -t ed25519 -C "your-email@example.com"

# Add public key to GitHub:
# Settings → SSH and GPG keys → New SSH key
# Paste contents of ~/.ssh/id_ed25519.pub

# Test SSH connection
ssh -T git@github.com
```

### Collaborator Can't See Repository

**Problem**: `anju-infracloud` cannot access the private repository

**Solutions**:
1. Verify they're logged in to their GitHub account
2. Check they're listed in **Settings → Collaborators and teams**
3. Ask them to accept the invitation (if pending)
4. Check their notification/email for collaboration invitation
5. Resend invitation if needed

### Authentication Issues

**Using HTTPS**:
```bash
# Generate personal access token:
# GitHub → Settings → Developer settings → Personal access tokens
# Use token instead of password when prompted

# Or cache credentials:
git config --global credential.helper store
# This will save credentials after first authentication
```

**Using SSH**:
```bash
# Add SSH key to ssh-agent
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_ed25519

# Test connection
ssh -T git@github.com
```

---

## Security Best Practices

### For Repository Owner (You)

1. ✅ Use **PRIVATE** repository ✓ (Already done)
2. ✅ Use **SSH keys** for authentication (more secure)
3. ✅ Enable **two-factor authentication** on GitHub account
4. ✅ Review collaborators regularly
5. ✅ Remove access when no longer needed

### For Collaborators

1. Use SSH keys instead of HTTPS
2. Never commit credentials or secrets
3. Use `.gitignore` to exclude sensitive files
4. Keep local repository secure
5. Remove local clone when done

---

## Managing Collaborators

### View All Collaborators

1. Go to repository **Settings**
2. Click **Collaborators and teams**
3. See all current collaborators and their roles

### Change Collaborator Permission

1. Find collaborator in the list
2. Click the role dropdown
3. Select new role
4. Changes take effect immediately

### Remove Collaborator

1. Find collaborator in the list
2. Click the "X" or remove button
3. Confirm removal
4. They'll lose access immediately

### Invite Pending Collaborator

1. If invitation shows as "Pending"
2. Click **Resend invitation**
3. They'll receive a new email

---

## Examples

### Example 1: Your Initial Setup (Owner)

```bash
# Your computer
cd ~/projects/URL-Shortener-Application
git init
git remote add origin https://github.com/yourname/URL-Shortener-Application.git
git add .
git commit -m "Initial commit: URL Shortener Application"
git push -u origin main
```

### Example 2: Anju Cloning and Contributing

```bash
# Anju's computer
git clone https://github.com/yourname/URL-Shortener-Application.git
cd URL-Shortener-Application

# Create feature branch
git checkout -b feature/metrics-improvement

# Make changes...
git add .
git commit -m "Improve metrics API response time"
git push origin feature/metrics-improvement

# Then create PR on GitHub
```

### Example 3: You Reviewing and Merging

```bash
# Back on your computer
git pull origin main
# Review the PR on GitHub website
# Click "Merge pull request"
# Delete branch after merge

# Pull the merged changes
git fetch origin
git checkout main
git pull origin main
```

---

## Next Steps

1. ✅ Create private repository on GitHub
2. ✅ Push your code
3. ✅ Add `anju-infracloud` as collaborator
4. ✅ Configure appropriate permissions
5. ✅ Share repository link with anju-infracloud
6. ✅ Have them clone and verify access
7. ✅ Start collaborating on code!

---

## Quick Reference

| Task | Location |
|------|----------|
| Create Repository | GitHub.com → "+" icon → New repository |
| Add Collaborator | Settings → Collaborators and teams → Add people |
| Change Permissions | Collaborators list → Role dropdown |
| Remove Collaborator | Collaborators list → "X" button |
| View Pending Invites | Settings → Collaborators and teams (shows pending) |
| Accept Invitation | Email or GitHub notifications (for collaborators) |

---

## Support

If you encounter issues:

1. Check [GitHub Help - Collaborators](https://docs.github.com/en/account-and-profile/setting-up-and-managing-your-github-user-account/managing-access-to-your-personal-repositories/inviting-collaborators-to-a-personal-repository)
2. Visit [GitHub Status](https://www.githubstatus.com/) to check for outages
3. Check your email for GitHub notifications
4. Verify both users have GitHub accounts active


