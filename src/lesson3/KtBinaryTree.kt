package lesson3

import java.util.SortedSet
import kotlin.NoSuchElementException

// Attention: comparable supported but comparator is not
class KtBinaryTree<T : Comparable<T>>() : AbstractMutableSet<T>(), CheckableSortedSet<T> {

    private var root: Node<T>? = null

    private var begin: T? = null

    private var end: T? = null

    private constructor(root: Node<T>?, begin: T?, end: T?) : this() {
        this.begin = begin
        this.end = end
        this.root = root
    }

    override var size = 0
        private set
        get() {
            val it = iterator()
            var ret = 0
            while (it.hasNext()) {
                it.next()
                ret++
            }
            return ret
        }

    private class Node<T>(val value: T) {

        var left: Node<T>? = null

        var right: Node<T>? = null
    }

    private fun checkBounds(element: T): Boolean =
            when {
                (begin == null || begin!! <= element) && (end == null || end!! > element) -> true
                else -> false
            }

    override fun add(element: T): Boolean {
        if (checkBounds(element)) {
            val closest = find(element)
            val comparison = if (closest == null) -1 else element.compareTo(closest.value)
            if (comparison == 0) {
                return false
            }
            val newNode = Node(element)
            when {
                closest == null -> root = newNode
                comparison < 0 -> {
                    assert(closest.left == null)
                    closest.left = newNode
                }
                else -> {
                    assert(closest.right == null)
                    closest.right = newNode
                }
            }
            size++
            return true
        } else {
            return false
        }
    }

    override fun checkInvariant(): Boolean =
            root?.let { checkInvariant(it) } ?: true

    private fun checkInvariant(node: Node<T>): Boolean {
        val left = node.left
        if (left != null && (left.value >= node.value || !checkInvariant(left))) return false
        val right = node.right
        return right == null || right.value > node.value && checkInvariant(right)
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    override fun remove(element: T): Boolean {
        if (checkBounds(element)) {
            var parent: Node<T> = root ?: return false
            var removed: Node<T>
            var removedDirection: Int
            removed = (when {
                element == parent.value -> {
                    removedDirection = 0
                    parent
                }
                element > parent.value -> {
                    removedDirection = 1
                    parent.right
                }
                else -> {
                    removedDirection = -1
                    parent.left
                }
            }) ?: return false
            while (removed.value != element) {
                parent = removed
                removed = (if (element > parent.value) {
                    removedDirection = 1
                    parent.right
                } else {
                    removedDirection = -1
                    parent.left
                }) ?: return false
            }
            if (removed.right != null && removed.left != null) {
                var parentSwap = removed
                var swap = removed.right
                while (swap!!.left != null) {
                    parentSwap = swap
                    swap = swap.left
                }
                when (removedDirection) {
                    1 -> parent.right = swap
                    0 -> root = swap
                    else -> parent.left = swap
                }
                if (parentSwap == removed) {
                    swap.left = removed.left
                } else {
                    parentSwap.left = swap.right
                    swap.right = removed.right
                    swap.left = removed.left
                }
            } else if (removed.right != null) {
                when (removedDirection) {
                    1 -> parent.right = removed.right
                    0 -> root = removed.right
                    else -> parent.left = removed.right
                }
            } else {
                when (removedDirection) {
                    1 -> parent.right = removed.left
                    0 -> root = removed.left
                    else -> parent.left = removed.left
                }
            }
            size--
            return true
        } else {
            return false
        }
        //R = O(1)
        //T = O(height)
    }

    override operator fun contains(element: T): Boolean {
        return if (checkBounds(element)) {
            val closest = find(element)
            closest != null && element.compareTo(closest.value) == 0
        } else {
            false
        }
    }

    private fun find(value: T): Node<T>? =
            root?.let { find(it, value) }

    private fun find(start: Node<T>, value: T): Node<T> {
        val comparison = value.compareTo(start.value)
        return when {
            comparison == 0 -> start
            comparison < 0 -> start.left?.let { find(it, value) } ?: start
            else -> start.right?.let { find(it, value) } ?: start
        }
    }

    inner class BinaryTreeIterator : MutableIterator<T> {

        private var current: Node<T>? = null

        /**
         * Поиск следующего элемента
         * Средняя
         */
        private fun findNext(): Node<T>? {
            var next: Node<T>? = null
            if (current == null) {
                next = firstElement()
                if (begin == null || next.value >= begin!!) return next else current = next
            }
            var temp = root
            while (temp != null) {
                if (temp.value > current!!.value) {
                    next = temp
                    temp = temp.left
                } else {
                    temp = temp.right
                }
            }
            if (next != null && end != null && next.value >= end!! ||
                    (next != null &&begin != null && next.value < begin!!)) next = null
            return next
            //R = O(1)
            //T = O(height)
        }

        override fun hasNext(): Boolean = findNext() != null

        override fun next(): T {
            current = findNext()
            return (current ?: throw NoSuchElementException()).value
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        override fun remove() {
            if (current == null) return
            var prev: Node<T>? = null
            var temp = root
            while (temp != null) {
                if (temp.value < current!!.value) {
                    prev = temp
                    temp = temp.right
                } else {
                    temp = temp.left
                }
            }
            if (prev != null && begin != null && prev.value < begin!!) prev = null
            remove(current?.value)
            current = prev
            //R = O(1)
            //T = O(height)
        }
    }

    override fun iterator(): MutableIterator<T> = BinaryTreeIterator()

    override fun comparator(): Comparator<in T>? = null

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    override fun subSet(fromElement: T, toElement: T): SortedSet<T> {
        return KtBinaryTree(root, fromElement, toElement)
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    override fun headSet(toElement: T): SortedSet<T> {
        return KtBinaryTree(root, null, toElement)
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    override fun tailSet(fromElement: T): SortedSet<T> {
        return KtBinaryTree(root, fromElement, null)
    }

    override fun first(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.left != null) {
            if (begin != null && current.left!!.value < begin!!) break
            current = current.left!!
        }
        return current.value
    }

    private fun firstElement(): Node<T> {
        var current: Node<T> = root ?: throw NoSuchElementException()
        if (begin != null) return find(begin!!)!!
        while (current.left != null) {
            current = current.left!!
        }
        return current
    }

    override fun last(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.right != null) {
            if (end != null && current.right!!.value > end!!) break
            current = current.right!!
        }
        return current.value
    }


}
