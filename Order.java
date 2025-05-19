import java.util.*;

public class Order {
    User user;
    List<Product> products;
    List<Integer> quantities;

    public Order(User user, List<Product> products, List<Integer> quantities) {
        this.user = user;
        this.products = new ArrayList<>(products);
        this.quantities = new ArrayList<>(quantities);
    }

    public void placeOrder() {
        // Save order or do something
    }

    public void trackOrder() {
        System.out.println("Tracking Order: Your order is being processed.");
    }

    // Add this method
    public double getTotalAmount() {
        double total = 0.0;
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int quantity = quantities.get(i);
            total += product.getPrice() * quantity;
        }
        return total;
    }
}
