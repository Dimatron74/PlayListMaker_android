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

// основной экран настроек
class SettingsActivity : AppCompatActivity() {

    // аннотация suppresslint используется для подавления предупреждений, связанных с использованием SwitchCompat.
    // метод onCreate вызывается при создании активности.
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // обработка кнопки "назад"
        val arrowSettingsBack = findViewById<ImageView>(R.id.settings_screen_arrow_back_like_button)
        // установка обработчика нажатия для кнопки "назад", который завершает активность.
        arrowSettingsBack.setOnClickListener {
            this.finish()
        }

        // переключатель темной темы
        val switchDarkTheme = findViewById<SwitchCompat>(R.id.switch_dark_theme)
        // проверка текущего состояния темной темы при старте и синхронизация состояния переключателя.
        darkThemeCheck(switchDarkTheme)
        // установка обработчика события изменения состояния переключателя.
        switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // включение темной темы
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // отключение темной темы
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // обработка кнопки "пользовательское соглашение"
        val userAgreementTextView = findViewById<TextView>(R.id.settings_screen_user_agreement_textview)
        // при нажатии открывается сайт в браузере.
        userAgreementTextView.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_offer)))
            startActivity(browserIntent)
        }

        // обработка кнопки "поделиться приложением"
        val shareApp = findViewById<TextView>(R.id.settings_screen_shareapp_textview)
        // при нажатии открывается интерфейс для поделиться ссылкой на приложение.
        shareApp.setOnClickListener {
            val shareText = getString(R.string.url_course)
            // создание и настройка Intent для отправки текста через другие приложения.
            val shareIntent = Intent.createChooser(
                Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain" // тип содержимого - текст.
                    putExtra(Intent.EXTRA_TEXT, shareText) // добавление текста для отправки.
                },
                getString(R.string.share_via) // текст для выбора способа отправки.
            )
            startActivity(shareIntent)
        }

        // обработка кнопки "написать в поддержку"
        val emailToSupport = findViewById<TextView>(R.id.settings_screen_send_mail_support_textview)
        // при нажатии создается Intent для отправки email в поддержку.
        emailToSupport.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                // создание Intent для отправки email-сообщения.
                data = Uri.parse("mailto:") // протокол для отправки email.
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_address))) // адрес получателя.
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.text_mail_subject)) // тема письма.
                putExtra(Intent.EXTRA_TEXT, getString(R.string.text_mail_body)) // тело письма.
            }
            // проверка, есть ли приложение для отправки email на устройстве.
            if (emailIntent.resolveActivity(packageManager) != null) {
                startActivity(emailIntent) // если есть, открываем приложение для отправки письма.
            } else {
                // если email-клиент не найден, показываем уведомление.
                Toast.makeText(
                    this,
                    getString(R.string.no_email_client),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // метод для проверки текущего состояния темной темы и синхронизации с состоянием переключателя
    private fun darkThemeCheck(switch: SwitchCompat) {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        // устанавливаем состояние переключателя в зависимости от текущего режима (темный или светлый).
        switch.isChecked = (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
    }
}

