package core

abstract class Cipher (
    private val text: String,
    private val key: String,
    private val alphabet: String
) {
    abstract fun encode(): String
    abstract fun decode(): String
}