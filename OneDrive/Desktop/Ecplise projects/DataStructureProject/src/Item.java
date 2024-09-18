class Item {
    String name;
    double price;
    int quantity;

    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return name + " - Quantity: " + quantity + " - Price: $" + price;
    }
}