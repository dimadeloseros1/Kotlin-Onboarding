package jetbrains.kotlin.course.warmup

import kotlin.contracts.contract

// You will use this function later
fun getGameRules(wordLength: Int, maxAttemptsCount: Int, secretExample: String) =
    "Welcome to the game! $newLineSymbol" +
            newLineSymbol +
            "Two people play this game: one chooses a word (a sequence of letters), " +
            "the other guesses it. In this version, the computer chooses the word: " +
            "a sequence of $wordLength letters (for example, $secretExample). " +
            "The user has several attempts to guess it (the max number is $maxAttemptsCount). " +
            "For each attempt, the number of complete matches (letter and position) " +
            "and partial matches (letter only) is reported. $newLineSymbol" +
            newLineSymbol +
            "For example, with $secretExample as the hidden word, the BCDF guess will " +
            "give 1 full match (C) and 1 partial match (B)."

fun generateSecret(): String = "ABCD"

fun countExactMatches(secret: String, guess: String): Int {

    return secret.filterIndexed {index, symbol -> guess[index] == symbol }.length
}

fun countAllMatches(secret: String, guess: String): Int {
    var match = 0;

    for (symbol in secret.toSet()) {
        match += minOf(
            secret.count{ it == symbol },
            guess.count{ it == symbol }
        )
    }

    return match
}

fun countPartialMatches(secret: String, guess: String): Int {
    var allMatches: Int = countAllMatches(secret, guess)
    var partialMatches: Int = countExactMatches(secret, guess)
    return allMatches - partialMatches
}

fun printRoundResults(secret: String, guess: String): Unit {
    val fullMatches = countExactMatches(secret, guess)
    val partialMatches = countPartialMatches(secret, guess)

    println("Your guess has $fullMatches full matches and $partialMatches partial matches.")
}

fun isWon(complete: Boolean, attempts: Int, maxAttemptsCount: Int): Boolean {
    if (complete && attempts <= maxAttemptsCount) {
        return true
    }
    return false
}

fun isLost(complete: Boolean, attempts: Int, maxAttemptsCount: Int): Boolean {
    if (!complete && attempts > maxAttemptsCount) {
        return true
    }
    return false
}

fun isComplete(secret: String, guess: String): Boolean {
    return secret == guess
}

fun playGame(secret: String, wordLength: Int, maxAttemptsCount: Int) {
    var complete: Boolean = false
    var attempts = 0;

    do {
        println("Please input your guess. It should be of length $wordLength.")
        val guess = safeReadLine()
        complete = isComplete(secret, guess)
        attempts++
        var lost = isLost(complete, attempts, maxAttemptsCount)
        var won = isWon(complete, attempts, maxAttemptsCount)

        printRoundResults(secret, guess)

        if (lost) {
            println("Sorry, you lost! :( My word is $secret")
        }
        else if (won) {
            println("Congratulations! You guessed it!")
        }

    } while (!won && !lost)
}

fun main() {
    val wordLength: Int = 4
    val maxAttemptsCount: Int = 3
    val secretExample: String = "ACEB"

    println(getGameRules(wordLength, maxAttemptsCount, secretExample))
    playGame(generateSecret(), wordLength, maxAttemptsCount)
}
