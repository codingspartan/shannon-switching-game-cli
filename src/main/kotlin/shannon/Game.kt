package shannon

import graph.Edge
import graph.Graph
import graph.algorithms.DepthFirstSearcher
import shannon.players.Breaker
import shannon.players.Maker
import shannon.players.Player
import kotlin.system.exitProcess

class Game<N>(private val graph: Graph<N>, private val origin: N, private val destination: N) {

    private val pathFinder: DepthFirstSearcher<N> = DepthFirstSearcher()

    enum class Status(val label: String) {
        BROKEN("broken"), UNBREAKABLE("unbreakable"), UNTOUCHED("untouched")
    }

    fun play() {
        gameInit()
        val winner = gameLoop()
        gameEnd(winner)
    }

    private fun gameInit() {
        printSeparator('=')
        printSeparator('=', " The Shannon switching game ")
        printSeparator('=')
        println(graph)

        if (!isDestinationReachable(pathFinder, origin, destination)) {
            println("No possible path from [$origin] to [$destination] in this graph. Game aborted !")
            exitProcess(0)
        }
    }

    private fun gameLoop(): Player<N> {
        val breaker = Breaker(this.graph)
        val maker = Maker(this.graph)
        val edgeFilter: (edge: ShannonEdge<N>) -> Boolean = { edge -> edge.status == ShannonEdge.Status.UNBREAKABLE }
        var turn = 1

        while (true) {
            printSeparator('=')
            println("Turn#$turn")
            println(graph.getEdges())

            playerPlays(breaker)

            if (!isDestinationReachable(pathFinder, origin, destination)) {
                return breaker
            }

            printSeparator('-')

            playerPlays(maker)

            if (isDestinationReachable(pathFinder, origin, destination, edgeFilter)) {
                return maker
            }

            turn++
        }
    }

    private fun gameEnd(winner: Player<N>) {
        printSeparator('=')
        println("${winner.type.label} wins !")

        println(graph.getEdges())
    }

    private fun printSeparator(padChar: Char, title: String = "") {
        var columns = 80
        if (title.length < columns) {
            columns -= title.length
        }
        println("".padStart(columns / 2, padChar) + title + "".padEnd(columns / 2, padChar))
    }

    private fun isDestinationReachable(
        pathFinder: DepthFirstSearcher<N>,
        origin: N,
        destination: N,
        edgeFilter: ((edge: ShannonEdge<N>) -> Boolean)? = null
    ): Boolean {
        return pathFinder.execute(this.graph, origin, destination, edgeFilter as ((Edge<N>) -> Boolean)?).isNotEmpty()
    }

    private fun playerPlays(player: Player<N>): ShannonEdge<N> {
        while (true) {
            val edge = player.plays() as ShannonEdge
            if (edge.status == ShannonEdge.Status.UNTOUCHED) {
                edge.status = player.type.status
                println("> $edge is now ${player.type.status.label} !")
                return edge
            }
            println("> Edge is already ${edge.status.label} ! Please select an ${ShannonEdge.Status.UNTOUCHED.label} edge.")
        }
    }

}