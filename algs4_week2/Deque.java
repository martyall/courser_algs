import java.util.*;
//* Implements a data strucutre called Deque which is a queue which allows pushing and popping at both ends are possible. The underlying data structure is a doubly linked list.


public class Deque<Item> implements Iterable<Item> {
    
    private Node frontSentinal;
    private Node backSentinal;
    private int size;
        
    public Deque() {
        this.frontSentinal = new Node(null,this.backSentinal,null);
        this.backSentinal = new Node(null,null,this.frontSentinal);
        this.size = 0;
    }
 
    private Node firstNode() {return frontSentinal.inFrontOf;}
  
    private Node lastNode() {return backSentinal.behind;}

    private class Node {
       
        public Item thing;
        public Node inFrontOf;
        public Node behind;
        
        public Node(Item thing, Node inFrontOf, Node behind) {
            this.thing = thing;
            this.inFrontOf = inFrontOf;
            this.behind = behind;
        }    
    }

    //@return true if the Deque is empty 
    public boolean isEmpty() {
        return (size == 0);
    }
   
    //@return the size of the Deque;
    public int size() {
        return this.size;
    }

    //add item to front of que
    //param @item the item to be added
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("Can't add null item to Deque.");
        } else {
            if (this.size == 0) {
                Node x = new Node(item, frontSentinal, backSentinal);
                frontSentinal.inFrontOf = x;
                backSentinal.behind = x;
            } else {
                Node x = new Node(item, frontSentinal.inFrontOf, frontSentinal);
                frontSentinal.inFrontOf.behind = x;
                frontSentinal.inFrontOf = x;
            }
            size++;
        }
    }

    //add item to the back of the queue
    //@param item the item to be added
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("Can't add null item to Deque.");
        } else {
            if (this.size == 0) {
                Node x = new Node(item, frontSentinal, backSentinal);
                frontSentinal.inFrontOf = x;
                backSentinal.behind = x;
            } else {
                Node x = new Node(item, backSentinal, backSentinal.behind);
                backSentinal.behind.inFrontOf = x;
                backSentinal.behind = x;
            }
            size++;
        }
    }

    // pop the item in the font of the queue
    public Item removeFirst() {
        if (this.size == 0) {
            throw new NoSuchElementException("The Deque is empty.");
        } else {
            Node x = this.firstNode();
            frontSentinal.inFrontOf = x.inFrontOf;
            x.inFrontOf.behind = frontSentinal;
            this.size--;
            return x.thing;
        }
    }

    // pop the item in the back of the queue
    public Item removeLast() {
        if (this.size == 0) {
            throw new NoSuchElementException("The Deque is empty.");
        } else {
            Node x = this.lastNode();
            backSentinal.behind = x.behind;
            x.behind.inFrontOf = backSentinal;
            this.size--;
            return x.thing;
        }
    }

    // implements the iterable interface for Deque
    public Iterator<Item> iterator() { return new ListIterator(); }

    private class ListIterator implements Iterator<Item> {
 
        public Node current = firstNode();
           
        public boolean hasNext() {return current.thing != null;}

        public  void remove() {
            throw new UnsupportedOperationException("'return' method not implemented.");
    }

        public Item next() {
            Item item = current.thing;
            if (item == null) {
                throw new NoSuchElementException("No next element in Deque.");
            }  
            current = current.inFrontOf;
            return item;
        }
    }
    
    public static void main(String[] args) {
    }   
}
	
