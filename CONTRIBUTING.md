# Contributing to Spring AI FAQ RAG

We welcome contributions to this project! Please follow these guidelines to ensure a smooth contribution process.

## Getting Started

1. Fork the repository
2. Clone your fork: `git clone https://github.com/your-username/springai-faq-rag.git`
3. Create a feature branch: `git checkout -b feature/your-feature-name`

## Development Setup

### Prerequisites
- Java 17 or higher
- Maven 3.9+
- Node.js 20+ and npm
- Docker and Docker Compose
- OpenAI API key

### Local Development
1. Set environment variables:
   ```bash
   export OPENAI_API_KEY=your_api_key_here
   export ADMIN_USERNAME=admin
   export ADMIN_PASSWORD=secure_password
   ```

2. Start PostgreSQL:
   ```bash
   docker run -d \
     --name postgres-pgvector \
     -e POSTGRES_PASSWORD=postgres \
     -e POSTGRES_DB=postgres \
     -p 5432:5432 \
     pgvector/pgvector:pg17
   ```

3. Run backend:
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

4. Run frontend:
   ```bash
   cd frontend
   npm install
   npm run dev
   ```

## Code Standards

### Backend (Java)
- Follow Google Java Style Guide
- Use Lombok for reducing boilerplate code
- Write comprehensive unit tests
- Use proper logging with SLF4J
- Handle exceptions appropriately

### Frontend (TypeScript/React)
- Use TypeScript for type safety
- Follow ESLint configuration
- Use Chakra UI for consistent styling
- Write functional components with hooks

## Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Linting
```bash
cd frontend
npm run lint
```

## Building

### Full Build
```bash
./mvnw clean install
```

### Frontend Only
```bash
cd frontend
npm run build
```

## Submitting Changes

1. Ensure all tests pass
2. Run linting and fix any issues
3. Write clear, descriptive commit messages
4. Push to your fork: `git push origin feature/your-feature-name`
5. Create a Pull Request with:
   - Clear description of changes
   - References to related issues
   - Screenshots for UI changes

## Code Review Process

1. All submissions require review
2. CI checks must pass
3. At least one maintainer approval required
4. No merge conflicts

## Reporting Issues

- Use GitHub Issues for bug reports and feature requests
- Include steps to reproduce for bugs
- Provide clear acceptance criteria for features
- Include relevant logs and screenshots

## Questions?

Feel free to open a GitHub Issue for questions about contributing.