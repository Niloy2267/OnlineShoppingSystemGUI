public class Product {
    String name;
    double price;
    String category;
    int stock;

    public Product(String name, double price, String category, int stock) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }
}
