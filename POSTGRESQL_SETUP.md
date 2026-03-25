# PostgreSQL Setup for OmniCare Backend

## 1. Install PostgreSQL

### Windows:
1. Download PostgreSQL from: https://www.postgresql.org/download/windows/
2. Run the installer
3. Default port: `5432`
4. Set password for `postgres` user (use `postgres` for development)
5. Install pgAdmin (included in installer)

### Using Chocolatey (Windows):
```bash
choco install postgresql
```

### Verify Installation:
```bash
psql --version
```

---

## 2. Create Database

### Option A: Using psql command line
```bash
# Connect to PostgreSQL
psql -U postgres

# Create database
CREATE DATABASE omnicaredb;

# List databases to verify
\l

# Exit
\q
```

### Option B: Using pgAdmin
1. Open pgAdmin
2. Right-click on "Databases"
3. Create > Database
4. Name: `omnicaredb`
5. Owner: `postgres`
6. Click "Save"

---

## 3. Update Application Configuration

The `application.properties` has been updated with:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/omnicaredb
spring.datasource.username=postgres
spring.datasource.password=postgres
```

**⚠️ Change the password** if you used a different one during PostgreSQL installation.

---

## 4. Rebuild and Run Backend

```bash
cd back
mvn clean install
mvn spring-boot:run
```

Spring Boot will automatically:
- Connect to PostgreSQL
- Create all tables (users, pre_inscriptions, contact_messages, etc.)
- Run migrations

---

## 5. Verify Database Tables

### Using psql:
```bash
psql -U postgres -d omnicaredb

# List all tables
\dt

# View table structure
\d users
\d pre_inscriptions
\d contact_messages

# Query data
SELECT * FROM users;
```

### Using pgAdmin:
1. Expand: Servers > PostgreSQL > Databases > omnicaredb > Schemas > public > Tables
2. Right-click table > View/Edit Data

---

## 6. Create Admin User

After the backend is running, create an admin user:

### PowerShell:
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/auth/register" -Method POST -Headers @{"Content-Type"="application/json"} -Body '{"email":"admin@omnicare.tn","password":"admin123","firstName":"Admin","lastName":"User","phoneNumber":"+216 12 345 678","role":"ADMIN"}'
```

### Or use curl (if installed):
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@omnicare.tn","password":"admin123","firstName":"Admin","lastName":"User","phoneNumber":"+216 12 345 678","role":"ADMIN"}'
```

---

## 7. Database Persistence

**PostgreSQL vs H2:**
- ✅ **PostgreSQL**: Data persists between server restarts
- ❌ **H2 (in-memory)**: Data lost when server stops

Your data is now stored permanently in PostgreSQL!

---

## 8. Connection Details

- **Host**: localhost
- **Port**: 5432
- **Database**: omnicaredb
- **Username**: postgres
- **Password**: postgres (or what you set during installation)
- **JDBC URL**: `jdbc:postgresql://localhost:5432/omnicaredb`

---

## 9. Useful PostgreSQL Commands

```sql
-- View all users
SELECT * FROM users;

-- View all pre-inscriptions
SELECT * FROM pre_inscriptions;

-- View all contact messages
SELECT * FROM contact_messages;

-- Count records
SELECT COUNT(*) FROM pre_inscriptions;

-- Delete all data (careful!)
TRUNCATE TABLE users CASCADE;
```

---

## 10. Troubleshooting

### Connection refused:
- Make sure PostgreSQL service is running
- Windows: Services > postgresql-x64-XX > Start

### Authentication failed:
- Check username/password in `application.properties`
- Reset postgres password if needed

### Database doesn't exist:
- Create it using: `CREATE DATABASE omnicaredb;`

### Port already in use:
- Change port in `application.properties`
- Or stop other PostgreSQL instances

---

## Production Notes

For production deployment:
1. Use environment variables for credentials
2. Enable SSL connections
3. Use strong passwords
4. Set up database backups
5. Configure connection pooling
6. Monitor database performance
