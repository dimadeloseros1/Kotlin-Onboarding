package jetbrains.kotlin.course.almost.done

fun main() {
    println(applySquaredFilter(cat))
}

fun trimPicture(picture: String): String {
    return picture.trimIndent()
}

fun applyBordersFilter(picture: String): String {
    val trimmedPicture = trimPicture(picture)
    val pictureWidth = getPictureWidth(trimmedPicture)
    val result = StringBuilder()
    val borders = borderSymbol.toString().repeat(pictureWidth + 4)

    result.append(borders)
    result.append(newLineSymbol)

    for (line in trimmedPicture.lines()) {
        val paddedLine = line.padEnd(pictureWidth, separator)
        result.append("$borderSymbol$separator$paddedLine$separator$borderSymbol")
        result.append(newLineSymbol)
    }

    result.append(borders)

    return result.toString()
}


fun applySquaredFilter(picture: String): String {
    val borderedImageLines = applyBordersFilter(picture).lines()

    val doubledLines = borderedImageLines.map { line ->
        line + line
    }

    return (doubledLines + doubledLines.drop(1)).joinToString(newLineSymbol)
}

fun applyFilter(picture: String, filter: String): String {
    val trimmedPic = trimPicture(picture)

    return when (filter) {
        "borders" -> applyBordersFilter(trimmedPic)
        "squared" -> applySquaredFilter(trimmedPic)
        else -> error("Unknown filter: $filter")
    }
}

fun safeReadLine(): String {
    val a: String? = readlnOrNull()

    if (a != null) {
        return a
    } else {
        error("The value is null")
    }
}

fun chooseFilter(): String {
    do {
        println("Please choose the filter: 'borders' or 'squared'.")
        when (val input = safeReadLine()) {
            "borders", "squared" -> {
                return input
            }

            else -> {
                println("Please input 'borders' or 'squared'")
            }
        }
    } while (true)
}

fun choosePicture(): String {
    val pictures = allPictures()
    do {
        println("Please choose a picture. The possible options are: $pictures")
        val pictureByName = getPictureByName()
        when (val input: String = safeReadLine()) {
            "spongeBob", "simba", "brianGriffin", "cat", "pig", "fox", "monkey", "elephant", "android", "apple" -> {
                return input
            }

            else -> println("Please choose again")
        }

    } while (true)
}