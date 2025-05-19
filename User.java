import javax.swing.JOptionPane;
import java.util.*;

public class User {
    String name;
    String email;
    String password;
    List<Product> cart = new ArrayList<>();
    List<Integer> cartQuantity = new ArrayList<>();

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public boolean login(String email, String pass) {
        return this.email.equals(email) && this.password.equals(pass);
    }

    public void addToCart(Product p, int qty) {
        if (p.stock >= qty) {
            cart.add(p);
            cartQuantity.add(qty);
            p.stock -= qty;
        } else {
            JOptionPane.showMessageDialog(null, "Not enough stock available.");
        }
    }

    public double calculateTotal() {
        double total = 0;
        for (int i = 0; i < cart.size(); i++) {
            total += cart.get(i).price * cartQuantity.get(i);
        }
        return total;
    }
}
