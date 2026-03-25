# OmniCare Backend - Docker Deployment to Railway

## Overview

This guide shows how to deploy the OmniCare backend using Docker to Railway when GitHub integration has issues.

---

## Railway Database Configuration

Based on your Railway PostgreSQL instance:

```
Public Network: centerbeam.proxy.rlwy.net:24309
Private Network: postgres.railway.internal
Database: railway
User: postgres
Password: qdGdGSQOaJupCTmoeeyliKLoKZInVRUM
```

---

## Method 1: Deploy via Railway CLI (Recommended)

### Step 1: Install Railway CLI

**macOS:**
```bash
brew install railway
```

**Linux/WSL:**
```bash
npm i -g @railway/cli
```

### Step 2: Login to Railway

```bash
railway login
```

This will open a browser for authentication.

### Step 3: Link to Your Project

```bash
cd /Users/rouge/Desktop/omniCare/omnicareweb/OmnicareWeb/back
railway link
```

Select your existing Railway project (the one with PostgreSQL).

### Step 4: Set Environment Variables

```bash
# Set the JDBC database URL using Railway's public endpoint
railway variables set JDBC_DATABASE_URL="jdbc:postgresql://centerbeam.proxy.rlwy.net:24309/railway"

# Set database credentials
railway variables set PGUSER="postgres"
railway variables set PGPASSWORD="qdGdGSQOaJupCTmoeeyliKLoKZInVRUM"

# Generate and set JWT secret
JWT_SECRET=$(openssl rand -base64 64)
railway variables set JWT_SECRET="$JWT_SECRET"

# Set JWT expiration (24 hours)
railway variables set JWT_EXPIRATION="86400000"

# Set CORS (update with your frontend URL later)
railway variables set CORS_ALLOWED_ORIGINS="http://localhost:5173"
```

### Step 5: Deploy with Docker

```bash
railway up
```

Railway will:
1. Detect the Dockerfile
2. Build the Docker image
3. Push to Railway's registry
4. Deploy the container

### Step 6: Get Your Backend URL

```bash
railway domain
```

Or check the Railway dashboard under your service → Settings → Domains.

---

## Method 2: Deploy via Docker Hub

If Railway CLI doesn't work, you can push to Docker Hub and deploy from there.

### Step 1: Build Docker Image Locally

```bash
cd /Users/rouge/Desktop/omniCare/omnicareweb/OmnicareWeb/back

# Build the image
docker build -t omnicare-backend:latest .
```

### Step 2: Test Locally (Optional)

```bash
# Start with docker-compose
docker-compose up

# Test the API
curl http://localhost:8080/api/auth/login
```

Press Ctrl+C to stop.

### Step 3: Push to Docker Hub

```bash
# Login to Docker Hub
docker login

# Tag the image
docker tag omnicare-backend:latest YOUR_DOCKERHUB_USERNAME/omnicare-backend:latest

# Push to Docker Hub
docker push YOUR_DOCKERHUB_USERNAME/omnicare-backend:latest
```

### Step 4: Deploy on Railway

1. Go to Railway dashboard
2. Click "New" → "Empty Service"
3. Go to Settings → Deploy
4. Select "Docker Image"
5. Enter: `YOUR_DOCKERHUB_USERNAME/omnicare-backend:latest`
6. Click "Deploy"

### Step 5: Configure Environment Variables

In Railway dashboard → Variables tab:

```
PORT=8080
JDBC_DATABASE_URL=jdbc:postgresql://centerbeam.proxy.rlwy.net:24309/railway
PGUSER=postgres
PGPASSWORD=qdGdGSQOaJupCTmoeeyliKLoKZInVRUM
JWT_SECRET=<generate with: openssl rand -base64 64>
JWT_EXPIRATION=86400000
CORS_ALLOWED_ORIGINS=http://localhost:5173
```

---

## Method 3: Manual GitHub Push + Railway Auto-Deploy

If you want to keep using GitHub:

### Step 1: Commit Docker Files

```bash
cd /Users/rouge/Desktop/omniCare/omnicareweb/OmnicareWeb/back

git add Dockerfile .dockerignore docker-compose.yml
git commit -m "Add Docker support"
git push origin main
```

### Step 2: Configure Railway to Use Dockerfile

1. Go to Railway dashboard
2. Click on your backend service
3. Go to Settings → Build
4. Set "Build Method" to "Dockerfile"
5. Railway will rebuild using the Dockerfile

---

## Environment Variables Explained

