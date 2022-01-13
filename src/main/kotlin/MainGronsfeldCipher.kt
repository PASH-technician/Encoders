import ciphers.CaesarsCipher
import core.CipherBuilder
//Шифр Гронсфельда
fun main(args: Array<String>) {
    print("ШИФР ГРОНСФЕЛЬДА\n")
    CipherBuilder(
        ALPHABET,
        CaesarsCipher.Factory()
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
}