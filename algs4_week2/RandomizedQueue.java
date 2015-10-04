import java.util.*;

//* Write a java program which implements the following data structure called RandomizedQueue: popping is random, i.e. does not depend on the push order. Also it should implement Iterable and the iterator should iterate at random.
//

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] randomizedArray;
    private int size;
    private int arraySize;
        
    public RandomizedQueue() {
        this.randomizedArray = (Item[]) new Object[1];
        this.randomizedArray[0] = null;
        this.size = 0;
        this.arraySize = 0;
    }
    
    //@return the size of the ambient array.
    private int getArraySize() { return this.arraySize; }
 
    //@return true if the array is empty
    public boolean isEmpty() {
        return (size == 0);
    }

    //@ return the size of the occupied array
    public int size() {
        return this.size;
    }
   
    //@param newSize the ambient size of the returned array
    private Item[] newQueue(int newSize) {
        Item[] temp = (Item[]) new Object[newSize + 1];
        temp[newSize] = null;
        return temp;
    }

    // creates new array when space is needed and copies over
    // the old array.
    private void growRandomizedArray() {
        Item[] x = newQueue(2*size);      
        for (int i = 0; i < size; i++) {
            x[i] = randomizedArray[i];
        }
        arraySize = 2*size; 
        randomizedArray = x;
    }

    // creates the new array when the current array has to little 
    // occupied space (i.e. <.25) and copies over the old array.
    private void shrinkRandomizedArray() {
        Item[] x = newQueue(arraySize/2);
        for (int i = 0; i < size; i++) {
            x[i] = randomizedArray[i];
        }
        arraySize = arraySize/2;
        randomizedArray = x;
    }
 
    //add new item to the queue
    //@param item the item to be added.
    public void enqueue(Item item) {
        if (size == 0) {
	    randomizedArray = (Item[]) new Object [2];
            randomizedArray[0] = item;
            size++;
            arraySize = 1;
        } else {
            if (size == arraySize) {
                growRandomizedArray();
                randomizedArray[size] = item;
            } else randomizedArray[size] = item;
            size++;
        }
    }

    // randomly dequeu an item in the array.
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("The Que is empty");
        }
        if (size == 1) {
            Item temp = randomizedArray[0];
            randomizedArray = (Item[]) new Object[] {null};
            size = 0;
            arraySize = 0;
            return temp;
        } else {
            int n = StdRandom.uniform(size);
            Item temp = randomizedArray[n];
            randomizedArray[n] = randomizedArray[size - 1];
            randomizedArray[size - 1] = null;
            size--;
            double prop = arraySize/size;
            if (prop >= 4) {
                shrinkRandomizedArray();
            }      	
        return temp;
        }
    }

    //return an item from the array, but don't pop it. 
    public Item sample() {
        if (this.size == 0) {
            throw new NoSuchElementException("The Deque is empty.");
        }
        int n = StdRandom.uniform(size);
        return randomizedArray[n];
    }
   

    // implements the iterable iterface.
    public Iterator<Item> iterator() { return new ListIterator(); }

    private class ListIterator implements Iterator<Item> {
  
        public int i = 0;
        public Item[] a = shuffle(randomizedArray); 
        public Item current = a[0];

        public boolean hasNext() {return current != null;}

        public  void remove() {
            throw new UnsupportedOperationException("'return' method not implemented.");
        }
     
        private Item[] shuffle(Item[] z) {      
	    int k = size;
	    for (int i = 0; i < k; i++) {
                int r = StdRandom.uniform(i+1);
                exchange(z, i, r); 
            }  
            return z;
        }     

        private void exchange(Item[] x, int n, int m) {
            Item temp = x[m];
            x[m] = x[n];
            x[n] = temp;
        }

        public Item next() {
            Item item = current;
            if (item == null) {
                throw new NoSuchElementException("No next element in Deque.");
            }
            i++;  
            current = a[i];
            return item;
        }
    }

    public static void main(String[] args) {
     }
}
