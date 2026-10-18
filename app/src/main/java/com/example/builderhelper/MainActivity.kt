package com.example.builderhelper

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import com.example.builderhelper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isDarkMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Настройка шторки бокового меню
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.nav_home, R.string.nav_home
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Кнопка «Поделиться»
        binding.btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text))
            }
            startActivity(Intent.createChooser(shareIntent, "Поделиться приложением"))
        }

        // Переключение темной / светлой темы
        binding.btnToggleTheme.setOnClickListener {
            isDarkMode = !isDarkMode
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Навигация из бокового меню
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.calc_wall_brick -> showBrickCalculator()
                R.id.calc_paint -> showPaintCalculator()
                R.id.calc_concrete -> showConcreteCalculator()
                R.id.calc_drywall -> showDrywallCalculator()
                R.id.calc_flooring -> showFlooringCalculator()
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Нижнее меню
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    showBrickCalculator()
                    true
                }
                R.id.nav_about -> {
                    showAboutScreen()
                    true
                }
                R.id.nav_privacy -> {
                    showPrivacyScreen()
                    true
                }
                else -> false
            }
        }

        // По умолчанию открываем калькулятор кирпича
        showBrickCalculator()
    }

    // Вспомогательный метод создания поля ввода
    private fun createInput(hint: String): EditText {
        return EditText(this).apply {
            this.hint = hint
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            setBackgroundColor(resources.getColor(R.color.white, null))
            setPadding(32, 32, 32, 32)
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, 0, 0, 16) }
        }
    }

    // Вспомогательный метод создания кнопки
    private fun createButton(text: String, onClick: () -> Unit): Button {
        return Button(this).apply {
            this.text = text
            setBackgroundColor(resources.getColor(R.color.green_primary, null))
            setTextColor(resources.getColor(R.color.white, null))
            setPadding(24, 24, 24, 24)
            setOnClickListener { onClick() }
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, 16, 0, 24) }
        }
    }

    // Вспомогательный метод текстового результата
    private fun createResultView(): TextView {
        return TextView(this).apply {
            textSize = 16f
            setTextColor(resources.getColor(R.color.text_dark, null))
            setBackgroundColor(resources.getColor(R.color.green_card_bg, null))
            setPadding(32, 32, 32, 32)
        }
    }

    // 1. Калькулятор кирпича с учётом вычета окон и дверей
    private fun showBrickCalculator() {
        binding.calculatorContainer.removeAllViews()
        
        val title = TextView(this).apply {
            text = "🧱 Калькулятор Кирпича и Блоков (с Вычетом Окон/Дверей)"
            textSize = 18f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setPadding(0, 0, 0, 24)
        }

        val inputLength = createInput("Длина стен (м)")
        val inputHeight = createInput("Высота стен (м)")
        val inputWindows = createInput("Площадь окон и дверей для вычета (кв.м)")
        val resultView = createResultView()

        val btnCalc = createButton("Рассчитать кирпич и раствор") {
            val length = inputLength.text.toString().toDoubleOrNull() ?: 0.0
            val height = inputHeight.text.toString().toDoubleOrNull() ?: 0.0
            val minusArea = inputWindows.text.toString().toDoubleOrNull() ?: 0.0

            val totalArea = (length * height - minusArea).coerceAtLeast(0.0)
            val bricksSingle = (totalArea * 51).toInt() // Норма одинарного кирпича
            val bricksDouble = (totalArea * 26).toInt() // Норма двойного блока
            val cementBags = (totalArea * 0.22).toInt() + 1

            resultView.text = "Результаты расчёта:\n" +
                    "• Чистая площадь кладки: %.2f кв.м\n".format(totalArea) +
                    "• Одинарного кирпича (1 НФ): $bricksSingle шт.\n" +
                    "• Двойного блока (2.1 НФ): $bricksDouble шт.\n" +
                    "• Мешков цемента (50 кг): $cementBags шт."
        }

        binding.calculatorContainer.addView(title)
        binding.calculatorContainer.addView(inputLength)
        binding.calculatorContainer.addView(inputHeight)
        binding.calculatorContainer.addView(inputWindows)
        binding.calculatorContainer.addView(btnCalc)
        binding.calculatorContainer.addView(resultView)
    }

    // 2. Калькулятор ЛКМ, колер и дозировка
    private fun showPaintCalculator() {
        binding.calculatorContainer.removeAllViews()

        val title = TextView(this).apply {
            text = "🎨 Калькулятор ЛКМ (Краска, Грунтовка, Колеровка)"
            textSize = 18f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setPadding(0, 0, 0, 24)
        }

        val inputArea = createInput("Площадь покраски (кв.м)")
        val inputLayers = createInput("Количество слоев (стандарт 2)")
        val resultView = createResultView()

        val btnCalc = createButton("Рассчитать ЛКМ и Колер") {
            val area = inputArea.text.toString().toDoubleOrNull() ?: 0.0
            val layers = inputLayers.text.toString().toIntOrNull() ?: 2

            val paintLiters = area * 0.15 * layers
            val primerLiters = area * 0.1
            val colorMl = paintLiters * 20 // ~20 мл колера на литр краски

            resultView.text = "Расход материалов:\n" +
                    "• Краска эмаль/водно-дисп.: %.2f л\n".format(paintLiters) +
                    "• Грунтовка глубокого проникновения: %.2f л\n".format(primerLiters) +
                    "• Колер пигментный: %.0f мл".format(colorMl)
        }

        binding.calculatorContainer.addView(title)
        binding.calculatorContainer.addView(inputArea)
        binding.calculatorContainer.addView(inputLayers)
        binding.calculatorContainer.addView(btnCalc)
        binding.calculatorContainer.addView(resultView)
    }

    // 3. Калькулятор бетона и фундамента
    private fun showConcreteCalculator() {
        binding.calculatorContainer.removeAllViews()

        val title = TextView(this).apply {
            text = "🏗️ Калькулятор Бетона и Компонентов"
            textSize = 18f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setPadding(0, 0, 0, 24)
        }

        val inputVol = createInput("Объем заливки фундамента / плиты (куб.м)")
        val resultView = createResultView()

        val btnCalc = createButton("Рассчитать состав М300") {
            val vol = inputVol.text.toString().toDoubleOrNull() ?: 0.0

            val cementKg = vol * 350
            val sandKg = vol * 800
            val crushedStoneKg = vol * 1200

            resultView.text = "Для получения %.2f куб.м бетона М300 нужно:\n".format(vol) +
                    "• Цемент М500: %.0f кг (%d мешков по 50кг)\n".format(cementKg, (cementKg / 50).toInt() + 1) +
                    "• Песок речной: %.0f кг (%.2f т)\n".format(sandKg, sandKg / 1000) +
                    "• Щебень (фракция 10-20): %.0f кг (%.2f т)".format(crushedStoneKg, crushedStoneKg / 1000)
        }

        binding.calculatorContainer.addView(title)
        binding.calculatorContainer.addView(inputVol)
        binding.calculatorContainer.addView(btnCalc)
        binding.calculatorContainer.addView(resultView)
    }

    // 4. Калькулятор ГКЛ и профилей
    private fun showDrywallCalculator() {
        binding.calculatorContainer.removeAllViews()

        val title = TextView(this).apply {
            text = "📐 Калькулятор Гипсокартона и Профилей"
            textSize = 18f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setPadding(0, 0, 0, 24)
        }

        val inputArea = createInput("Площадь стен/потолка под ГКЛ (кв.м)")
        val resultView = createResultView()

        val btnCalc = createButton("Рассчитать каркас и ГКЛ") {
            val area = inputArea.text.toString().toDoubleOrNull() ?: 0.0

            val gklSheets = (area / 3.0 * 1.1).toInt() + 1 // Лист 2.5х1.2м + 10% запас
            val profileCD = (area * 2.0).toInt()
            val profileUD = (area * 0.7).toInt()

            resultView.text = "Материалы для ГКЛ (%.2f кв.м):\n".format(area) +
                    "• Листы ГКЛ (1200х2500мм): $gklSheets шт.\n" +
                    "• Профиль стоечный / потолочный (CD/ПП): $profileCD м\n" +
                    "• Профиль направляющий (UD/ПН): $profileUD м"
        }

        binding.calculatorContainer.addView(title)
        binding.calculatorContainer.addView(inputArea)
        binding.calculatorContainer.addView(btnCalc)
        binding.calculatorContainer.addView(resultView)
    }

    // 5. Плитка и Ламинат
    private fun showFlooringCalculator() {
        binding.calculatorContainer.removeAllViews()

        val title = TextView(this).apply {
            text = "🪵 Калькулятор Плитки, Ламината и Штукатурки"
            textSize = 18f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setPadding(0, 0, 0, 24)
        }

        val inputArea = createInput("Площадь пола/стен (кв.м)")
        val resultView = createResultView()

        val btnCalc = createButton("Рассчитать подполье и отделку") {
            val area = inputArea.text.toString().toDoubleOrNull() ?: 0.0

            val laminatePacks = (area * 1.08 / 2.0).toInt() + 1 // 2 кв.м в пачке + 8% подрезка
            val tileGlueBags = (area * 4.5 / 25).toInt() + 1 // 4.5кг/м2
            val plasterBags = (area * 8.5 / 30).toInt() + 1 // Штукатурка при слое 10мм

            resultView.text = "Расход на покрытие (%.2f кв.м):\n".format(area) +
                    "• Упаковок ламината: $laminatePacks пач.\n" +
                    "• Клей для плитки (25кг): $tileGlueBags меш.\n" +
                    "• Штукатурка гипсовая (30кг): $plasterBags меш."
        }

        binding.calculatorContainer.addView(title)
        binding.calculatorContainer.addView(inputArea)
        binding.calculatorContainer.addView(btnCalc)
        binding.calculatorContainer.addView(resultView)
    }

    // Экран О Нас
    private fun showAboutScreen() {
        binding.calculatorContainer.removeAllViews()
        val view = TextView(this).apply {
            text = "ℹ️ О ПРИЛОЖЕНИИ «ПОМОГАТОР СТРОИТЕЛЯ»\n\n" +
                    "Данное приложение создано как профессиональный инженерный помощник для прорабов, мастеров, строителей и тех, кто делает ремонт своими руками.\n\n" +
                    "Возможности:\n" +
                    "• Точный расчёт кирпичной кладки с учётом вычета оконных и дверных проёмов.\n" +
                    "• Расчёт лакокрасочных материалов, слоев, грунтовки и пигментной колеровки.\n" +
                    "• Подбор пропорций компонентов бетона (цемент, песок, щебень) под объём.\n" +
                    "• Расчёт гипсокартонных систем, направляющих профилей и крепежа.\n" +
                    "• Подсчёт плитки, ламината, плиточного клея и штукатурных смесей."
            textSize = 16f
            setPadding(16, 16, 16, 16)
            setTextColor(resources.getColor(R.color.text_dark, null))
        }
        binding.calculatorContainer.addView(view)
    }

    // Экран Privacy Policy
    private fun showPrivacyScreen() {
        binding.calculatorContainer.removeAllViews()
        val view = TextView(this).apply {
            text = "🔒 ПОЛИТИКА КОНФИДЕНЦИАЛЬНОСТИ\n\n" +
                    "1. Сбор данных: Приложение «Помогатор строителя» НЕ собирает, НЕ хранит и НЕ передаёт персональные данные пользователей на сторонние сервера.\n\n" +
                    "2. Разрешения: Приложение работает полностью в автономном режиме (оффлайн) и не требует доступа к контактам, геопозиции или файловой системе устройства.\n\n" +
                    "3. Расчёты: Все математические расчёты производятся исключительно на вашем устройстве локально."
            textSize = 16f
            setPadding(16, 16, 16, 16)
            setTextColor(resources.getColor(R.color.text_dark, null))
        }
        binding.calculatorContainer.addView(view)
    }
}