| Variable | Value | Description |
|----------|-------|-------------|
| `PORT` | `8080` | Server port (Railway may override) |
| `JDBC_DATABASE_URL` | `jdbc:postgresql://centerbeam.proxy.rlwy.net:24309/railway` | Database connection URL |
| `PGUSER` | `postgres` | Database username |
| `PGPASSWORD` | `qdGdGSQOaJupCTmoeeyliKLoKZInVRUM` | Database password |
| `JWT_SECRET` | Generated string | Secret key for JWT tokens |
| `JWT_EXPIRATION` | `86400000` | Token expiration (24 hours in ms) |
| `CORS_ALLOWED_ORIGINS` | Frontend URL | Allowed origins for CORS |

---

## Database Connection Notes

Railway provides two connection options:

### Public Network (Use This)
```
Host: centerbeam.proxy.rlwy.net
Port: 24309
Database: railway
```

**JDBC URL:**
```
jdbc:postgresql://centerbeam.proxy.rlwy.net:24309/railway
```

### Private Network (Only works within Railway)
```
Host: postgres.railway.internal
Port: 5432
Database: railway
```

**For Railway deployments, use the public network URL** since it's accessible from anywhere.

---

## Testing the Deployment

### 1. Check Deployment Status

```bash
railway status
```

### 2. View Logs

```bash
railway logs
```

Or in Railway dashboard → Deployments → View Logs

### 3. Test API Endpoints

```bash
# Replace with your Railway URL
BACKEND_URL="https://your-backend.railway.app"

# Test health (should return 401/403 - endpoint requires auth)
curl $BACKEND_URL/api/auth/login

# Test with credentials (after creating a user)
curl -X POST $BACKEND_URL/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password"}'
```

---

## Troubleshooting

### Build Fails

**Check Dockerfile syntax:**
```bash
docker build -t test .
```

**Check Railway logs:**
```bash
railway logs --build
```

### Database Connection Fails

**Verify connection URL:**
- Must use `jdbc:postgresql://` prefix
- Host: `centerbeam.proxy.rlwy.net`
- Port: `24309`
- Database: `railway`

**Test connection locally:**
```bash
psql postgresql://postgres:qdGdGSQOaJupCTmoeeyliKLoKZInVRUM@centerbeam.proxy.rlwy.net:24309/railway
```

### Application Won't Start

**Check environment variables:**
```bash
railway variables
```

**View runtime logs:**
```bash
railway logs
```

**Common issues:**
- Missing `JDBC_DATABASE_URL`
- Wrong database credentials
- Missing `JWT_SECRET`

### Port Issues

Railway automatically assigns a port via `$PORT` environment variable. The Dockerfile is configured to use it:

```dockerfile
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar app.jar"]
```

---

## Local Development with Docker

### Start Everything

```bash
docker-compose up
```

This starts:
- PostgreSQL on port 5432
- Backend on port 8080

### Stop Everything

```bash
docker-compose down
```

### Rebuild After Code Changes

```bash
docker-compose up --build
```

### View Logs

```bash
docker-compose logs -f backend
```

---

## Updating the Deployment

### Update Code

```bash
# Make your changes
git add .
git commit -m "Update backend"
git push origin main

# If using Railway CLI
railway up
```

### Update Environment Variables

```bash
railway variables set VARIABLE_NAME="value"
```

Or update in Railway dashboard → Variables tab.

---

## Next Steps

1. ✅ Deploy backend to Railway
2. ✅ Verify database connection
3. ✅ Test API endpoints
4. ⬜ Get backend URL
5. ⬜ Update frontend `VITE_API_URL`
6. ⬜ Update backend `CORS_ALLOWED_ORIGINS` with frontend URL
7. ⬜ Deploy frontend

---

## Railway CLI Quick Reference

```bash
# Login
railway login

# Link to project
railway link

# Set variables
railway variables set KEY="value"

# View variables
railway variables

# Deploy
railway up

# View logs
railway logs

# View status
railway status

# Get domain
railway domain

# Open in browser
railway open
```

---

## Security Checklist

- [ ] Strong JWT secret generated (64+ characters)
- [ ] Database password is secure (Railway generated)
- [ ] CORS properly configured with frontend URL
- [ ] HTTPS enabled (automatic on Railway)
- [ ] Environment variables not in Git
- [ ] `.env` files in `.gitignore`

---

## Cost Optimization

- Railway free tier: $5 credit/month
- Docker deployments are efficient
- Database included in project
- Monitor usage in Railway dashboard

---

## Support

- Railway Docs: https://docs.railway.app
- Railway Discord: https://discord.gg/railway
- Docker Docs: https://docs.docker.com

---

## Success Checklist

- [ ] Dockerfile created
- [ ] Docker image builds successfully
- [ ] Environment variables configured
- [ ] Database connection works
- [ ] Application starts without errors
- [ ] API endpoints respond
- [ ] Logs show no errors
- [ ] Backend URL accessible

Your backend should now be running on Railway! 🚀
