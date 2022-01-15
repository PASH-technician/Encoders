package ciphers

import core.Cipher
import core.CipherFactory

class GronsfeldCipher private constructor(
    private val text: String,
    private val key: String,
    private val alphabet: String
) : Cipher(text, key, alphabet) {

    private fun getCharFromAlphabet(index: Int): Char{
        return alphabet[
                if (index < 0) alphabet.length + index
                else if (index >= alphabet.length) index - alphabet.length
                else index
        ]
    }

    private fun usingCipher(word: String, isEncode: Boolean): String{
        val k = if (isEncode) 1 else -1
        return String(
            word.mapIndexed { index, c ->
                val i = alphabet.indexOf(c) + k * key[index % key.length].digitToInt()
                getCharFromAlphabet(i)
            }.toCharArray()
        )
    }

    override fun encode(): String {
        return text.split(" ").joinToString(separator = " ") {
            usingCipher(it, true)
        }
    }

    override fun decode(): String {
        return text.split(" ").joinToString(separator = " ") {
            usingCipher(it, false)
        }
    }

    class Factory() : CipherFactory {
        override fun createCipher(text: String, key: String, alphabet: String) = GronsfeldCipher(text, key, alphabet)
        override fun verificationKey(key: String): Boolean {
            return key.toIntOrNull() != null
        }
    }
}