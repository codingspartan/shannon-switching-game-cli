package graph

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class GraphBuilderTest {

    private val graphBuilder = GraphBuilder<String>()

    @Test
    fun fromFile() {
        val graph = graphBuilder.fromFile("src/main/resources/graph-001.csv")

        assertEquals(8, graph.getNodes().size)
        assertEquals(14, graph.getEdges().size)
    }

    @Test
    fun fromResource() {
        val graph = graphBuilder.fromResource("graph-001.csv")

        assertEquals(8, graph.getNodes().size)
        assertEquals(14, graph.getEdges().size)

    }

    @Test
    fun fromString() {
        val graph = graphBuilder.fromString("1,2,3\n2,3\n4,1,2")

        assertEquals(4, graph.getNodes().size)
        assertEquals(5, graph.getEdges().size)
    }
}