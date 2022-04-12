public class LinkedListDeque <T> {
    public class ListNode{
        private ListNode prev;
        private T item;
        private ListNode next;

        public ListNode() {
            prev = null;
            next = null;
        }

        public ListNode(ListNode p, ListNode n) {
            prev = p;
            next = n;
        }

        public ListNode(T x, ListNode p, ListNode n) {
            prev = p;
            item = x;
            next = n;
        }

    }
    private int size;
    private ListNode sentinel;

    public LinkedListDeque() {
        size = 0;
        sentinel = new ListNode(null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    public void addFirst(T item) {
        ListNode first = new ListNode(item, sentinel, sentinel.next);
        sentinel.next.prev = first;
        sentinel.next = first;
        size += 1;
    }

    public void addLast(T item) {
        ListNode last = new ListNode(item, sentinel.prev, sentinel);
        sentinel.prev.next = last;
        sentinel.prev = last;
        size += 1;
    }

    public boolean isEmpty() {
        return size ==0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (size == 0) {
            return;
        }
        ListNode p = sentinel.next;
        while(p != sentinel){
            System.out.print(p.item + " ");
            p = p.next;
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T removeItem = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return removeItem;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T removeItem = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return removeItem;
    }

    public T get(int index) {
        if (index >= size) return null;
        ListNode p = sentinel.next;
        while(index > 0){
            p = p.next;
            index --;
        }
        return p.item;
    }

    private T getRecursiveHelper(ListNode node, int index) {
        if (node == sentinel) return null;
        if (index == 0) return node.item;
        return getRecursiveHelper(node.next, index - 1);
    }

    public T getRecursive(int index) {
        return getRecursiveHelper(sentinel.next, index);
    }

}
