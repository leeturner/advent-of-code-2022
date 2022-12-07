fun main() {

  class Dir(val parent: Dir?, val name: String, val children: MutableList<Dir> = mutableListOf()) {
    var size: Long = 0
    val totalSize: Long
      get() = size + children.sumOf { it.totalSize }

    override fun toString(): String {
      return "Dir($name) { size=$size, totalSize=$totalSize }"
    }
  }
  
  fun List<String>.parseDirectories(): List<Dir> {
    val root = Dir(null, "/")
    val directories = mutableListOf(root)
    var currentDir: Dir = root
    this.drop(1).forEach { cliOutput ->
      when {
        cliOutput == "$ cd .." -> currentDir = currentDir.parent ?: error("Parent not available")
        cliOutput.startsWith("$ cd") -> {
          val newDir = Dir(currentDir, cliOutput.substringAfterLast(" "))
          currentDir.children.add(newDir)
          directories.add(newDir)
          currentDir = newDir
        }
        cliOutput[0].isDigit() -> currentDir.size += cliOutput.substringBefore(" ").toLong()
      }
    }
    return directories
  }

  fun part1(input: List<String>): Long {
    return input.parseDirectories().filter { it.totalSize <= 100000 }.sumOf { it.totalSize }
  }

  fun part2(input: List<String>): Long {
    val totalDiskSpace = 70000000
    val diskSpaceRequired = 30000000
    val directories = input.parseDirectories()
    val totalSpaceUsed = directories.first().totalSize
    val missing = diskSpaceRequired - (totalDiskSpace - totalSpaceUsed)
    return directories.filter { it.totalSize >= missing }.minOf { it.totalSize }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day07_test")
  check(part1(testInput) == 95437L)
  check(part2(testInput) == 24933642L)

  val input = readInput("Day07")
  println(part1(input))
  println(part2(input))
}
