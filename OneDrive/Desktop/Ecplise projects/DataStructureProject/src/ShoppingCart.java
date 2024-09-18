class ShoppingCart {
    private static final int DEFAULT_CAPACITY = 100;
    private Item[] items;
    private int top;

    public ShoppingCart() {
        this.items = new Item[DEFAULT_CAPACITY];
        this.top = -1;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void addItem(Item item, int quantity) {
        boolean itemFound = false;
        for (int i = 0; i <= top; i++) {
            if (items[i].name.equalsIgnoreCase(item.name)) {
                items[i].quantity += quantity;
                itemFound = true;
                break;
            }
        }
        if (!itemFound) {
            item = new Item(item.name, item.price, quantity); 
            push(item);
        }
    }

    public void printCart() {
        if (isEmpty()) {
            System.out.println("The cart is empty.");
            return;
        }
        for (int i = 0; i <= top; i++) {
            Item currentItem = items[i];
            System.out.println(currentItem.name + " - Quantity: " + currentItem.quantity + " - Price: $" + currentItem.price * currentItem.quantity);
        }
    }

    public boolean isEmpty() {
        return top == -1;
    }

    private void push(Item item) {
        if (top == items.length - 1) {
            resize();
        }
        items[++top] = item;
    }

    public int removeItem(String name, int quantity) {
        int removedQuantity = 0;
        ShoppingCart tempStack = new ShoppingCart();
        while (!isEmpty()) {
            Item currentItem = pop();
            if (currentItem.name.equalsIgnoreCase(name)) {
                if (currentItem.quantity > quantity) {
                    currentItem.quantity -= quantity;
                    removedQuantity = quantity;
                    tempStack.push(currentItem);
                } else {
                    removedQuantity = currentItem.quantity;
                }
                break;
            } else {
                tempStack.push(currentItem);
            }
        }
        while (!tempStack.isEmpty()) {
            push(tempStack.pop());
        }
        return removedQuantity;
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (int i = 0; i <= top; i++) {
            totalPrice += items[i].price * items[i].quantity;
        }
        return totalPrice;
    }

    private Item pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return items[top--];
    }

    private void resize() {
        Item[] newItems = new Item[items.length * 2];
        System.arraycopy(items, 0, newItems, 0, items.length);
        items = newItems;
    }
}