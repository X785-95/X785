package com.example.lab1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var generateButton: Button
    private lateinit var calculateButton: Button
    private lateinit var TextLabel1: TextView
    private lateinit var TextLabel2: TextView
    private lateinit var TextField1: TextView
    private lateinit var TextField2: TextView
    private lateinit var result: TextView

    private val Randomizer = Randomizer()
    private var mainnum1 = 0
    private var masnum1 = Array<Int>(5, {0})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        generateButton = findViewById(R.id.generateButton)
        calculateButton = findViewById(R.id.calculateButton)
        TextLabel1 = findViewById(R.id.Label1)
        TextLabel2 = findViewById(R.id.Label2)
        TextField1 = findViewById (R.id.Input1)
        TextField2 = findViewById (R.id.Input2)
        result = findViewById(R.id.resultoutput)

        // Устанавливаем подсказки
        TextField1.hint = "Введите число"
        TextField2.hint = "Введите массив чисел"
        result.hint = "Результат появится здесь"
    }

    private fun setupClickListeners() {
        generateButton.setOnClickListener {
            generation()
        }

        calculateButton.setOnClickListener {
            calculateEasy()
        }
    }

    //генерируем случайное главное число и массив чисел (по умолчанию от 2 до 1000)
    private fun generation() {

            mainnum1 = Randomizer.Randomization1()
            masnum1 = Randomizer.Randomization2()
            TextField1.text = mainnum1.toString()
            TextField2.text = Randomizer.formation(masnum1)
            result.text = ""
    }

    //вывод взаимно простых чисел по отношению к главному числу
    private fun calculateEasy() {
        if (TextField1.text == "" || TextField2.text == "") {
            result.text = "Ошибка: Сгенерируйте данные"
            return
        }
        mainnum1 = TextField1.text.toString().toInt()
        masnum1 = Randomizer.Parser(TextField2.text.toString())

        result.text = Randomizer.formation(Randomizer.Easynums(mainnum1,masnum1))
    }
}