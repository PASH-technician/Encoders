package ciphers

import core.Cipher
import core.CipherFactory

class PlayfairCipher private constructor(
    private val text: String,
    key: String,
    private val alphabet: String
) : Cipher(text, key, alphabet) {

    private fun getTransformedWord(word: String): String {
            var result = ""
            word.forEachIndexed { index, c ->
                result += if (index < word.length - 1 && word[index + 1] == c) c + "x"
                else c
            }
            if (result.length % 2 == 1) result += "x"
            return result
        }

    private val key = key
        get() {
            var result = field.filterIndexed { index, c ->
                if (index < field.length - 1)
                    c != field[index + 1]
                else true
            }
            return result
        }

    private fun searchCharFromAlphabet(key: String) = alphabet.filter { it !in key }

    private val matrix by lazy {
        val matrixString = this.key + searchCharFromAlphabet(key)
        Array(5) { row ->
            Array(5) { column ->
                val index = row * 5 + column
                matrixString[index]
            }
        }
    }

    private fun findingCoordinates(c: Char): List<Int> {
        matrix.forEachIndexed { indexRow, column ->
            column.forEachIndexed { indexColumn, char ->
                if (c == char) {
                    return listOf(indexColumn, indexRow)
                }
            }
        }
        throw NullPointerException("Нет такого символа:$c")
    }

    private fun textOf2char(word: String): List<String> {
        val resultList = mutableListOf<String>()
        word.forEachIndexed { index, c ->
            if (index % 2 == 0) {
                resultList.add(c.toString())
            } else {
                resultList[resultList.size - 1] = resultList[(index - 1) / 2] + c
            }
        }
        return resultList
    }

    private fun checkingIndex(lengthArray: Int, index: Int): Int {
        return if (index < 0) lengthArray + index
        else if (index >= lengthArray) index - lengthArray
        else index
    }

    private fun matchByX(firstCoordinates: List<Int>, secondCoordinates: List<Int>, isEncode: Boolean): String {
        val x = firstCoordinates[0]
        val y1 = firstCoordinates[1]
        val y2 = secondCoordinates[1]
        val k = if (isEncode) 1 else -1

        val nextChar1 = matrix[checkingIndex(matrix.size, y1 + k)][x]
        val nextChar2 = matrix[checkingIndex(matrix.size, y2 + k)][x]
        return String(
            charArrayOf(
                nextChar1,
                nextChar2
            )
        )
    }

    private fun matchByY(firstCoordinates: List<Int>, secondCoordinates: List<Int>, isEncode: Boolean): String {
        val y = firstCoordinates[1]
        val x1 = firstCoordinates[0]
        val x2 = secondCoordinates[0]
        val value = matrix[y]
        val k = if (isEncode) 1 else -1

        val nextChar1 = matrix[y][checkingIndex(value.size, x1 + k)]
        val nextChar2 = matrix[y][checkingIndex(value.size, x2 + k)]

        return String(
            charArrayOf(
                nextChar1,
                nextChar2
            )
        )
    }

    private fun noMatches(firstCoordinates: List<Int>, secondCoordinates: List<Int>): String {
        val x1 = firstCoordinates[0]
        val y1 = firstCoordinates[1]
        val x2 = secondCoordinates[0]
        val y2 = secondCoordinates[1]

        return String(
            charArrayOf(
                matrix[y1][x2],
                matrix[y2][x1]
            )
        )
    }

    private fun usingCipher(word: String, isEncode: Boolean): String{
        var resultEncode = ""
        textOf2char(word).map { pair  ->
            val firstCoordinates = findingCoordinates(pair[0])
            val x1 = firstCoordinates[0]
            val y1 = firstCoordinates[1]
            val secondCoordinates = findingCoordinates(pair[1])
            val x2 = secondCoordinates[0]
            val y2 = secondCoordinates[1]
            resultEncode += when {
                x1 == x2 -> matchByX(firstCoordinates, secondCoordinates, isEncode)
                y1 == y2 -> matchByY(firstCoordinates, secondCoordinates, isEncode)
                else -> noMatches(firstCoordinates, secondCoordinates)
            }
        }
        return resultEncode
    }

    override fun encode(): String {
        return this.text.split(" ").joinToString(separator = " ") {
            usingCipher(getTransformedWord(it), true)
        }
    }

    override fun decode(): String {
        return this.text.split(" ").joinToString(separator = " ") {
            usingCipher(getTransformedWord(it), false)
        }
    }

    class Factory : CipherFactory {
        override fun createCipher(text: String, key: String, alphabet: String) = PlayfairCipher(text, key, alphabet)
    }
}