import graph.GraphBuilder
import shannon.Game
import shannon.ShannonEdge

val graphBuilder = GraphBuilder<String>(ShannonEdge::class)
val graph = graphBuilder.fromResource("graph-001.csv")

val game = Game(graph, "a", "b")
game.play()
