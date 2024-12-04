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
    // Переменная для хранения текста, введенного пользователем
    private var userInputText: String = ""

    // Константа для ключа Bundle, который используется для сохранения текста в состоянии Activity
    companion object {
        const val USERTEXT = "USER_INPUT" // Константа для ключа сохранения текста в Bundle
    }

    // Метод onCreate вызывается при создании Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search) // Устанавливаем layout для данной Activity

        // Инициализация элементов пользовательского интерфейса (UI)
        val editTextSearchActivity = findViewById<EditText>(R.id.search_activity_edittext) // Поле ввода для поиска
        val searchClearEdittextImageview = findViewById<ImageView>(R.id.search_clear_edittext_imageview) // Кнопка очистки текста
        val settingsArrowBack = findViewById<androidx.appcompat.widget.Toolbar>(R.id.search_activity_toolbar) // Кнопка "Назад" в Toolbar

        // Устанавливаем слушатель для кнопки очистки (крестик), которая очищает поле ввода
        searchClearEdittextImageview.setOnClickListener {
            // Очищаем текст в поле ввода
            editTextSearchActivity.setText("")
            // Проверяем, есть ли в данный момент активное поле ввода
            val view: View? = this.currentFocus
            // Если фокус есть, то скрываем клавиатуру
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        // Создание и добавление TextWatcher для отслеживания изменений в поле ввода
        val simpleTextWatcher = object : TextWatcher {
            // Метод вызывается до изменения текста
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Этот метод остается пустым, так как в нем нет необходимости производить действия
            }

            // Метод вызывается при изменении текста в поле ввода
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Меняем видимость кнопки очистки в зависимости от того, есть ли текст в поле
                searchClearEdittextImageview.visibility = clearButtonVisibility(s)
                // Обновляем переменную с текстом, введенным пользователем
                userInputText = s.toString()
            }

            // Метод вызывается после изменения текста
            override fun afterTextChanged(s: Editable?) {
                // Этот метод также остается пустым, так как дополнительных действий не требуется
            }
        }

        // Добавляем TextWatcher к полю ввода, чтобы отслеживать изменения текста
        editTextSearchActivity.addTextChangedListener(simpleTextWatcher)

        // Устанавливаем обработчик для кнопки "Назад" в Toolbar
        settingsArrowBack.setNavigationOnClickListener {
            // Завершаем текущую Activity, то есть возвращаемся на предыдущий экран
            this.finish()
        }
    }

    // Метод для сохранения состояния Activity при изменении конфигурации (например, при повороте экрана)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Сохраняем введенный текст в состояние Activity, чтобы восстановить его позже
        outState.putString(USERTEXT, userInputText)
    }

    // Метод для восстановления состояния Activity при изменении конфигурации (например, при повороте экрана)
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Восстанавливаем текст, который был введен пользователем, из Bundle
        userInputText = savedInstanceState.getString(USERTEXT, "")
        // Устанавливаем восстановленный текст обратно в поле ввода
        findViewById<EditText>(R.id.search_activity_edittext).setText(userInputText)
    }
}

// Функция для определения видимости кнопки очистки в зависимости от состояния текста в поле ввода
private fun clearButtonVisibility(s: CharSequence?): Int {
    // Если строка пуста, то кнопка очистки скрывается
    return if (s.isNullOrEmpty()) {
        View.GONE
    } else {
        // Если строка не пуста, то кнопка очистки показывается
        View.VISIBLE
    }
}
