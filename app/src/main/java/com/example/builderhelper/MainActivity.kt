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

    // Кнопки меню калькуляторов
    private lateinit var menuPutty: Button
    private lateinit var menuWallpaper: Button
    private lateinit var menuPaint: Button
    private lateinit var menuColoring: Button
    private lateinit var menuCement: Button
    private lateinit var menuPlaster: Button
    private lateinit var menuPenoplex: Button
    private lateinit var menuPvc: Button
    private lateinit var menuFloorTile: Button
    private lateinit var menuMasonry: Button
    private lateinit var menuCeilingTile: Button
    private lateinit var menuPlinth: Button
    private lateinit var menuSelfLeveling: Button

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

        // Инициализация кнопок меню
        menuPutty = findViewById(R.id.menuPutty)
        menuWallpaper = findViewById(R.id.menuWallpaper)
        menuPaint = findViewById(R.id.menuPaint)
        menuColoring = findViewById(R.id.menuColoring)
        menuCement = findViewById(R.id.menuCement)
        menuPlaster = findViewById(R.id.menuPlaster)
        menuPenoplex = findViewById(R.id.menuPenoplex)
        menuPvc = findViewById(R.id.menuPvc)
        menuFloorTile = findViewById(R.id.menuFloorTile)
        menuMasonry = findViewById(R.id.menuMasonry)
        menuCeilingTile = findViewById(R.id.menuCeilingTile)
        menuPlinth = findViewById(R.id.menuPlinth)
        menuSelfLeveling = findViewById(R.id.menuSelfLeveling)

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

        // Кнопка переключения темы
        btnToggleTheme.setOnClickListener {
            isDarkMode = !isDarkMode
            applyTheme()
        }

        setupCalculatorUI()

        btnCalculate.setOnClickListener { calculateResult() }

        // Выбор калькуляторов в боковом меню
        menuPutty.setOnClickListener { currentCalculator = 1; setupCalculatorUI(); drawerLayout.closeDrawers() }
        menuWallpaper.setOnClickListener { currentCalculator = 2; setupCalculatorUI(); drawerLayout.closeDrawers() }
        menuPaint.setOnClickListener { currentCalculator = 3; setupCalculatorUI(); drawerLayout.closeDrawers() }
        menuColoring.setOnClickListener { currentCalculator = 4; setupCalculatorUI(); drawerLayout.closeDrawers() }
        menuCement.setOnClickListener { currentCalculator = 5; setupCalculatorUI(); drawerLayout.closeDrawers() }
        menuPlaster.setOnClickListener { currentCalculator = 6; setupCalculatorUI(); drawerLayout.closeDrawers() }
        menuPenoplex.setOnClickListener { currentCalculator = 7; setupCalculatorUI(); drawerLayout.closeDrawers() }
        menuPvc.setOnClickListener { currentCalculator = 8; setupCalculatorUI(); drawerLayout.closeDrawers() }
        menuFloorTile.setOnClickListener { currentCalculator = 9; setupCalculatorUI(); drawerLayout.closeDrawers() }
        menuMasonry.setOnClickListener { currentCalculator = 10; setupCalculatorUI(); drawerLayout.closeDrawers() }
        menuCeilingTile.setOnClickListener { currentCalculator = 11; setupCalculatorUI(); drawerLayout.closeDrawers() }
        menuPlinth.setOnClickListener { currentCalculator = 12; setupCalculatorUI(); drawerLayout.closeDrawers() }
        menuSelfLeveling.setOnClickListener { currentCalculator = 13; setupCalculatorUI(); drawerLayout.closeDrawers() }

        // Нижняя панель навигации
        findViewById<LinearLayout>(R.id.navHome).setOnClickListener { currentCalculator = 1; setupCalculatorUI() }
        findViewById<LinearLayout>(R.id.navAbout).setOnClickListener { showInfoDialog(getLocalizedText("О нас", "About Us", "Biz haqimizda", "Дар бораи мо", "Մեր մասին", "Пра нас", "ჩვენ შესახებ", "Despre noi", "Биз жөнүндө", "Біз туралы"), "Помогатор Строителя — универсальный калькулятор для точного расчёта стройматериалов.") }
        findViewById<LinearLayout>(R.id.navContacts).setOnClickListener { showInfoDialog(getLocalizedText("Контакты", "Contacts", "Aloqa", "Тамос", "Կապ", "Кантакты", "კონტაქტები", "Contacte", "Байланыштар", "Байланыстар"), "Поддержка: almukhametov.rifkat@yandex.ru") }
        findViewById<LinearLayout>(R.id.navPrivacy).setOnClickListener { showInfoDialog(getLocalizedText("Приваси", "Privacy", "Maxfiylik", "Маҳрамият", "Գաղտնիություն", "Прыватнасць", "კონფიდენციალուრობա", "Confidențialitate", "Купуялуулук", "Құпиялылық"), "Приложение работает офлайн и не собирает личные данные.") }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
            return
        }

        val title = getLocalizedText("Выход", "Exit", "Chiqish", "Баромад", "Ելք", "Выхад", "გამოსვლა", "Ieșire", "Чыгуу", "Шығу")
        val message = getLocalizedText("Вы уходите?", "Are you leaving?", "Chetga chiqasizmi?", "Шумо меравед?", "Դուք հեռանո՞ւմ եք:", "Вы сыходзіце?", "მიდიხართ?", "Părăsiți aplicația?", "Сиз кетип жатасызбы?", "Сіз кетесіз бе?")
        val yesText = getLocalizedText("Да", "Yes", "Ha", "Ҳа", "Այո", "Так", "დიახ", "Da", "Ооба", "Иә")
        val noText = getLocalizedText("Нет", "No", "Yo'q", "Не", "Ոչ", "Не", "არა", "Nu", "Жок", "Жоқ")

        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(yesText) { _, _ -> finishAffinity() }
            .setNegativeButton(noText) { dialog, _ -> dialog.dismiss() }
            .show()
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
        calcTitle.setTextColor(textColor)
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

    private fun applyLocalization() {
        txtAppTitle.text = getLocalizedText("Помогатор Строителя", "Builder Helper", "Quruvchi Yordamchisi", "Мададгори Сохтмончӣ", "Շինարարի Օգնական", "Памагатар Будаўніка", "მშენებლის დამხმარე", "Ajutorul Constructorului", "Куруучунун Жардамчысы", "Құрылысшы Көмекшісі")
        txtHeaderSubtitle.text = getLocalizedText("Профессиональный расчёт", "Professional calculation", "Professional hisob-kitob", "Ҳисоботи касбӣ", "Պրոֆեսիոնալ հաշվարկ", "Прафесійны разлік", "პროფესიონალური გამოთვლა", "Calcul profesional", "Кесиптик эсептөө", "Кәсіби есептеу")
        txtMenuHeader.text = getLocalizedText("Калькуляторы", "Calculators", "Kalkulyatorlar", "Калкуляторҳо", "Հաշվիչներ", "Калькулятары", "კალკულატორები", "Calculatoare", "Калькуляторлор", "Калькуляторлар")
        txtSettingsHeader.text = getLocalizedText("НАСТРОЙКИ", "SETTINGS", "SOZLAMALAR", "ТАНЗИМОТ", "ԿԱՐԳԱՎՈՐՈՒՄՆԵՐ", "НАЛАДКІ", "პარამეტრები", "SETĂRI", "ЖӨНӨТҮҮЛӨР", "ПАРАМЕТРЛЕР")
        txtLangLabel.text = getLocalizedText("Язык интерфейса", "Language", "Interfeys tili", "Забони интерфейс", "Լեզու", "Мова інтэрфейсу", "ინტერფეისის ენა", "Limba interfeței", "Интерфейс тили", "Интерфейс тілі")
        
        txtNavHome.text = getLocalizedText("Домой", "Home", "Bosh sahifa", "Асосӣ", "Գլխավոր", "Дадому", "მთავარი", "Acasă", "Башкы", "Басты")
        txtNavAbout.text = getLocalizedText("О нас", "About Us", "Biz haqimizda", "Дар бораи мо", "Մեր մասին", "Пра нас", "ჩვენ შესახებ", "Despre noi", "Биз жөнүндө", "Біз туралы")
        txtNavContacts.text = getLocalizedText("Контакты", "Contacts", "Aloqa", "Тамос", "Կապ", "Кантакты", "კონტაქტები", "Contacte", "Байланыштар", "Байланыстар")
        txtNavPrivacy.text = getLocalizedText("Приваси", "Privacy", "Maxfiylik", "Маҳрамият", "Գաղտնիություն", "Прыватнасць", "კონფიდენციալուრობա", "Confidențialitate", "Купуялуулук", "Құпиялылық")
        btnCalculate.text = getLocalizedText("Рассчитать", "Calculate", "Hisoblash", "Ҳисоб кардан", "Հաշվել", "Разлічыць", "გამოთვლა", "Calculează", "Эсептөө", "Есептеу")

        // Перевод пунктов бокового меню
        menuPutty.text = getLocalizedText("Шпаклёвка", "Putty", "Shpaklyovka", "Шпаклёвка", "Շպակլովկա", "Шпаклёўка", "შპაკლი", "Șpaclu", "Шпаклевка", "Шпаклевка")
        menuWallpaper.text = getLocalizedText("Обои по стенам", "Wallpaper", "Oboy", "Девори обоӣ", "Պաստառներ", "Шпалеры", "შპალერი", "Tapet", "Обои", "Тұсқағаз")
        menuPaint.text = getLocalizedText("Краска (все виды)", "Paint", "Bo'yoq", "Ранг", "Ներկ", "Фарба", "საღებავი", "Vopsea", "Боёк", "Боя")
        menuColoring.text = getLocalizedText("Колеровка", "Coloring", "Kolerovka", "Колеровка", "Գունավորում", "Калероўка", "ფერწერა", "Colorare", "Түстөө", "Бояу")
        menuCement.text = getLocalizedText("Цемент и песок", "Cement & Sand", "Sement va qum", "Семент ва қум", "Ցեմենտ և ավազ", "Цэмент і пясок", "ცემენტი და ქვიშა", "Ciment și nisip", "Цемент жана кум", "Цемент пен құм")
        menuPlaster.text = getLocalizedText("Декоративная штукатурка", "Decorative Plaster", "Dekorativ suvoq", "Сувоқи озорӣ", "Դեկորատիվ սվաղ", "Дэкаратыўная тынкоўка", "დეკორატიული შელესვა", "Tencuială decorativă", "Декоративдик суак", "Декоративті сылақ")
        menuPenoplex.text = getLocalizedText("Обшивка пеноплексом", "Penoplex Insulation", "Penoplex qoplami", "Пеноплекс", "Пенопլեքս", "Пенәплекс", "პენოპლექსი", "Izolație Penoplex", "Пеноплекс жылуулоо", "Пеноплекс жылылау")
        menuPvc.text = getLocalizedText("ПВХ панели", "PVC Panels", "PVX panellar", "Панелҳои ПВХ", "ՊՎԽ պանելներ", "ПВХ панэлі", "პვხ პანელები", "Panouri PVC", "ПВХ панелдери", "ПВХ панельдері")
        menuFloorTile.text = getLocalizedText("Напольная плитка", "Floor Tile", "Pol plitkasi", "Плиткаи фарш", "Հատակի սալիկ", "Налогавая плітка", "იატაკის ფილა", "Gresie", "Пол плиткасы", "Еден плиткасы")
        menuMasonry.text = getLocalizedText("Кладка блоков и кирпича", "Masonry", "G'isht terish", "Девори хишт", "Աղյուսե շարվածք", "Мураванне", "აგურის წყობა", "Zidărie", "Кыш дубал", "Кірпіш қалау")
        menuCeilingTile.text = getLocalizedText("Потолочная плитка", "Ceiling Tile", "Shift plitkasi", "Плиткаи шифт", "Առաստաղի սալիկ", "Стальная плітка", "ჭერის ფილა", "Plafon", "Шып плиткасы", "Төбе плиткасы")
        menuPlinth.text = getLocalizedText("Плинтус", "Plinth", "Plintus", "Плинтус", "Շրջանակ", "Плінтус", "პლინტუსი", "Pervaz", "Плинтус", "Плинтус")
        menuSelfLeveling.text = getLocalizedText("Наливной пол / Стяжка", "Self-Leveling Floor", "Quymalov pol", "Фарши рехта", "Լցնովի հատակ", "Наліўная падлога", "თვითგასწორებელი", "Pardoseală turnată", "Куюлучу пол", "Төгілетін еден")

        setupCalculatorUI()
    }

    private fun getLocalizedText(vararg texts: String): String {
        return if (currentLangIndex in texts.indices) texts[currentLangIndex] else texts[0]
    }

    private fun setupCalculatorUI() {
        txtResult.visibility = View.GONE
        
        // По умолчанию показываем все поля, далее скрываем лишние в зависимости от калькулятора
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

        // Настройка названий и видимости полей в зависимости от выбранного калькулятора
        when (currentCalculator) {
            1 -> { // Шпаклёвка
                calcTitle.text = getLocalizedText("Шпаклёвка", "Putty", "Shpaklyovka", "Шпаклёвка", "Շպակլովկա", "Шпаклёўка", "შპაკლი", "Șpaclu", "Шпаклевка", "Шпаклевка")
                calcSubtitle.text = getLocalizedText("Расчёт расхода шпаклёвки по типу и площади", "Calculate putty consumption", "Aralashma turi va maydoni", "Ҳисоби шпаклёвка", "Շպակլովկայի հաշվարկ", "Разлік шпаклёўкі", "შპაკლის ხარჯი", "Calculul șpaclului", "Шпаклевка чыгымы", "Шпаклевка шығыны")
                lblParam1.text = getLocalizedText("Вид шпаклёвки", "Putty type", "Shpaklyovka turi", "Намуди шпаклёвка", "Շպակլովկայի տեսակ", "Від шпаклёўкі", "შპაკლის ტიპი", "Tipul șpaclului", "Шпаклевка түрү", "Шпаклевка түрі")
                lblParam2.text = getLocalizedText("Площадь стен (м²)", "Wall area (m²)", "Devor maydoni (m²)", "Масоҳати девор (м²)", "Պատերի մակերես (մ²)", "Плошча сцен (м²)", "კედლების ფართობი (მ²)", "Suprafața pereților (m²)", "Дубал аянты (m²)", "Қабырға алаңы (m²)")
                lblParam3.text = getLocalizedText("Толщина слоя (мм)", "Layer thickness (mm)", "Qatlam qalinligi (mm)", "Ғафсии қабат (мм)", "Շերտի հաստություն (մմ)", "Таўшчыня слоя (мм)", "ფენის სქщина (მმ)", "Grosimea stratului (mm)", "Калыңдыгы (мм)", "Қалыңдығы (мм)")
                lblParam4.text = getLocalizedText("Вес мешка (кг)", "Bag weight (kg)", "Qop og'irligi (kg)", "Вазни халта (кг)", "Պարկի քաշ (կգ)", "Вага мяшка (кг)", "ტექნიკური წონა (კგ)", "Greutatea sacului (kg)", "Кабык салмагы (кг)", "Қап салмағы (кг)")
            }
            2 -> { // Обои
                calcTitle.text = getLocalizedText("Обои по стенам", "Wallpaper", "Oboy", "Девори обоӣ", "Պաստառներ", "Шпалеры", "შპალერი", "Tapet", "Обои", "Тұсқағаз")
                calcSubtitle.text = getLocalizedText("Расчёт рулонов обоев по периметру", "Calculate wallpaper rolls", "Oboy rulonlarini hisoblash", "Ҳисоби рулонҳои обой", "Պաստառների հաշվարկ", "Разлік рулонаў", "შპალერის რულონები", "Calculul tapetului", "Обои эсептөө", "Тұсқағаз есептеу")
                lblParam1.text = getLocalizedText("Тип рулона", "Roll type", "Rulon turi", "Намуди рулон", "Ռուլոնի տեսակ", "Тып рулона", "რულონის ტიპი", "Tipul rolei", "Рулон түрү", "Рулон түрі")
                lblParam2.text = getLocalizedText("Периметр комнаты (м)", "Room perimeter (m)", "Xona perimetri (m)", "Периметри хона (м)", "Սենյակի պարագիծ (մ)", "Перыметр пакоя (м)", "ოთახის პერიმეტრი (მ)", "Perimetrul camerei (m)", "Бөлмө периметри (м)", "Бөлме периметрі (м)")
                lblParam3.text = getLocalizedText("Высота потолка (м)", "Ceiling height (m)", "Shift balandligi (m)", "Балландии шифт (м)", "Առաստաղի բարձրություն (մ)", "Вышыня столі (м)", "ჭერის სიმაღლე (მ)", "Înălțimea tavanului (m)", "Шып бийиктиги (m)", "Төбе биіктігі (m)")
                inputThickness.setText("2.7")
                inputBagWeight.visibility = View.GONE
                lblParam4.visibility = View.GONE
            }
            3 -> { // Краска
                calcTitle.text = getLocalizedText("Краска (все виды)", "Paint", "Bo'yoq", "Ранг", "Ներկ", "Фарба", "საღებავი", "Vopsea", "Боёк", "Боя")
                calcSubtitle.text = getLocalizedText("Расчёт литров краски", "Calculate paint liters", "Bo'yoq sarfini hisoblash", "Ҳисоби ранг", "Ներկի հաշվարկ", "Разлік фарбы", "საღებავის ხარჯი", "Calculul vopselei", "Боёк чыгымы", "Боя шығыны")
                lblParam1.text = getLocalizedText("Вид краски", "Paint type", "Bo'yoq turi", "Намуди ранг", "Ներկի տեսակ", "Від фарбы", "საღებავის ტიპი", "Tipul vopselei", "Боёк түрү", "Боя түрі")
                lblParam2.text = getLocalizedText("Площадь покраски (м²)", "Painting area (m²)", "Bo'yash maydoni (m²)", "Масоҳати рангкунӣ (м²)", "Ներկման մակերես (մ²)", "Плошча фарбавання (м²)", "საღებავის ფართობი (მ²)", "Suprafața vopsită (m²)", "Боёк аянты (m²)", "Боялау алаңы (m²)")
                lblParam3.text = getLocalizedText("Количество слоев", "Number of coats", "Qatlamlar soni", "Миқдори қабатҳо", "Շերտերի քանակ", "Колькасць слаёў", "ფენების რაოდენობა", "Numărul de straturi", "Кабат саны", "Қабат саны")
                inputThickness.setText("2")
                inputBagWeight.visibility = View.GONE
                lblParam4.visibility = View.GONE
            }
            4 -> { // Колеровка
                calcTitle.text = getLocalizedText("Колеровка", "Coloring", "Kolerovka", "Колеровка", "Գունավորում", "Калероўка", "ფერწერა", "Colorare", "Түстөө", "Бояу")
                calcSubtitle.text = getLocalizedText("Расчёт пигмента для колеровки", "Calculate pigment", "Kolerovka pigmentini hisoblash", "Ҳисоби пигмент", "Գունավորման հաշվարկ", "Разлік пігмента", "პიგმენტის გამოთვლა", "Calculul pigmentului", "Пигмент эсептөө", "Пигмент есептеу")
                lblParam1.text = getLocalizedText("Интенсивность", "Intensity", "Intensivlik", "Шиддатнокӣ", "Ինտենսիվություն", "Інтэнсіўнасць", "ინტენსივობა", "Intensitate", "Интенсивдүүлүк", "Интенсивтілік")
                lblParam2.text = getLocalizedText("Объём базы (л)", "Base volume (l)", "Asos hajmi (l)", "Ҳаҷми асос (л)", "Հիմքի ծավալ (լ)", "Аб'ём базы (л)", "ბაზის მოცულობა (ლ)", "Volumul bazei (l)", "База көлөмү (l)", "База көлемі (л)")
                inputThickness.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
                lblParam3.visibility = View.GONE
                lblParam4.visibility = View.GONE
            }
            5 -> { // Цемент и песок
                calcTitle.text = getLocalizedText("Цемент и песок", "Cement and Sand", "Sement va qum", "Семент ва қум", "Ցեմենտ և ավազ", "Цэмент і пясок", "ცემენტი და ქვიშა", "Ciment și nisip", "Цемент жана кум", "Цемент пен құм")
                calcSubtitle.text = getLocalizedText("Расчёт пропорций раствора", "Calculate mortar proportions", "Qorishma proporsiyasi", "Ҳисоби маҳлул", "Շաղախի հաշվարկ", "Разлік раствора", "ხსნარის გამოთვლა", "Calculul mortarului", "Эритме эсептөө", "Ерітінді есептеу")
                lblParam1.text = getLocalizedText("Марка раствора", "Mortar grade", "Qorishma markasi", "Маркаи маҳлул", "Շաղախի մարկա", "Марка раствора", "ხსნარის მარკა", "Marca mortarului", "Эритме маркасы", "Ерітінді маркасы")
                lblParam2.text = getLocalizedText("Объём раствора (м³)", "Mortar volume (m³)", "Qorishma hajmi (m³)", "Ҳаҷми маҳлул (м³)", "Շաղախի ծավալ (մ³)", "Аб'ём раствора (м³)", "ხსნარის მოცულობა (მ³)", "Volumul mortarului (m³)", "Эритме көлөмү (м³)", "Ерітінді көлемі (м³)")
                inputThickness.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
                lblParam3.visibility = View.GONE
                lblParam4.visibility = View.GONE
            }
            6 -> { // Штукатурка
                calcTitle.text = getLocalizedText("Декоративная штукатурка", "Decorative Plaster", "Dekorativ suvoq", "Сувоқи озорӣ", "Դեկորատիվ սվաղ", "Дэкаратыўная тынкоўка", "დეკორატიული შელესვა", "Tencuială decorativă", "Декоративдик суак", "Декоративті сылақ")
                calcSubtitle.text = getLocalizedText("Расчёт штукатурки по площади", "Calculate plaster", "Suvoq sarfi", "Ҳисоби сувоқ", "Սվաղի հաշվարկ", "Разлік тынкоўкі", "შელესვის ხარჯი", "Calculul tencuielii", "Суак чыгымы", "Сылақ шығыны")
                lblParam1.text = getLocalizedText("Тип штукатурки", "Plaster type", "Suvoq turi", "Намуди сувоқ", "Սվաղի տեսակ", "Тып тынкоўкі", "შელესვის ტიპი", "Tipul tencuielii", "Суак түрү", "Сылақ түрі")
                lblParam2.text = getLocalizedText("Площадь стен (m²)", "Wall area (m²)", "Devor maydoni (m²)", "Масоҳати девор (м²)", "Պատերի մակերես (մ²)", "Плошча сцен (m²)", "კედლების ფართობი (მ²)", "Suprafața pereților (m²)", "Дубал аянты (m²)", "Қабырға алаңы (m²)")
                inputThickness.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
                lblParam3.visibility = View.GONE
                lblParam4.visibility = View.GONE
            }
            7, 8, 9, 10, 11, 12, 13 -> { // Остальные калькуляторы (Пеноплекс, ПВХ, Плитка, Кладка, Потолок, Плинтус, Стяжка)
                calcTitle.text = menuGetTitle(currentCalculator)
                calcSubtitle.text = getLocalizedText("Профессиональный расчёт параметров", "Professional calculation", "Professional hisob-kitob", "Ҳисобот", "Հաշվարկ", "Разлік", "გამოთვლა", "Calcul", "Эсептөө", "Есептеу")
                lblParam1.text = getLocalizedText("Тип материала", "Material type", "Material turi", "Намуди мавод", "Նյութի տեսակ", "Тып матэрыялу", "მასალის ტიპი", "Tipul materialului", "Материал түрү", "Материал түрі")
                lblParam2.text = getLocalizedText("Площадь / Периметр (м² / м)", "Area / Perimeter", "Maydoni / Perimetri", "Масоҳат", "Մակերես / Պարագիծ", "Плошча / Перымэтр", "ფართობი / პერიმეტრი", "Suprafață / Perimetru", "Аянт / Периметр", "Алаңы / Периметрі")
                inputThickness.visibility = View.GONE
                inputBagWeight.visibility = View.GONE
                lblParam3.visibility = View.GONE
                lblParam4.visibility = View.GONE
            }
        }
    }

    private fun menuGetTitle(calcId: Int): String {
        return when(calcId) {
            7 -> getLocalizedText("Обшивка пеноплексом", "Penoplex Insulation", "Penoplex qoplami", "Пеноплекс", "Пенопլեքս", "Пенаплекс", "პენოპლექსი", "Izolație Penoplex", "Пеноплекс жылуулоо", "Пеноплекс жылылау")
            8 -> getLocalizedText("ПВХ панели", "PVC Panels", "PVX panellar", "Панелҳои ПВХ", "ՊՎԽ պանելներ", "ПВХ панэлі", "პვხ პანელები", "Panouri PVC", "ПВХ панелдери", "ПВХ панельдері")
            9 -> getLocalizedText("Напольная плитка", "Floor Tile", "Pol plitkasi", "Плиткаи фарш", "Հատակի սալիկ", "Налогавая плітка", "იატაკის ფილა", "Gresie", "Пол плиткасы", "Еден плиткасы")
            10 -> getLocalizedText("Кладка блоков и кирпича", "Masonry", "G'isht terish", "Девори хишт", "Աղյուսե շարվածք", "Мураванне", "აგურის წყობა", "Zidărie", "Кыш дубал", "Кірпіш қалау")
            11 -> getLocalizedText("Потолочная плитка", "Ceiling Tile", "Shift plitkasi", "Плиткаи шифт", "Առաստաղի սալիկ", "Стальная плітка", "ჭერის ფილა", "Plafon", "Шып плиткасы", "Төбе плиткасы")
            12 -> getLocalizedText("Плинтус", "Plinth", "Plintus", "Плинтус", "Շրջանակ", "Плінтус", "პლინტუსი", "Pervaz", "Плинтус", "Плинтус")
            13 -> getLocalizedText("Наливной пол / Стяжка", "Self-Leveling Floor", "Quymalov pol", "Фарши рехта", "Լցնովի հատակ", "Наліўная падлога", "თვითგასწორებელი", "Pardoseală turnată", "Куюлучу пол", "Төгілетін еден")
            else -> "Калькулятор"
        }
    }

    private fun calculateResult() {
        val val1 = inputArea.text.toString().toDoubleOrNull() ?: 0.0
        val val2 = inputThickness.text.toString().toDoubleOrNull() ?: 0.0
        val val3 = inputBagWeight.text.toString().toDoubleOrNull() ?: 1.0

        val resultText = when (currentCalculator) {
            1 -> {
                val totalKg = val1 * val2 * 1.2
                val bags = Math.ceil(totalKg / val3).toInt()
                "Расход: %.1f кг\nМешков (по %.0f кг): %d шт.".format(totalKg, val3, bags)
            }
            2 -> {
                val rolls = Math.ceil((val1 * val2) / 5.0).toInt()
                "Требуется рулонов (прим.): %d шт.".format(rolls)
            }
            3 -> {
                val liters = val1 * 0.12 * val2
                "Требуется краски: %.1f литров".format(liters)
            }
            4 -> {
                val pigment = val1 * 0.03
                "Требуется пигмента: %.2f литров".format(pigment)
            }
            5 -> {
                val cementKg = val1 * 350.0
                "Цемента М400: %.1f кг\nПеска: %.1f м³".format(cementKg, val1 * 0.9)
            }
            else -> {
                val total = val1 * 1.15
                "Итого с запасом 15%:\n%.2f единиц (м² / шт.)".format(total)
            }
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
