fun List<String>.parse(): List<List<Set<Int>>> {
  return this.map {
    it.split(',')
        .map { pair -> pair.split('-').map { assignment -> assignment.toInt() } }
        .map { (start, end) -> (start..end).toSet() }
  }
}

fun main() {

  fun part1(input: List<String>): Int {
    return input.parse().count { (firstAssignment, secondAssignment) ->
      firstAssignment.containsAll(secondAssignment) || secondAssignment.containsAll(firstAssignment)
    }
  }

  fun part2(input: List<String>): Int {
    return input.parse().count { (firstAssignment, secondAssignment) ->
      (firstAssignment intersect secondAssignment).isNotEmpty()
    }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day04_test")
  check(part1(testInput) == 2)
  check(part2(testInput) == 4)

  val input = readInput("Day04")
  println(part1(input))
  println(part2(input))
}
