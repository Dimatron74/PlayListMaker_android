package com.practicum.playlistmaker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {
    // переменная для хранения текста, введенного пользователем
    private var userInputText: String = ""

    // константа для ключа Bundle, который используется для сохранения текста в состоянии Activity
    companion object {
        const val USERTEXT = "USER_INPUT" // константа для ключа сохранения текста в Bundle
    }

    // метод onCreate вызывается при создании Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search) // устанавливаем layout для данной Activity

        // инициализация элементов пользовательского интерфейса (UI)
        val editTextSearchActivity = findViewById<EditText>(R.id.search_activity_edittext) // поле ввода для поиска
        val searchClearEdittextImageview = findViewById<ImageView>(R.id.search_clear_edittext_imageview) // кнопка очистки текста
        val settingsArrowBack = findViewById<androidx.appcompat.widget.Toolbar>(R.id.search_activity_toolbar) // кнопка "Назад" в Toolbar

        // устанавливаем слушатель для кнопки очистки (крестик), которая очищает поле ввода
        searchClearEdittextImageview.setOnClickListener {
            // очищаем текст в поле ввода
            editTextSearchActivity.setText("")
            // проверяем, есть ли в данный момент активное поле ввода
            val view: View? = this.currentFocus
            // если фокус есть, то скрываем клавиатуру
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        // создание и добавление TextWatcher для отслеживания изменений в поле ввода
        val simpleTextWatcher = object : TextWatcher {
            // метод вызывается до изменения текста
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // этот метод остается пустым, так как в нем нет необходимости производить действия
            }

            // метод вызывается при изменении текста в поле ввода
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // меняем видимость кнопки очистки в зависимости от того, есть ли текст в поле
                searchClearEdittextImageview.visibility = clearButtonVisibility(s)
                // обновляем переменную с текстом, введенным пользователем
                userInputText = s.toString()
            }

            // метод вызывается после изменения текста
            override fun afterTextChanged(s: Editable?) {
                // этот метод также остается пустым, так как дополнительных действий не требуется
            }
        }

        // добавляем TextWatcher к полю ввода, чтобы отслеживать изменения текста
        editTextSearchActivity.addTextChangedListener(simpleTextWatcher)

        // устанавливаем обработчик для кнопки "Назад" в Toolbar
        settingsArrowBack.setNavigationOnClickListener {
            // завершаем текущую Activity, то есть возвращаемся на предыдущий экран
            this.finish()
        }
    }

    // метод для сохранения состояния Activity при изменении конфигурации (например, при повороте экрана)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // сохраняем введенный текст в состояние Activity, чтобы восстановить его позже
        outState.putString(USERTEXT, userInputText)
    }

    // метод для восстановления состояния Activity при изменении конфигурации (например, при повороте экрана)
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // восстанавливаем текст, который был введен пользователем, из Bundle
        userInputText = savedInstanceState.getString(USERTEXT, "")
        // устанавливаем восстановленный текст обратно в поле ввода
        findViewById<EditText>(R.id.search_activity_edittext).setText(userInputText)
    }
}

// функция для определения видимости кнопки очистки в зависимости от состояния текста в поле ввода
private fun clearButtonVisibility(s: CharSequence?): Int {
    // если строка пуста, то кнопка очистки скрывается
    return if (s.isNullOrEmpty()) {
        View.GONE
    } else {
        // если строка не пуста, то кнопка очистки показывается
        View.VISIBLE
    }
}

