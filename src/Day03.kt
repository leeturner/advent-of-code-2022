fun priority(c: Char) = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(c) + 1

fun main() {
  fun part1(rucksacks: List<String>) =
      rucksacks
          .map { it.chunked(it.length / 2) }
          .map { it[0].toSet() intersect it[1].toSet() }
          .sumOf { priority(it.first()) }

  fun part2(rucksacks: List<String>) =
      rucksacks
          .chunked(3)
          .map { it[0].toSet() intersect it[1].toSet() intersect it[2].toSet() }
          .sumOf { priority(it.first()) }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day03_test")
  check(part1(testInput) == 157)
  check(part2(testInput) == 70)

  val input = readInput("Day03")
  println(part1(input))
  println(part2(input))
}
