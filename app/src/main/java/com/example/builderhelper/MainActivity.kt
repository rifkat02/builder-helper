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

    private var currentCalculator = 1 

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

        // Бургер меню
        findViewById<ImageButton>(R.id.btnMenu).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Поделиться
        findViewById<ImageButton>(R.id.btnShare).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Строительный калькулятор 'Помогатор Строителя' — незаменимый инструмент для точного расчёта!")
            }
            startActivity(Intent.createChooser(intent, "Поделиться"))
        }

        // Выбор языка
        val languages = arrayOf("Русский", "English", "O'zbekcha", "Тоҷикӣ", "Հայերեն", "Беларуская", "ქართული", "Moldovenească", "Кыргызча", "Қазақша")
        val langAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languages)
        findViewById<Spinner>(R.id.spinnerLanguage).adapter = langAdapter

        setupCalculatorUI()

        btnCalculate.setOnClickListener {
            calculateResult()
        }

        // Переключение калькуляторов в меню
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
        findViewById<Button>(R.id.menuPlinth).setOnClickListener { currentCalculator = 12; setupCalculatorUI(); drawerLayout.closeDrawers() }
        findViewById<Button>(R.id.menuSelfLeveling).setOnClickListener { currentCalculator = 13; setupCalculatorUI(); drawerLayout.closeDrawers() }

        // Нижняя панель
        findViewById<LinearLayout>(R.id.navHome).setOnClickListener { currentCalculator = 1; setupCalculatorUI() }
        findViewById<LinearLayout>(R.id.navAbout).setOnClickListener { showInfoDialog("О нас", "Помогатор Строителя — удобный универсальный калькулятор для мастеров и прорабов.") }
        findViewById<LinearLayout>(R.id.navContacts).setOnClickListener { showInfoDialog("Контакты", "Поддержка: support@pomogator-builder.app\nTelegram: @builder_helper_bot") }
        findViewById<LinearLayout>(R.id.navPrivacy).setOnClickListener { showInfoDialog("Приваси", "Приложение работает полностью офлайн и не собирает личные данные.") }
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
                val items = arrayOf("Финишная (1.2 кг/м²/мм)", "Стартовая (1.5 кг/м²/мм)", "Штукатурная (1.0 кг/м²/мм)", "Суперфиниш (1.1 кг/м²/мм)")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Площадь стен (м²)"
                inputArea.setText("25")
                lblParam3.text = "Толщина слоя (мм)"
                inputThickness.setText("2")
                lblParam4.text = "Вес мешка (кг)"
                inputBagWeight.setText("20")
            }
            2 -> { // Обои
                calcTitle.text = "Обои по стенам"
                calcSubtitle.text = "Расчёт рулонов обоев с учётом окон и дверей"
                lblParam1.text = "Размер рулона"
                val items = arrayOf("0.53 м х 10.05 м", "1.06 м х 10.05 м (Евро)", "1.06 м х 25 м")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Периметр комнаты (м)"
                inputArea.setText("18")
                lblParam3.text = "Высота потолка (м)"
                inputThickness.setText("2.7")
                lblParam4.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
            }
            3 -> { // Краска
                calcTitle.text = "Краска (все виды)"
                calcSubtitle.text = "Расчёт краски для всех типов поверхностей"
                lblParam1.text = "Тип краски"
                val items = arrayOf("Водоэмульсионная (120 мл/м²)", "Акриловая (150 мл/м²)", "Латексная (110 мл/м²)", "Эмаль (160 мл/м²)")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Площадь окраски (м²)"
                inputArea.setText("40")
                lblParam3.text = "Количество слоёв"
                inputThickness.setText("2")
                lblParam4.text = "Запас (%)"
                inputBagWeight.setText("10")
            }
            4 -> { // Колеровка
                calcTitle.text = "Колеровка"
                calcSubtitle.text = "Расчёт колер-пасты для всех типов смесей"
                lblParam1.text = "Насыщенность тона"
                val items = arrayOf("Пастельный (1%)", "Средний тон (3%)", "Насыщенный (5%)", "Яркий (10%)")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Объём базы (л/кг)"
                inputArea.setText("10")
                lblParam3.visibility = View.GONE
                inputThickness.visibility = View.GONE
                lblParam4.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
            }
            5 -> { // Цемент и песок
                calcTitle.text = "Цемент и песок"
                calcSubtitle.text = "Расчёт компонентов раствора"
                lblParam1.text = "Марка раствора"
                val items = arrayOf("Раствор М75", "Раствор М100", "Раствор М150", "Бетон М200")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Требуемый объём (м³)"
                inputArea.setText("1")
                lblParam3.visibility = View.GONE
                inputThickness.visibility = View.GONE
                lblParam4.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
            }
            6 -> { // Штукатурка
                calcTitle.text = "Декоративная штукатурка"
                calcSubtitle.text = "Все виды декоративных покрытий"
                lblParam1.text = "Вид покрытия"
                val items = arrayOf("Короед (3.0 кг/м²)", "Барашек (2.5 кг/м²)", "Венецианская (0.8 кг/м²)", "Арт-бетон (2.1 кг/м²)")
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
                calcSubtitle.text = "Расчёт XPS панелей и клея"
                lblParam1.text = "Толщина плиты"
                val items = arrayOf("20 мм (20 шт/пач)", "30 мм (13 шт/пач)", "50 мм (8 шт/пач)", "100 мм (4 шт/пач)")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Площадь утепления (м²)"
                inputArea.setText("30")
                lblParam3.visibility = View.GONE
                inputThickness.visibility = View.GONE
                lblParam4.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
            }
            8 -> { // ПВХ панели
                calcTitle.text = "ПВХ панели"
                calcSubtitle.text = "Расчёт панелей и профилей"
                lblParam1.text = "Ширина панели"
                val items = arrayOf("250 мм (0.25 м)", "375 мм (0.375 м)", "500 мм (0.50 м)")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Длина стены/потолка (м)"
                inputArea.setText("4")
                lblParam3.visibility = View.GONE
                inputThickness.visibility = View.GONE
                lblParam4.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
            }
            9 -> { // Плитка
                calcTitle.text = "Напольная плитка"
                calcSubtitle.text = "Расчёт керамогранита и клея"
                lblParam1.text = "Способ укладки"
                val items = arrayOf("Прямая (+10% запаса)", "Диагональная (+15% запаса)")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Площадь пола (м²)"
                inputArea.setText("15")
                lblParam3.visibility = View.GONE
                inputThickness.visibility = View.GONE
                lblParam4.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
            }
            10 -> { // Кладка
                calcTitle.text = "Кладка блоков и кирпича"
                calcSubtitle.text = "Кирпич, газоблок, керамзитоблок"
                lblParam1.text = "Материал кладки"
                val items = arrayOf("Кирпич одинарный (51 шт/м²)", "Кирпич полуторный (39 шт/м²)", "Газоблок (8.3 шт/м²)")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Площадь кладки (м²)"
                inputArea.setText("60")
                lblParam3.visibility = View.GONE
                inputThickness.visibility = View.GONE
                lblParam4.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
            }
            11 -> { // Потолок
                calcTitle.text = "Потолочная плитка"
                calcSubtitle.text = "Расчёт плитки 50х50 см"
                lblParam1.text = "Размер плитки"
                val items = arrayOf("50 x 50 см", "30 x 30 см", "60 x 60 см")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Площадь потолка (м²)"
                inputArea.setText("12")
                lblParam3.visibility = View.GONE
                inputThickness.visibility = View.GONE
                lblParam4.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
            }
            12 -> { // Плинтус
                calcTitle.text = "Плинтус"
                calcSubtitle.text = "Расчёт напольного/потолочного плинтуса"
                lblParam1.text = "Длина одной планки (м)"
                val items = arrayOf("2.5 м", "2.0 м", "3.0 м")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Периметр помещения (м)"
                inputArea.setText("18")
                lblParam3.text = "Вычет дверей (м)"
                inputThickness.setText("0.9")
                lblParam4.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
            }
            13 -> { // Наливной пол
                calcTitle.text = "Наливной пол / Стяжка"
                calcSubtitle.text = "Расчёт смеси для выравнивания полов"
                lblParam1.text = "Расход смеси"
                val items = arrayOf("Стандарт (1.5 кг/м²/мм)", "Легкий (1.2 кг/м²/мм)", "Тяжелый (1.8 кг/м²/мм)")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
                lblParam2.text = "Площадь пола (м²)"
                inputArea.setText("20")
                lblParam3.text = "Толщина слоя (мм)"
                inputThickness.setText("10")
                lblParam4.text = "Вес мешка (кг)"
                inputBagWeight.setText("25")
            }
        }
    }

    private fun calculateResult() {
        val val1 = inputArea.text.toString().toDoubleOrNull() ?: 0.0
        val val2 = inputThickness.text.toString().toDoubleOrNull() ?: 0.0
        val val3 = inputBagWeight.text.toString().toDoubleOrNull() ?: 1.0

        val resultText = when (currentCalculator) {
            1 -> { // Шпаклёвка
                val rate = when (spinnerType.selectedItemPosition) { 0 -> 1.2; 1 -> 1.5; 2 -> 1.0; else -> 1.1 }
                val totalKg = val1 * val2 * rate
                val bags = Math.ceil(totalKg / val3).toInt()
                "Общий вес: %.1f кг\nМешков (по %.0f кг): %d шт.".format(totalKg, val3, bags)
            }
            2 -> { // Обои
                val rollW = if (spinnerType.selectedItemPosition == 0) 0.53 else 1.06
                val rollL = if (spinnerType.selectedItemPosition == 2) 25.0 else 10.05
                val stripsPerRoll = Math.floor(rollL / val2)
                val totalStrips = Math.ceil(val1 / rollW)
                val rolls = Math.ceil(totalStrips / stripsPerRoll).toInt()
                "Всего полос: %.0f шт.\nПотребуется рулонов: %d шт.".format(totalStrips, rolls)
            }
            3 -> { // Краска
                val rate = when (spinnerType.selectedItemPosition) { 0 -> 0.12; 1 -> 0.15; 2 -> 0.11; else -> 0.16 }
                val totalLiters = val1 * rate * val2 * (1 + val3 / 100)
                "Необходимый объём: %.2f л".format(totalLiters)
            }
            4 -> { // Колеровка
                val percent = when (spinnerType.selectedItemPosition) { 0 -> 0.01; 1 -> 0.03; 2 -> 0.05; else -> 0.10 }
                val colorantMl = val1 * 1000 * percent
                "Объём колер-пасты: %.0f мл".format(colorantMl)
            }
            5 -> { // Цемент и песок
                var cementKg = 300.0; var sandKg = 1200.0
                if (spinnerType.selectedItemPosition == 0) { cementKg = 260.0; sandKg = 1250.0 }
                if (spinnerType.selectedItemPosition == 1) { cementKg = 320.0; sandKg = 1150.0 }
                if (spinnerType.selectedItemPosition == 2) { cementKg = 400.0; sandKg = 1100.0 }
                val cementBags = Math.ceil((cementKg * val1) / 50).toInt()
                "Цемент: %.0f кг (%d меш. по 50кг)\nПесок: %.0f кг".format(cementKg * val1, cementBags, sandKg * val1)
            }
            6 -> { // Штукатурка
                val rate = when (spinnerType.selectedItemPosition) { 0 -> 3.0; 1 -> 2.5; 2 -> 0.8; else -> 2.1 }
                "Штукатурка: %.1f кг".format(val1 * rate)
            }
            7 -> { // Пеноплекс
                val totalSheets = Math.ceil(val1 / 0.288).toInt()
                val perPack = when (spinnerType.selectedItemPosition) { 0 -> 20; 1 -> 13; 2 -> 8; else -> 4 }
                val packs = Math.ceil(totalSheets.toDouble() / perPack).toInt()
                "Всего плит: %d шт.\nУпаковок: %d пач.".format(totalSheets, packs)
            }
            8 -> { // ПВХ
                val panelW = when (spinnerType.selectedItemPosition) { 0 -> 0.25; 1 -> 0.375; else -> 0.5 }
                val panels = Math.ceil(val1 / panelW).toInt()
                "Количество панелей: %d шт.".format(panels)
            }
            9 -> { // Плитка
                val margin = if (spinnerType.selectedItemPosition == 0) 1.10 else 1.15
                "Площадь плитки с запасом: %.2f м²".format(val1 * margin)
            }
            10 -> { // Кладка
                val perM2 = when (spinnerType.selectedItemPosition) { 0 -> 51.0; 1 -> 39.0; else -> 8.3 }
                "Необходимо элементов: %d шт.".format(Math.ceil(val1 * perM2).toInt())
            }
            11 -> { // Потолок
                val tileSize = when (spinnerType.selectedItemPosition) { 0 -> 0.25; 1 -> 0.09; else -> 0.36 }
                val count = Math.ceil((val1 * 1.1) / tileSize).toInt()
                "Плитки (с запасом 10%%): %d шт.".format(count)
            }
            12 -> { // Плинтус
                val net = Math.max(0.0, val1 - val2)
                val count = Math.ceil((net * 1.05) / 2.5).toInt()
                "Планки плинтуса: %d шт.".format(count)
            }
            13 -> { // Наливной пол
                val rate = when (spinnerType.selectedItemPosition) { 0 -> 1.5; 1 -> 1.2; else -> 1.8 }
                val totalKg = val1 * val2 * rate
                val bags = Math.ceil(totalKg / val3).toInt()
                "Смеси всего: %.1f кг\nМешков (по %.0f кг): %d шт.".format(totalKg, val3, bags)
            }
            else -> "Готово"
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
