package core

interface CipherFactory {
    fun createCipher(text: String, key: String, alphabet: String): Cipher
    fun checkingText(text: String) = true
    fun verificationKey(key: String) = true
}