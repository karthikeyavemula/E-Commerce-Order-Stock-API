# 🛒 E-Commerce Order & Stock API

A production-ready, high-performance Java backend microservice built to handle real-time online checkout validation and multi-zone warehouse stock tracking. The engine is engineered with strict decoupling principles, concurrency safety guards, and optimized low-level database operations.

## 🏛️ System Architecture Flow
[HTTP JSON Request Body] ──► REST Controller ──► Service Layer (Transaction Guard)
                                                            │
                                            ┌───────────────┴───────────────┐
                                            ▼                               ▼
                                   Spring Data JPA                   Native SQL Queries 
                                 (Standard CRUD Saves)             (Direct JDBC Megaphone)
                                            │                               │
                                            └───────────────┬───────────────┘
                                                            ▼
                                                   Target Active DB Engine

---

## 📂 File-by-File Technical Blueprint

### 1. `Application.java` (The Ignition Switch)
* **Role:** Serves as the master entry point and control center of the ecosystem.
* **Key Detail:** Leverages `@SpringBootApplication` to trigger automated component scans and boot the embedded web container. Implements a startup context interceptor validation routine to confirm successful active server and driver health.

### 2. `OrderController.java` (The Web Entry Gate)
* **Role:** Establishes the versioned HTTP REST interface (`/api/v1/orders`).
* **Key Detail:** Listens exclusively for incoming `POST` data traffic. Uses modern `@RequestBody` mapping to bind incoming client streams directly into an immutable Java record frame, keeping the endpoint lightweight and secure.

### 3. `OrderRequest.java` (The Secure Carrier)
* **Role:** Acts as the unalterable data transfer block (DTO) for web inputs.
* **Key Detail:** Programmed as a native **Java Record** rather than a class, achieving strict data immutability for `email`, `sku`, and `quantity` variables traveling over the network layer.

### 4. `OrderService.java` (The Core Business Logic Brain)
* **Role:** Orchestrates the calculation decisions, validation checks, and transaction limits.
* **Key Detail:** Fully implemented with **Constructor Injection** and **Zero `new` keyword expressions** by extracting new product rows dynamically from the `ApplicationContext` container box. Guarded by a strict **`@Transactional` safety shield** to guarantee atomic rollback protection during high-traffic checkouts.

### 5. `OrderRepository.java` (The Database Megaphone Bridge)
* **Role:** Manages high-speed direct communication loops to the persistent storage hardware.
* **Key Detail:** Extends `JpaRepository` for standard CRUD actions, but implements custom **Native SQL Aggregate Queries (`SUM`, `MAX`)** with explicit placeholder param binding (`nativeQuery = true`). This bypasses ORM conversion overhead, pushing logic directly to the JDBC driver layer.

### 6. `WarehouseInventory.java` (The Supply Shelf Entity Model)
* **Role:** Maps the physical warehouse data schema blueprint.
* **Key Detail:** Annotated with JPA `@Entity` markers to mirror the `warehouse_inventory` database table. Tracks real-world logistics columns including strict integer stock balances (`stockquantity`), location tracks (`warehousezone`), and unit cost metrics.

### 7. `ProductOrder.java` (The Customer Receipt Entity Model)
* **Role:** Maps the transaction history sales ledger logging schema blueprint.
* **Key Detail:** Automatically records order details, final amounts, and checkout status outcomes (`FullFilled` / `Failed`). Uses **`@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")`** to provide clean, crisp, human-readable timestamps to downstream frontend services.

### 8. `application.yml` (The Environmental Switchboard)
* **Role:** Centralizes structural application parameters and connection details.
* **Key Detail:** Configures the live MySQL connection configuration maps (`mart` schema integration) with automatic entity table assembly properties enabled.

---

## 🛠️ Technology Stack
* **Language Core:** Java 17+ (Records, Native Immutability)
* **Framework Layer:** Spring Boot, Spring Data JPA
* **Database Pipeline:** Native MySQL Engine, JDBC Channels
* **API Testing Engine:** Postman REST Clients

## 🗄️ Database Seeding Script (MySQL Workbench)
Execute the following setup commands inside your database console client editor to populate your inventory shelves before firing API traffic:

```sql
USE mart;

INSERT INTO warehouse_inventory (id, skucode, stockquantity, warehouse_zone, unit_price) 
VALUES (1, 'LAPTOP-M4', 50, 'Zone-Alpha', 149999.00);

INSERT INTO warehouse_inventory (id, skucode, stockquantity, warehouse_zone, unit_price) 
VALUES (2, 'PHONE-17', 15, 'Zone-Beta', 99999.00);
```

## 🌐 Sample Production Testing Endpoints

### 🟢 Test Case 1: Transaction Fulfilled (Stock Available)
* **Endpoint:** `POST http://localhost:8080/api/v1/orders`
* **JSON Request Body Payload:**
```json
{
  "email": "karthikeya@test.com",
  "sku": "LAPTOP-M4",
  "quantity": 2
}
```
* **API JSON Response Metadata:**
```json
{
  "id": 1,
  "customerEmail": "karthikeya@test.com",
  "skuCode": "LAPTOP-M4",
  "orderQuantity": 2,
  "totalAmount": 299998.0,
  "orderStatus": "FullFilled",
  "createdAt": "2026-10-09 12:05:32"
}
```

### ❌ Test Case 2: Stock Deficit Protection (Stock Insufficient)
* **Endpoint:** `POST http://localhost:8080/api/v1/orders`
* **JSON Request Body Payload:**
```json
{
  "email": "hacker@test.com",
  "sku": "LAPTOP-M4",
  "quantity": 100
}
```
* **API JSON Response Metadata:**
```json
{
  "id": 2,
  "customerEmail": "hacker@test.com",
  "skuCode": "LAPTOP-M4",
  "orderQuantity": 100,
  "totalAmount": 14999900.0,
  "orderStatus": "Failed",
  "createdAt": "2026-10-09 12:06:15"
}
```
