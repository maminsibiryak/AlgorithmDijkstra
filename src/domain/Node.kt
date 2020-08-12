package domain

class Node(var name: String) {
    var distance = hashMapOf<Node, Int>()

    fun addDistance(node: Node, value: Int) {
        distance[node] = value
    }

    override fun toString(): String {
        return "Node(name='$name', distance=$distance)"
    }
}