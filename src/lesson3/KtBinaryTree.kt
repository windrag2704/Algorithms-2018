package lesson3

import java.util.SortedSet
import kotlin.NoSuchElementException

// Attention: comparable supported but comparator is not
class KtBinaryTree<T : Comparable<T>> : AbstractMutableSet<T>(), CheckableSortedSet<T> {

    private var root: Node<T>? = null

    override var size = 0
        private set

    private class Node<T>(val value: T) {

        var left: Node<T>? = null

        var right: Node<T>? = null
    }

    override fun add(element: T): Boolean {
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
        var parent: Node<T>
        if (root == null) {
            return false
        } else {
            parent = root!!
        }
        var removed: Node<T>
        var removedDirection: Int
        removed =
                (if (element.compareTo(parent.value) > 0) {
                    removedDirection = 1
                    parent.right
                } else {
                    removedDirection = -1
                    parent.left
                }) ?: return false
        if (root!!.value == element) {
            removedDirection = 0
            removed = root!!
        }
        while (removed.value != element) {
            parent = removed
            removed = (if (element.compareTo(parent.value) > 0) {
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
        //R = O(1)
        //T = O(height)
    }

    override operator fun contains(element: T): Boolean {
        val closest = find(element)
        return closest != null && element.compareTo(closest.value) == 0
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
            if (current == null) return firstElement()
            var next: Node<T>? = null
            var temp = root
            while (temp != null) {
                if (temp.value > current!!.value) {
                    next = temp
                    temp = temp.left
                } else {
                    temp = temp.right
                }
            }
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
        TODO()
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    override fun headSet(toElement: T): SortedSet<T> {
        TODO()
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    override fun tailSet(fromElement: T): SortedSet<T> {
        TODO()
    }

    override fun first(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.left != null) {
            current = current.left!!
        }
        return current.value
    }

    private fun firstElement(): Node<T> {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.left != null) {
            current = current.left!!
        }
        return current
    }

    override fun last(): T {
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.right != null) {
            current = current.right!!
        }
        return current.value
    }
}
