import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> items;

    public Cart() {
        items = new LinkedHashMap<>();
    }

    public boolean addProduct(Product product, int quantity) {
        if (quantity <= 0 || quantity > product.getQuantity()) {
            return false;
        }
        int currentQty = items.getOrDefault(product, 0);
        if (currentQty + quantity > product.getQuantity()) {
            return false;
        }
        items.put(product, currentQty + quantity);
        return true;
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }
} 