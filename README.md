High-Level Architecture Layers


┌─────────────────────────────────────────┐
│         Presentation Layer              │
│    (REST Controllers/APIs)              │
└─────────────────────────────────────────┘
                   ↓
┌─────────────────────────────────────────┐
│         Service Layer                   │
│    (Business Logic)                     │
└─────────────────────────────────────────┘
                   ↓
┌─────────────────────────────────────────┐
│         Repository Layer                │
│    (Data Access - JPA/Hibernate)        │
└─────────────────────────────────────────┘
                   ↓
┌─────────────────────────────────────────┐
│         Database                        │
│    (MySQL/PostgreSQL)                   │
└─────────────────────────────────────────┘
2. Core Modules/Components

User Management

User registration/authentication
User profiles
Role management (Admin, Customer)


Payment Processing

Create payment transactions
Payment gateway integration
Payment status tracking


Transaction Management

Transaction history and payment history
Transaction details
Refund handling


Notification Service

Email notifications
Payment confirmations
Transaction alerts



3. Basic Entity Structure

User: id, username, email, password, role
Payment: id, userId, amount, currency, status, paymentMethod
Transaction: id, paymentId, transactionDate, status, description
PaymentMethod: id, userId, type (card/UPI/netbanking), details

4. Key Technologies

Backend: Spring Boot, Spring Data JPA, Spring Security
Database: MySQL/PostgreSQL
Payment Gateway: Stripe/Razorpay/PayPal (integration)
API Documentation: Swagger/OpenAPI
Authentication: JWT tokens

5. Basic Flow
User → Register/Login → Select Service → Initiate Payment 
→ Process Payment → Update Status → Send Confirmation


pom.xml - All necessary Maven dependencies
application.properties - PostgreSQL configuration
Complete User Module with:

Entity (User)
Repository (UserRepository)
Service (UserService)
Controller (User Controller)
DTOs (UserDTO, CreateUser Request)
Enum (UserRole)



🚀 How to Run:
Step 1: Setup PostgreSQL Database
sqlCREATE DATABASE payment_db;
Step 2: Update application.properties
Change username/password if needed:
propertiesspring.datasource.username=your_postgres_username
spring.datasource.password=your_postgres_password
Step 3: Run the Application
bashmvn clean install
mvn spring-boot:run
🧪 Test the APIs (Using Postman or cURL):
1. Create a User
bashPOST http://localhost:8080/api/users
Content-Type: application/json

{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "password123",
  "fullName": "John Doe",
  "phoneNumber": "1234567890"
}
2. Get All Users
bashGET http://localhost:8080/api/users
3. Get User by ID
bashGET http://localhost:8080/api/users/1
4. Get User by Username
bashGET http://localhost:8080/api/users/username/johndoe
5. Update User
bashPUT http://localhost:8080/api/users/1
Content-Type: application/json

{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "password123",
  "fullName": "John Doe Updated",
  "phoneNumber": "9876543210"
}
6. Delete User
bashDELETE http://localhost:8080/api/users/1
✅ Features:

CRUD operations for User
Validation (email format, required fields, size constraints)
Auto-generated timestamps (createdAt, updatedAt)
Unique constraints on username and email
User roles (ADMIN, CUSTOMER, MERCHANT)
-----------------------------------------------------------------------------
Complete User Module with:

Entity (User)
Repository (UserRepository)
Service (UserService)
Controller (UserController)
DTOs (UserDTO, CreateUserRequest)
Enum (UserRole)



🚀 How to Run:
Step 1: Setup PostgreSQL Database
sqlCREATE DATABASE payment_db;
Step 2: Update application.properties
Change username/password if needed:
propertiesspring.datasource.username=your_postgres_username
spring.datasource.password=your_postgres_password
Step 3: Run the Application
bashmvn clean install
mvn spring-boot:run
🧪 Test the APIs (Using Postman or cURL):
1. Create a User
bashPOST http://localhost:8080/api/users
Content-Type: application/json

{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "password123",
  "fullName": "John Doe",
  "phoneNumber": "1234567890"
}
2. Get All Users
bashGET http://localhost:8080/api/users
3. Get User by ID
bashGET http://localhost:8080/api/users/1
4. Get User by Username
bashGET http://localhost:8080/api/users/username/johndoe
5. Update User
bashPUT http://localhost:8080/api/users/1
Content-Type: application/json

{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "password123",
  "fullName": "John Doe Updated",
  "phoneNumber": "9876543210"
}
6. Delete User
bashDELETE http://localhost:8080/api/users/1
✅ Features:

CRUD operations for User
Validation (email format, required fields, size constraints)
Auto-generated timestamps (createdAt, updatedAt)
Unique constraints on username and email
User roles (ADMIN, CUSTOMER, MERCHANT)

------------------------------------------------------
Refund API Testing Guide
Base URL: http://localhost:8081/api/refunds

1️⃣ Create Refund
bashPOST http://localhost:8081/api/refunds
Content-Type: application/json

{
  "paymentId": 1,
  "userId": 1,
  "refundAmount": 50.00,
  "currency": "USD",
  "refundType": "PARTIAL_REFUND",
  "reason": "Product damaged during shipping",
  "description": "Customer reported damage on arrival. Refunding 50% of payment amount.",
  "initiatedBy": "customer_support",
  "refundFee": 2.00,
  "notes": "Customer provided photo evidence"
}
Response: Status 201 Created
json{
  "id": 1,
  "paymentId": 1,
  "userId": 1,
  "username": "johndoe",
  "refundAmount": 50.00,
  "currency": "USD",
  "status": "INITIATED",
  "refundType": "PARTIAL_REFUND",
  "refundReference": "REF-abc-123-xyz",
  "reason": "Product damaged during shipping",
  "refundFee": 2.00,
  "netRefundAmount": 48.00,
  "initiatedDate": "2024-01-15T10:30:00"
}

