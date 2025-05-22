import java.util.ArrayList;

public class User {
    private String username;
    private String email;
    private String password;

    ArrayList<Product> cart;
    ArrayList<Integer> cartQuantity;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        cart = new ArrayList<>();
        cartQuantity = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public void addToCart(Product product, int quantity) {
        int index = cart.indexOf(product);
        if (index >= 0) {
            int currentQty = cartQuantity.get(index);
            cartQuantity.set(index, currentQty + quantity);
        } else {
            cart.add(product);
            cartQuantity.add(quantity);
        }
    }

    public double calculateTotal() {
        double total = 0;
        for (int i = 0; i < cart.size(); i++) {
            total += cart.get(i).getPrice() * cartQuantity.get(i);
        }
        return total;
    }
}