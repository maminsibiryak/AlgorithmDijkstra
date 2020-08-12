import domain.Node

var processed = mutableSetOf<Node>()
fun main() {

    val nodeA = Node("A")
    val nodeB = Node("B")
    val nodeC = Node("C")
    val nodeD = Node("D")
    val nodeFin = Node("Fin")
    val nodeStart = Node("Start")
    val listParent = mutableListOf<Node>()
    val listChild = mutableListOf<Node>()
    val result = mutableSetOf<Node>()

    nodeStart.addDistance(nodeA, 5)
    nodeStart.addDistance(nodeB, 2)

    nodeA.addDistance(nodeC, 4)
    nodeA.addDistance(nodeD, 2)

    nodeB.addDistance(nodeD, 7)
    nodeB.addDistance(nodeA, 8)

    nodeC.addDistance(nodeFin, 3)
    nodeC.addDistance(nodeD, 6)

    nodeD.addDistance(nodeFin, 1)

    val coasts = hashMapOf<Node, Int>(
        nodeA to 5,
        nodeB to 2,
        nodeC to Int.MAX_VALUE,
        nodeD to Int.MAX_VALUE,
        nodeFin to Int.MAX_VALUE
    )

    val parents = hashMapOf<Node, Node>(
        nodeA to nodeStart,
        nodeB to nodeStart,
        nodeC to nodeStart,
        nodeD to nodeStart,
        nodeFin to nodeStart
    )

    var lowest = findLowestCoast(coasts)
    while (lowest != null) {
        val coast = coasts[lowest]
        val neighbors = lowest.distance

        for (entry in neighbors.entries) {
            val node = entry.key
            val value = entry.value
            val newCoast = coast?.plus(value)

            if (coasts[node]!! > newCoast!!) {
                coasts[node] = newCoast
                parents[node] = lowest

            }
        }

        processed.add(lowest)
        lowest = findLowestCoast(coasts)
    }

    parents.forEach { (k, v) ->
        listParent.add(v)
        listChild.add(k)
    }
    result.add(nodeStart)
    for (node in listParent) {
        for (n in listChild) {
            if (node.name == n.name) {
                result.add(node)
            }
        }
    }
    result.add(nodeFin)
    result.forEach { r ->
        if (r.name == "Fin") {
            print("${r.name} \n")
        } else print("${r.name}->")
    }
    result.forEach { r ->
        if (r == nodeFin) {
            print("${coasts[r]}")
        } else print("${coasts[r]}->")
    }

}


private fun findLowestCoast(coasts: HashMap<Node, Int>): Node? {
    var lowestCoast = Int.MAX_VALUE
    var lowest: Node? = null
    for (entry in coasts.entries) {
        val node = entry.key
        val coast = entry.value

        if (coast < lowestCoast && !processed.contains(node)) {
            lowestCoast = coast
            lowest = node
        }

    }
    return lowest
}
