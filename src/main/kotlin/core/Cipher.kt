package core

abstract class Cipher(
    private val text: String,
    private val key: String,
    private val alphabet: String
) {
    abstract fun encode(): String
    abstract fun decode(): String
    protected fun getCharFromAlphabet(index: Int): Char{
        return alphabet[
                if (index < 0) alphabet.length + index
                else if (index >= alphabet.length) index - alphabet.length
                else index
        ]
    }
}