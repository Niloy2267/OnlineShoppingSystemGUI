public class CashOnDeliveryPayment extends Payment {

    public CashOnDeliveryPayment(Order order) {
        super(order);
    }

    @Override
    public void processPayment() {
        // Simulation of COD payment
        System.out.println("Order will be paid on delivery.");
        order.setStatus("Cash on Delivery");
    }
}
