import java.util.List;

public class ShippingService {
    public static void shipItems(List<Shippable> items) {
        if (items == null || items.isEmpty()) return;
        System.out.println("** Shipment notice **");
        double totalWeight = 0;
        java.util.Map<String, Double> nameToWeight = new java.util.LinkedHashMap<>();
        java.util.Map<String, Integer> nameToCount = new java.util.LinkedHashMap<>();
        for (Shippable item : items) {
            nameToWeight.put(item.getName(), nameToWeight.getOrDefault(item.getName(), 0.0) + item.getWeight());
            nameToCount.put(item.getName(), nameToCount.getOrDefault(item.getName(), 0) + 1);
            totalWeight += item.getWeight();
        }
        for (String name : nameToCount.keySet()) {
            int count = nameToCount.get(name);
            double weight = nameToWeight.get(name);
            System.out.printf("%dx %s\t%.0fg\n", count, name, weight * 1000);
        }
        System.out.printf("Total package weight %.1fkg\n", totalWeight);
        System.out.println();
    }
} 