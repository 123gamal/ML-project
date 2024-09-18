class CheckoutQueue {
    private final ShoppingCart[] queue;
    private int front;
    private int rear;
    private int size;
    private final int capacity;

    public CheckoutQueue() {
        this.capacity = 100;
        this.queue = new ShoppingCart[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    public void enqueue(ShoppingCart cart) {
        if (isFull()) {
            System.out.println("Queue is full. Cannot enqueue.");
            return;
        }
        rear = (rear + 1) % capacity;
        queue[rear] = cart;
        size++;
    }

    public ShoppingCart dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty. Cannot dequeue.");
            return null;
        }
        ShoppingCart removedItem = queue[front];
        front = (front + 1) % capacity;
        size--;
        return removedItem;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public int size() {
        return size;
    }
}