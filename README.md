# Employee Management Web Application (Spring Boot + Vanilla JS)

A beginner-friendly **Employee Management** web app with full CRUD (Create, Read, Update, Delete).

- **Backend:** Spring Boot (Java 17), Spring Web, Spring Data JPA, H2
- **Frontend:** HTML + CSS + Vanilla JavaScript (no frameworks)
- **API:** REST endpoints under `/api/employees`

> Repo structure:
>
> - `backend/` Spring Boot app (Maven)
> - `frontend/` static site (GitHub Pages)

---

## 1) Features

### Employee fields
- `id` (auto-generated)
- `firstName`
- `lastName`
- `email`
- `department`
- `salary`

### Frontend
- Add employee form
- Employee table
- Edit / Delete actions
- Search by name (first/last)
- Success / error messages
- Responsive, clean styling

### Backend
- CRUD REST APIs
- Validation + exception handling
- CORS enabled for frontend
- H2 database (in-memory by default)

---

## 2) API Endpoints

- `GET /api/employees`
- `GET /api/employees/{id}`
- `POST /api/employees`
- `PUT /api/employees/{id}`
- `DELETE /api/employees/{id}`

---

## 3) Run locally

### Backend

Requirements:
- Java 17
- Maven 3.9+

From repo root:

```bash
cd backend
mvn spring-boot:run
```

Backend runs at:
- `http://localhost:8080`

H2 console:
- `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:employeesdb`
- User: `sa`
- Password: (empty)

### Frontend

You can open `frontend/index.html` directly, but **fetch calls may be blocked** by browser security when using the `file://` protocol.

Recommended: run a tiny static server:

```bash
cd frontend
# if you have python installed
python -m http.server 5500
```

Then open:
- `http://localhost:5500`

---

## 4) Configure frontend API base URL

Frontend reads the backend URL from:

- `frontend/config.js`

For local dev it defaults to:

```js
const API_BASE_URL = "http://localhost:8080/api";
```

When deployed, set it to your Render/Railway backend URL.

---

## 5) Deploy

### 5.1 Deploy frontend to GitHub Pages

1. Go to **Settings → Pages**
2. Build and deployment: **Deploy from a branch**
3. Branch: `main`
4. Folder: `/frontend`

Your site will be available at:
- `https://<your-username>.github.io/<repo-name>/`

### 5.2 Deploy backend to Render

**Option A (Docker - recommended for simplicity):**

1. Create a new **Web Service** in Render
2. Connect your GitHub repo
3. Root directory: `backend`
4. Build command:

```bash
mvn clean package -DskipTests
```

5. Start command:

```bash
java -jar target/employee-management-0.0.1-SNAPSHOT.jar
```

6. Set environment variable (optional):
- `PORT` (Render provides this automatically; Spring Boot will read it via `server.port` if configured)

This project already includes `server.port=${PORT:8080}`.

---

## 6) Notes

- This project is designed to be beginner-friendly and well-commented.
- You can switch to a persistent H2 file DB or add MySQL later.

---

## Links

- GitHub repo: (this repository)
- Live site (GitHub Pages): after enabling Pages, it will be
  `https://eyahmed_ejadasa.github.io/Ai-Adoption/`
