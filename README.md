# Overview
Welcome to the Payment API, a Java-based solution for processing multiple payments modes and retrieving payment information.
Card payment is currently available, all other payments are still under modification and build.

## Features
- **Make Payment:** Securely process payments (allows only card for now)
- **Get Payments:** Retrieve payment information based on criteria

## Usage
# Getting Started
1. **Clone Repository:**
   git clone https://github.com/e-Numa/payment-api.git

3. **Build and Run:**
   cd payment-api
   mvn clean install
   java -jar target/payment-api.jar

4. **Endpoint:**
   *Using postman, enter the following API request details:*

   *Make Payment:*
     POST http://localhost:3944/api/payments/make-payment
     Content-Type: application/json
     {
     "amount": 12350.00,
     "cardNumber": "1234567890123456",
     "expiryDate": "12/25",
     "cvv": 123,
     "deviceType": "Mobile",
     "paymentMode": "CARD"
     }

   *Get Payments:*
     GET http://localhost:3944/api/payments/get-payments



