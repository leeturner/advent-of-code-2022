fun main() {
  fun part1(input: List<List<Int>>): Int {
    return input.maxOf { it.sum() }
  }

  fun part2(input: List<List<Int>>): Int {
    return input.map { it.sum() }.sortedDescending().take(3).sum()
  }

  // test if implementation meets criteria from the description, like:
  val testInput =
      readInputSplitBy("Day01_test", "\n\n").map {
        it.split("\n").map { calories -> calories.toInt() }
      }
  check(part1(testInput) == 24000)
  check(part2(testInput) == 45000)

  val input = readInputSplitBy("Day01", "\n\n").map {
    it.split("\n").map { calories -> calories.toInt() }
  }
  println(part1(input))
  println(part2(input))
}
