public class CircularlyLinkedList<E> {
    private static class Node<E> {
        private E element;
        private Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        public E getElement() {

            return element;
        }

        public void setElement(E element) {

            this.element = element;
        }

        public Node<E> getNext() {

            return next;
        }

        public void setNext(Node<E> next) {

            this.next = next;
        }
    }


    private Node<E> tail = null;
    private int size = 0;


//    public int Size() {
//        return size;
//
//    }

    public boolean isEmpty() {

        return size == 0;
    }


    public E Last() {
        if (isEmpty()) return null;
        return tail.getElement();
    }


//    public void addFirst(E e) { // adds element e to the front of the list
//         if (size == 0) {
//             tail = new Node<>(e, null);
//             tail.setNext(tail);
//             } else {
//               Node<E> newest = new Node<>(e, tail.getNext( ));
//               tail.setNext(newest);
//             }
//         size++;
//         }
public void addLast(E e) { // adds element e to the end of the list
    addFirst(e); // insert new element at front of list
    tail = tail.getNext( ); // now new element becomes the tail
}


    public E removeFirst( ) {
        if (isEmpty( )) return null;
        Node<E> head = tail.getNext( );
        if (head == tail) tail = null;
        else tail.setNext(head.getNext( ));
        size--;
        return head.getElement( );
    }
    //1
public void addFirst(E e) {
    if (size == 0) {
        tail = new Node<>(e, null);
        tail.setNext(tail);
    } else {
        tail.setNext(new Node<>(e, tail.getNext()));
    }
    size++;
}
//2
public int size() {
    if (isEmpty()) { // إذا كانت القائمة فارغة
        return 0;
    }

    int count = 1; // نبدأ بـ 1 لأننا سنحسب العقدة الأولى
    Node<E> current = tail.getNext(); // أول عقدة (بعد الذيل)

    while (current != tail) { // نستمر حتى نصل مرة أخرى إلى الذيل
        count++;
        current = current.getNext();
    }

    return count;
}
//3
@Override
public boolean equals(Object o) {
    if (o == this) return true; // إذا كانتا نفس الكائن
    if (!(o instanceof CircularlyLinkedList)) return false; // إذا لم يكن النوع متطابقًا

    CircularlyLinkedList<?> other = (CircularlyLinkedList<?>) o;

    if (this.size() != other.size()) return false; // إذا كانتا مختلفتين في الطول

    if (this.isEmpty() && other.isEmpty()) return true; // إذا كانتا فارغتين

    Node<E> current = this.tail.getNext(); // أول عنصر في هذه القائمة
    Node<?> otherCurrent = other.tail.getNext(); // أول عنصر في القائمة الأخرى

    do {
        if (!current.getElement().equals(otherCurrent.getElement())) {
            return false; // إذا كان هناك عنصر غير متطابق
        }
        current = current.getNext();
        otherCurrent = otherCurrent.getNext();
    } while (current != this.tail.getNext()); // تنتهي عندما نكمل دورة كاملة

    return true; // جميع العناصر متطابقة
}
//4
public static boolean areCircularListsEqual(CircularlyLinkedList<?> L, CircularlyLinkedList<?> M) {
    if (L.size() != M.size()) return false; // القائمتان مختلفتان في الحجم
    if (L.isEmpty() && M.isEmpty()) return true; // القائمتان فارغتان

    Node<?> startL = L.tail.getNext(); // أول عنصر في L
    Node<?> startM = M.tail.getNext(); // أول عنصر في M

    Node<?> currentM = startM;
    do {
        if (startL.getElement().equals(currentM.getElement())) {
            // احتمال وجود تطابق - تحقق من كل العناصر
            if (areSequencesEqual(startL, currentM, L.size())) {
                return true; // القائمتان متطابقتان
            }
        }
        currentM = currentM.getNext(); // جرّب نقطة بداية جديدة في M
    } while (currentM != startM);

    return false; // لم نجد أي تطابق
}

    private static boolean areSequencesEqual(Node<?> startL, Node<?> startM, int size) {
        Node<?> currentL = startL;
        Node<?> currentM = startM;

        for (int i = 0; i < size; i++) {
            if (!currentL.getElement().equals(currentM.getElement())) {
                return false; // تسلسل غير متطابق
            }
            currentL = currentL.getNext();
            currentM = currentM.getNext();
        }
        return true; // جميع العناصر متطابقة
    }
//5
public static <E> CircularlyLinkedList<E>[] splitList(CircularlyLinkedList<E> L) {
    if (L.isEmpty() || L.size() % 2 != 0) {
        throw new IllegalArgumentException("List must be non-empty and contain an even number of nodes.");
    }

    CircularlyLinkedList<E> L1 = new CircularlyLinkedList<>();
    CircularlyLinkedList<E> L2 = new CircularlyLinkedList<>();

    // استخدام مؤشرين لتحديد نقطة المنتصف
    Node<E> slow = L.tail.getNext(); // البداية
    Node<E> fast = L.tail.getNext();

    while (fast.getNext() != L.tail.getNext() && fast.getNext().getNext() != L.tail.getNext()) {
        slow = slow.getNext(); // يتحرك بمقدار واحد
        fast = fast.getNext().getNext(); // يتحرك بمقدار اثنين
    }

    // بناء L1: من البداية إلى منتصف القائمة
    Node<E> current = L.tail.getNext(); // البداية
    while (current != slow.getNext()) {
        L1.addLast(current.getElement());
        current = current.getNext();
    }

    // بناء L2: من منتصف القائمة إلى نهاية القائمة
    while (current != L.tail.getNext()) {
        L2.addLast(current.getElement());
        current = current.getNext();
    }

    // جعل القائمتين دائريتين
    if (!L1.isEmpty()) {
        L1.tail.setNext(L1.tail.getNext());
    }
    if (!L2.isEmpty()) {
        L2.tail.setNext(L2.tail.getNext());
    }

    return new CircularlyLinkedList[]{L1, L2}; // إرجاع القائمتين
}

    //6
@Override
public CircularlyLinkedList<E> clone() {
    CircularlyLinkedList<E> clonedList = new CircularlyLinkedList<>();

    if (this.isEmpty()) {
        return clonedList; // إذا كانت القائمة الأصلية فارغة، نرجع قائمة فارغة
    }

    Node<E> current = this.tail.getNext(); // أول عنصر في القائمة الأصلية
    Node<E> start = current; // حفظ نقطة البداية

    while (current != null) {
        clonedList.addLast(current.getElement()); // إضافة العنصر إلى النسخة الجديدة
        current = current.getNext(); // الانتقال إلى العقدة التالية

        if (current == start) { // إذا عدنا إلى نقطة البداية
            break;
        }
    }

    return clonedList; // نرجع النسخة
}



}
