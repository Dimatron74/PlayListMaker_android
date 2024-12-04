package com.practicum.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat

// Основной экран настроек
class SettingsActivity : AppCompatActivity() {

    // Аннотация SuppressLint используется для подавления предупреждений, связанных с использованием SwitchCompat.
    // Метод onCreate вызывается при создании активности.
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @RequiresApi(Build.VERSION_CODES.R) // Требуется для работы с новыми функциями Android (например, API 30+)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Обработка кнопки "Назад"
        val arrowSettingsBack = findViewById<ImageView>(R.id.settings_screen_arrow_back_like_button)
        // Установка обработчика нажатия для кнопки "Назад", который завершает активность.
        arrowSettingsBack.setOnClickListener {
            this.finish()
        }

        // Переключатель тёмной темы
        val switchDarkTheme = findViewById<SwitchCompat>(R.id.switch_dark_theme)
        // Проверка текущего состояния тёмной темы при старте и синхронизация состояния переключателя.
        darkThemeCheck(switchDarkTheme)
        // Установка обработчика события изменения состояния переключателя.
        switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Включение тёмной темы
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Отключение тёмной темы
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Обработка кнопки "Пользовательское соглашение"
        val userAgreementTextView = findViewById<TextView>(R.id.settings_screen_user_agreement_textview)
        // При нажатии открывается сайт в браузере.
        userAgreementTextView.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_offer)))
            startActivity(browserIntent)
        }

        // Обработка кнопки "Поделиться приложением"
        val shareApp = findViewById<TextView>(R.id.settings_screen_shareapp_textview)
        // При нажатии открывается интерфейс для поделиться ссылкой на приложение.
        shareApp.setOnClickListener {
            val shareText = getString(R.string.url_course)
            // Создание и настройка Intent для отправки текста через другие приложения.
            val shareIntent = Intent.createChooser(
                Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain" // Тип содержимого - текст.
                    putExtra(Intent.EXTRA_TEXT, shareText) // Добавление текста для отправки.
                },
                getString(R.string.share_via) // Текст для выбора способа отправки.
            )
            startActivity(shareIntent)
        }

        // Обработка кнопки "Написать в поддержку"
        val emailToSupport = findViewById<TextView>(R.id.settings_screen_send_mail_support_textview)
        // При нажатии создается Intent для отправки email в поддержку.
        emailToSupport.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                // Создание Intent для отправки email-сообщения.
                data = Uri.parse("mailto:") // Протокол для отправки email.
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_address))) // Адрес получателя.
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.text_mail_subject)) // Тема письма.
                putExtra(Intent.EXTRA_TEXT, getString(R.string.text_mail_body)) // Тело письма.
            }
            // Проверка, есть ли приложение для отправки email на устройстве.
            if (emailIntent.resolveActivity(packageManager) != null) {
                startActivity(emailIntent) // Если есть, открываем приложение для отправки письма.
            } else {
                // Если email-клиент не найден, показываем уведомление.
                Toast.makeText(
                    this,
                    getString(R.string.no_email_client),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // Метод для проверки текущего состояния тёмной темы и синхронизации с состоянием переключателя
    private fun darkThemeCheck(switch: SwitchCompat) {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        // Устанавливаем состояние переключателя в зависимости от текущего режима (тёмный или светлый).
        switch.isChecked = (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
    }
}
