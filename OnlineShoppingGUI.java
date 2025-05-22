import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OnlineShoppingGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private ArrayList<User> users = new ArrayList<>();
    private User loggedUser = null;

    private ArrayList<Product> accessories = new ArrayList<>();
    private ArrayList<Product> electronics = new ArrayList<>();

    // GUI Components for different panels
    private JTextField regNameField, regEmailField;
    private JPasswordField regPassField;

    private JTextField loginEmailField;
    private JPasswordField loginPassField;

    private DefaultListModel<Product> productListModel;
    private JList<Product> productList;
    private JTextField quantityField;

    private DefaultListModel<String> cartListModel;
    private JList<String> cartList;

    private JLabel totalLabel;

    public OnlineShoppingGUI() {
        setTitle("Online Shopping System");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Sample products
        accessories.add(new Product("Keyboard", 1500, 15));
        accessories.add(new Product("Mouse", 800, 20));

        electronics.add(new Electronics("Laptop", 50000, 10, 12));
        electronics.add(new Electronics("Smartphone", 30000, 5, 24));
        electronics.add(new Electronics("Smartwatch", 8000, 8, 12));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(getWelcomePanel(), "welcome");
        mainPanel.add(getRegisterPanel(), "register");
        mainPanel.add(getLoginPanel(), "login");
        mainPanel.add(getShopPanel(), "shop");
        mainPanel.add(getCartPanel(), "cart");
        mainPanel.add(getCheckoutPanel(), "checkout");

        add(mainPanel);
        cardLayout.show(mainPanel, "welcome");
    }

    private JPanel getWelcomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        JButton registerBtn = new JButton("Register");
        JButton loginBtn = new JButton("Login");

        registerBtn.addActionListener(e -> cardLayout.show(mainPanel, "register"));
        loginBtn.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        panel.add(registerBtn);
        panel.add(loginBtn);

        return panel;
    }

    private JPanel getRegisterPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Name:"));
        regNameField = new JTextField();
        panel.add(regNameField);

        panel.add(new JLabel("Email:"));
        regEmailField = new JTextField();
        panel.add(regEmailField);

        panel.add(new JLabel("Password:"));
        regPassField = new JPasswordField();
        panel.add(regPassField);

        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back");

        registerBtn.addActionListener(e -> registerUser());
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));

        panel.add(registerBtn);
        panel.add(backBtn);

        return panel;
    }

    private JPanel getLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.add(new JLabel("Email:"));
        loginEmailField = new JTextField();
        panel.add(loginEmailField);

        panel.add(new JLabel("Password:"));
        loginPassField = new JPasswordField();
        panel.add(loginPassField);

        JButton loginBtn = new JButton("Login");
        JButton backBtn = new JButton("Back");

        loginBtn.addActionListener(e -> loginUser());
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));

        panel.add(loginBtn);
        panel.add(backBtn);

        return panel;
    }

    private JPanel getShopPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        JButton accessoriesBtn = new JButton("Accessories");
        JButton electronicsBtn = new JButton("Electronics");
        JButton viewCartBtn = new JButton("View Cart");
        JButton logoutBtn = new JButton("Logout");

        accessoriesBtn.addActionListener(e -> loadProducts(accessories));
        electronicsBtn.addActionListener(e -> loadProducts(electronics));
        viewCartBtn.addActionListener(e -> {
            updateCartList();
            cardLayout.show(mainPanel, "cart");
        });
        logoutBtn.addActionListener(e -> {
            loggedUser = null;
            cardLayout.show(mainPanel, "welcome");
        });

        topPanel.add(accessoriesBtn);
        topPanel.add(electronicsBtn);
        topPanel.add(viewCartBtn);
        topPanel.add(logoutBtn);

        productListModel = new DefaultListModel<>();
        productList = new JList<>(productListModel);
        productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(productList);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField(5);
        bottomPanel.add(quantityField);

        JButton addToCartBtn = new JButton("Add to Cart");
        addToCartBtn.addActionListener(e -> addToCart());
        bottomPanel.add(addToCartBtn);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel getCartPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        cartListModel = new DefaultListModel<>();
        cartList = new JList<>(cartListModel);
        JScrollPane scrollPane = new JScrollPane(cartList);

        totalLabel = new JLabel("Total: Tk 0.0");
        JButton checkoutBtn = new JButton("Checkout");
        JButton backBtn = new JButton("Back to Shop");

        checkoutBtn.addActionListener(e -> cardLayout.show(mainPanel, "checkout"));
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "shop"));

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(totalLabel);
        bottomPanel.add(checkoutBtn);
        bottomPanel.add(backBtn);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel getCheckoutPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));

        panel.add(new JLabel("Select Payment Method:"));

        JButton bkashBtn = new JButton("bKash");
        JButton nagadBtn = new JButton("Nagad");
        JButton codBtn = new JButton("Cash on Delivery");
        JButton backBtn = new JButton("Back to Cart");

        bkashBtn.addActionListener(e -> processPayment("bkash"));
        nagadBtn.addActionListener(e -> processPayment("nagad"));
        codBtn.addActionListener(e -> processPayment("cod"));
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "cart"));

        panel.add(bkashBtn);
        panel.add(nagadBtn);
        panel.add(codBtn);
        panel.add(backBtn);

        return panel;
    }

    // --- Helper Methods ---

    private void registerUser() {
        String name = regNameField.getText().trim();
        String email = regEmailField.getText().trim();
        String password = new String(regPassField.getPassword());

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        // Check if email already exists
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                JOptionPane.showMessageDialog(this, "Email already registered.");
                return;
            }
        }

        users.add(new User(name, email, password));
        JOptionPane.showMessageDialog(this, "Registration successful! Please login.");
        cardLayout.show(mainPanel, "login");
    }

    private void loginUser() {
        String email = loginEmailField.getText().trim();
        String password = new String(loginPassField.getPassword());

        for (User u : users) {
            if (u.login(email, password)) {
                loggedUser = u;
                JOptionPane.showMessageDialog(this, "Welcome, " + loggedUser.getEmail() + "!");
                loadProducts(accessories); // default load accessories
                cardLayout.show(mainPanel, "shop");
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Invalid email or password.");
    }

    private void loadProducts(ArrayList<Product> products) {
        productListModel.clear();
        for (Product p : products) {
            productListModel.addElement(p);
        }
    }

    private void addToCart() {
        Product selected = productList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a product first.");
            return;
        }
        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
            if (quantity <= 0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid quantity.");
            return;
        }

        if (selected.getStock() < quantity) {
            JOptionPane.showMessageDialog(this, "Insufficient stock.");
            return;
        }

        // Reduce stock
        selected.setStock(selected.getStock() - quantity);
        loggedUser.addToCart(selected, quantity);

        JOptionPane.showMessageDialog(this, "Added to cart.");
        quantityField.setText("");
        loadProducts(accessories.contains(selected) ? accessories : electronics);
    }

    private void updateCartList() {
        cartListModel.clear();
        double total = 0;
        for (int i = 0; i < loggedUser.cart.size(); i++) {
            Product p = loggedUser.cart.get(i);
            int qty = loggedUser.cartQuantity.get(i);
            cartListModel.addElement(p.getName() + " x " + qty + " = Tk " + (p.getPrice() * qty));
            total += p.getPrice() * qty;
        }
        totalLabel.setText("Total: Tk " + total);
    }

    private void processPayment(String method) {
        Order order = new Order(loggedUser, loggedUser.cart, loggedUser.cartQuantity);
        Payment payment;

        switch (method) {
            case "bkash" -> payment = new BkashPayment(order);
            case "nagad" -> payment = new NagadPayment(order);
            case "cod" -> payment = new CashOnDeliveryPayment(order);
            default -> {
                JOptionPane.showMessageDialog(this, "Invalid payment method.");
                return;
            }
        }

        payment.processPayment();
        JOptionPane.showMessageDialog(this, "Payment status: " + order.getStatus() + "\nThank you for shopping!");
        loggedUser.cart.clear();
        loggedUser.cartQuantity.clear();
        cardLayout.show(mainPanel, "welcome");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new OnlineShoppingGUI().setVisible(true);
        });
    }
}
