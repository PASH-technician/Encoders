import ciphers.CaesarsCipher
import core.CipherBuilder

fun main(args: Array<String>) {
    print("ШИФР ЦЕЗАРЯ\n")
    CipherBuilder(ALPHABET, CaesarsCipher.Factory())
        .setText("Введите текст:") { readln() }
        .setKey("Введите ключ:") { readln() }
        .build()
        .apply {
            print("Зашифровать(з) или расшифровать(р):")
            println(
                when (readln()) {
                    "з" -> encode()
                    "р" -> decode()
                    else -> ""
                }
            )
        }
}