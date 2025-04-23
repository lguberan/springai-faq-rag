# Frontend Setup Guide (Vite.js + React)

## 🚀 1. Install the Environment

After cloning the project:

```bash
cd frontend
npm install
```

This installs **Node.js dependencies** (Vite, React, etc.).

> If you don't have Node.js installed, use **nvm**:

```bash
nvm install 20
nvm use 20
```

---

## 🛠️ 2. Build for Production

To generate optimized static files (in the `dist/` folder):

```bash
npm run build
```

This uses **Vite** to compile and optimize the app for production.

---

## 🔍 3. Run Locally (Development Mode)

For hot-reloading and local development:

```bash
npm run dev
```

- This starts the **Vite dev server** (usually at `localhost:5173`).
- **API requests** to `/api` are proxied to `localhost:8080` (backend) via `vite.config.ts`.

---

## 🐳 4. Deploy with Docker

The `Dockerfile` builds and serves the frontend with **Nginx**.

### Docker standalone (without Compose):

```bash
docker build -t my-frontend .

docker run -d -p 5173:80 my-frontend
```

- Access the app at **localhost:5173**.

---

### Docker Compose (recommended):

Example in `docker-compose.yml`:

```yaml
frontend:
  build: ./frontend
  ports:
    - "5173:80"
```

Run:

```bash
docker-compose up --build
```

---

## ⚡ Summary of Key Commands

| Action                    | Command                             |
|---------------------------|-------------------------------------|
| Install dependencies      | `npm install`                       |
| Start in development mode | `npm run dev`                       |
| Build for production      | `npm run build`                     |
| Docker build (standalone) | `docker build -t my-frontend .`     |
| Docker run (standalone)   | `docker run -p 3000:80 my-frontend` |
| Docker Compose (global)   | `docker-compose up --build`         |
