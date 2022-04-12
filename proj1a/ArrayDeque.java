public class ArrayDeque<T> {
    private int size;
    private int nextFirst;
    private int nextLast;
    private T[] array;
    private int length;

    public ArrayDeque() {
        size = 0;
        array = (T[]) new Object[8];
        nextFirst = 4;
        nextLast = 4;
        length = 8;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int minusOne(int index) {
        if (index == 0) {
            return length - 1;
        }
        return index - 1;
    }

    private int plusOne(int index, int module) {
        index %= module;
        if (index == module - 1) {
            return 0;
        }
        return index + 1;
    }

    private void grow() {
        T[] newArray = (T[]) new Object[length * 2];
        int ptr1 = nextFirst;
        int ptr2 = length;
        while (ptr1 != nextLast) {
            newArray[ptr2] = array[ptr1];
            ptr1 = plusOne(ptr1, length);
            ptr2 = plusOne(ptr2, length * 2);
        }
        nextFirst = length;
        nextLast = ptr2;
        array = newArray;
        length *= 2;
    }

    private void shrink() {
        T[] newArray = (T[]) new Object[length / 2];
        int ptr1 = nextFirst;
        int ptr2 = length / 4;
        while (ptr1 != nextLast) {
            newArray[ptr2] = array[ptr1];
            ptr1 = plusOne(ptr1, length);
            ptr2 = plusOne(ptr2, length / 2);
        }
        nextFirst = length / 4;
        nextLast = ptr2;
        array = newArray;
        length /= 2;
    }

    public void addFirst(T item) {
        if (size == length - 1) {
            grow();
        }
        nextFirst = minusOne(nextFirst);
        array[nextFirst] = item;
        size++;
    }

    public void addLast(T item) {
        if (size == length - 1) {
            grow();
        }
        array[nextLast] = item;
        nextLast = plusOne(nextLast, length);
        size++;
    }

    public int size(){
        return size;
    }

    public void printDeque() {
        int p = nextFirst;
        while (p != nextLast){
            System.out.print(array[p] + " ");
            p = plusOne(p, length);
        }
    }

    public T removeFirst() {
        if (length >= 16 && length / size >= 4) {
            shrink();
        }
        if (size == 0) {
            return null;
        }
        T ret = array[nextFirst];
        nextFirst = plusOne(nextFirst, length);
        size--;
        return ret;
    }

    public T removeLast() {
        if (length >= 16 && length / size >= 4) {
            shrink();
        }
        if (size == 0) {
            return null;
        }
        nextLast = minusOne(nextLast);
        size--;
        return array[nextLast];
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int ptr = nextFirst;
        for (int i = 0; i < index; i++) {
            ptr = plusOne(ptr, length);
        }
        return array[ptr];
    }
}


