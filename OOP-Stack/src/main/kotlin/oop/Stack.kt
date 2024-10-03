package oop

interface Stack<T> : Iterable<T> {
    fun push(e: T): Stack<T>
    fun pop(): Stack<T>
    fun isEmpty(): Boolean
    val top: T
}

fun <T> Stack(): Stack<T> = StackEmpty()

private class Node<T>(val elem: T, val next: Node<T>?)

class StackEmpty<T> : Stack<T> {
    private fun throwEmpty(): Nothing { throw NoSuchElementException("Empty Stack")}
    override fun push(e: T): Stack<T> = StackNotEmpty(e)
    override fun pop() = throwEmpty()
    override fun isEmpty() = true
    override val top: Nothing get() = throwEmpty()
    override fun iterator() = object : Iterator<T> {
        override fun hasNext() = false
        override fun next() = throwEmpty()
    }
    override fun equals(other: Any?) = other is StackEmpty<*>
    override fun hashCode() = 1
}

class StackNotEmpty<T> private constructor(private val head: Node<T>): Stack<T> {
    constructor(e: T) :this( Node(e,null))
    override fun push(e: T): Stack<T> = StackNotEmpty(Node(e,head))
    override fun pop() = if (head.next==null) StackEmpty() else StackNotEmpty(head.next)
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
        this.fold(0){ h, e -> h*31 + e.hashCode()
    }
    override fun equals(other: Any?): Boolean {
        if (other !is StackNotEmpty<*>) return false
        val it = iterator()
        for(e in other)
            if (!it.hasNext() || e != it.next()) return false
        return !it.hasNext()
    }
}