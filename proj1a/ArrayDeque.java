public class ArrayDeque<T> {
    public int size;
    public int nextFirst;
    public int nextLast;
    public T[] array;
    public int length;

    public ArrayDeque(){
        size = 0;
        array = (T[]) new Object[8];
        nextFirst = 4;
        nextLast = 5;
        length = 8;
    }

    public boolean isEmpty(){
        if (size == 0) return true;
        return false;
    }

    public void addFirst(T item){
        size +=1;
        array[nextFirst] = item;
        nextFirst -=1;
        if (nextFirst < 0) nextFirst = length;
    }

    public void addLast(T item){
        size +=1;
        array[nextLast] = item;
        nextLast +=1;
        if (nextLast > length) nextLast = 0;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        int p = nextFirst + 1;
        if(p == length) p = 0;
        while (p != nextLast){
            System.out.print(array[p] + " ");
            p += 1;
            if(p == length) p = 0;
        }
    }

    public T removeFirst(){
        nextFirst += 1;
        if (nextFirst == length) {
            nextFirst = 0;
            return array[0];
        }
        return array[nextFirst];
    }

    public T removeLast(){
        nextLast -= 1;
        if (nextLast < 0) {
            nextLast = length-1;
            return array[0];
        }
        return array[nextLast];
    }

    public T get(int index){
        if (nextFirst < nextLast){
            if (index>nextFirst && index<nextLast) return array[index];
            return null;
        }
        else{
            if (index > nextLast && index < nextFirst) return null;
            return array[index];
        }
    }

}
