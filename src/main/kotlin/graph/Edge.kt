package graph

open class Edge<N>(open val origin: N, open val destination: N) {

    override fun toString(): String {
        return "Edge($origin->$destination)"
    }

}