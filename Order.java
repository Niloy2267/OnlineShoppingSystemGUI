import java.util.List;

public class Order {
    private User user;
    private List<Product> products;
    private List<Integer> quantities;
    private String status;

    public Order(User user, List<Product> products, List<Integer> quantities) {
        this.user = user;
        this.products = products;
        this.quantities = quantities;
        this.status = "Pending";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}