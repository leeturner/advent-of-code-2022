fun main() {

  fun String.distinctSection(window: Int): Int {
    return this.windowed(window).indexOfFirst { it.toList().distinct().count() == window } + window
  }

  fun part1(input: List<String>): Int {
    return input.first().distinctSection(4)
  }

  fun part2(input: List<String>): Int {
    return input.first().distinctSection(14)
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day06_test")
  check(part1(testInput) == 7)
  check(part2(testInput) == 19)

  val input = readInput("Day06")
  println(part1(input))
  println(part2(input))
}
