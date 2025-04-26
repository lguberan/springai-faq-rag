# Spring AI FAQ RAG

# About the Project

This project is a **Retrieval-Augmented Generation (RAG) FAQ system** built with **Spring AI** (currently version
1.0.0-M7). It demonstrates the integration of:

- **Spring Boot 3.2.4 + Java 23** (Backend)
- **Spring AI** for OpenAI integration
- **pgvector + PostgreSQL** for vector storage and similarity search
- **React + Vite** (Frontend)
- **Nginx** for serving the frontend in production
- **Docker Compose** for orchestration

It is inspired by [log-analytics-faq-ai](https://github.com/lguberan/log-analytics-faq-ai), which uses Python RAG
libraries. This version replaces the Python backend with a **Java-native solution** using **Spring AI**.

## Architecture

![Architecture Diagram](docs/architecture.png)

### Components:

- **Frontend (React + Vite + Nginx)**: Displays FAQs, handles user questions, calls the backend REST API.
- **Backend (Spring Boot + Spring AI)**: Handles REST API requests, processes user questions via OpenAI, performs
  similarity search using pgvector in PostgreSQL.
- **PostgreSQL + pgvector**: Stores FAQs, embeddings, and supports semantic search.
- **OpenAI API**: Generates answers and embeddings for user queries.

---

## Getting Started

### Prerequisites

- **Docker & Docker Compose** installed
- **Java 23** and **Maven 3.9+** (for local development)
- **OpenAI API Key** see https://platform.openai.com/

### Prerequisites

- **[Docker](https://www.docker.com/products/docker-desktop/)** and *
  *[Docker Compose](https://docs.docker.com/compose/install/)** installed
- **[Java 23](https://jdk.java.net/23/)** and **[Maven 3.9+](https://maven.apache.org/)** (for local development)
- An **OpenAI API key** (you can get one at https://platform.openai.com/account/api-keys)

---

## Running with Docker Compose

```bash
# Clone the repository
git clone https://github.com/lguberan/springai-faq-rag.git
cd springai-faq-rag

# Set up environment variables
# Edit .env and add your OPENAI_API_KEY

# Build and start all services
docker-compose up --build
```

### Once all containers are running, open your browser and visit:

- **Frontend**: [http://localhost:5173](http://localhost:5173) (use admin/admin)
- **API doc. Swagger**:  [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **Backend API**: [http://localhost:8080/api/faq?validated=true](http://localhost:8080/api/faq?validated=true)
- **pgweb** web-based PostgreSQL client: http://localhost:8081”

---

## 🔒 Security

- Only authenticated users with role `ADMIN` can access the validation endpoints.
- CSRF and HTTP Basic are enabled for backend.
