import java.time.LocalDate;

public class ExpirableProduct extends Product {
    private String expiryDate; // format: yyyy-MM-dd

    public ExpirableProduct(String name, double price, int quantity, String expiryDate) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public boolean isExpired(String today) {
        LocalDate exp = LocalDate.parse(expiryDate);
        LocalDate now = LocalDate.parse(today);
        return now.isAfter(exp);
    }
} 