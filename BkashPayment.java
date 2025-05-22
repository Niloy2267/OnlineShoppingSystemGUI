public class BkashPayment extends Payment {

    public BkashPayment(Order order) {
        super(order);
    }

    @Override
    public void processPayment() {
        // Simulation of bKash payment
        System.out.println("Processing bKash payment for order...");
        order.setStatus("Paid via bKash");
    }
}