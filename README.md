# Voice Bot Assistant - Backend Server

A production-ready Spring Boot 4.1.0 backend server providing AI-powered chat capabilities with Gemini integration. Serverless-ready for Vercel deployment with optimized performance.

## 🎯 Features

- **Spring Boot 4.1.0** - Latest Spring Boot framework
- **Java 21** - Modern Java language features
- **Gemini AI Integration** - Google's advanced AI for natural conversations
- **RESTful API** - Clean, standards-compliant endpoints
- **CORS Enabled** - Frontend integration ready
- **Environment Configuration** - 12-factor app principles
- **Serverless Ready** - Optimized for Vercel deployment
- **Production Hardened** - Security best practices included

## 🚀 Quick Start

### Prerequisites

- **Java 21** or higher
- **Maven 3.6+** (or use Maven Wrapper)
- **Gemini API Key** (from [Google AI Studio](https://aistudio.google.com/app/apikey))

### Local Development Setup

```bash
# Navigate to project directory
cd voicebot-service

# Set environment variable (Windows PowerShell)
$env:GEMINI_API_KEY="your_actual_gemini_api_key"

# Build project
./mvnw clean install

# Run development server
./mvnw spring-boot:run

# Server runs on http://localhost:8080
```

### Or using native Maven

```bash
mvn clean install
mvn spring-boot:run
```

## 📁 Project Structure

```
voicebot-service/
├── src/
│   ├── main/
│   │   ├── java/com/saiteja/voicebot/voicebot_service/
│   │   │   ├── VoicebotServiceApplication.java     # Main entry point
│   │   │   ├── config/
│   │   │   │   └── WebConfig.java                  # CORS & Web configuration
│   │   │   ├── controller/
│   │   │   │   └── VoiceBotController.java         # REST endpoints
│   │   │   ├── dto/
│   │   │   │   ├── ChatRequest.java                # Request DTO
│   │   │   │   └── ChatResponse.java               # Response DTO
│   │   │   └── service/
│   │   │       └── GeminiService.java              # Gemini AI integration
│   │   └── resources/
│   │       └── application.properties              # Configuration
│   └── test/
│       └── java/.../VoicebotServiceApplicationTests.java
├── pom.xml                                          # Maven dependencies
├── mvnw & mvnw.cmd                                  # Maven Wrapper scripts
├── vercel.json                                      # Vercel serverless config
└── README.md                                        # This file
```

## 📡 API Endpoints

### Chat Endpoint

**URL:** `POST /api/chat`

**Request:**
```json
{
  "message": "Hello, how can you help me?",
  "userId": "optional-user-id"
}
```

**Response (Success):**
```json
{
  "response": "Hello! I'm here to assist you with...",
  "timestamp": "2026-06-27T12:30:45Z",
  "status": "success"
}
```

**Response (Error):**
```json
{
  "error": "Error message",
  "status": "error",
  "timestamp": "2026-06-27T12:30:45Z"
}
```

**Status Codes:**
- `200` - Success
- `400` - Bad Request (invalid message)
- `500` - Server Error (API failure)

## 🔧 Configuration

### Environment Variables

| Variable | Description | Required | Example |
|----------|-------------|----------|---------|
| `GEMINI_API_KEY` | Google Gemini API Key | ✅ Yes | `AQ.Ab8RN6KlbS...` |
| `SERVER_PORT` | Server port | ❌ No | `8080` (default) |
| `JAVA_TOOL_OPTIONS` | JVM options | ❌ No | `-Xmx512m` |

### application.properties

```properties
spring.application.name=voicebot-service

# Gemini API Configuration
gemini.api.key=${GEMINI_API_KEY:placeholder-key}

# Server Configuration
server.port=${SERVER_PORT:8080}
```

## 🌐 CORS Configuration

**Enabled Origins (configure in `WebConfig.java`):**
- Development: `http://localhost:4200`
- Production: Your Vercel frontend domain

**Update for your domain:**
```java
registry.addMapping("/api/**")
    .allowedOrigins("https://your-vercel-domain.vercel.app")
    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
    .allowCredentials(true)
    .maxAge(3600);
```

## 🐳 Building & Packaging

### Create JAR Package

```bash
./mvnw clean package -DskipTests
```

Output: `target/voicebot-service-0.0.1-SNAPSHOT.jar`

### Run Packaged JAR

```bash
java -jar target/voicebot-service-0.0.1-SNAPSHOT.jar
```

## ☁️ Vercel Serverless Deployment

This backend is optimized for **Vercel's serverless platform** and can be deployed as API Routes.

### Prerequisites for Vercel

1. GitHub repository connected to Vercel
2. Environment variables configured
3. `vercel.json` configured (included)

### Deployment Steps

#### Step 1: Push to GitHub

```bash
git push origin main
```

#### Step 2: Connect to Vercel

1. Go to [vercel.com](https://vercel.com)
2. Click "New Project"
3. Select GitHub repository: `voicebot-server`
4. Configure settings:
   - **Framework:** Other
   - **Root Directory:** `.` (root)
   - **Build Command:** `./mvnw clean package -DskipTests`
   - **Start Command:** `java -jar target/voicebot-service-0.0.1-SNAPSHOT.jar`
   - **Install Command:** (leave empty for Java)

#### Step 3: Set Environment Variables

In Vercel Dashboard → **Settings → Environment Variables**:

```
GEMINI_API_KEY = your_actual_gemini_api_key
```

#### Step 4: Deploy

- Automatic deployment on push to main branch
- Or manually trigger in Vercel dashboard

### Vercel Configuration (vercel.json)

```json
{
  "framework": "spring-boot",
  "env": {
    "GEMINI_API_KEY": "@gemini_api_key"
  }
}
```

### Your Backend URL

After deployment:
```
https://your-project-name.vercel.app/api/chat
```

## 🔀 Alternative Deployment Options

### Render.com

```bash
# Build Command
cd voicebot-service && ./mvnw clean package -DskipTests

# Start Command
java -jar target/voicebot-service-0.0.1-SNAPSHOT.jar
```

### Railway.app

1. Connect GitHub repository
2. Set `GEMINI_API_KEY` in environment
3. Automatic deployment

### AWS Elastic Beanstalk

```bash
eb create voicebot-service-prod
eb deploy
```

### Google Cloud Run

```bash
# Build Docker image
docker build -t voicebot-service:latest .

# Deploy
gcloud run deploy voicebot-service
```

## 🧪 Testing

### Unit Tests

```bash
./mvnw test
```

### Integration Tests

```bash
./mvnw verify
```

### Manual API Testing

**Using cURL:**
```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Hello"}'
```

**Using Postman:**
1. Create POST request to `http://localhost:8080/api/chat`
2. Set header: `Content-Type: application/json`
3. Body: `{"message": "Test message"}`
4. Send and verify response

## 🔒 Security Best Practices

1. **API Keys:**
   - ✅ Use environment variables (never hardcode)
   - ✅ Rotate keys regularly
   - ✅ Restrict API key permissions

2. **CORS:**
   - ✅ Whitelist specific origins
   - ✅ Disable in production if not needed
   - ✅ Use credentials only when necessary

3. **Input Validation:**
   - ✅ Validate message length (max 2000 chars recommended)
   - ✅ Sanitize user input
   - ✅ Rate limiting (recommended)

4. **HTTPS:**
   - ✅ Always use HTTPS in production
   - ✅ Vercel handles SSL/TLS automatically

5. **Dependencies:**
   - ✅ Keep Spring Boot updated
   - ✅ Review security advisories
   - ✅ Use `./mvnw dependency-check:check`

## 📊 Performance Optimization

### JVM Tuning

```bash
# Reduce memory footprint for serverless
export JAVA_TOOL_OPTIONS="-Xmx256m -Xms128m"

# For production
export JAVA_TOOL_OPTIONS="-Xmx512m -XX:+UseG1GC"
```

### Vercel Cold Start Optimization

- Use lightweight frameworks ✓ (Spring Boot)
- Minimize dependencies ✓
- Pre-compile if possible
- Monitor cold start times in Vercel Analytics

## 🐛 Troubleshooting

### Issue: "Cannot connect to Gemini API"

```
Check:
1. GEMINI_API_KEY is set correctly
2. API key has required permissions
3. Network connectivity to googleapis.com
4. API quota not exceeded

Solution: Verify in Google AI Studio dashboard
```

### Issue: "CORS errors from frontend"

```
Check:
1. Frontend URL is in allowedOrigins
2. Request headers match configuration
3. Backend is reachable

Solution: Update WebConfig.java with correct domain
```

### Issue: "Build fails on Vercel"

```
Check:
1. Java version (must be 21+)
2. Maven version compatibility
3. Sufficient memory allocated
4. All environment variables set

Solution: Check Vercel build logs for details
```

### Issue: "High memory usage"

```
Solution: Adjust JVM options:
export JAVA_TOOL_OPTIONS="-Xmx256m"
```

## 📈 Monitoring & Logging

### Local Logging

```properties
# application.properties
logging.level.root=INFO
logging.level.com.saiteja.voicebot=DEBUG
```

### Vercel Logs

```bash
vercel logs [project-name]
```

### Application Insights (Azure)

Can be integrated for advanced monitoring.

## 🔄 CI/CD Integration

### GitHub Actions

Create `.github/workflows/deploy.yml`:

```yaml
name: Deploy to Vercel

on:
  push:
    branches: [main]

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: vercel/action@master
        with:
          vercel-token: ${{ secrets.VERCEL_TOKEN }}
```

## 📚 Dependencies

**Core:**
- Spring Boot Starter Web
- Spring Boot Starter WebMVC
- Spring Framework 6.x

**Testing:**
- JUnit 5
- Mockito
- Spring Boot Test

## 📋 Deployment Checklist

- [ ] GEMINI_API_KEY configured
- [ ] CORS origins updated for production
- [ ] Environment variables set in Vercel
- [ ] Build succeeds locally (`./mvnw clean package`)
- [ ] Tests pass (`./mvnw test`)
- [ ] API endpoints tested manually
- [ ] Frontend API_BASE_URL points to backend
- [ ] SSL/TLS enabled (Vercel handles)
- [ ] Monitoring configured
- [ ] Error handling verified
- [ ] Rate limiting configured (if needed)
- [ ] Documentation updated

## 🚦 Health Check Endpoint

Add this to monitor server status:

```java
@GetMapping("/health")
public ResponseEntity<Map<String, String>> health() {
    return ResponseEntity.ok(Map.of("status", "UP"));
}
```

Test:
```bash
curl http://localhost:8080/health
```

## 🔗 Related Repositories

- **Frontend Repository:** [voicebot-UI](https://github.com/busasaiteja571-cloud/voicebot-UI)
- **Full Stack Project:** Contact team for integration guide

## 📞 Support

For issues and questions:
- Create an issue in this repository
- Check Vercel documentation: https://vercel.com/docs
- Consult Spring Boot docs: https://spring.io/projects/spring-boot

## 📄 License

MIT License - See LICENSE file for details

## ✨ Acknowledgments

- Built with [Spring Boot](https://spring.io/projects/spring-boot)
- AI Integration via [Google Gemini](https://ai.google.dev/)
- Deployed on [Vercel](https://vercel.com)

---

**Version:** 1.0.0  
**Last Updated:** June 27, 2026  
**Framework:** Spring Boot 4.1.0  
**Java Version:** 21+  
**Deployment Platform:** Vercel (Serverless)
