import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int tail = 0;
    private int size = 0;
    private Item[] queue;

    private class RandomizedQueueIterator implements Iterator<Item>
    {
        private int current = 0;
        private int[] itemsPosition;

        public RandomizedQueueIterator()
        {
            itemsPosition = new int[size()];
            for (var i = 0; i < itemsPosition.length; i++)
            {
                itemsPosition[i] = i;
            }
            StdRandom.shuffle(itemsPosition);
        }

        public boolean hasNext() { return current < tail; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next()
        {
            if (current == tail)
            {
                throw new NoSuchElementException();
            }

            var item = queue[itemsPosition[current]];
            current++;
            return item;
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue()
    {
        queue = (Item[]) new Object[1];
        //rand = new StdRandom();
    }

    // is the randomized queue empty?
    public boolean isEmpty()
    {
        return this.size == 0;
    }

    // return the number of items on the randomized queue
    public int size()
    {
        return this.size;
    }

    // add the item
    public void enqueue(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException();
        }

        if (this.tail == this.queue.length)
        {
            resizeArrayQueue(this.queue.length * 2);
        }

        queue[this.tail++] = item;
        this.size++;
    }

    // remove and return a random item
    public Item dequeue()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }

        if (this.tail <= this.queue.length / 4)
        {
            resizeArrayQueue(this.queue.length / 2);
        }

        var randomPosition = StdRandom.uniform(0, size());
        var itemToDequeue = this.queue[randomPosition];
        this.queue[randomPosition] = this.queue[--this.tail];
        this.size--;
        return itemToDequeue;
    }

    // return a random item (but do not remove it)
    public Item sample()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }

        var randomPosition = StdRandom.uniform(0, size());
        return this.queue[randomPosition];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        var randQueue = new RandomizedQueue<String>();
        System.out.println("Deque is empty: " + randQueue.isEmpty());
        System.out.println("size == 0: " + (randQueue.size() == 0));
        randQueue.enqueue("Joseph");
        randQueue.enqueue("Mary");
        System.out.println("Dequeue random name: " + (randQueue.dequeue()));
        randQueue.enqueue("Carlos");
        System.out.println("Sample random name: " + randQueue.sample());
        System.out.println("Size is 2: " + (randQueue.size() == 2));
        randQueue.enqueue("Jack");
        var numberOfNames = 0;
        for (var name : randQueue)
        {
            numberOfNames++;
        }
        System.out.println("Iterator works: " + (numberOfNames == randQueue.size()));
        randQueue.dequeue();
        randQueue.dequeue();
        randQueue.dequeue();
        System.out.println("randQueue is empty again: " + randQueue.isEmpty());
    }

    private void resizeArrayQueue(int newLength)
    {
        var newArray = (Item[]) new Object[newLength];
        for (var i = 0; i < this.tail; i++)
        {
           newArray[i] = this.queue[i];
        }
        this.queue = newArray;
    }

}
