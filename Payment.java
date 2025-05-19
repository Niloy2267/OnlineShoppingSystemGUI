import javax.swing.*;

public class Payment {
    Order order;

    public Payment(Order order) {
        this.order = order;
    }

    public void processPayment() {
        // প্রোমো কোড ইনপুট নাও
        String promoCode = JOptionPane.showInputDialog(null, "Enter Promo Code (if any):");

        double discount = 0.0;
        if (promoCode != null && promoCode.equalsIgnoreCase("NILOY")) {
            discount = 0.15; // ১৫% ডিসকাউন্ট
            JOptionPane.showMessageDialog(null, "Promo code applied! You got 15% discount.");
        } else if (promoCode != null && !promoCode.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invalid Promo Code.");
        }

        // অর্ডারের মোট দাম থেকে ডিসকাউন্ট প্রয়োগ করো
        double totalAmount = order.getTotalAmount();
        double discountedAmount = totalAmount - (totalAmount * discount);

        // ইউজারকে নতুন পেমেন্ট অমাউন্ট দেখাও
        JOptionPane.showMessageDialog(null, "Total amount after discount: " + discountedAmount);

        // পেমেন্ট মেথড সিলেক্ট করানোর ডায়লগ
        String[] methods = { "bKash", "Nagad", "Cash on Delivery" };
        String selected = (String) JOptionPane.showInputDialog(null,
                "Select Payment Method:",
                "Payment",
                JOptionPane.QUESTION_MESSAGE,
                null,
                methods,
                methods[0]);

        if (selected != null) {
            JOptionPane.showMessageDialog(null, selected + " payment selected. Payment Successful.");
            // এখানে চাইলে payment complete করার লজিক দিতে পারো
            // যেমন: order.setPaid(true);
        } else {
            JOptionPane.showMessageDialog(null, "Payment Cancelled.");
        }
    }
}
