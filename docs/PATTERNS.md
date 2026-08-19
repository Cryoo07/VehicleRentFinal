# Vehicle Renting System - Design Patterns Map

## Creational Patterns

### Singleton (`config/SystemConfig.java`)
- **What:** Only one instance of system configuration exists.
- **Why:** Prevents conflicting DB/settings across the app.
- **Viva answer:** "We use double-checked locking in getInstance() to ensure thread-safe lazy initialization."

### Factory Method (`notification/`)
- **What:** NotificationCreator subclasses decide which Notification to create.
- **Why:** Adding a new channel (e.g., WhatsApp) only needs a new Creator + Notification pair.
- **Viva answer:** "Client calls notifyCustomer() without knowing the concrete notification class."

### Builder (`vehicle/VehicleBuilder.java`)
- **What:** Fluent API to build Car/Bike with optional features.
- **Why:** Avoids telescoping constructors with many boolean parameters.
- **Viva answer:** "build() validates and returns immutable Vehicle products."

## Structural Patterns

### Adapter (`payment/PaymentGatewayAdapter.java`)
- **What:** Wraps ThirdPartyPaymentApi to match PaymentGateway interface.
- **Why:** External APIs have incompatible method signatures.
- **Viva answer:** "Adapter translates chargeCustomer(userRef, currency, amountStr) to processTransaction(customerId, amount)."

### Facade (`rental/RentalServiceFacade.java`)
- **What:** Single entry point for rent/cancel/return.
- **Why:** Hides complexity of payment, commands, state, and notifications.
- **Viva answer:** "Customer code calls rentVehicle() instead of orchestrating 5 subsystems."

### Proxy (`security/ReportServiceProxy.java`)
- **What:** Same interface as ReportService but checks admin role first.
- **Why:** Security without polluting ReportServiceImpl.
- **Viva answer:** "Proxy implements ReportService and delegates to real service after authorization."

### Decorator (`vehicle/*Decorator.java`)
- **What:** Wraps Vehicle to add GPS/Insurance/Child Seat pricing.
- **Why:** Open/Closed — extend features without modifying Car/Bike.
- **Viva answer:** "Decorators stack: new InsuranceDecorator(new GpsDecorator(car))."

## Behavioral Patterns

### Strategy (`payment/*Payment.java`)
- **What:** PaymentContext delegates to CreditCard, PayPal, or MobileWallet.
- **Why:** Switch payment algorithm at runtime.
- **Viva answer:** "Strategy encapsulates varying payment algorithms behind one interface."

### Observer (`observer/`)
- **What:** VehicleStatusSubject notifies CustomerNotifier observers.
- **Why:** Loose coupling between status changes and notifications.
- **Viva answer:** "Subject maintains observer list and calls update() on status change."

### Command (`rental/command/`)
- **What:** RentCommand, CancelCommand, ReturnCommand as objects.
- **Why:** Supports execute/undo and command history via BookingInvoker.
- **Viva answer:** "Commands encapsulate requests as objects with execute() and undo()."

### State (`rental/state/`)
- **What:** AvailableState, BookedState, UnderMaintenanceState.
- **Why:** State-specific behavior without large if-else chains.
- **Viva answer:** "Context delegates rent()/returnVehicle() to current state object."

## Core Features

| Feature            | Implementation                          |
|--------------------|-----------------------------------------|
| User Management    | UserService (register, login, roles)    |
| Vehicle Management | VehicleManager + Builder                |
| Reservation        | RentalServiceFacade + Command           |
| Notifications      | Factory Method (Email, SMS, Push)       |
| Reports            | ReportServiceImpl + Proxy               |
| Status Tracking    | State pattern + Observer                |
| Security           | SecurityContext + Proxy (admin access)  |
