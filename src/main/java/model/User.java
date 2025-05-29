package model;

public class User {
    private final String name;
    private double walletBalance;

    public User(String name, double initialBalance) {
        this.name = name;
        this.walletBalance = initialBalance;
    }

    public String getName() {
        return name;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void deduct(double amount) {
        if (walletBalance < amount) {
            throw new IllegalStateException("Insufficient wallet balance");
        }
        walletBalance -= amount;
    }

    public void addFunds(double amount) {
        walletBalance += amount;
    }
}

