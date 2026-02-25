# Private Repository & Collaborator Setup Checklist

Quick reference checklist for setting up a private GitHub repository with `anju-infracloud` as a collaborator.

---

## 🔐 Prerequisites

- [ ] GitHub account created and logged in
- [ ] Git installed on your machine
- [ ] Project files ready to push
- [ ] GitHub username ready
- [ ] Collaborator GitHub username: `anju-infracloud`

---

## 📋 Step 1: Create Private Repository on GitHub

### On GitHub Website:

- [ ] Log in to [GitHub.com](https://github.com)
- [ ] Click "+" icon → "New repository"
- [ ] Fill in repository details:
  - [ ] Repository name: `URL-Shortener-Application`
  - [ ] Description: `A Spring Boot REST API for URL shortening with metrics tracking`
  - [ ] Visibility: **SELECT PRIVATE** ⚠️
  - [ ] Add README (optional)
  - [ ] Add .gitignore (Java)
  - [ ] Add license (optional)
- [ ] Click "Create repository"
- [ ] Copy repository URL (HTTPS or SSH)

**Repository URL**: `https://github.com/YOUR_USERNAME/URL-Shortener-Application.git`

---

## 📤 Step 2: Push Code to Repository

### Option A: Using Provided Script (Easiest)

**On Windows (PowerShell):**
```powershell
.\setup-github-repo.ps1
```

**On macOS/Linux (Bash):**
```bash
chmod +x setup-github-repo.sh
./setup-github-repo.sh
```

### Option B: Manual Git Commands

```bash
# Navigate to project
cd URL-Shortener-Application

# Initialize git
git init

# Add remote
git remote add origin https://github.com/YOUR_USERNAME/URL-Shortener-Application.git

# Rename to main
git branch -M main

# Stage files
git add .

# Commit
git commit -m "Initial commit: URL Shortener Application"

# Push to GitHub
git push -u origin main
```

### Verification:
- [ ] Repository appears on GitHub website
- [ ] All project files are visible on GitHub
- [ ] Main branch is selected and contains all code

---

## 👥 Step 3: Add Collaborator

### On GitHub Website:

1. [ ] Go to your repository: `https://github.com/YOUR_USERNAME/URL-Shortener-Application`
2. [ ] Click **Settings** tab
3. [ ] Click **Collaborators and teams** (left sidebar)
4. [ ] Click **Add people** button
5. [ ] Search for username: `anju-infracloud`
6. [ ] Select `anju-infracloud` from search results
7. [ ] Choose role:
   - [ ] **Pull** (Read-only)
   - [ ] **Triage** (Manage issues/PRs)
   - [ ] **Push** (Developer) ⭐ **RECOMMENDED**
   - [ ] **Maintain** (Manage without delete)
   - [ ] **Admin** (Full access)
8. [ ] Click **Add [username] to this repository**
9. [ ] **Verify**: `anju-infracloud` appears in collaborators list

---

## ✉️ Step 4: Notify Collaborator

- [ ] Send `anju-infracloud` a message with:
  - [ ] Repository URL
  - [ ] Their assigned role
  - [ ] Clone instructions

**Sample Message:**
```
Hi anju-infracloud,

I've added you as a collaborator to my URL Shortener project:
https://github.com/YOUR_USERNAME/URL-Shortener-Application

Role: Push (developer access)

To get started, clone the repository:
git clone https://github.com/YOUR_USERNAME/URL-Shortener-Application.git

Let me know if you have any questions!
```

---

## 🔍 Step 5: Verify Setup

### As Repository Owner (You):

- [ ] Go to Settings → Collaborators and teams
- [ ] Verify `anju-infracloud` is listed
- [ ] Verify correct role is assigned
- [ ] Check that repository is marked as **PRIVATE**

### As Collaborator (anju-infracloud):

- [ ] Receive collaboration invitation (email/GitHub notification)
- [ ] Accept the invitation
- [ ] Clone the repository:
  ```bash
  git clone https://github.com/YOUR_USERNAME/URL-Shortener-Application.git
  ```
- [ ] Verify all files are present
- [ ] Test ability to pull:
  ```bash
  git pull
  ```

---

## 🚀 Step 6: Start Collaborating

### For Feature Development:

**Anju creates a feature branch:**
```bash
git checkout -b feature/new-feature
# Make changes...
git add .
git commit -m "Add new feature"
git push origin feature/new-feature
```

**You review and merge:**
```bash
# On GitHub website:
# 1. Review the Pull Request
# 2. Approve if good
# 3. Click "Merge pull request"
# 4. Delete branch after merge
```

**Pull merged changes:**
```bash
git fetch origin
git checkout main
git pull origin main
```

---

## 🔒 Security Checklist

- [ ] Repository is **PRIVATE** (not public)
- [ ] Only intended collaborators have access
- [ ] Appropriate roles assigned (not everyone as Admin)
- [ ] Two-factor authentication enabled on GitHub account
- [ ] SSH keys configured (instead of HTTPS if possible)
- [ ] `.gitignore` configured to exclude sensitive files
- [ ] No credentials committed to repository
- [ ] Regular review of collaborator access

---

## 📊 Repository Information Reference

| Item | Value |
|------|-------|
| Repository Name | URL-Shortener-Application |
| Repository URL | https://github.com/YOUR_USERNAME/URL-Shortener-Application |
| Visibility | **PRIVATE** |
| Owner | YOUR_USERNAME |
| Collaborators | anju-infracloud |
| Default Branch | main |
| Repository Type | Java/Spring Boot |

---

## ❌ Troubleshooting

### Repository Not Found Error
- [ ] Verify repository exists on GitHub
- [ ] Check repository URL is correct
- [ ] Verify you have push access

### Anju Can't See Repository
- [ ] Verify invitation was sent
- [ ] Check anju-infracloud's email for GitHub notification
- [ ] Ask them to accept invitation
- [ ] Verify they're logged into correct GitHub account
- [ ] Check Settings → Collaborators to confirm they're added

### Permission Denied Error
- [ ] Check git credentials are saved correctly
- [ ] For SSH: Verify SSH key is added to GitHub
- [ ] For HTTPS: Verify personal access token is valid
- [ ] Try signing out and signing in again

### Push Rejected Error
- [ ] Verify you have push permission
- [ ] Check branch protection rules (if any)
- [ ] Ensure remote URL is correct

---

## 🔄 Common Git Commands for Collaboration

```bash
# Clone repository (for new users)
git clone https://github.com/YOUR_USERNAME/URL-Shortener-Application.git

# Update your local main from GitHub
git fetch origin
git checkout main
git pull origin main

# Create feature branch
git checkout -b feature/my-feature

# Commit changes
git add .
git commit -m "Description of changes"

# Push feature branch
git push origin feature/my-feature

# View all branches
git branch -a

# Delete local branch
git branch -d feature/my-feature

# Delete remote branch
git push origin --delete feature/my-feature
```

---

## 📞 Support Resources

- [ ] [GitHub Help - Collaborators](https://docs.github.com/en/account-and-profile/setting-up-and-managing-your-github-user-account/managing-access-to-your-personal-repositories/inviting-collaborators-to-a-personal-repository)
- [ ] [GitHub Help - SSH Keys](https://docs.github.com/en/authentication/connecting-to-github-with-ssh)
- [ ] [GitHub Help - Access Tokens](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token)

---

## ✅ Final Checklist

- [ ] Private repository created
- [ ] Code pushed to main branch
- [ ] anju-infracloud added as collaborator
- [ ] Correct role assigned
- [ ] Collaborator invited and notified
- [ ] Collaborator has accepted invitation
- [ ] Collaborator can clone repository
- [ ] Collaborator can see all project files
- [ ] Security best practices implemented
- [ ] Ready to start collaboration!

---

**Status**: Ready for Collaboration ✅

Generated on: February 25, 2026

