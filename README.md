# Java JDBC PostgreSQL CRUD

A minimal application to understand JDBC with PostgreSQL.

## Prerequisites
- Java 21+
- PostgreSQL
- Maven

## Installation

### 1. Create the Database:
```sql
-- Connect to PostgreSQL
sudo -u postgres psql

-- Create database
CREATE DATABASE javadb;

-- Connect to the database
\c javadb;

-- Create users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    age INTEGER
);
```

### 2. Create Application User:
```sql
-- Create dedicated user (replace 'your_password' with actual password)
CREATE USER java_app_user WITH PASSWORD 'your_password';

-- Allow user to login
ALTER USER java_app_user WITH LOGIN;

-- Grant privileges on database
GRANT ALL PRIVILEGES ON DATABASE javadb TO java_app_user;

-- Connect to javadb
\c javadb

-- Grant privileges on tables
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO java_app_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO java_app_user;

-- For future tables
ALTER DEFAULT PRIVILEGES IN SCHEMA public 
GRANT ALL PRIVILEGES ON TABLES TO java_app_user;
```

### 3. Configure Application:
1. Copy `database.properties.example` to `database.properties`:
```bash
cp src/main/resources/example.database.properties src/main/resources/database.properties
```

2. Edit `database.properties` with your credentials:
```properties
db.url=jdbc:postgresql://localhost:5432/javadb
db.user=java_app_user
db.password=your_password
```

## Project Structure
```
Java-JDBC-Postgres-CRUD/
├── src/main/java/com/example/
│   ├── model/           # Data classes (User)
│   ├── dao/            # Database operations (CRUD)
│   ├── config/         # Configuration
│   ├── util/           # Connection utility
│   └── main/           # Entry point
├── src/main/resources/  # Configuration files
├── pom.xml             # Maven dependencies
└── README.md           # Documentation
```

## Key Concepts

1. **MODEL** (`User.java`): Represents business data
2. **DAO** (`UserDAO.java`): CRUD operations on database
3. **CONFIG** (`DatabaseConfig.java`): Reads configuration from properties file
4. **UTIL** (`DatabaseConnection.java`): Manages JDBC connection
5. **MAIN** (`Main.java`): Tests all CRUD operations

## JDBC Concepts Covered
- Driver loading
- Database connection
- Statement and PreparedStatement
- ResultSet handling
- Try-with-resources for auto-closing
- Parameterized queries (prevents SQL injection)

## Running the Application

### In IntelliJ IDEA:
1. Open `Main.java`
2. Click the green run button ▶️ next to the `main` method
3. Or press `Shift + F10`

### From Command Line:
```bash
# Compile and run
mvn clean compile exec:java

# Or compile first, then run
mvn clean compile
mvn exec:java -Dexec.mainClass="com.example.main.Main"
```

### Create Executable JAR:
```bash
mvn clean package
java -jar target/Java-JDBC-Postgres-CRUD-1.0-SNAPSHOT.jar
```

## Testing SQL Commands
```sql
-- Check if data is inserted
SELECT * FROM users;

-- Reset the table if needed
TRUNCATE TABLE users RESTART IDENTITY;

-- Check user permissions
\c javadb
\dp users
```

## Troubleshooting

### Connection Issues:
1. **PostgreSQL not running:**
   ```bash
   # Linux
   sudo service postgresql start
   
   # Mac
   brew services start postgresql
   
   # Check status
   sudo service postgresql status
   ```

2. **Permission denied:**
   ```sql
   \c javadb
   GRANT ALL ON users TO java_app_user;
   GRANT USAGE ON SCHEMA public TO java_app_user;
   ```

3. **Authentication failed:**
    - Check password in `database.properties`
    - Reset password: `ALTER USER java_app_user WITH PASSWORD 'new_password';`

### Common Errors:
- **"No suitable driver found"**: Check Maven dependencies are downloaded
- **"Connection refused"**: PostgreSQL not running or wrong port
- **"Permission denied"**: User lacks privileges on database/table

## CRUD Operations Demonstrated
- **CREATE**: Insert new users
- **READ**: Get all users or by ID
- **UPDATE**: Modify existing users
- **DELETE**: Remove users

## Best Practices Implemented
1. Use PreparedStatement to prevent SQL injection
2. Try-with-resources for automatic resource closing
3. Separate configuration from code
4. Use dedicated database user (not superuser)
5. Clear separation of concerns (Model, DAO, Config)

## Next Steps to Explore
1. Add connection pooling (HikariCP)
2. Implement transactions
3. Add logging (SLF4J)
4. Create unit tests
5. Build a simple REST API
6. Add input validation

## Dependencies
- PostgreSQL JDBC Driver (42.6.0)
- Java 21+

## License
Educational project for learning JDBC with PostgreSQL