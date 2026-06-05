package jetbrains.kotlin.course.hangman

// You will use this function later
fun getGameRules(wordLength: Int, maxAttemptsCount: Int) = "Welcome to the game!$newLineSymbol$newLineSymbol" +
        "In this game, you need to guess the word made by the computer.$newLineSymbol" +
        "The hidden word will appear as a sequence of underscores, one underscore means one letter.$newLineSymbol" +
        "You have $maxAttemptsCount attempts to guess the word.$newLineSymbol" +
        "All words are English words, consisting of $wordLength letters.$newLineSymbol" +
        "Each attempt you should enter any one letter,$newLineSymbol" +
        "if it is in the hidden word, all matches will be guessed.$newLineSymbol$newLineSymbol" +
        "" +
        "For example, if the word \"CAT\" was guessed, \"_ _ _\" will be displayed first, " +
        "since the word has 3 letters.$newLineSymbol" +
        "If you enter the letter A, you will see \"_ A _\" and so on.$newLineSymbol$newLineSymbol" +
        "" +
        "Good luck in the game!"

// You will use this function later
fun isWon(complete: Boolean, attempts: Int, maxAttemptsCount: Int) = complete && attempts <= maxAttemptsCount

// You will use this function later
fun isLost(complete: Boolean, attempts: Int, maxAttemptsCount: Int) = !complete && attempts >= maxAttemptsCount

fun isComplete(secret: String, currentGuess: String): Boolean {
    var trimmedLetters = currentGuess.replace(" ", "")

    if (trimmedLetters == secret) {
        return true
    } else {
        return false
    }
}

fun generateNewUserWord(secret: String, guess: Char, currentUserWord: String = separator): String {
    var result = "";
    for (i in secret.indices) {
        if (guess == secret[i]) {
            result += "${secret[i]}$separator"
        } else {
            result += "${currentUserWord[i * 2]}$separator"
        }
    }

    return result.removeSuffix(separator)
}

fun generateSecret(): String {
    val randomWord = words.random()
    return randomWord
}

fun getHiddenSecret(wordLength: Int): String {
    var result = "";
    for (i in 0 until wordLength) {
        result += "${underscore}${separator}"
    }

    return result.removeSuffix(separator)
}

fun isCorrectInput(userInput: String): Boolean {
    if (userInput.length != 1) {
        println("The length of your guess should be 1! Try again!")
        return false
    }

    if (!userInput[0].isLetter()) {
        println("You should input only English letters! Try again!")
        return false
    }
    return true
}

fun safeUserInput(): Char {
    var guess: String
    do {
        println("Please input your guess.")
        guess = safeReadLine().uppercase()
    } while (!isCorrectInput(guess))

    return guess[0];
}

fun getRoundResults(secret: String, guess: Char, currentUserWord: String): String {
    var newUserWord = generateNewUserWord(secret, guess, currentUserWord);

    if (guess !in secret) {
        println("Sorry, the secret does not contain the symbol: $guess. The current word is $currentUserWord")
    } else {
        println("Great! This letter is in the word! The current word is $newUserWord")
    }
    return newUserWord;
}

fun playGame(secret: String, maxAttemptsCount: Int): Unit {
//    val userInput = safeUserInput()
    var currentUserWord = getHiddenSecret(secret.length)
    var attempts = 0

    do {
        val guess = safeUserInput()
        currentUserWord = getRoundResults(secret, guess, currentUserWord)
        attempts++
    } while (!isComplete(secret, currentUserWord) && attempts < maxAttemptsCount)

    if (isLost(isComplete(secret, currentUserWord), attempts, maxAttemptsCount)) {
        println("Sorry, you lost! My word is $secret")
    }
    else {
        println("Congratulations! You guessed it!")
    }
}



fun main() {
    println(getGameRules(wordLength, maxAttemptsCount))
    println(playGame(generateSecret(), maxAttemptsCount))
}

