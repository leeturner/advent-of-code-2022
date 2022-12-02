import DesiredResult.*
import GameChoice.*

typealias Game = Pair<GameChoice, GameChoice>

typealias FixedGame = Pair<GameChoice, DesiredResult>

enum class GameChoice(val score: Int) {
  ROCK(1),
  PAPER(2),
  SCISSORS(3)
}

enum class DesiredResult {
  WIN,
  DRAW,
  LOSE
}

fun String.toGameChoice() =
    when (this) {
      in listOf("A", "X") -> ROCK
      in listOf("B", "Y") -> PAPER
      in listOf("C", "Z") -> SCISSORS
      else -> throw IllegalArgumentException("GameChoice character not supported - $this")
    }

fun String.toDesiredResult() =
    when (this) {
      "X" -> LOSE // need to lose
      "Y" -> DRAW // need draw
      "Z" -> WIN // need to win
      else -> throw IllegalArgumentException("DesiredResult character not supported - $this")
    }

fun DesiredResult.toGameChoice(opponent: GameChoice): GameChoice {
  return when (this) {
    WIN -> {
      when (opponent) {
        ROCK -> PAPER
        PAPER -> SCISSORS
        SCISSORS -> ROCK
      }
    }
    DRAW -> {
      when (opponent) {
        ROCK -> ROCK
        PAPER -> PAPER
        SCISSORS -> SCISSORS
      }
    }
    LOSE -> {
      when (opponent) {
        ROCK -> SCISSORS
        PAPER -> ROCK
        SCISSORS -> PAPER
      }
    }
  }
}

fun Game.isDraw() = this.first == this.second

fun Game.isWin() =
    when {
      this.second == ROCK && this.first == SCISSORS -> true
      this.second == PAPER && this.first == ROCK -> true
      this.second == SCISSORS && this.first == PAPER -> true
      else -> false
    }

fun main() {
  fun part1(input: List<String>): Int {
    return input
        .map { gameString -> gameString.split(' ').map { it.toGameChoice() } }
        .map { Game(it[0], it[1]) }
        .sumOf { game ->
          when {
            game.isWin() -> 6 + game.second.score
            game.isDraw() -> 3 + game.second.score
            else -> 0 + game.second.score
          }
        }
  }

  fun part2(input: List<String>): Int {
    return input
        .map { gameString -> gameString.split(' ') }
        .map { FixedGame(it[0].toGameChoice(), it[1].toDesiredResult()) }
        .map { Game(it.first, it.second.toGameChoice(it.first)) }
        .sumOf { game ->
          when {
            game.isWin() -> 6 + game.second.score
            game.isDraw() -> 3 + game.second.score
            else -> 0 + game.second.score
          }
        }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day02_test")
  check(part1(testInput) == 15)
  check(part2(testInput) == 12)

  val input = readInput("Day02")
  println(part1(input))
  println(part2(input))
}
