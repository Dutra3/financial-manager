# Financial Manager

Personal finance SaaS-style app: manage accounts, transactions, budgets, goals, and an investment wallet with live B3 quotes. Built with React + TypeScript and Spring Boot, with a REST API under `/api`.

## Features

### Dashboard (Home)
- Personalized greeting with the authenticated user's name
- Monthly income/expense cards, current balance, currency conversion (USD/BRL/EUR)
- Visual reports: expenses by category (pie), income vs expenses over months (line)
- Goal progress and budget alerts

### Transactions
- Create, edit, delete income and expenses with categories and notes
- Optional link to an account; transactions affect that account's balance
- Recurring series (weekly/monthly/yearly, up to 120 occurrences) with one-click "delete entire series"
- Paginated list API; CSV export

### Accounts & Transfers
- Multiple accounts (checking, savings, cash, investment) with per-account current balance
- Atomic transfers between your accounts (debit + credit legs)

### Wallet (Investments)
- Unified B3 asset catalog (stocks, FIIs, ETFs) — buy, sell, dividend transactions
- Positions with quantity, weighted average price, current value, P&L and P&L%
- Realized P&L from sells, dividend tracking
- Allocation pie chart by asset class and invested-capital history chart
- Live quotes via brapi.dev (manual "Refresh Prices" + scheduled every 30 min)

### Profile & Preferences
- Personal and financial info (salary, payday, initial balance)
- Persisted currency and theme preferences
- Goals with progress, monthly budgets by category, budget alerts
- Net Worth: accounts balance + investments value in one number

### Auth & Security
- JWT access + refresh token rotation, logout revocation
- Google OAuth login and email/password sign up (BCrypt)
- Forgot/reset password flow (reset token logged until an email provider is plugged in)
- User identity always derived from the JWT — no client-supplied user ids
- Password strength rules enforced server-side; auth rate limiting (20 req/min/IP)

## Tech Stack

| Layer | Tech |
|---|---|
| Frontend | React 18, TypeScript, Vite, MUI 5, React Query, Axios, Recharts, date-fns, Vitest |
| Backend | Java 17, Spring Boot 3.3, Spring Security (JWT/JJWT), Spring Data JPA |
| DB | PostgreSQL, Flyway migrations (H2 for tests) |
| Ops | Docker multi-stage builds, docker-compose, GitHub Actions CI, Actuator |

## Running

### Local dev

```bash
# backend (needs Postgres at localhost:5432)
cd financial-manager-backend
DB_URL=jdbc:postgresql://localhost:5432/financial_manager \
DB_USERNAME=you DB_PASSWORD=secret JWT_SECRET=dev-secret-32chars-minimum \
mvn spring-boot:run

# frontend
cd financial-manager-frontend
yarn install && yarn dev   # http://localhost:5173, API at http://localhost:8080/api
```

### Docker (whole stack)

```bash
export JWT_SECRET=your-32-char-minimum-secret
docker compose up --build   # frontend :3000, backend :8080, postgres inside
```

### Environment variables

| Var | Used by | Notes |
|---|---|---|
| `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` | backend | Postgres connection |
| `JWT_SECRET` | backend | required, min 32 chars |
| `BRAPI_TOKEN` | backend | optional, raises brapi.dev rate limit |
| `VITE_API_BASE` | frontend build | defaults to `http://localhost:8080/api` |

### Tests & CI

```bash
cd financial-manager-backend && mvn test    # 200+ tests (JUnit + MockMvc + Testcontainers-free H2)
cd financial-manager-frontend && yarn test  # vitest + testing-library
```

CI runs `mvn verify` and `yarn test && yarn build` on every push/PR (`.github/workflows/ci.yml`).

## API map (everything under `/api`)

| Area | Endpoints |
|---|---|
| Auth | `POST /auth/login`, `/auth/google`, `/auth/refresh`, `/auth/logout`, `/auth/forgot-password`, `/auth/reset-password` |
| Users | `POST /users` |
| Transactions | `GET/POST/PUT/DELETE /transactions`, `GET /transactions/{userId}?page&size&sort`, `POST /transactions/recurring`, `DELETE /transactions/recurring/{groupId}` |
| Accounts | `GET/POST/DELETE /accounts`, `POST /transfers` |
| Profiles | `GET /profiles/me`, `GET/POST/PUT/DELETE /profiles` |
| Goals & budgets | `/goals`, `/goals/balance/{userId}`, `/budgets` |
| Wallet | `/assets`, `POST /assets/refresh-prices`, `/asset-transactions`, `/portfolio/{userId}`, `/portfolio/{userId}/allocation`, `/portfolio/{userId}/history` |
| Dashboards | `/dashboards/debits|credits/{id}`, `/dashboards/net-worth/{userId}` |
| Other | `/categories`, `/export`, `/actuator/health` |
