package graph.algorithms

import graph.Edge
import graph.Graph
import java.util.*

/**
 * Iterative implementation of DFS (Depth-First Search)
 */
class DepthFirstSearcher<N> {

    fun execute(
        graph: Graph<N>,
        origin: N,
        destination: N,
        edgeFilter: ((edge: Edge<N>) -> Boolean)? = null
    ): List<N> {
        println("Finding path between [$origin] and [$destination]...")

        if (!graph.getNodes().contains(origin)) {
            throw Exception("Node [$origin] doesn't belong to the graph !")
        }

        if (!graph.getNodes().contains(destination)) {
            throw Exception("Node [$destination] doesn't belong to the graph !")
        }

        val toVisit = Stack<N>()
        val visitedNodes = mutableSetOf<N>()

        toVisit.push(origin)
        visitedNodes.add(origin)

        val path: MutableList<N> = mutableListOf()

        while (!toVisit.empty()) {
            val curOrigin = toVisit.pop()

            val unvisitedNodes = graph.getAdjacentNodes(curOrigin) subtract visitedNodes

            val visitableNodes: MutableSet<N> = mutableSetOf()
            if (edgeFilter == null) {
                visitableNodes.addAll(unvisitedNodes)
            } else {
                for (possibleDestination in unvisitedNodes) {
                    val edge = graph.getEdge(curOrigin, possibleDestination)
                    if (edgeFilter(edge!!)) {
                        visitableNodes.add(possibleDestination)
                    }
                }
            }
            if (visitableNodes.isNotEmpty()) {
                path.add(curOrigin)
            }

            for (visitableNode in visitableNodes) {
                if (visitableNode == destination) {
                    path.add(visitableNode)
                    println("DFS : path found $path")
                    return path
                } else {
                    toVisit.push(visitableNode)
                    visitedNodes.add(visitableNode)
                }
            }
        }

        println("DFS : no path found")
        return emptyList()
    }

}