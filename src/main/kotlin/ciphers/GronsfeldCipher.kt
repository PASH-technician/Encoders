package ciphers

import core.Cipher
import core.CipherFactory

class GronsfeldCipher private constructor(
    private val text: String,
    private val key: String,
    private val alphabet: String
) : Cipher(text, key, alphabet) {
    override fun encode(): String {
        return String(
            text.mapIndexed { index, c ->
                val i = alphabet.indexOf(c) + key[index % key.length].digitToInt()
                getCharFromAlphabet(i)
            }.toCharArray()
        )
    }

    override fun decode(): String {
        return String(
            text.mapIndexed { index, c ->
                val i = alphabet.indexOf(c) - key[index % key.length].digitToInt()
                getCharFromAlphabet(i)
            }.toCharArray()
        )
    }

    class Factory() : CipherFactory {
        override fun createCipher(text: String, key: String, alphabet: String) = GronsfeldCipher(text, key, alphabet)
        override fun verificationKey(key: String): Boolean {
            return key.toIntOrNull() != null
        }
    }
}