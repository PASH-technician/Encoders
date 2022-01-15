
import ciphers.CaesarsCipher
import core.CipherBuilder

fun main(args: Array<String>) {
    print("ШИФР ЦЕЗАРЯ\n")
    CipherBuilder(ALPHABET, CaesarsCipher.Factory())
        .setText("Введите слово:") { readln() }
        .setKey("Введите ключ:") { readln() }
        .build()
        .apply {
            print("Зашифровать(e) или расшифровать(d):")
            println(
                when (readln()) {
                    "e" -> encode()
                    "d" -> decode()
                    else -> ""
                }
            )
        }
}