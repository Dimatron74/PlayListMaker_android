package com.practicum.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    // Метод onCreate вызывается при создании Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Устанавливаем layout для данной Activity

        // Находим кнопку поиска по её идентификатору и присваиваем её переменной
        val buttonSearch: View = findViewById<Button>(R.id.button_search)

        // Способ 1: использование анонимного класса для обработки клика
        // В этом примере создается анонимный класс, который реализует интерфейс View.OnClickListener.
        // При клике на кнопку "Search" будет создан Intent для перехода на экран SearchActivity.
        val buttonSearchClickListener: View.OnClickListener = View.OnClickListener {
            // Создаем Intent для перехода на другую Activity (SearchActivity)
            val searchIntent = Intent(this, SearchActivity::class.java)
            // Запускаем Activity с помощью метода startActivity
            startActivity(searchIntent)
        }
        // Устанавливаем обработчик клика для кнопки
        buttonSearch.setOnClickListener(buttonSearchClickListener)

        // Способ 2: использование лямбда-выражения для обработки клика
        // Лямбда-выражение позволяет сократить код для создания обработчика клика
        val buttonLibrary: View = findViewById(R.id.button_library)
        buttonLibrary.setOnClickListener {
            // Создаем Intent для перехода на экран MediaActivity
            startActivity(Intent(this, MediaActivity::class.java))
        }

        // Способ 1: еще раз анонимный класс, но на этот раз для кнопки настроек
        val buttonSettings: View = findViewById(R.id.button_settings)
        val buttonSettingsClicklistener: View.OnClickListener = object : View.OnClickListener {
            // Переопределяем метод onClick для обработки клика
            override fun onClick(v: View?) {
                // Создаем Intent для перехода на экран настроек (SettingsActivity)
                val settingsActivity = Intent(this@MainActivity, SettingsActivity::class.java)
                // Запускаем Activity
                startActivity(settingsActivity)
            }
        }
        // Устанавливаем обработчик клика для кнопки настроек
        buttonSettings.setOnClickListener(buttonSettingsClicklistener)
    }
}
