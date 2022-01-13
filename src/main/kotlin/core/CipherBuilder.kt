package core

class CipherBuilder<T : CipherFactory>(
    private val alphabet: String,
    private val cipherFactory: T
) {
    private var text: String? = null
    private var key: String? = null

    fun setText(invitationText: String, getString: () -> String) = apply {
        while (true) {
            println(invitationText)
            val resultText = getString()
            val resultBadList = resultText.lowercase().filter { it !in alphabet }
            if (resultBadList.isNotEmpty()) {
                print("Этих символов нет в алфавите: ${resultBadList.toList()}\n")
            } else {
                if (resultText.isNotBlank()) {
                    if (cipherFactory.checkingText(resultText)) {
                        text = resultText
                        break
                    }
                    else print("Текст введён не корректно!\n")
                } else print("Поле не должно быть пустым!\n")
            }
        }
    }

    fun setKey(invitationText: String, getString: () -> String) = apply {
        while (true) {
            print(invitationText)
            val resultKey = getString()
            if (resultKey.isNotBlank()) {
                if (cipherFactory.verificationKey(resultKey)) {
                    key = resultKey
                    break
                }
                else print("Ключ введён не корректно!\n")
            } else {
                print("Поле не должно быть пустым!\n")
            }
        }
    }

    fun build(): Cipher {
        return if (text == null) throw NullPointerException("Не задан текст (setText(value: String))!")
        else if (key == null) throw NullPointerException("Не задан ключ (setKey(value: String))!")
        else cipherFactory.createCipher(text!!, key!!, alphabet)
    }
}