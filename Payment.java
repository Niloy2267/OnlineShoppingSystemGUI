public abstract class Payment {
    protected Order order;

    public Payment(Order order) {
        this.order = order;
    }

    public abstract void processPayment();
}
