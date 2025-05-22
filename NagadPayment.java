public class NagadPayment extends Payment {

    public NagadPayment(Order order) {
        super(order);
    }

    @Override
    public void processPayment() {
        // Simulation of Nagad payment
        System.out.println("Processing Nagad payment for order...");
        order.setStatus("Paid via Nagad");
    }
}