package com.example.builderhelper

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var mainContainer: RelativeLayout
    private lateinit var topBar: LinearLayout
    private lateinit var bottomNav: LinearLayout
    private lateinit var calculatorCard: LinearLayout
    private lateinit var sidebarContainer: LinearLayout

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

    private lateinit var txtAppTitle: TextView
    private lateinit var txtHeaderSubtitle: TextView
    private lateinit var txtMenuHeader: TextView
    private lateinit var txtSettingsHeader: TextView
    private lateinit var txtLangLabel: TextView
    private lateinit var txtNavHome: TextView
    private lateinit var txtNavAbout: TextView
    private lateinit var txtNavContacts: TextView
    private lateinit var txtNavPrivacy: TextView
    private lateinit var btnToggleTheme: Button

    private var currentCalculator = 1
    private var currentLangIndex = 0
    private var isDarkMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        mainContainer = findViewById(R.id.mainContainer)
        topBar = findViewById(R.id.topBar)
        bottomNav = findViewById(R.id.bottomNav)
        calculatorCard = findViewById(R.id.calculatorCard)
        sidebarContainer = findViewById(R.id.sidebarContainer)

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

        txtAppTitle = findViewById(R.id.txtAppTitle)
        txtHeaderSubtitle = findViewById(R.id.txtHeaderSubtitle)
        txtMenuHeader = findViewById(R.id.txtMenuHeader)
        txtSettingsHeader = findViewById(R.id.txtSettingsHeader)
        txtLangLabel = findViewById(R.id.txtLangLabel)
        txtNavHome = findViewById(R.id.txtNavHome)
        txtNavAbout = findViewById(R.id.txtNavAbout)
        txtNavContacts = findViewById(R.id.txtNavContacts)
        txtNavPrivacy = findViewById(R.id.txtNavPrivacy)
        btnToggleTheme = findViewById(R.id.btnToggleTheme)

        // Меню (бургер)
        findViewById<ImageButton>(R.id.btnMenu).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Поделиться
        findViewById<ImageButton>(R.id.btnShare).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Строительный калькулятор 'Помогатор Строителя' — точные расчёты!")
            }
            startActivity(Intent.createChooser(intent, "Поделиться"))
        }

        // Настройка языкового списка
        val languages = arrayOf("Русский", "English", "O'zbekcha", "Тоҷикӣ", "Հայերեն", "Беларуская", "ქართული", "Moldovenească", "Кыргызча", "Қазақша")
        val langSpinner = findViewById<Spinner>(R.id.spinnerLanguage)
        val langAdapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.setTextColor(if (isDarkMode) Color.WHITE else Color.BLACK)
                return view
            }
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                view.setTextColor(Color.BLACK)
                view.setBackgroundColor(Color.WHITE)
                return view
            }
        }
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        langSpinner.adapter = langAdapter
        langSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                currentLangIndex = position
                applyLocalization()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Кнопка переключения светлого/тёмного режима
        btnToggleTheme.setOnClickListener {
            isDarkMode = !isDarkMode
            applyTheme()
        }

        setupCalculatorUI()

        btnCalculate.setOnClickListener { calculateResult() }

        // Калькуляторы в боковом меню
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

        // Нижняя панель навигации
        findViewById<LinearLayout>(R.id.navHome).setOnClickListener { currentCalculator = 1; setupCalculatorUI() }
        findViewById<LinearLayout>(R.id.navAbout).setOnClickListener { showInfoDialog(getLocalizedText("О нас", "About Us", "Biz haqimizda", "Дар бораи мо", "Մեր մասին", "Пра нас", "ჩვენ შესახებ", "Despre noi", "Биз жөнүндө", "Біз туралы"), "Помогатор Строителя — универсальный калькулятор для точного расчёта стройматериалов.") }
        findViewById<LinearLayout>(R.id.navContacts).setOnClickListener { showInfoDialog(getLocalizedText("Контакты", "Contacts", "Aloqa", "Тамос", "Կապ", "Кантакты", "კონტაქტები", "Contacte", "Байланыштар", "Байланыстар"), "Поддержка: almukhametov.rifkat@yandex.ru") }
        findViewById<LinearLayout>(R.id.navPrivacy).setOnClickListener { showInfoDialog(getLocalizedText("Приваси", "Privacy", "Maxfiylik", "Маҳрамият", "Գաղտնիություն", "Прыватнасць", "კონფიდენციալուრობა", "Confidențialitate", "Купуялуулук", "Құпиялылық"), "Приложение работает офлайн и не собирает личные данные.") }
    }

    private fun applyTheme() {
        val bgColor = if (isDarkMode) Color.parseColor("#0F172A") else Color.parseColor("#E0F2FE")
        val cardColor = if (isDarkMode) Color.parseColor("#1E293B") else Color.parseColor("#FFFFFF")
        val textColor = if (isDarkMode) Color.parseColor("#F8FAFC") else Color.parseColor("#0F172A")
        val subTextColor = if (isDarkMode) Color.parseColor("#94A3B8") else Color.parseColor("#64748B")
        val inputBg = if (isDarkMode) Color.parseColor("#0F172A") else Color.parseColor("#F1F5F9")

        drawerLayout.setBackgroundColor(bgColor)
        mainContainer.setBackgroundColor(bgColor)
        topBar.setBackgroundColor(cardColor)
        bottomNav.setBackgroundColor(cardColor)
        calculatorCard.setBackgroundColor(cardColor)
        sidebarContainer.setBackgroundColor(cardColor)

        txtAppTitle.setTextColor(textColor)
        txtCalcTitleText(textColor)
        calcSubtitle.setTextColor(subTextColor)
        lblParam1.setTextColor(textColor)
        lblParam2.setTextColor(textColor)
        lblParam3.setTextColor(textColor)
        lblParam4.setTextColor(textColor)

        inputArea.setBackgroundColor(inputBg)
        inputArea.setTextColor(textColor)
        inputThickness.setBackgroundColor(inputBg)
        inputThickness.setTextColor(textColor)
        inputBagWeight.setBackgroundColor(inputBg)
        inputBagWeight.setTextColor(textColor)
        spinnerType.setBackgroundColor(inputBg)

        txtMenuHeader.setTextColor(textColor)
        txtSettingsHeader.setTextColor(subTextColor)
        txtLangLabel.setTextColor(textColor)

        if (isDarkMode) {
            btnToggleTheme.text = getLocalizedText("Светлая тема", "Light Mode", "Yorqin rejim", "Ҳолати рӯшноӣ", "Լուսավոր ռեժիմ", "Светлая рэжым", "ნათელი რეჟიმი", "Mod luminos", "Жарык режим", "Жарық режим")
            btnToggleTheme.setBackgroundColor(Color.parseColor("#334155"))
            btnToggleTheme.setTextColor(Color.WHITE)
        } else {
            btnToggleTheme.text = getLocalizedText("Тёмная тема", "Dark Mode", "Tungi rejim", "Ҳолати торик", "Մութ ռեժիմ", "Цёмны рэжым", "მუქი რეჟიმი", "Mod întunecat", "Караңгы режим", "Қараңғы режим")
            btnToggleTheme.setBackgroundColor(Color.parseColor("#E2E8F0"))
            btnToggleTheme.setTextColor(Color.BLACK)
        }

        setupCalculatorUI()
    }

    private fun txtCalcTitleText(color: Int) {
        calcTitle.setTextColor(color)
    }

    private fun applyLocalization() {
        txtAppTitle.text = getLocalizedText("Помогатор Строителя", "Builder Helper", "Quruvchi Yordamchisi", "Мададгори Сохтмончӣ", "Շինարարի Օգնական", "Памагатар Будаўніка", "მშენებლის დამხმარე", "Ajutorul Constructorului", "Куруучунун Жардамчысы", "Құрылысшы Көмекшісі")
        txtHeaderSubtitle.text = getLocalizedText("Профессиональный расчёт", "Professional calculation", "Professional hisob-kitob", "Ҳисоботи касбӣ", "Պրոֆեսիոնալ հաշվարկ", "Прафесійны разлік", "პროფესიონალური გამოთვლა", "Calcul profesional", "Кесиптик эсептөө", "Кәсіби есептеу")
        txtMenuHeader.text = getLocalizedText("Калькуляторы", "Calculators", "Kalkulyatorlar", "Калкуляторҳо", "Հաշվիչներ", "Калькулятары", "კალკულატორები", "Calculatoare", "Калькуляторлор", "Калькуляторлар")
        txtSettingsHeader.text = getLocalizedText("НАСТРОЙКИ", "SETTINGS", "SOZLAMALAR", "ТАНЗИМОТ", "ԿԱՐԳԱՎՈՐՈՒՄՆԵՐ", "НАЛАДКІ", "პარამეტრები", "SETĂRI", "ЖӨНӨТҮҮЛӨР", "ПАРАМЕТРЛЕР")
        txtLangLabel.text = getLocalizedText("Язык интерфейса", "Language", "Interfeys tili", "Забони интерфейс", "Լեզու", "Мова інтэрфейсу", "ინტერფეისის ენა", "Limba interfeței", "Интерфейс тили", "Интерфейс тілі")
        txtNavHome.text = getLocalizedText("Домой", "Home", "Bosh sahifa", "Асосӣ", "Գլխավոր", "Дадому", "მთავარი", "Acasă", "Башкы", "Басты")
        txtNavAbout.text = getLocalizedText("О нас", "About Us", "Biz haqimizda", "Дар бораи мо", "Մեր մասին", "Пра нас", "ჩვენ შესახებ", "Despre noi", "Биз жөнүндө", "Біз туралы")
        txtNavContacts.text = getLocalizedText("Контакты", "Contacts", "Aloqa", "Тамос", "Կապ", "Кантакты", "კონტაქტები", "Contacte", "Байланыштар", "Байланыстар")
        txtNavPrivacy.text = getLocalizedText("Приваси", "Privacy", "Maxfiylik", "Маҳрамият", "Գաղտնիություն", "Прыватнасць", "კონფიდենციալուრობա", "Confidențialitate", "Купуялуулук", "Құпиялылық")
        btnCalculate.text = getLocalizedText("Рассчитать", "Calculate", "Hisoblash", "Ҳисоб кардан", "Հաշվել", "Разлічыць", "გამოთვლა", "Calculează", "Эсептөө", "Есептеу")
        setupCalculatorUI()
    }

    private fun getLocalizedText(vararg texts: String): String {
        return if (currentLangIndex in texts.indices) texts[currentLangIndex] else texts[0]
    }

    private fun setupCalculatorUI() {
        txtResult.visibility = View.GONE
        inputThickness.visibility = View.VISIBLE
        inputBagWeight.visibility = View.VISIBLE
        lblParam3.visibility = View.VISIBLE
        lblParam4.visibility = View.VISIBLE

        val items = when (currentCalculator) {
            1 -> arrayOf("Финишная (1.2 кг/м²/мм)", "Стартовая (1.5 кг/м²/мм)", "Штукатурная (1.0 кг/м²/мм)")
            2 -> arrayOf("0.53 м х 10.05 м", "1.06 м х 10.05 м", "1.06 м х 25 м")
            3 -> arrayOf("Водоэмульсионная (120 мл/м²)", "Акриловая (150 мл/м²)", "Латексная (110 мл/м²)")
            4 -> arrayOf("Пастельный (1%)", "Средний тон (3%)", "Насыщенный (5%)")
            5 -> arrayOf("Раствор М75", "Раствор М100", "Раствор М150")
            6 -> arrayOf("Короед (3.0 кг/м²)", "Барашек (2.5 кг/м²)", "Венецианская (0.8 кг/м²)")
            7 -> arrayOf("20 мм", "30 мм", "50 мм", "100 мм")
            8 -> arrayOf("250 мм", "375 мм", "500 мм")
            9 -> arrayOf("Прямая (+10%)", "Диагональная (+15%)")
            10 -> arrayOf("Кирпич (51 шт/м²)", "Газоблок (8.3 шт/м²)")
            11 -> arrayOf("50 x 50 см", "30 x 30 см", "60 x 60 см")
            12 -> arrayOf("2.5 м", "2.0 м", "3.0 м")
            13 -> arrayOf("Стандарт (1.5 кг/м²/мм)", "Легкий (1.2 кг/м²/мм)")
            else -> arrayOf("Вариант 1", "Вариант 2")
        }

        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.setTextColor(if (isDarkMode) Color.WHITE else Color.BLACK)
                return view
            }
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                view.setTextColor(Color.BLACK)
                view.setBackgroundColor(Color.WHITE)
                return view
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerType.adapter = adapter

        when (currentCalculator) {
            1 -> {
                calcTitle.text = getLocalizedText("Шпаклёвка", "Putty", "Shpaklyovka", "Шпаклёвка", "Շպակլովկա", "Шпаклёўка", "შპაკლი", "Șpaclui", "Шпаклевка", "Шпаклевка")
                calcSubtitle.text = getLocalizedText("Расчёт расхода шпаклёвки по типу смеси и площади", "Calculate putty consumption", "Aralashma turi va maydoni bo'yicha hisob", "Ҳисоби шпаклёвка", "Շպակլովկայի հաշվարկ", "Разлік шпаклёўкі", "შპაკლის ხარჯი", "Calculul șpaclului", "Шпаклевка чыгымы", "Шпаклевка шығыны")
                lblParam1.text = getLocalizedText("Вид шпаклёвки", "Putty type", "Shpaklyovka turi", "Намуди шпаклёвка", "Շպակլովկայի տեսակ", "Від шпаклёўкі", "შპაკლის ტიპი", "Tipul șpaclului", "Шпаклевка түрү", "Шпаклевка түрі")
                lblParam2.text = getLocalizedText("Площадь стен (м²)", "Wall area (m²)", "Devor maydoni (m²)", "Масоҳати девор (м²)", "Պատերի մակերես (մ²)", "Плошча сцен (м²)", "კედლების ფართობი (მ²)", "Suprafața pereților (m²)", "Дубал аянты (м²)", "Қабырға алаңы (м²)")
            }
        }
    }

    private fun calculateResult() {
        val val1 = inputArea.text.toString().toDoubleOrNull() ?: 0.0
        val val2 = inputThickness.text.toString().toDoubleOrNull() ?: 0.0
        val val3 = inputBagWeight.text.toString().toDoubleOrNull() ?: 1.0

        val totalKg = val1 * val2 * 1.2
        val bags = Math.ceil(totalKg / val3).toInt()

        txtResult.visibility = View.VISIBLE
        txtResult.text = "Расход: %.1f кг\nМешков (по %.0f кг): %d шт.".format(totalKg, val3, bags)
    }

    private fun showInfoDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
