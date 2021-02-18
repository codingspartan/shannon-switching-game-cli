package shannon.players

import graph.Graph

class Maker<N>(val graph: Graph<N>, override val type: Type = Type.MAKER) : Player<N>(graph)