package ciphers

import core.Cipher
import core.CipherFactory

class CaesarsCipher private constructor(
    private val text: String,
    private val key: String,
    private val alphabet: String
) : Cipher(text, key, alphabet) {
    override fun encode(): String {
        return String(
            text.lowercase().map { c ->
                val i = (alphabet.indexOf(c) + key.toInt()) % alphabet.length
                alphabet[
                        if (i < 0) alphabet.length + i
                        else i
                ]
            }.toCharArray()
        )
    }

    override fun decode(): String {
        return String(
            text.lowercase().map { c ->
                val i = (alphabet.indexOf(c) - key.toInt()) % alphabet.length
                alphabet[
                        if (i < 0) alphabet.length + i
                        else i
                ]
            }.toCharArray()
        )
    }

    class Factory() : CipherFactory {
        override fun createCipher(
            text: String,
            key: String,
            alphabet: String
        ) = CaesarsCipher(text, key, alphabet)

        override fun verificationKey(key: String): Boolean {
            return key.toIntOrNull() != null
        }
    }
}
