package graph.algorithms

import graph.GraphBuilder
import org.junit.jupiter.api.Test

internal class DepthFirstSearcherTest {

    private val graphFileReader = GraphBuilder<String>()

    @Test
    fun execute() {
        val graph = graphFileReader.fromString("1,2,3,4,11\n2,5,6,10\n4,2,7\n3,8\n1,9\n7,8\n4,8\n10,8")

        val dfsPathFinder = DepthFirstSearcher<String>()
        val path = dfsPathFinder.execute(graph, "1", "8")
        println(path)
    }
}