package linkedlist

class Stack<T> private constructor(private val head: Node<T>?) : Iterable<T> {
    private class Node<U>(val elem: U, val next: Node<U>? =null)
    private val headNotNull: Node<T> get() =
        head ?: throw NoSuchElementException("Stack empty")

    constructor(): this(null)
    fun push(e: T) = Stack(Node(e,head))
    fun pop() = Stack(headNotNull.next)
    fun isEmpty() = head==null
    val top: T get() = headNotNull.elem

    fun pop2(): Pair<T,Stack<T>> = top to pop()

    /*
    fun forEach(action: (T)->Unit ) {
        var node = head
        while (node!=null) {
            action(node.elem)
            node = node.next
        }
    }
    fun <U> map(conv: (T)->U) = buildList {
        var node = head
        while (node!=null) {
            add(conv(node.elem))
            node = node.next
        }
    }
    */

    private class It<U>(var node: Node<U>?): Iterator<U> {
        override fun hasNext() = node!=null
        override fun next(): U =
            (node ?: throw NoSuchElementException())
                .also { node = it.next }.elem
    }

    override fun iterator(): Iterator<T> = It(head)

    /*
    for( a in c ) print(a)
    -- is compiled to --
    val it = c.iterator()
    while( it.hasNext() ) {
       val a = it.next()
       print(a)
    }
    */
}