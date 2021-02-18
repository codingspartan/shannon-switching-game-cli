package shannon.players

import graph.Graph

class Breaker<N>(val graph: Graph<N>, override val type: Type = Type.BREAKER) : Player<N>(graph)