typealias Stack = ArrayDeque<Char>

val numbersInInstructionRegex = """\d+""".toRegex()

data class Instruction(val num: Int, val from: Int, val to: Int)

fun List<String>.parseStacks(): MutableList<Stack> {
  val numberOfStacks =
      this.takeWhile { it.trim().isNotEmpty() }.last().last { it.isDigit() }.digitToInt()
  val stacks = MutableList(numberOfStacks) { Stack() }
  this.filter { it.contains('[') }
      .reversed()
      .map { it.chunked(4).map { item -> item[1] } }
      .forEach {
        it.forEachIndexed { index, item ->
          if (!item.isWhitespace()) {
            stacks[index].add(item)
          }
        }
      }
  return stacks
}

fun List<String>.parseInstructions(): List<Instruction> =
    this.dropWhile { it.isNotEmpty() }
        .drop(1)
        .map {
          val (num, from, to) =
              numbersInInstructionRegex
                  .findAll(it)
                  .map { instruction -> instruction.value.toInt() }
                  .toList()
          Instruction(num, from - 1, to - 1)
        }

fun MutableList<Stack>.peekTopItems(): String {
  return map { stack -> stack.last() }.joinToString(separator = "")
}

object Crane {
  fun moveCrateMover9000(stacks: MutableList<Stack>, instruction: Instruction) {
    repeat(instruction.num) { stacks[instruction.to].add(stacks[instruction.from].removeLast()) }
  }

  fun moveCrateMover9001(stacks: MutableList<Stack>, instruction: Instruction) {
    val temp: MutableList<Char> = mutableListOf()
    repeat(instruction.num) { temp.add(stacks[instruction.from].removeLast()) }
    temp.reversed().forEach { stacks[instruction.to].add(it) }
  }
}

fun main() {

  fun part1(input: List<String>): String {
    val stacks = input.parseStacks()
    input.parseInstructions().forEach { Crane.moveCrateMover9000(stacks, it) }
    return stacks.peekTopItems()
  }

  fun part2(input: List<String>): String {
    val stacks = input.parseStacks()
    input.parseInstructions().forEach { Crane.moveCrateMover9001(stacks, it) }
    return stacks.peekTopItems()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day05_test")
  check(part1(testInput) == "CMZ")
  check(part2(testInput) == "MCD")

  val input = readInput("Day05")
  println(part1(input))
  println(part2(input))
}
