# Overview
Welcome to the Payment API, a Java-based solution for processing card payments and retrieving payment information.

## Features
- **Make Payment:** Securely process card payments.
- **Get Payments:** Retrieve payment information based on criteria.

## Usage
# Getting Started
1. **Clone Repository:**
   ```bash
   git clone https://github.com/e-Numa/payment-api.git

2. **Build and Run:**
   cd payment-api
   mvn clean install
   java -jar target/payment-api.jar

3. **Endpoint:**
   Using postman:
   **Make Payment:**
   POST http://localhost:3944/api/payments/make-payment
   Content-Type: application/json

{
  "amount": 50.00,
  "cardNumber": "1234567890123456",
  "expiryDate": "12/25",
  "cvv": 123,
  "deviceType": "Mobile"
}

   **Get Payments:**
    GET http://localhost:3944/api/payments/get-payments



