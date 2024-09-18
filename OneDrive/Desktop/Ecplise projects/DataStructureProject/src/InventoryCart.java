class InventoryCart {
    private InventoryNode head;
    


	public InventoryNode getHead() {
		return head;
	}

	public InventoryCart() {
        this.head = null;
    }

    public void addItem(Item item) {
        InventoryNode newNode = new InventoryNode(item);
        if (head == null) {
            head = newNode;
        } else {
            InventoryNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public Item getItem(String name) {
        InventoryNode current = head;
        while (current != null) {
            if (current.item.name.equalsIgnoreCase(name)) {
                return current.item;
            }
            current = current.next;
        }
        return null;
    }
    public void showInventory() {
        InventoryNode current = head;
        while (current != null) {
            System.out.println(current.item.name + " - Quantity: " + current.item.quantity + " - Price: $" + current.item.price);
            current = current.next;
        }
    }
}