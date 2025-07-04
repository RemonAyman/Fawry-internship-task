import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Create products
        Product romyOld = new ExpirableProduct("Rommy Cheese Old", 250, 100, "2025-06-01");
        Product romyMedium = new ExpirableProduct("Rommy Cheese Medium", 220, 100, "2025-08-01");
        Product romyNew = new ExpirableProduct("Rommy Cheese New", 200, 100, "2025-12-01");
        Product cottageCheese = new ExpirableShippableProduct("Cottage Cheese", 90, 100, "2025-12-01", 0.5);
        Product processedCheese = new Product("Processed Cheese", 60, 100);
        Product biscuits = new ExpirableProduct("Biscuits", 20, 100, "2025-05-30");
        Product tv = new ShippableProduct("TV", 8000, 50, 10.0);
        Product scratchCard = new Product("Mobile Scratch Card", 50, 200);

        List<Product> products = Arrays.asList(romyOld, romyMedium, romyNew, cottageCheese, processedCheese, biscuits, tv, scratchCard);

        // Get customer name (letters only)
        String customerName = "";
        while (true) {
            System.out.print("Enter your name (letters only): ");
            customerName = scanner.nextLine();
            if (customerName.matches("[a-zA-Z ]+")) {
                break;
            } else {
                System.out.println("Invalid name. Please use letters only.");
            }
        }

        // Get balance (numbers only)
        double balance = 0;
        while (true) {
            System.out.print("Enter your balance (numbers only): ");
            String balanceInput = scanner.nextLine();
            try {
                balance = Double.parseDouble(balanceInput);
                if (balance < 0) {
                    System.out.println("Balance must be positive.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid balance. Please enter numbers only.");
            }
        }
        Customer customer = new Customer(customerName, balance);
        Cart cart = new Cart();
        final double SHIPPING_FEES = 30;
        String today = "2025-07-04";

        // Show products
        System.out.println("Available products:");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.printf("%d. %s - Price: %.2f, Qty: %d%s%s\n", i+1, p.getName(), p.getPrice(), p.getQuantity(),
                (p instanceof ExpirableProduct ? ", Expiry: " + ((ExpirableProduct)p).getExpiryDate() : ""),
                (p instanceof Shippable ? ", Weight: " + ((Shippable)p).getWeight() + "kg" : ""));
        }

        // User adds products to cart
        while (true) {
            int prodNum = -1;
            while (true) {
                System.out.print("Enter product number to add to cart (0 to finish): ");
                String prodInput = scanner.nextLine();
                try {
                    prodNum = Integer.parseInt(prodInput);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }
            if (prodNum == 0) break;
            if (prodNum < 1 || prodNum > products.size()) {
                System.out.println("Invalid product number.");
                continue;
            }
            Product selected = products.get(prodNum - 1);
            int qty = -1;
            while (true) {
                System.out.print("Enter quantity: ");
                String qtyInput = scanner.nextLine();
                try {
                    qty = Integer.parseInt(qtyInput);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }
            if (!cart.addProduct(selected, qty)) {
                System.out.println("Invalid quantity or not enough stock.");
            } else {
                System.out.println("Added to cart.");
            }
        }

        // Get shipping address (any input allowed)
        System.out.print("Enter your shipping address: ");
        String address = scanner.nextLine();

        // Checkout logic
        if (cart.isEmpty()) {
            System.out.println("Error: Cart is empty.");
            return;
        }

        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            if (p instanceof ExpirableProduct) {
                ExpirableProduct ep = (ExpirableProduct) p;
                if (ep.isExpired(today)) {
                    System.out.println("Error: Product '" + p.getName() + "' is expired.");
                    return;
                }
            }
            if (qty > p.getQuantity()) {
                System.out.println("Error: Product '" + p.getName() + "' is out of stock.");
                return;
            }
        }

        double subtotal = 0;
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            subtotal += entry.getKey().getPrice() * entry.getValue();
        }

        List<Shippable> toShip = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            if (p instanceof Shippable) {
                for (int i = 0; i < qty; i++) {
                    toShip.add((Shippable) p);
                }
            }
        }
        double shipping = toShip.isEmpty() ? 0 : SHIPPING_FEES;
        double total = subtotal + shipping;
        if (customer.getBalance() < total) {
            System.out.println("Error: Insufficient balance.");
            return;
        }

        if (!toShip.isEmpty()) {
            ShippingService.shipItems(toShip);
        }

        System.out.println("** Checkout receipt **");
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            System.out.printf("%dx %s\t%.0f\n", entry.getValue(), entry.getKey().getName(), entry.getKey().getPrice() * entry.getValue());
        }
        System.out.println("--------------------");
        System.out.printf("Subtotal\t%.0f\n", subtotal);
        System.out.printf("Shipping\t%.0f\n", shipping);
        System.out.printf("Amount\t%.0f\n", total);
        double newBalance = customer.getBalance() - total;
        customer.setBalance(newBalance);
        System.out.printf("Balance after payment: %.2f\n", customer.getBalance());
        System.out.println("Shipping address: " + address);
        System.out.println("Your order will be delivered within 2-5 days.");

        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            p.setQuantity(p.getQuantity() - qty);
        }
        cart.clear();
    }
}