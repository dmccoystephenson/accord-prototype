# accord-prototype

**Accord Chat MVP** - A self-hosted real-time chat application with Spring Boot WebSocket backend and LibGDX frontend.

## Overview

Accord is a Discord-like self-hosted chat application designed for simplicity and extensibility. This MVP demonstrates core chat functionality with a single chat room, username-based login, and real-time messaging.

**Technology Stack:**
- **Backend**: Spring Boot 3.x, WebSocket (STOMP), H2 Database, Spring Data JPA
- **Frontend**: LibGDX, Java WebSocket client, Scene2D UI

## Features

- ✅ Single chat room (global)
- ✅ Username-based login (no password required for MVP)
- ✅ Real-time message broadcasting via WebSocket
- ✅ Message persistence in H2 database
- ✅ Message history on login
- ✅ Timestamp for each message
- ✅ User join/leave notifications
- ✅ Cross-platform desktop support (LibGDX)

## Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6+ (for backend)
- Gradle 7+ (included via wrapper for frontend)

### Running the Application

**Option 1: Run both services manually**

```bash
# Terminal 1 - Start backend
cd backend
mvn spring-boot:run

# Terminal 2 - Start frontend (in a new terminal)
cd frontend
./gradlew desktop:run
```

**Option 2: Run backend first, then multiple frontend clients**

```bash
# Terminal 1 - Start backend
cd backend && mvn spring-boot:run

# Terminal 2+ - Start as many frontend clients as you want
cd frontend && ./gradlew desktop:run
```

### Using the Chat Application

1. **Backend will start on** `http://localhost:8080`
2. **Frontend window will open** (800x600)
3. **Enter your username** (minimum 3 characters)
4. **Click "Login"** to enter the chat room
5. **Type your message** and click "Send" or press Enter
6. **Open multiple clients** to test real-time messaging

### Accessing H2 Console (Optional)

View the database contents:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:chatdb`
- Username: `sa`
- Password: (leave empty)

## Project Structure

```
accord-prototype/
├── MVP.md                          # Detailed MVP documentation
├── README.md                       # This file
├── backend/                        # Spring Boot backend
│   ├── pom.xml                    # Maven configuration
│   └── src/main/java/com/accord/
│       ├── AccordApplication.java
│       ├── config/                # WebSocket configuration
│       ├── controller/            # REST and WebSocket controllers
│       ├── model/                 # JPA entities
│       ├── repository/            # Data repositories
│       └── service/               # Business logic
└── frontend/                       # LibGDX frontend
    ├── build.gradle               # Root Gradle config
    ├── core/                      # Shared code
    │   └── src/com/accord/
    │       ├── AccordGame.java
    │       ├── screen/            # Login & Chat screens
    │       └── websocket/         # WebSocket client
    └── desktop/                   # Desktop launcher
        └── src/com/accord/desktop/
            └── DesktopLauncher.java
```

## Building from Source

### Backend

```bash
cd backend
mvn clean install
```

### Frontend

```bash
cd frontend
./gradlew build
```

## Configuration

### Backend (`backend/src/main/resources/application.properties`)

```properties
server.port=8080
spring.datasource.url=jdbc:h2:mem:chatdb
spring.h2.console.enabled=true
```

### Frontend

WebSocket endpoint is configured in `ChatScreen.java`:
```java
URI uri = new URI("ws://localhost:8080/ws");
```

To connect to a remote server, change `localhost` to your server's address.

## Development Roadmap

See [MVP.md](MVP.md) for the complete roadmap and architecture details.

### Next Steps
- Multiple chat rooms/channels
- User authentication (password-based)
- Private direct messages
- User online/offline status
- Message search and pagination
- PostgreSQL/MySQL support for production

## Documentation

- **[MVP.md](MVP.md)** - Complete MVP documentation including:
  - Architecture details
  - API documentation
  - Setup instructions
  - Feature roadmap
  - Troubleshooting guide

## Contributing

This is an MVP prototype. Contributions are welcome for:
- Bug fixes
- Performance improvements
- Documentation updates
- Feature implementations from the roadmap

## License

[To be determined]

## Support

For issues or questions, please open an issue on GitHub.

