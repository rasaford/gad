public class List {

  private static class Node {

    private int data;

    public int getData() {
      return data;
    }

    public void setData(int data) {
      this.data = data;
    }

    private Node next;

    public Node getNext() {
      return next;
    }

    public Node(int data, Node next) {
      this.data = data;
      this.next = next;
    }
  }

  private Node head;
  private int size;

  public int getSize() {
    return size;
  }

  public List() {
  }

  void prepend(int data) {
    head = new Node(data, head);
    size++;
  }

  int get(int index) {
    Node it = head;
    while (index != 0) {
      index--;
      it = it.getNext();
      if (it == null) {
        throw new RuntimeException("Out of Bounds");
      }
    }
    return it.getData();
  }

  void swap(int indexFirst, int indexSecond) {
    if (indexFirst > indexSecond) {
      swap(indexSecond, indexFirst);
      return;
    }
    int distance = indexSecond - indexFirst;
    if (head == null) {
      throw new RuntimeException("Out of bounds");
    }
    Node it_first = head;
    while (indexFirst != 0) {
      indexFirst--;
      it_first = it_first.getNext();
      if (it_first == null) {
        throw new RuntimeException("Out of bounds");
      }
    }
    Node it_second = it_first;
    while (distance != 0) {
      distance--;
      it_second = it_second.getNext();
      if (it_second == null) {
        throw new RuntimeException("Out of bounds");
      }
    }
    int temp = it_second.getData();
    it_second.setData(it_first.getData());
    it_first.setData(temp);
  }
}
