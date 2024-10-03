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
    private inner class It: Iterator<T> {
        var node: Node<T>? = head
        override fun hasNext() = node!=null
        override fun next(): T =
            (node ?: throw NoSuchElementException())
                .also { node = it.next }.elem
    }
*/

    /*
        override fun iterator(): Iterator<T> {
            class It: Iterator<T> {
                var node: Node<T>? = head
                override fun hasNext() = node!=null
                override fun next(): T =
                    (node ?: throw NoSuchElementException())
                        .also { node = it.next }.elem
            }
            return It()
        }
    */

    override fun iterator() = object : Iterator<T> {
        var node: Node<T>? = head
        override fun hasNext() = node!=null
        override fun next(): T =
            (node ?: throw NoSuchElementException())
                .also { node = it.next }.elem
    }

    companion object {
        val values = listOf(1,2,3)
        fun fx() { println("Stack") }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Stack<*>) return false
        val it = iterator()
        for(e in other)
            if (!it.hasNext() || e != it.next()) return false
        return !it.hasNext()
    }

    override fun hashCode(): Int =
        this.fold(0){ h, e -> h*31 + e.hashCode()}
}