import ciphers.CaesarsCipher
import ciphers.GronsfeldCipher
import core.CipherBuilder

const val ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"

fun main() {
    var answer: String
    while (true) {
        print("Как шифровать или расшифровывать(Шифр Цезаря(ц), Шифр Гронсфельда(г)):")
        val answer = readln().lowercase()
        if (answer == "ц" || answer == "г")
            CipherBuilder(
                ALPHABET,
                when (answer) {
                    "ц" -> CaesarsCipher.Factory()
                    "г" -> GronsfeldCipher.Factory()
                    else -> throw IllegalArgumentException()
                }
            )
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

        print("Выйти(д):")
        if (readln().lowercase() == "y") return
    }
}