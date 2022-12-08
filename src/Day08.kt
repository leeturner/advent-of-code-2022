typealias Grid = List<List<Int>>

data class Coordinate(val row: Int, val col: Int)

fun List<String>.parseGrid(): Grid {
  return this.map { line -> line.map { tree -> tree.digitToInt() } }
}

fun Grid.isEdge(coordinate: Coordinate): Boolean {
  return coordinate.row == 0 ||
      coordinate.row == this.size - 1 ||
      coordinate.col == 0 ||
      coordinate.col == this.first().size - 1
}

fun Grid.visibleOnRow(coordinate: Coordinate): Boolean {
  val tree = this[coordinate.row][coordinate.col]
  return this[coordinate.row].subList(0, coordinate.col).max() < tree ||
      this[coordinate.row].subList(coordinate.col + 1, this[coordinate.row].size).max() < tree
}

fun Grid.visibleOnColumn(coordinate: Coordinate): Boolean {
  val tree = this[coordinate.row][coordinate.col]
  val column = this.map { it[coordinate.col] }
  return column.subList(0, coordinate.row).max() < tree ||
      column.subList(coordinate.row + 1, column.size).max() < tree
}

fun Grid.visibleTrees(): List<Int> {
  val visible = mutableListOf<Int>()
  this.forEachIndexed { rowIndex, row ->
    row.forEachIndexed { colIndex, col ->
      if (isEdge(Coordinate(rowIndex, colIndex))) {
        visible.add(col)
      } else if (visibleOnRow(Coordinate(rowIndex, colIndex))) {
        visible.add(col)
      } else if (visibleOnColumn(Coordinate(rowIndex, colIndex))) visible.add(col)
    }
  }
  return visible
}

fun Grid.calculateScore(coordinate: Coordinate): Int {
  val tree = this[coordinate.row][coordinate.col]
  val column = this.map { it[coordinate.col] }

  // look left
  val leftSection = this[coordinate.row].subList(0, coordinate.col)
  val left =
      if (leftSection.indexOfLast { it >= tree } != -1)
          leftSection.size - leftSection.indexOfLast { it >= tree }
      else leftSection.size

  // look right
  val rightSection = this[coordinate.row].subList(coordinate.col + 1, this[coordinate.row].size)
  val right =
      if (rightSection.indexOfFirst { it >= tree } != -1)
          rightSection.indexOfFirst { it >= tree } + 1
      else rightSection.size

  // look up
  val upSection = column.subList(0, coordinate.row)
  val up =
      if (upSection.indexOfLast { it >= tree } != -1)
          coordinate.row - upSection.indexOfLast { it >= tree }
      else upSection.size

  // look down
  val downSection = column.subList(coordinate.row + 1, column.size)
  val down =
      if (downSection.indexOfFirst { it >= tree } != -1) downSection.indexOfFirst { it >= tree } + 1
      else downSection.size

  return left * right * up * down
}

fun Grid.scenicScores(): List<Int> {
  val scores = mutableListOf<Int>()
  this.forEachIndexed { rowIndex, row ->
    row.forEachIndexed { colIndex, col ->
      scores.add(calculateScore(Coordinate(rowIndex, colIndex)))
    }
  }
  return scores
}

fun main() {

  fun part1(input: List<String>): Int {
    return input.parseGrid().visibleTrees().size
  }

  fun part2(input: List<String>): Int {
    return input.parseGrid().scenicScores().max()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day08_test")
  check(part1(testInput) == 21)
  check(part2(testInput) == 8)

  val input = readInput("Day08")
  println(part1(input)) // 1801
  println(part2(input)) // 209880
}
