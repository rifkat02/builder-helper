package com.example.builderhelper

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var calcTitle: TextView
    private lateinit var calcSubtitle: TextView
    private lateinit var inputArea: EditText
    private lateinit var inputThickness: EditText
    private lateinit var inputBagWeight: EditText
    private lateinit var txtResult: TextView
    private lateinit var btnCalculate: Button
    private lateinit var spinnerType: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        calcTitle = findViewById(R.id.calcTitle)
        calcSubtitle = findViewById(R.id.calcSubtitle)
        inputArea = findViewById(R.id.inputArea)
        inputThickness = findViewById(R.id.inputThickness)
        inputBagWeight = findViewById(R.id.inputBagWeight)
        txtResult = findViewById(R.id.txtResult)
        btnCalculate = findViewById(R.id.btnCalculate)
        spinnerType = findViewById(R.id.spinnerType)

        // Кнопка открытия бокового меню
        findViewById<ImageButton>(R.id.btnMenu).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Кнопка Поделиться
        findViewById<ImageButton>(R.id.btnShare).setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Скачивайте калькулятор Помогатор Строителя!")
            }
            startActivity(Intent.createChooser(intent, "Поделиться"))
        }

        // Выбор вариантов для первого калькулятора (Шпаклёвка)
        val puttyTypes = arrayOf("Финишная (1.2 кг/м²/мм)", "Стартовая (1.4 кг/м²/мм)", "Гипсовая (1.0 кг/м²/мм)")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, puttyTypes)
        spinnerType.adapter = adapter

        // Расчёт шпаклёвки
        btnCalculate.setOnClickListener {
            val area = inputArea.text.toString().toDoubleOrNull() ?: 0.0
            val thickness = inputThickness.text.toString().toDoubleOrNull() ?: 0.0
            val bagWeight = inputBagWeight.text.toString().toDoubleOrNull() ?: 1.0

            val consumptionRate = when (spinnerType.selectedItemPosition) {
                0 -> 1.2
                1 -> 1.4
                else -> 1.0
            }

            val totalKg = area * thickness * consumptionRate
            val bags = Math.ceil(totalKg / bagWeight).toInt()

            txtResult.visibility = View.VISIBLE
            txtResult.text = "Расход: %.1f кг\nПотребуется мешков: %d шт.".format(totalKg, bags)
        }

        // Быстрое переключение калькуляторов в меню
        findViewById<Button>(R.id.menuPutty).setOnClickListener {
            calcTitle.text = "Шпаклёвка"
            calcSubtitle.text = "Расчёт расхода шпаклёвки по типу смеси и площади"
            drawerLayout.closeDrawers()
        }

        findViewById<Button>(R.id.menuColoring).setOnClickListener {
            calcTitle.text = "Колеровка"
            calcSubtitle.text = "Расчёт колер-пасты для всех типов смесей"
            drawerLayout.closeDrawers()
        }

        findViewById<Button>(R.id.menuPlaster).setOnClickListener {
            calcTitle.text = "Декоративная штукатурка"
            calcSubtitle.text = "Все виды декоративных покрытий"
            drawerLayout.closeDrawers()
        }
    }
}
