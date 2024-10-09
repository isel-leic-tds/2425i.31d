package oop

interface Stack<T> : Iterable<T> {
    fun push(e: T): Stack<T>
    fun pop(): Stack<T>
    fun isEmpty(): Boolean
    val top: T
}

@Suppress("UNCHECKED_CAST")
fun <T> Stack(): Stack<T> = StackEmpty as Stack<T>

fun <T> stackOf(vararg elems: T): Stack<T> =
    if (elems.isEmpty()) Stack()
    else StackNotEmpty(
        elems.fold(null as Node<T>?) { n, e -> Node(e,n) } as Node<T>
    )
   //    elems.fold(Stack()) { s, e -> s.push(e) }

private class Node<T>(val elem: T, val next: Node<T>?)

private object StackEmpty : Stack<Any> {
    private fun throwEmpty(): Nothing { throw NoSuchElementException("Empty Stack")}
    override fun push(e: Any): Stack<Any> = StackNotEmpty(e)
    override fun pop() = throwEmpty()
    override fun isEmpty() = true
    override val top get() = throwEmpty()
    override fun iterator() = object : Iterator<Nothing> {
        override fun hasNext() = false
        override fun next() = throwEmpty()
    }
}

private class StackNotEmpty<T>(private val head: Node<T>): Stack<T> {
    constructor(e: T) :this( Node(e,null))
    override fun push(e: T): Stack<T> = StackNotEmpty(Node(e,head))
    override fun pop() = if (head.next==null) Stack() else StackNotEmpty(head.next)
    override fun isEmpty() = false
    override val top: T get() = head.elem
    override fun iterator() = object : Iterator<T> {
        var node: Node<T>? = head
        override fun hasNext() = node!=null
        override fun next(): T =
            (node ?: throw NoSuchElementException())
                .also { node = it.next }.elem
    }
    override fun hashCode(): Int =
        this.fold(0){ h, e -> h*31 + e.hashCode() }

    @Suppress("UNCHECKED_CAST")
    override fun equals(other: Any?): Boolean =
        other is StackNotEmpty<*> && equalNodes(head, other.head as Node<T>)
    private tailrec fun equalNodes(n1: Node<T>?, n2: Node<T>?): Boolean = when {
        n1==null -> n2==null
        n2==null || n1.elem != n2.elem -> false
        else -> equalNodes(n1.next, n2.next)
    }
}