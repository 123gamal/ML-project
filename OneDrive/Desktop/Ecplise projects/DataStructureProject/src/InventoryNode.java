class InventoryNode {
    Item item;
    InventoryNode next;

    public InventoryNode(Item item) {
        this.item = item;
        this.next = null;
    }
}