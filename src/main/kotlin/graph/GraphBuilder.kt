package graph

import java.io.File
import java.net.URL
import kotlin.reflect.KClass
import kotlin.reflect.KFunction

class GraphBuilder<N>(edgeClass: KClass<*> = Edge::class) {

    private val DELIMITER = ","
    private var edgeConstructor : KFunction<*> = edgeClass.constructors.first { true }

    fun fromResource(location: String): Graph<N> {
        val classLoader = javaClass.classLoader
        val resource: URL = classLoader.getResource(location)

        println("Reading resource [${resource.toURI()}]")
        return fromFile(resource.path)
    }

    fun fromFile(location: String): Graph<N> {
        val file = File(location)
        println("Reading file [${file.absolutePath}]")
        val lines = file.readLines()

        return fromLines(lines)
    }

    fun fromString(string: String): Graph<N> {
        val lines = string.split("\n")
        return fromLines(lines)
    }

    private fun fromLines(lines: List<String>): Graph<N> {
        val graph = Graph<N>(false)

        for (line in lines) {
            val nodes = line.split(DELIMITER)

            val adjacencyList: MutableList<N> = mutableListOf()
            for (node in nodes) {
                adjacencyList.add((node.trim() as N))
            }

            val origin: N = adjacencyList[0]
            val destinations: MutableList<N> = adjacencyList.toMutableList()
            destinations.removeFirst()

            if (destinations.size == 0) {
                throw Exception("Adjacency data missing for node [$origin]")
            }

            addAdjacencyList(graph, origin, destinations)
        }

        return graph
    }

    private fun addAdjacencyList(graph: Graph<N>, origin: N, destinations: List<N>) {
        for (destination in destinations) {
            val edge: Edge<N> = edgeConstructor.call(origin, destination) as Edge<N>

            graph.addEdge(edge)
        }
    }

}