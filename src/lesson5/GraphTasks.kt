@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson5

import lesson5.impl.GraphBuilder
import java.util.*

/**
 * Эйлеров цикл.
 * Средняя
 *
 * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
 * Если в графе нет Эйлеровых циклов, вернуть пустой список.
 * Соседние дуги в списке-результате должны быть инцидентны друг другу,
 * а первая дуга в списке инцидентна последней.
 * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
 * Веса дуг никак не учитываются.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
 *
 * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
 * связного графа ровно по одному разу
 */

fun Graph.findEulerLoop(): List<Graph.Edge> {
    val result = mutableListOf<Graph.Edge>()
    var n = 0
    for (v in this.vertices) {
        n += this.getNeighbors(v).size % 2
    }
    if (n != 0) return result
    val st: Stack<Pair<Graph.Vertex, Graph.Edge>> = Stack()
    st.push(Pair(this.edges.first().begin, this.edges.first()))
    val passed = mutableSetOf(st.peek().second)
    while (!st.empty()) {
        var neigh = 0
        var nextEdge = st.peek().second
        var nextVertex = st.peek().first
        when (st.peek().first) {
            st.peek().second.begin -> nextVertex = st.peek().second.end
            st.peek().second.end -> nextVertex = st.peek().second.begin
        }
        for (e in getConnections(nextVertex).values) {
            if (!passed.contains(e)) {
                neigh++
                nextEdge = e
            }
        }
        if (neigh == 0) {
            result += st.pop().second
        } else {
            st.push(Pair(nextVertex, nextEdge))
            passed += nextEdge
        }

    }
    return result
    // T = O(N*N)
    // R = O(N)
}

/**
 * Минимальное остовное дерево.
 * Средняя
 *
 * Дан граф (получатель). Найти по нему минимальное остовное дерево.
 * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
 * вернуть любое из них. Веса дуг не учитывать.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ:
 *
 *      G    H
 *      |    |
 * A -- B -- C -- D
 * |    |    |
 * E    F    I
 * |
 * J ------------ K
 */

fun Graph.minimumSpanningTree(): Graph {
    val res = GraphBuilder()
    val ids = mutableMapOf<Graph.Vertex, Int>()
    for ((count, v) in vertices.withIndex()) {
        ids[res.addVertex(v.name)] = count
    }
    for (edge in edges) {
        val a = edge.begin
        val b = edge.end
        if (ids[a] != ids[b]) {
            res.addConnection(a, b)
            val oldId = ids[a]
            val newId = ids[b]
            for (v_id in ids) {
                if (v_id.value == oldId) {
                    v_id.setValue(newId!!)
                }
            }
        }
    }
    return res.build()
    //T = O(M*N) M - количество ребер, N - количество вершин
    //R = O(N)
}

/**
 * Максимальное независимое множество вершин в графе без циклов.
 * Сложная
 *
 * Дан граф без циклов (получатель), например
 *
 *      G -- H -- J
 *      |
 * A -- B -- D
 * |         |
 * C -- F    I
 * |
 * E
 *
 * Найти в нём самое большое независимое множество вершин и вернуть его.
 * Никакая пара вершин в независимом множестве не должна быть связана ребром.
 *
 * Если самых больших множеств несколько, приоритет имеет то из них,
 * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
 *
 * В данном случае ответ (A, E, F, D, G, J)
 *
 * Эта задача может быть зачтена за пятый и шестой урок одновременно
 */
fun Graph.largestIndependentVertexSet(): Set<Graph.Vertex> {
    TODO()
}

/**
 * Наидлиннейший простой путь.
 * Сложная
 *
 * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
 * Простым считается путь, вершины в котором не повторяются.
 * Если таких путей несколько, вернуть любой из них.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ: A, E, J, K, D, C, H, G, B, F, I
 */
fun Graph.longestSimplePath(): Path {
    TODO()
}