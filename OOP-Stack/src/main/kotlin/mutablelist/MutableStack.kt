package mutablelist

class MutableStack<T> {
    private val elems: MutableList<T> = mutableListOf()
    fun push(e: T) { elems.add(e) }
    fun pop(): T = elems.removeLast()
    fun isEmpty() = elems.isEmpty()
    val top: T get() = elems.last()
    override fun equals(other: Any?): Boolean =
        other is MutableStack<*> && elems == other.elems
    override fun hashCode() = elems.hashCode()
}