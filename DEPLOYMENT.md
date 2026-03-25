# OmniCare Backend - Railway Deployment Guide

## Prerequisites
- Railway account (https://railway.app)
- GitHub account
- PostgreSQL database on Railway

## Step 1: Create Backend Repository

```bash
cd /Users/rouge/Desktop/omniCare/omnicareweb/OmnicareWeb/back
git init
git add .
git commit -m "Initial commit - OmniCare Backend"
git branch -M main
git remote add origin <your-backend-repo-url>
git push -u origin main
```

## Step 2: Setup PostgreSQL on Railway

1. Go to Railway dashboard
2. Click "New Project"
3. Select "Provision PostgreSQL"
4. Railway will automatically create a PostgreSQL database
5. Copy the connection details (you'll need these for environment variables)

## Step 3: Deploy Backend to Railway

1. In Railway dashboard, click "New" → "GitHub Repo"
2. Select your backend repository
3. Railway will auto-detect it's a Java/Maven project

## Step 4: Configure Environment Variables

In Railway, go to your backend service → Variables tab and add:

```
PORT=8080
DATABASE_URL=<Railway PostgreSQL connection string>
DB_USERNAME=<from Railway PostgreSQL>
DB_PASSWORD=<from Railway PostgreSQL>
JWT_SECRET=<generate a strong random string - at least 64 characters>
JWT_EXPIRATION=86400000
CORS_ALLOWED_ORIGINS=<your-frontend-url>
```

### Generate JWT Secret (run in terminal):
```bash
openssl rand -base64 64
```

### Railway PostgreSQL URL Format:
Railway provides `DATABASE_URL` in this format:
```
postgresql://user:password@host:port/database
```

Convert it to JDBC format:
```
jdbc:postgresql://host:port/database
```

## Step 5: Deploy

Railway will automatically:
1. Build your application using Maven
2. Run `mvn clean package -DskipTests`
3. Start the application with the generated JAR

## Step 6: Verify Deployment

1. Check Railway logs for successful startup
2. Test the health endpoint: `https://your-backend-url.railway.app/api/auth/login`
3. Verify database connection in logs

## Important Notes

- **Database**: Railway PostgreSQL is automatically configured
- **Port**: Railway assigns a dynamic port via `$PORT` environment variable
- **Build**: Maven build runs automatically on each push
- **Logs**: Available in Railway dashboard under "Deployments"

## Troubleshooting

### Build Fails
- Check Maven dependencies in `pom.xml`
- Verify Java version compatibility
- Check Railway build logs

### Database Connection Issues
- Verify `DATABASE_URL` format is correct
- Check database credentials
- Ensure PostgreSQL service is running

### CORS Errors
- Update `CORS_ALLOWED_ORIGINS` with your frontend URL
- Format: `https://your-frontend.railway.app` (no trailing slash)

## Monitoring

- Railway provides automatic metrics
- Check CPU, Memory, and Network usage in dashboard
- Set up alerts for downtime

## Scaling

Railway automatically scales based on:
- Memory usage
- CPU usage
- Request volume

Upgrade plan if needed for higher limits.
