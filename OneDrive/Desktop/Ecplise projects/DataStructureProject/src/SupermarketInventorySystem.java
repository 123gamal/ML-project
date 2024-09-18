import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SupermarketInventorySystem {
    private InventoryCart inventory;
    private ShoppingCart currentCart;
    private CheckoutQueue checkoutQueue;
    private JFrame frame;
    private JTextField itemNameField;
    private JTextField itemQuantityField;

    public SupermarketInventorySystem() {
        inventory = new InventoryCart();
        currentCart = new ShoppingCart();
        checkoutQueue = new CheckoutQueue();
        
        
        inventory.addItem(new Item("Apple", 1.0, 50));
        inventory.addItem(new Item("Banana", 0.5, 100));
        inventory.addItem(new Item("Orange", 0.75, 75));
        inventory.addItem(new Item("Water", 0.5, 100));
        inventory.addItem(new Item("Chicken", 5.0, 20));
        inventory.addItem(new Item("Chocolate", 1.5, 150));

        frame = new JFrame("Supermarket Inventory System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new GridLayout(6, 2));

        JButton showInventoryButton = new JButton("Show Inventory");
        JButton addItemButton = new JButton("Add Item to Cart");
        JButton removeItemButton = new JButton("Remove Item from Cart");
        JButton printCartButton = new JButton("Print Cart");
        JButton checkoutButton = new JButton("Checkout");

        itemNameField = new JTextField();
        itemQuantityField = new JTextField();

        frame.add(new JLabel("Item Name:"));
        frame.add(itemNameField);
        frame.add(new JLabel("Item Quantity:"));
        frame.add(itemQuantityField);
        frame.add(showInventoryButton);
        frame.add(addItemButton);
        frame.add(removeItemButton);
        frame.add(printCartButton);
        frame.add(checkoutButton);

        showInventoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuilder inventoryDetails = new StringBuilder("Inventory:\n");
                InventoryNode current = inventory.getHead();
                while (current != null) {
                    inventoryDetails.append(current.item.name)
                            .append(" - Quantity: ").append(current.item.quantity)
                            .append(" - Price: $").append(current.item.price).append("\n");
                    current = current.next;
                }	
                JOptionPane.showMessageDialog(frame, inventoryDetails.toString(), "Inventory", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        addItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String itemName = itemNameField.getText();
                String quantityText = itemQuantityField.getText();

                if (itemName.isEmpty() || quantityText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter both item name and quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int quantity = Integer.parseInt(quantityText);
                    Item itemToAdd = inventory.getItem(itemName);
                    if (itemToAdd != null) {
                        int itemQuantity = itemToAdd.quantity;
                        if (quantity <= itemQuantity) {
                            currentCart.addItem(itemToAdd, quantity);
                            itemToAdd.quantity -= quantity;
                            JOptionPane.showMessageDialog(frame, "Item added to cart.");
                            itemNameField.setText("");
                            itemQuantityField.setText("");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Insufficient quantity available in inventory.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Item not found in inventory. Please choose a different item.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number for quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removeItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String itemName = itemNameField.getText();
                String quantityText = itemQuantityField.getText();

                if (itemName.isEmpty() || quantityText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter both item name and quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int quantity = Integer.parseInt(quantityText);
                    int removedQuantity = currentCart.removeItem(itemName, quantity);
                    if (removedQuantity > 0) {
                        Item itemInInventory = inventory.getItem(itemName);
                        if (itemInInventory != null) {
                            itemInInventory.quantity += removedQuantity;
                        } else {
                            inventory.addItem(new Item(itemName, 0, removedQuantity));
                        }
                        JOptionPane.showMessageDialog(frame, "Item removed from cart and added back to inventory.");
                        itemNameField.setText("");
                        itemQuantityField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Item not found in cart or insufficient quantity in cart.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number for quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        printCartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentCart.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "The cart is empty.", "Cart", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                StringBuilder cartDetails = new StringBuilder("Cart:\n");
                for (Item item : currentCart.getItems()) {
                    if (item != null) {
                        cartDetails.append(item.name)
                                .append(" - Quantity: ").append(item.quantity)
                                .append(" - Price: $").append(item.price * item.quantity).append("\n");
                    }
                }
                JOptionPane.showMessageDialog(frame, cartDetails.toString(), "Cart", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentCart.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "The cart is empty.", "Checkout", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                checkoutQueue.enqueue(currentCart);
                JOptionPane.showMessageDialog(frame, "Cart sent for checkout. Total price: $" + currentCart.getTotalPrice(), "Checkout", JOptionPane.INFORMATION_MESSAGE);
                currentCart = new ShoppingCart();
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SupermarketInventorySystem();
    }
}