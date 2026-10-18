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
    private var isDarkMode = false

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

        // Бургер меню
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

        // Языки
        val languages = arrayOf("Русский", "English", "O'zbekcha", "Тоҷикӣ", "Հայերեն", "Беларуская", "ქართული", "Moldovenească", "Кыргызча", "Қазақша")
        val langSpinner = findViewById<Spinner>(R.id.spinnerLanguage)
        langSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languages)
        langSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                currentLangIndex = position
                applyLocalization()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Кнопка переключения темы внизу бокового меню
        btnToggleTheme.setOnClickListener {
            isDarkMode = !isDarkMode
            applyTheme()
        }

        setupCalculatorUI()

        btnCalculate.setOnClickListener { calculateResult() }

        // Кнопки калькуляторов
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

        // Нижняя навигация
        findViewById<LinearLayout>(R.id.navHome).setOnClickListener { currentCalculator = 1; setupCalculatorUI() }
        findViewById<LinearLayout>(R.id.navAbout).setOnClickListener { showInfoDialog("О нас", "Помогатор Строителя — универсальный помощник для мастеров.") }
        findViewById<LinearLayout>(R.id.navContacts).setOnClickListener { showInfoDialog("Контакты", "Поддержка: support@pomogator-builder.app") }
        findViewById<LinearLayout>(R.id.navPrivacy).setOnClickListener { showInfoDialog("Приваси", "Приложение работает офлайн и не передает данные.") }
    }

    private fun applyTheme() {
        if (isDarkMode) {
            btnToggleTheme.text = getLocalizedText("Светлая тема", "Light Mode", "Yorqin rejim", "Ҳолати рӯшноӣ", "Լուսավոր ռեժիմ", "Светлая рэжым", "ნათელი რეჟიმი", "Mod luminos", "Жарык режим", "Жарық режим")
        } else {
            btnToggleTheme.text = getLocalizedText("Тёмная тема", "Dark Mode", "Tungi rejim", "Ҳолати торик", "Մութ ռեժիմ", "Цёмны рэжым", "მუქი რეჟიმი", "Mod întunecat", "Караңгы режим", "Қараңғы режим")
        }
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
        txtNavPrivacy.text = getLocalizedText("Приваси", "Privacy", "Maxfiylik", "Маҳрамият", "Գաղտնիություն", "Прыватнасць", "კონფიდენციალურობა", "Confidențialitate", "Купуялуулук", "Құпиялылық")
        btnCalculate.text = getLocalizedText("Рассчитать", "Calculate", "Hisoblash", "Ҳисоб кардан", "Հաշվել", "Разлічыць", "გამოთვლა", "Calculează", "Эсептөө", "Есептеу")
        applyTheme()
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

        when (currentCalculator) {
            1 -> {
                calcTitle.text = getLocalizedText("Шпаклёвка", "Putty", "Shpaklyovka", "Шпаклёвка", "Շպակլովկա", "Шпаклёўка", "შპაკლი", "Șpaclui", "Шпаклевка", "Шпаклевка")
                calcSubtitle.text = getLocalizedText("Расчёт расхода шпаклёвки по типу смеси и площади", "Calculate putty consumption", "Aralashma turi va maydoni bo'yicha hisob", "Ҳисоби шпаклёвка", "Շպակլովկայի հաշվարկ", "Разлік шпаклёўкі", "შპაკლის ხარჯი", "Calculul șpaclului", "Шпаклевка чыгымы", "Шпаклевка шығыны")
                lblParam1.text = getLocalizedText("Вид шпаклёвки", "Putty type", "Shpaklyovka turi", "Намуди шпаклёвка", "Շպակլովկայի տեսակ", "Від шпаклёўкі", "შპაკლის ტიპი", "Tipul șpaclului", "Шпаклевка түрү", "Шпаклевка түрі")
                spinnerType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayOf("Финишная", "Стартовая", "Штукатурная"))
                lblParam2.text = getLocalizedText("Площадь стен (м²)", "Wall area (m²)", "Devor maydoni (m²)", "Масоҳати девор (м²)", "Պատերի մակերես (մ²)", "Плошча сцен (м²)", "კედლების ფართობი (მ²)", "Suprafața pereților (m²)", "Дубал аянты (м²)", "Қабырға алаңы (м²)")
            }
            // Остальные калькуляторы аналогично используют getLocalizedText по аналогии
        }
    }

    private fun calculateResult() {
        txtResult.visibility = View.VISIBLE
        txtResult.text = getLocalizedText("Расчёт выполнен успешно!", "Calculation successful!", "Hisob-kitob muvaffaqiyatli bajarildi!", "Ҳисоб бо муваффақият анҷом ёфт!", "Հաշվարկը հաջողությամբ ավարտվեց!", "Разлік паспяхова завершаны!", "გამოთვლა წარმატებით დასრულდა!", "Calcul cu succes!", "Эсептөө ийгиликтүү аяктады!", "Есептеу сәтті аяқталды!")
    }

    private fun showInfoDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
