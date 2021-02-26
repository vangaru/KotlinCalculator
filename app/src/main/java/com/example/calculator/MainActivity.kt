package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.floor

class MainActivity : AppCompatActivity() {

    private val mathOperations = listOf("/", "*", "-", "+")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        math_operation_field.text = "0"

        btn_0.setOnClickListener { putInputInMathOperationField("0") }
        btn_1.setOnClickListener { putInputInMathOperationField("1") }
        btn_2.setOnClickListener { putInputInMathOperationField("2") }
        btn_3.setOnClickListener { putInputInMathOperationField("3") }
        btn_4.setOnClickListener { putInputInMathOperationField("4") }
        btn_5.setOnClickListener { putInputInMathOperationField("5") }
        btn_6.setOnClickListener { putInputInMathOperationField("6") }
        btn_7.setOnClickListener { putInputInMathOperationField("7") }
        btn_8.setOnClickListener { putInputInMathOperationField("8") }
        btn_9.setOnClickListener { putInputInMathOperationField("9") }

        btn_plus.setOnClickListener { putInputInMathOperationField("+") }
        btn_comma.setOnClickListener { putInputInMathOperationField(".") }
        btn_minus.setOnClickListener { putInputInMathOperationField("-") }
        btn_divide.setOnClickListener { putInputInMathOperationField("/") }
        btn_multiply.setOnClickListener { putInputInMathOperationField("*") }
        btn_left_bracket.setOnClickListener { putInputInMathOperationField("(") }
        btn_right_bracket.setOnClickListener { putInputInMathOperationField(")") }

        btn_AC.setOnClickListener {
            eraseTextFromMathOperationField()
            math_operation_field.text = "0"
        }

        btn_equal.setOnClickListener{
            val result = getResult()
            putResultInMathOperationField(result)
        }

    }

    private fun isMathOperation(operation: String): Boolean{
        return (operation in mathOperations)
    }

    private fun putInputInMathOperationField(text: String){

        if( math_operation_field.text.length in 0..8 )  {
            math_operation_field.textSize = 80f
        }
        else if( math_operation_field.text.length in 9..13 ) {
            math_operation_field.textSize = 50f
        }

        if(math_operation_field.text.length in 0..13) {

            val lastChar: String? = if(math_operation_field.text.isNotEmpty())
                math_operation_field.text[math_operation_field.text.lastIndex].toString() else null


            if( !(isMathOperation(text) && ( math_operation_field.text.isNullOrEmpty() || lastChar?.let { isMathOperation(it) } == true) ) ){
                math_operation_field.append(text)
            }

        }

    }

    private fun getResult() : String{
        val expression = ExpressionBuilder(math_operation_field.text.toString()).build()
        val res = expression.evaluate()

        eraseTextFromMathOperationField()

        //if result is int erase .0
        return if(floor(res) == res) res.toLong().toString() else res.toString()
    }

    private fun putResultInMathOperationField(text: String){
        if( text.length in 0..8 )  {
            math_operation_field.textSize = 80f
        }
        else if( text.length > 8 ) {
            math_operation_field.textSize = 50f
        }

        if (text.length in 0..13) {
            eraseTextFromMathOperationField()
            math_operation_field.append(text)
        }
        else if(text.length > 13){
            eraseTextFromMathOperationField()
            math_operation_field.append(text.substring(0, 11) + "e${text.length - 11}")
        }
    }

    private fun eraseTextFromMathOperationField(){
        math_operation_field.text = ""
    }


}