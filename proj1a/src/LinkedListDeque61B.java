import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {

    private static class Node<T> {
        T item;
        Node<T> prev;
        Node<T> next;

        Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    //single sentinel head
    private final Node<T> sentinel;

//    // double sentinel heads
//    private Node<T> sentFront;
//    private Node<T> sentBack;

    private int size;

    public LinkedListDeque61B() {
        // single sentinel
        sentinel = new Node<>(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
//        // double sentinels
//        sentFront = new Node<>(null, null, null);
//        sentBack = new Node<>(null, null, null);
//        sentFront.next = sentBack;
//        sentBack.prev = sentFront;

        size = 0;
    }

    @Override
    public void addFirst(T x) {
        // single sentinel
        Node<T> newNode = new Node<>(x, sentinel, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;

//        // double sentinels
//        Node<T> newNode = new Node<>(x, sentFront, sentFront.next);
//        sentFront.next.prev = newNode;
//        sentFront.next = newNode;
//        size++;
    }

    @Override
    public void addLast(T x) {
        // single sentinel
        Node<T> newNode = new Node<>(x, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;

//        // double sentinels
//        Node<T> newNode = new Node<>(x, sentBack.prev, sentBack);
//        sentBack.prev.next = newNode;
//        sentBack.prev = newNode;
//        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node<T> current = sentinel.next;
        while (current != sentinel) {
            returnList.add(current.item);
            current = current.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
           return null;
        }

        Node<T> firstNode = sentinel.next;
        T item = firstNode.item;

        sentinel.next = firstNode.next;
        sentinel.next.prev = sentinel;

        firstNode.item = null;
        firstNode.next = null;
        firstNode.prev = null;

        size--;
        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        Node<T> lastNode = sentinel.prev;
        T item = lastNode.item;

        sentinel.prev = lastNode.prev;
        sentinel.prev.next = sentinel;

        size--;
        return item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node<T> current = sentinel.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.item;
    }


    @Override
    public T getRecursive(int index) {

        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    private T getRecursiveHelper(Node<T> current, int index) {
        if (index == 0) {
            return current.item;
        }
        return getRecursiveHelper(current.next, index - 1);
    }

}
