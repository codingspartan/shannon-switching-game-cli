package shannon

import graph.Edge

class ShannonEdge<N>(override val origin: N, override val destination: N) : Edge<N>(origin, destination) {

    var status: Status = Status.UNTOUCHED

    enum class Status(val label: String) {
        BROKEN("broken"), UNBREAKABLE("unbreakable"), UNTOUCHED("untouched")
    }

    override fun toString(): String {
        return "ShannonEdge($origin->$destination=$status)"
    }

}