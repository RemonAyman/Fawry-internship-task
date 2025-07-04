# Fawry Internship Task - Java Console Project

This project is a simple console-based shopping system built in Java. It simulates a real-world shopping experience where users can select products, add them to a cart, and proceed to checkout with basic shipping and expiration handling.

## Features

- Product list with expirable and/or shippable items
- Cart system with quantity and stock validation
- Customer input with name and balance checks
- Handles product expiry dates
- Shipping logic with weight calculation
- Receipt with subtotal, shipping, and final amount

## Product Types

- **Product**: base class with name, price, and quantity
- **ExpirableProduct**: inherits Product and adds expiry date check
- **ShippableProduct**: inherits Product, adds weight and shipping logic
- **ExpirableShippableProduct**: combines expiry and shipping features

## Customer

- Each customer has a name and a balance
- Name must contain letters only
- Balance must be a valid non-negative number

## Cart

- You can add multiple products with specific quantities
- Checks for stock availability
- Supports clearing the cart and looping through items

## ShippingService

- Takes a list of shippable items
- Groups and shows total weights
- Adds a flat shipping fee (30) if applicable

## Main Flow

1. User enters their name and balance
2. Product list is displayed with expiry and weight info
3. User selects products and adds to cart
4. On checkout, expiry dates are checked and balance is validated
5. Receipt is shown with updated stock

## Assumptions

- Today's date is set to `2025-07-04`
- All prices are in Egyptian Pounds (EGP)
- Shipping applies only if any product is shippable

---

### Code Structure

- `Main.java` – runs the main program logic
- `Product.java`, `ExpirableProduct.java`, `ShippableProduct.java`, `ExpirableShippableProduct.java`
- `Customer.java`, `Cart.java`, `Shippable.java`, `ShippingService.java`

### Built With

- Java (JDK 17+)
- No external libraries required
