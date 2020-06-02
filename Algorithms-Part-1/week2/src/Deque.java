import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int size = 0;
    private Node<Item> first;
    private Node<Item> last;

    private class Node<Item>
    {
        Item value;
        Node<Item> next;
        Node<Item> previous;

        public Node(Item value)
        {
            this.value = value;
        }

        public Node(Item value, Node next)
        {
            this.value = value;
            this.next = next;
        }

        public Node(Item value, Node next, Node previous)
        {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }

    private class DequeIterator implements Iterator<Item>
    {
        private Node<Item> current = first;

        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next()
        {
            if (current == null)
            {
                throw new NoSuchElementException();
            }

            var item = current.value;
            current = current.next;
            return item;
        }
    }

    // construct an empty deque
    public Deque()
    {

    }

    // is the deque empty?
    public boolean isEmpty()
    {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size()
    {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException();
        }

        this.first = new Node<Item>(item, this.first);
        if (isEmpty())
        {
            this.last = this.first;
        }
        else
        {
            this.first.next.previous = this.first;
        }

        this.size++;
    }

    // add the item to the back
    public void addLast(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException();
        }

        var oldLast = this.last;
        this.last = new Node<Item>(item, null, oldLast);
        if (isEmpty())
        {
            this.first = this.last;
        }
        else
        {
            oldLast.next = this.last;
        }

        this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        if (this.first == null)
        {
            throw new NoSuchElementException();
        }

        var oldFirstItem = this.first.value;
        this.first = this.first.next;
        if(this.first != null)
        {
            this.first.previous = null;
        }
        if (this.first.next == null)
        {
            this.last = this.first;
        }

        this.size--;
        return oldFirstItem;
    }

    // remove and return the item from the back
    public Item removeLast()
    {
        if (this.last == null)
        {
            throw new NoSuchElementException();
        }

        var oldLastItem = this.last.value;
        this.last = this.last.previous;
        if (this.last != null)
        {
            this.last.next = null;
            if (this.last.previous == null)
            {
                this.first = this.last;
            }
        }

        this.size--;
        return oldLastItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        var deque = new Deque<String>();
        System.out.println("Deque is empty: " + deque.isEmpty());
        System.out.println("size == 0: " + (deque.size == 0));
        deque.addFirst("Joseph");
        deque.addFirst("Mary");
        System.out.println("Mary is first: " + (deque.removeFirst() == "Mary"));
        deque.addLast("Carlos");
        System.out.println("Carlos is last: " + (deque.removeLast() == "Carlos"));
        System.out.println("Size is 1: " + (deque.size() == 1));
        deque.addLast("Jack");
        var numberOfNames = 0;
        for (var name : deque)
        {
            numberOfNames++;
        }
        System.out.println("Iterator works: " + (numberOfNames == deque.size));
        deque.removeFirst();
        deque.removeLast();
        System.out.println("Deque is empty again: " + deque.isEmpty());
    }

}
