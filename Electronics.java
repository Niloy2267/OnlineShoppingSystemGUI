public class Electronics extends Product {
    private int warrantyMonths;

    public Electronics(String name, double price, int stock, int warrantyMonths) {
        super(name, price, stock);
        this.warrantyMonths = warrantyMonths;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    @Override
    public String toString() {
        return super.toString() + " - Warranty: " + warrantyMonths + " months";
    }
}