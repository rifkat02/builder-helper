package com.example.builderhelper

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var calcTitle: TextView
    private lateinit var calcSubtitle: TextView
    
    private lateinit var lblParam1: TextView
    private lateinit var spinnerType: Spinner
    
    private lateinit var lblParam2: TextView
    private lateinit var inputArea: EditText
    
    private lateinit var lblParam3: TextView
    private lateinit var inputThickness: EditText
    
    private lateinit var lblParam4: TextView
    private lateinit var inputBagWeight: EditText
    
    private lateinit var btnCalculate: Button
    private lateinit var txtResult: TextView

    private var currentCalculator = 1 // 1: Шпаклёвка, 2: Обои, 3: Краска, 4: Колеровка, 5: Цемент, 6: Штукатурка, 7: Пеноплекс, 8: ПВХ, 9: Плитка, 10: Кирпич, 11: Потолок

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        calcTitle = findViewById(R.id.calcTitle)
        calcSubtitle = findViewById(R.id.calcSubtitle)
        
        lblParam1 = findViewById(R.id.lblParam1)
        spinnerType = findViewById(R.id.spinnerType)
        
        lblParam2 = findViewById(R.id.lblParam2)
        inputArea = findViewById(R.id.inputArea)
        
        lblParam3 = findViewById(R.id.lblParam3)
        inputThickness = findViewById(R.id.inputThickness)
        
        lblParam4 = findViewById(R.id.lblParam4)
        inputBagWeight = findViewById(R.id.inputBagWeight)
        
        btnCalculate = findViewById(R.id.btnCalculate)
        txtResult = findViewById(R.id.txtResult)

        // Меню (бургер)
        findViewById<ImageButton>(R.id.btnMenu).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Кнопка Поделиться
        findViewById<ImageButton>(R.id.btnShare).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Лучший помощник в строительстве — приложение 'Помогатор Строителя'!")
            }
            startActivity(Intent.createChooser(intent, "Поделиться"))
        }

        // Настройка языков в меню
        val languages = arrayOf("Русский", "English", "O'zbekcha", "Тоҷикӣ", "Հայերեն", "Беларуская", "ქართული", "Moldovenească", "Кыргызча", "Қазақша")
        val langAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languages)
        findViewById<Spinner>(R.id.spinnerLanguage).adapter = langAdapter

        // Установка калькулятора по умолчанию (Шпаклёвка)
        setupCalculatorUI()

        // Кнопка расчёта для всех калькуляторов
        btnCalculate.setOnClickListener {
            calculateResult()
        }

        // Кнопки бокового меню (Калькуляторы)
        findViewById<Button>(R.id.menuPutty).setOnClickListener { currentCalculator = 1; setupCalculatorUI(); drawerLayout.closeDrawers() }
        findViewById<Button>(R.id.menuWallpaper).setOnClickListener { currentCalculator = 2; setupCalculatorUI(); drawerLayout.closeDrawers() }
        findViewById<Button>(R.id.menuPaint).setOnClickListener { currentCalculator = 3; setupCalculatorUI(); drawerLayout.closeDrawers() }
        findViewById<Button>(R.id.menuColoring).setOnClickListener { currentCalculator = 4; setupCalculatorUI(); drawerLayout.closeDrawers() }
        findViewById<Button>(R.id.menuCement).setOnClickListener { currentCalculator = 5; setupCalculatorUI(); drawerLayout.closeDrawers() }
        findViewById<Button>(R.id.menuPlaster).setOnClickListener { currentCalculator = 6; setupCalculatorUI(); drawerLayout.closeDrawers() }
        findViewById<Button>(R.id.menuPenoplex).setOnClickListener { currentCalculator = 7; setupCalculatorUI(); drawerLayout.closeDrawers() }
        findViewById<Button>(R.id.menuPvc).setOnClickListener { currentCalculator = 8; setupCalculatorUI(); drawerLayout.closeDrawers() }
        findViewById<Button>(R.id.menuFloorTile).setOnClickListener { currentCalculator = 9; setupCalculatorUI(); drawerLayout.closeDrawers() }
        findViewById<Button>(R.id.menuMasonry).setOnClickListener { currentCalculator = 10; setupCalculatorUI(); drawerLayout.closeDrawers() }
        findViewById<Button>(R.id.menuCeilingTile).setOnClickListener { currentCalculator = 11; setupCalculatorUI(); drawerLayout.closeDrawers() }

        // Нижняя навигация
        findViewById<LinearLayout>(R.id.navHome).setOnClickListener {
            currentCalculator = 1
            setupCalculatorUI()
        }
        findViewById<LinearLayout>(R.id.navAbout).setOnClickListener {
            showInfoDialog("О нас", "Помогатор Строителя — ваш надежный цифровой помощник для точных расчетов строительных и отделочных материалов.")
        }
        findViewById<LinearLayout>(R.id.navContacts).setOnClickListener {
            showInfoDialog("Контакты", "По вопросам поддержки и предложениям пишите на support@builderhelper.app")
        }
        findViewById<LinearLayout>(R.id.navPrivacy).setOnClickListener {
            showInfoDialog("Политика конфиденциальности", "Приложение не собирает персональные данные и работает полностью автономно.")
        }
    }

    private fun setupCalculatorUI() {
        txtResult.visibility = View.GONE
        inputThickness.visibility = View.VISIBLE
        inputBagWeight.visibility = View.VISIBLE
        lblParam3.visibility = View.VISIBLE
        lblParam4.visibility = View.VISIBLE

        when (currentCalculator) {
            1 -> { // Шпаклёвка
                calcTitle.text = "Шпаклёвка"
                calcSubtitle.text = "Расчёт расхода шпаклёвки по типу смеси и площади"
                lblParam1.text = "Вид шпаклёвки"
                val items = arrayOf("Финишная (1.2 кг/м²/мм)", "Стартовая (1.4 кг/м²/мм)", "Гипсовая (1.0 кг/м²/мм)")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Площадь стен (м²)"
                inputArea.setText("25")
                lblParam3.text = "Толщина слоя (мм)"
                inputThickness.setText("2")
                lblParam4.text = "Вес мешка (кг)"
                inputBagWeight.setText("20")
            }
            2 -> { // Обои по стенам
                calcTitle.text = "Обои по стенам"
                calcSubtitle.text = "Расчет количества рулонов обоев"
                lblParam1.text = "Тип обоев"
                val items = arrayOf("Стандартные (0.53м x 10м)", "Широкие (1.06м x 10м)")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Площадь комнаты (м²)"
                inputArea.setText("30")
                lblParam3.visibility = View.GONE
                inputThickness.visibility = View.GONE
                lblParam4.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
            }
            3 -> { // Краска
                calcTitle.text = "Краска (все виды)"
                calcSubtitle.text = "Расчет объема краски на площадь"
                lblParam1.text = "Тип краски"
                val items = arrayOf("Водоэмульсионная (0.15 кг/м²)", "Акриловая (0.2 кг/м²)", "Масляная (0.25 кг/м²)")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Площадь окраски (м²)"
                inputArea.setText("40")
                lblParam3.text = "Количество слоев"
                inputThickness.setText("2")
                lblParam4.text = "Объем банки (л/кг)"
                inputBagWeight.setText("5")
            }
            4 -> { // Колеровка
                calcTitle.text = "Колеровка"
                calcSubtitle.text = "Расчет колер-пасты для смесей"
                lblParam1.text = "Насыщенность"
                val items = arrayOf("Пастельный (1%)", "Средний (3%)", "Насыщенный (5%)")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Объем основы (л/кг)"
                inputArea.setText("10")
                lblParam3.visibility = View.GONE
                inputThickness.visibility = View.GONE
                lblParam4.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
            }
            5 -> { // Цемент и песок
                calcTitle.text = "Цемент и песок"
                calcSubtitle.text = "Расчет компонентов для раствора"
                lblParam1.text = "Марка раствора"
                val items = arrayOf("М-150 (Цемент : Песок = 1:3)", "М-200 (Цемент : Песок = 1:2.5)")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Объем раствора (м³)"
                inputArea.setText("1")
                lblParam3.visibility = View.GONE
                inputThickness.visibility = View.GONE
                lblParam4.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
            }
            6 -> { // Декоративная штукатурка
                calcTitle.text = "Декоративная штукатурка"
                calcSubtitle.text = "Расчет фактурных покрытий"
                lblParam1.text = "Вид покрытия"
                val items = arrayOf("Короед (3 кг/м²)", "Шуба (2.5 кг/м²)", "Венецианская (1 кг/м²)")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Площадь нанесения (м²)"
                inputArea.setText("20")
                lblParam3.visibility = View.GONE
                inputThickness.visibility = View.GONE
                lblParam4.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
            }
            7 -> { // Пеноплекс
                calcTitle.text = "Обшивка пеноплексом"
                calcSubtitle.text = "Расчет утеплителя"
                lblParam1.text = "Толщина плиты"
                val items = arrayOf("30 мм", "50 мм", "100 мм")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Площадь утепления (м²)"
                inputArea.setText("25")
                lblParam3.visibility = View.GONE
                inputThickness.visibility = View.GONE
                lblParam4.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
            }
            8 -> { // ПВХ панели
                calcTitle.text = "ПВХ панели"
                calcSubtitle.text = "Расчет панелей и профилей"
                lblParam1.text = "Размер панели"
                val items = arrayOf("Стандарт (0.25м x 2.7м)")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Площадь стен (м²)"
                inputArea.setText("18")
                lblParam3.visibility = View.GONE
                inputThickness.visibility = View.GONE
                lblParam4.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
            }
            9 -> { // Напольная плитка
                calcTitle.text = "Напольная плитка"
                calcSubtitle.text = "Расчет плитки и запаса"
                lblParam1.text = "Способ укладки"
                val items = arrayOf("Прямая (запас +5%)", "Диагональная (запас +15%)")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Площадь пола (м²)"
                inputArea.setText("15")
                lblParam3.visibility = View.GONE
                inputThickness.visibility = View.GONE
                lblParam4.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
            }
            10 -> { // Кладка блоков
                calcTitle.text = "Кладка блоков и кирпича"
                calcSubtitle.text = "Расчет строительных блоков"
                lblParam1.text = "Тип материала"
                val items = arrayOf("Газоблок (600x300x200)", "Кирпич одинарный")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Площадь кладки (м²)"
                inputArea.setText("20")
                lblParam3.visibility = View.GONE
                inputThickness.visibility = View.GONE
                lblParam4.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
            }
            11 -> { // Потолочная плитка
                calcTitle.text = "Потолочная плитка"
                calcSubtitle.text = "Расчет плит 50x50 см"
                lblParam1.text = "Запас на подрезку"
                val items = arrayOf("Стандарт (+5%)", "Сложный потолок (+10%)")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Площадь потолка (м²)"
                inputArea.setText("12")
                lblParam3.visibility = View.GONE
                inputThickness.visibility = View.GONE
                lblParam4.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
            }
        }
    }

    private fun calculateResult() {
        val val1 = inputArea.text.toString().toDoubleOrNull() ?: 0.0
        val val2 = inputThickness.text.toString().toDoubleOrNull() ?: 0.0
        val val3 = inputBagWeight.text.toString().toDoubleOrNull() ?: 1.0

        val resultText = when (currentCalculator) {
            1 -> { // Шпаклёвка
                val rate = when (spinnerType.selectedItemPosition) { 0 -> 1.2; 1 -> 1.4; else -> 1.0 }
                val totalKg = val1 * val2 * rate
                val bags = Math.ceil(totalKg / val3).toInt()
                "Расход: %.1f кг\nМешков (по %.0f кг): %d шт.".format(totalKg, val3, bags)
            }
            2 -> { // Обои
                val rollArea = if (spinnerType.selectedItemPosition == 0) 5.0 else 10.0
                val rolls = Math.ceil(val1 / rollArea * 1.1).toInt()
                "Требуется рулонов (с учетом запаса 10%): %d шт.".format(rolls)
            }
            3 -> { // Краска
                val rate = when (spinnerType.selectedItemPosition) { 0 -> 0.15; 1 -> 0.2; else -> 0.25 }
                val totalLiters = val1 * rate * val2
                val cans = Math.ceil(totalLiters / val3).toInt()
                "Общий расход: %.1f л\nБанок (по %.1f л): %d шт.".format(totalLiters, val3, cans)
            }
            4 -> { // Колеровка
                val percent = when (spinnerType.selectedItemPosition) { 0 -> 0.01; 1 -> 0.03; else -> 0.05 }
                val totalMl = val1 * 1000 * percent
                "Необходимо колер-пасты: %.0f мл".format(totalMl)
            }
            5 -> { // Цемент и песок
                val cementKg = val1 * 300
                val sandKg = val1 * 1050
                val bags50 = Math.ceil(cementKg / 50).toInt()
                "Цемент: %.0f кг (мешков по 50кг: %d)\nПесок: %.0f кг".format(cementKg, bags50, sandKg)
            }
            6 -> { // Штукатурка
                val rate = when (spinnerType.selectedItemPosition) { 0 -> 3.0; 1 -> 2.5; else -> 1.0 }
                val totalKg = val1 * rate
                "Общий вес смеси: %.1f кг".format(totalKg)
            }
            7 -> { // Пеноплекс
                val boards = Math.ceil(val1 / 0.72 * 1.05).toInt()
                "Плит утеплителя (с запасом 5%): %d шт.".format(boards)
            }
            8 -> { // ПВХ панели
                val panels = Math.ceil(val1 / 0.675 * 1.1).toInt()
                "Панелей ПВХ (с запасом 10%): %d шт.".format(panels)
            }
            9 -> { // Напольная плитка
                val factor = if (spinnerType.selectedItemPosition == 0) 1.05 else 1.15
                val areaWithReserve = val1 * factor
                "Площадь с учетом запаса: %.2f м²".format(areaWithReserve)
            }
            10 -> { // Кладка блоков
                val count = if (spinnerType.selectedItemPosition == 0) val1 * 8.3 else val1 * 51
                "Необходимо блоков/кирпичей: %d шт.".format(Math.ceil(count).toInt())
            }
            11 -> { // Потолочная плитка
                val factor = if (spinnerType.selectedItemPosition == 0) 1.05 else 1.10
                val tiles = Math.ceil(val1 * 4 * factor).toInt()
                "Потолочных плиток (50x50см): %d шт.".format(tiles)
            }
            else -> "Расчет выполнен успешно!"
        }

        txtResult.visibility = View.VISIBLE
        txtResult.text = resultText
    }

    private fun showInfoDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
