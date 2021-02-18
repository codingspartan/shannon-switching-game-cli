package shannon.players

import graph.Edge
import graph.Graph
import shannon.ShannonEdge

abstract class Player<N>(private val graph: Graph<N>) {

    abstract val type: Type
    enum class Type(val label: String, val status: ShannonEdge.Status) {
        MAKER("Maker", ShannonEdge.Status.UNBREAKABLE), BREAKER("Breaker", ShannonEdge.Status.BROKEN);
    }


    fun plays(): Edge<N> {
        while (true) {
            println("${type.label} is playing...")

            val origin: N = askInput("origin") ?: continue

            val destination: N = askInput("destination") ?: continue

            val edge = graph.getEdge(origin, destination)
            if (edge == null) {
                println("> No edge from [$origin] to [$destination] found.")
                continue
            }

            return edge
        }
    }

    private fun askInput(nodeType: String): N? {
        println("Please enter [$nodeType] node of edge :")
        print("? ")
        val node = readLine()
        if (!graph.containsNode(node as N)) {
            println("> Node [$node] doesn't belong to the graph. Try again !")
            return null
        }
        return node
    }
}