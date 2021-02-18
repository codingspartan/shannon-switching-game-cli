package graph

import java.util.*

open class Graph<N>(private val isDirected: Boolean) {

    private val originToDestinationsToEdges = HashMap<N, MutableMap<N, MutableSet<Edge<N>>>>()
    private val edges: MutableSet<Edge<N>> = mutableSetOf()

    fun addEdge(edge: Edge<N>) {
        val originNode = getOrInitNode(edge.origin)
        val edgesFromOrigin = getOrInitEdges(edge.destination, originNode)
        edgesFromOrigin.add(edge)

        if (!isDirected) {
            val destinationNode = getOrInitNode(edge.destination)
            val edgesToDestination = getOrInitEdges(edge.origin, destinationNode)
            edgesToDestination.add(edge)
        }

        edges.add(edge)
    }

    private fun getOrInitEdges(key: N, destinations: MutableMap<N, MutableSet<Edge<N>>>): MutableSet<Edge<N>> {
        var edges = destinations[key]
        if (edges == null) {
            edges = mutableSetOf()
            destinations[key] = edges
        }
        return edges
    }

    private fun getOrInitNode(key: N): MutableMap<N, MutableSet<Edge<N>>> {
        var destinations = originToDestinationsToEdges[key]
        if (destinations == null) {
            destinations = mutableMapOf()
            originToDestinationsToEdges[key] = destinations
        }
        return destinations
    }

    override fun toString(): String {
        var s = "Graph(isDirected=$isDirected, nbEdges=${edges.size})\n"
        for (entry in originToDestinationsToEdges.entries) {
            s += ("         ${entry.key} -> ${entry.value.keys} = ${entry.value.size} edges\n")
        }
        return s
    }

    fun getNodes(): Set<N> {
        return originToDestinationsToEdges.keys
    }

    fun containsNode(node: N): Boolean {
        return getNodes().contains(node)
    }

    fun getAdjacentNodes(origin: N): Set<N> {
        return originToDestinationsToEdges[origin]!!.keys
    }

    fun getEdge(origin: N?, destination: N?): Edge<N>? {
        val edges = getParallelEdges(origin, destination)

        if (edges != null && edges.size == 1)
            return edges.iterator().next()
        else throw UnsupportedOperationException("Parallel edges are not yet handled for a multigraph")

    }

    fun getParallelEdges(origin: N?, destination: N?): Set<Edge<N>>? {
        return originToDestinationsToEdges[origin]?.get(destination)
    }

    fun getEdges(): Set<Edge<N>> {
        return edges
    }

}
