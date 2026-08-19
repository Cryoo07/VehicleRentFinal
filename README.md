# Vehicle Renting System

A complete Java application demonstrating **Creational**, **Structural**, and **Behavioral** design patterns in a vehicle rental domain.

## Project Structure

```
src/main/java/com/vehiclerent/
├── config/          → Singleton (SystemConfig)
├── notification/    → Factory Method (Email, SMS, Push)
├── vehicle/         → Builder + Decorator (Car, Bike, add-ons)
├── payment/         → Strategy + Adapter
├── rental/          → Facade + Command + State
├── observer/        → Observer (status notifications)
├── security/        → Proxy (admin report access)
├── reports/         → Report implementations
├── user/            → User & customer management
├── util/            → Console output formatting
└── Main.java        → Demo application
```

## Design Patterns

| Category    | Pattern        | Location                          | Purpose                                      |
|-------------|----------------|-----------------------------------|----------------------------------------------|
| Creational  | Singleton      | `config/SystemConfig`             | Single system configuration instance         |
| Creational  | Factory Method | `notification/*`                  | Create Email/SMS/Push notifications          |
| Creational  | Builder        | `vehicle/VehicleBuilder`          | Construct Car/Bike with optional features    |
| Structural  | Adapter        | `payment/PaymentGatewayAdapter`   | Integrate third-party payment API            |
| Structural  | Facade         | `rental/RentalServiceFacade`      | Simplify rental booking workflow             |
| Structural  | Proxy          | `security/ReportServiceProxy`     | Restrict report access to admins             |
| Structural  | Decorator      | `vehicle/*Decorator`              | Add GPS, Insurance, Child Seat dynamically   |
| Behavioral  | Strategy       | `payment/*Payment`                | Interchangeable payment methods              |
| Behavioral  | Observer       | `observer/*`                      | Notify customers on status changes           |
| Behavioral  | Command        | `rental/command/*`                | Encapsulate Rent/Cancel/Return operations    |
| Behavioral  | State          | `rental/state/*`                  | Vehicle availability state machine           |

## Requirements

- Java 17+
- Maven 3.6+

## Build & Run

```bash
# Compile
mvn compile

# Run demo application
mvn exec:java -Dexec.mainClass="com.vehiclerent.Main"

# Or after packaging
mvn package
java -jar target/vehicle-rent-1.0.0.jar

# Run all tests
mvn test
```

## Default Credentials

| Role     | Username | Password  |
|----------|----------|-----------|
| Admin    | admin    | admin123  |
| Customer | (register via demo) | — |

## UML Class Diagram

PlantUML source: [`docs/uml/class-diagram.puml`](docs/uml/class-diagram.puml)

Render with [PlantUML](https://plantuml.com/) or any PlantUML-compatible plugin in IntelliJ IDEA.

## Screenshots for Report

Run the main application and capture console output sections:

1. Singleton – System Configuration
2. Factory Method – Notifications
3. Builder – Vehicle Construction
4. Decorator – Add-ons
5. Strategy – Payment Processing
6. Adapter – Payment Gateway
7. Observer – Status Notification
8. State – Vehicle Status
9. Command – Rent/Cancel/Return
10. Facade – Rental Service
11. Proxy – Secure Report Access

## Test Coverage

JUnit 5 tests in `src/test/java/com/vehiclerent/`:

- `config/SystemConfigTest` – Singleton
- `notification/NotificationFactoryTest` – Factory Method
- `vehicle/VehicleBuilderDecoratorTest` – Builder & Decorator
- `payment/PaymentPatternTest` – Strategy & Adapter
- `rental/state/VehicleStateTest` – State
- `rental/command/BookingCommandTest` – Command
- `observer/ObserverPatternTest` – Observer
- `security/ReportServiceProxyTest` – Proxy
- `rental/RentalWorkflowTest` – End-to-end workflow
- `user/UserServiceTest` – User management

