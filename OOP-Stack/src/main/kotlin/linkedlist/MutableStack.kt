package linkedlist

class MutableStack<T> {
    private class Node<U>(val elem: U, val next: Node<U>? =null)
    private var head: Node<T>? = null
    private val headNotNull: Node<T> get() =
        head ?: throw NoSuchElementException("Stack empty")

    fun push(e: T) { head = Node(e,head) }
    fun pop(): T = top.also{ head = headNotNull.next }
    fun isEmpty() = head==null
    val top: T get() = headNotNull.elem

    override fun equals(other: Any?): Boolean {
        if (other !is MutableStack<*>) return false
        var n1 = head
        var n2 = other.head
        while (n1!=null && n2!=null) {
            if (n1.elem != n2.elem) return false
            n1 = n1.next
            n2 = n2.next
        }
        return n1==null && n2==null
    }

    override fun hashCode(): Int {
        var n = head
        var h = 0
        while (n != null) {
            h = h*31 + n.elem.hashCode()
            n = n.next
        }
        return h
    }
}