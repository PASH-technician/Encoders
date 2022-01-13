import ciphers.CaesarsCipher
import core.CipherBuilder

const val ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"

fun main() {
    var answer: String
    while (true) {
        CipherBuilder(ALPHABET, CaesarsCipher.Factory())
            .setText("Введите текст:") { readln() }
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

        print("Выйти(y):")
        if (readln().lowercase() == "y") return
    }
}