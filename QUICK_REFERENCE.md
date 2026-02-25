# Quick Reference Card: Private Repo + Collaborator Setup

## 🎯 3-Minute Overview

```
┌─────────────────────────────────────────────────────────┐
│           SETUP PRIVATE GITHUB REPOSITORY                │
│              WITH anju-infracloud                        │
└─────────────────────────────────────────────────────────┘
```

---

## 📍 Location: GitHub.com

### STEP 1: Create Repository (2 minutes)
```
GitHub.com → "+" icon → "New repository"
├── Name: URL-Shortener-Application
├── Description: A Spring Boot REST API for URL shortening...
├── Visibility: ⚠️ PRIVATE ← SELECT THIS!
└── Create repository
```

**Result**: You get repository URL
```
https://github.com/YOUR_USERNAME/URL-Shortener-Application.git
```

---

### STEP 2: Push Your Code (5 minutes)

**Option A - Automatic (Recommended)**

Windows PowerShell:
```powershell
.\setup-github-repo.ps1
```

macOS/Linux Bash:
```bash
./setup-github-repo.sh
```

**Option B - Manual Commands**
```bash
cd URL-Shortener-Application
git init
git remote add origin https://github.com/YOUR_USERNAME/URL-Shortener-Application.git
git branch -M main
git add .
git commit -m "Initial commit: URL Shortener Application"
git push -u origin main
```

**Result**: All code appears on GitHub ✓

---

### STEP 3: Add Collaborator (1 minute)

```
Repository Settings → Collaborators and teams
├── Click "Add people"
├── Search: anju-infracloud
├── Select from results
├── Role: Push (developer) ⭐
└── Add to repository
```

**Result**: anju-infracloud can now see the repository ✓

---

### STEP 4: Notify Anju

Send message with:
```
Repository URL:
https://github.com/YOUR_USERNAME/URL-Shortener-Application

They should run:
git clone https://github.com/YOUR_USERNAME/URL-Shortener-Application.git
```

---

## 🔐 Important Checklist

- [ ] Repository is **PRIVATE** (not public)
- [ ] anju-infracloud has **Push** role (can write code)
- [ ] Code is pushed to **main** branch
- [ ] No passwords in committed files

---

## 🚨 If Something Goes Wrong

| Problem | Fix |
|---------|-----|
| Repository not found | Check GitHub URL format |
| Permission denied | Check Git credentials setup |
| Anju can't see repo | Verify they accepted invitation |
| Need to delete repo | Settings → Scroll down → Delete repository |

---

## 📂 Documentation Files

For more details, read:
- `PRIVATE_REPO_SETUP.md` - Full detailed guide (15 min read)
- `SETUP_CHECKLIST.md` - Step-by-step checklist
- `SETUP_SUMMARY.md` - Complete overview

---

## 🎓 Key Concepts

```
PRIVATE repository = Only people you invite can see it
COLLABORATOR = Person with permission to view/edit code
PUSH role = Can read and write code (developer access)
main branch = Primary branch where code lives
git commit = Save changes with a description
git push = Send changes to GitHub
```

---

## 🔄 After Setup: Working Together

```
1. Anju creates branch: git checkout -b feature/something
2. Anju makes changes and commits
3. Anju pushes: git push origin feature/something
4. You review on GitHub and merge
5. Both pull latest: git pull origin main
```

---

## 💡 Pro Tips

✅ Always use PRIVATE for confidential projects  
✅ Use descriptive commit messages  
✅ Review code before merging  
✅ Delete feature branches after merging  
✅ Never commit passwords or secrets  

---

## 📞 Quick Links

- GitHub: https://github.com
- Repository: https://github.com/YOUR_USERNAME/URL-Shortener-Application
- Help: https://docs.github.com

---

## ✅ Success = This

```
Repository created ✓
Code pushed ✓
anju-infracloud added ✓
Can both collaborate ✓
Ready to code! 🚀
```

---

**Time to Complete**: ~10 minutes  
**Difficulty**: Easy  
**Status**: Ready ✅

