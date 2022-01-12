const val alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"

fun main() {
    var answer: String
    while (true) {
        print("Зашифровать(e) или расшифровать(d):")
        answer = readln().uppercase()
        if (answer == "E" || answer == "D") {
            val text = getText(
                "Введите текст:",
                alphabet.toList()
            ) { readln() }

            val key = getKey("Введите ключ:") { readln() }

            println(
                if (answer == "E") alphabet.encrypt(text, key.toInt())
                else alphabet.decrypt(text, key.toInt())
            )
        }

        print("Выйти(y):")
        if (readln().lowercase() == "y") return
    }
}

fun getKey(invitationText: String, getString: () -> String): String {
    while (true) {
        print(invitationText)
        val key = getString()
        if (key.isNotBlank()) {
            if (key.toIntOrNull() != null) return key
            else print("Ключ введён не корректно!\n")
        } else {
            print("Поле не должно быть пустым!\n")
        }
    }
}

fun getText(invitationText: String, listChar: List<Char>, getString: () -> String): String {
    while (true) {
        println(invitationText)
        val text = getString()
        val resultBadList = text.lowercase().filter { it !in listChar }
        if (resultBadList.isNotEmpty()) {
            print("Этих символов нет в алфавите: $resultBadList\n")
        } else {
            if (text.isNotBlank()) return text
            else print("Поле не должно быть пустым!\n")
        }
    }
}

private fun String.encrypt(text: String, key: Int): String {
    return String(
        text.lowercase().mapIndexed { index, c ->
            val i = (this.indexOf(c) + key) % this.length
            val test = this[
                    if (i < 0) this.length + i
                    else i
            ]
            test
        }.toCharArray()
    )
}

private fun String.decrypt(text: String, key: Int): String {
    return String(
        text.lowercase().mapIndexed { index, c ->
            val i = (this.indexOf(c) - (key)) % this.length
            val test = this[
                    if (i < 0) this.length + i
                    else i
            ]
            test
        }.toCharArray()
    )
}
