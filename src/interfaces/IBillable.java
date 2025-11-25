package interfaces;

public interface IBillable {
    double calculateBill();
    void addToBill(double amount);
    void payBill(double amount);
}