2️⃣ Get Refund by ID
bashGET http://localhost:8081/api/refunds/1

3️⃣ Get Refund by Reference
bashGET http://localhost:8081/api/refunds/reference/REF-abc-123-xyz

4️⃣ Get All Refunds
bashGET http://localhost:8081/api/refunds

5️⃣ Get Refunds by User ID
bashGET http://localhost:8081/api/refunds/user/1

6️⃣ Get Refunds by Payment ID
bashGET http://localhost:8081/api/refunds/payment/1

7️⃣ Get Refunds by Status
bashGET http://localhost:8081/api/refunds/status/INITIATED
Available Statuses:

INITIATED
PENDING_APPROVAL
APPROVED
REJECTED
PROCESSING
COMPLETED
FAILED
CANCELLED


8️⃣ Get Pending Approval Refunds
bashGET http://localhost:8081/api/refunds/pending-approval

9️⃣ Get Recent Refunds by User (Last 10)
bashGET http://localhost:8081/api/refunds/user/1/recent

🔟 Approve Refund
bashPUT http://localhost:8081/api/refunds/1/approve
Content-Type: application/json

{
  "approvedBy": "admin_john"
}

1️⃣1️⃣ Reject Refund
bashPUT http://localhost:8081/api/refunds/1/reject
Content-Type: application/json

{
  "rejectionReason": "Refund request does not meet policy requirements. Product was used beyond return period."
}

1️⃣2️⃣ Process Refund (Send to Gateway)
bashPUT http://localhost:8081/api/refunds/1/process
Content-Type: application/json

{
  "gatewayRefundId": "STRIPE-REFUND-98765"
}

1️⃣3️⃣ Complete Refund
bashPUT http://localhost:8081/api/refunds/1/complete
This also updates the payment status to REFUNDED

1️⃣4️⃣ Fail Refund
bashPUT http://localhost:8081/api/refunds/1/fail
Content-Type: application/json

{
  "failureReason": "Gateway processing error - Card expired"
}

1️⃣5️⃣ Cancel Refund
bashPUT http://localhost:8081/api/refunds/1/cancel

1️⃣6️⃣ Get Total Refund Amount by User and Status (Analytics)
bashGET http://localhost:8081/api/refunds/user/1/total?status=COMPLETED
Response: 150.50 (BigDecimal)

1️⃣7️⃣ Count Refunds by Status (Analytics)
bashGET http://localhost:8081/api/refunds/count?status=PENDING_APPROVAL
Response: 5 (Long)

🔄 Complete Refund Lifecycle Flow
Scenario: Customer requests a refund
bash# Step 1: Create a refund request
POST http://localhost:8081/api/refunds
{
  "paymentId": 1,
  "userId": 1,
  "refundAmount": 100.00,
  "currency": "USD",
  "refundType": "FULL_REFUND",
  "reason": "Changed mind",
  "initiatedBy": "customer"
}
# Status: INITIATED

# Step 2: Admin approves the refund
PUT http://localhost:8081/api/refunds/1/approve
{
  "approvedBy": "admin_sarah"
}
# Status: APPROVED

# Step 3: Process refund through payment gateway
PUT http://localhost:8081/api/refunds/1/process
{
  "gatewayRefundId": "STRIPE-REF-12345"
}
# Status: PROCESSING

# Step 4: Complete the refund
PUT http://localhost:8081/api/refunds/1/complete
# Status: COMPLETED
# Payment status also changes to: REFUNDED

📊 Different Refund Types Examples
1. Full Refund
json{
  "paymentId": 1,
  "userId": 1,
  "refundAmount": 100.00,
  "refundType": "FULL_REFUND",
  "reason": "Order cancelled by customer"
}
2. Partial Refund
json{
  "paymentId": 1,
  "userId": 1,
  "refundAmount": 30.00,
  "refundType": "PARTIAL_REFUND",
  "reason": "One item out of three was damaged"
}
3. Chargeback
json{
  "paymentId": 1,
  "userId": 1,
  "refundAmount": 100.00,
  "refundType": "CHARGEBACK",
  "reason": "Customer disputed charge with bank"
}
4. Reversal
json{
  "paymentId": 1,
  "userId": 1,
  "refundAmount": 100.00,
  "refundType": "REVERSAL",
  "reason": "Duplicate payment detected"
}

✅ Testing Checklist

 Create refund (INITIATED)
 Get refund by ID
 Get refund by reference
 Approve refund (APPROVED)
 Reject refund (REJECTED)
 Process refund (PROCESSING)
 Complete refund (COMPLETED) - Check payment status changes to REFUNDED
 Fail refund (FAILED)
 Cancel refund (CANCELLED)
 Get all refunds by user
 Get pending approval refunds
 Get total refund amount
 Count refunds by status


🎯 Key Points

Refund Amount Validation: Cannot exceed payment amount
Status Flow: INITIATED → APPROVED → PROCESSING → COMPLETED
Payment Update: Completing a refund updates payment status to REFUNDED
Net Amount: Calculated as refundAmount - refundFee
Cannot Cancel: Once refund is COMPLETED, it cannot be cancelled

Your complete Payment Management System now has 4 entities:

✅ User
✅ Payment
✅ Transaction
✅ Refund

All working with proper relationships and business logic! 🚀
