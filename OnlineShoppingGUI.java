import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List; // Ensure you import this explicitly to avoid ambiguity

public class OnlineShoppingGUI {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Declare user list and product lists as instance variables
    private List<User> users = new ArrayList<>();
    private User loggedUser;
    private List<Product> accessories;
    private List<Product> electronics;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OnlineShoppingGUI().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Online Shopping System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize products lists
        accessories = Arrays.asList(
                new Product("Keyboard", 1500, "Accessories", 15),
                new Product("Mouse", 800, "Accessories", 20));
        electronics = Arrays.asList(
                new Product("Laptop", 50000, "Electronics", 10),
                new Product("Power Bank", 2500, "Electronics", 10),
                new Product("Router", 3500, "Electronics", 12),
                new Product("Smartphone", 30000, "Electronics", 5),
                new Product("Smartwatch", 8000, "Electronics", 8));

        mainPanel.add(loginPanel(), "Login");
        mainPanel.add(registerPanel(), "Register");
        mainPanel.add(shopPanel(), "Shop");

        frame.add(mainPanel);
        frame.setVisible(true);

        cardLayout.show(mainPanel, "Login");
    }

    private JPanel loginPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1)); // Fixed row count to accommodate all components
        JTextField emailField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton loginBtn = new JButton("Login");
        JButton toRegisterBtn = new JButton("Register");

        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(loginBtn);
        panel.add(toRegisterBtn);

        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String pass = new String(passField.getPassword());
            for (User u : users) {
                if (u.login(email, pass)) {
                    loggedUser = u;
                    cardLayout.show(mainPanel, "Shop");
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Login failed!");
        });

        toRegisterBtn.addActionListener(e -> cardLayout.show(mainPanel, "Register"));

        return panel;
    }

    private JPanel registerPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 1)); // Fixed rows count for all components
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton regBtn = new JButton("Register");
        JButton backBtn = new JButton("Back");

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(regBtn);
        panel.add(backBtn);

        regBtn.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String pass = new String(passField.getPassword());
            users.add(new User(name, email, pass));
            JOptionPane.showMessageDialog(frame, "Registration Successful");
            cardLayout.show(mainPanel, "Login");
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        return panel;
    }

    private JPanel shopPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea productsArea = new JTextArea();
        productsArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(productsArea);
        panel.add(scroll, BorderLayout.CENTER);

        JButton addToCartBtn = new JButton("Add to Cart");
        JButton viewCartBtn = new JButton("View Cart");
        JButton checkoutBtn = new JButton("Checkout");

        JPanel btnPanel = new JPanel();
        btnPanel.add(addToCartBtn);
        btnPanel.add(viewCartBtn);
        btnPanel.add(checkoutBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);

        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (Product p : accessories) {
            sb.append(index++).append(": ").append(p.name).append(" - Tk ").append(p.price)
                    .append(" | Stock: ").append(p.stock).append("\n");
        }
        for (Product p : electronics) {
            sb.append(index++).append(": ").append(p.name).append(" - Tk ").append(p.price)
                    .append(" | Stock: ").append(p.stock).append("\n");
        }
        productsArea.setText(sb.toString());

        addToCartBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter product number:");
            try {
                int choice = Integer.parseInt(input);
                int qty = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter quantity:"));
                List<Product> allProducts = new ArrayList<>();
                allProducts.addAll(accessories);
                allProducts.addAll(electronics);
                if (choice >= 1 && choice <= allProducts.size()) {
                    loggedUser.addToCart(allProducts.get(choice - 1), qty);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid product number.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input.");
            }
        });

        viewCartBtn.addActionListener(e -> {
            StringBuilder cartSummary = new StringBuilder();
            List<Product> cart = loggedUser.cart;
            List<Integer> qtys = loggedUser.cartQuantity;
            for (int i = 0; i < cart.size(); i++) {
                cartSummary.append(cart.get(i).name).append(" x ").append(qtys.get(i)).append(" = Tk ")
                        .append(cart.get(i).price * qtys.get(i)).append("\n");
            }
            cartSummary.append("Total: Tk ").append(loggedUser.calculateTotal());
            JOptionPane.showMessageDialog(frame, cartSummary.toString());
        });

        checkoutBtn.addActionListener(e -> {
            Order order = new Order(loggedUser, loggedUser.cart, loggedUser.cartQuantity);
            order.placeOrder();
            new Payment(order).processPayment();
            order.trackOrder();
            JOptionPane.showMessageDialog(frame, "Order placed successfully!");
            System.exit(0);
        });

        return panel;
    }
}

// Make sure you have these classes defined elsewhere in your project:
// Product, User, Order, Payment
