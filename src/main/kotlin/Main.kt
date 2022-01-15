import ciphers.CaesarsCipher
import ciphers.GronsfeldCipher
import ciphers.PlayfairCipher
import core.CipherBuilder

const val ALPHABET = "abcdefghiklmnopqrstuvwxyz"

fun main() {
    while (true) {
        println("Алфавит: $ALPHABET")
        print("Как шифровать или расшифровывать(Шифр Цезаря(c), Шифр Гронсфельда(g), Шифр Плейфейера(p):")
        val answer = readln().lowercase()
        if (answer == "c" || answer == "g" || answer == "p")
            CipherBuilder(
                alphabet =  ALPHABET,
                cipherFactory =  when (answer) {
                    "c" -> CaesarsCipher.Factory()
                    "g" -> GronsfeldCipher.Factory()
                    "p" -> PlayfairCipher.Factory()
                    else -> throw IllegalArgumentException()
                }
            )
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