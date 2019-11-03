package lopezlab.shun.smartcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import kotlin.math.ceil
import kotlin.math.floor

class MainActivity : AppCompatActivity() {

    private var formula = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        formulaText.text = "0"
        initButtons()
    }

    private fun initButtons() {
        zero.setOnClickListener {
            formula += "0"
            formulaText.text = formula
        }
        one.setOnClickListener {
            formula += "1"
            formulaText.text = formula
        }
        two.setOnClickListener {
            formula += "2"
            formulaText.text = formula
        }
        three.setOnClickListener {
            formula += "3"
            formulaText.text = formula
        }
        four.setOnClickListener {
            formula += "4"
            formulaText.text = formula
        }
        five.setOnClickListener {
            formula += "5"
            formulaText.text = formula
        }
        six.setOnClickListener {
            formula += "6"
            formulaText.text = formula
        }
        seven.setOnClickListener {
            formula += "7"
            formulaText.text = formula
        }
        eight.setOnClickListener {
            formula += "8"
            formulaText.text = formula
        }
        nine.setOnClickListener {
            formula += "9"
            formulaText.text = formula
        }
        dot.setOnClickListener {
            formula += "."
            formulaText.text = formula
        }
        plus.setOnClickListener {
            formula += "+"
            formulaText.text = formula
        }
        minus.setOnClickListener {
            formula += "-"
            formulaText.text = formula
        }
        multipile.setOnClickListener {
            formula += "×"
            formulaText.text = formula
        }
        divide.setOnClickListener {
            formula += "÷"
            formulaText.text = formula
        }
        power.setOnClickListener {
            formula += "^"
            formulaText.text = formula
        }
        parenthesis_open.setOnClickListener {
            formula += "("
            formulaText.text = formula
        }
        parenthesis_close.setOnClickListener {
            formula += ")"
            formulaText.text = formula
        }
        clear.setOnClickListener {
            formula = ""
            formulaText.text = "0"
        }
        equal.setOnClickListener {
            formula = calculate(formula)
            formulaText.text = formula
        }
    }

    private fun checkFormula(str: String): Boolean {
        var isNum = false
        var isDecimal = false
        var isPower = false
        val toast = Toast.makeText(applicationContext, "Invalid formula!", Toast.LENGTH_SHORT)

        for (c in str) {
            if (!isNum && c !in '0'..'9') {
                toast.show()
                return false
            }
            if (c in '0'..'9' || c == '.') {
                isNum = true
                if (c == '.') {
                    if (isDecimal || isPower) {
                        toast.show()
                        return false
                    }
                    else {
                        isDecimal = true
                    }
                }
            }
            else {
                isPower = c == '^'
                isNum = false
                isDecimal = false
            }
        }
        return true
    }

    private fun calculate(str: String): String {
        if (!checkFormula(str)) {
            return str
        }

        val (numList, opeList) = createElementsList(str)

        val (numLis, opeLis) = power(numList, opeList)

        val (numLi, opeLi) = timesDiv(numLis, opeLis)

        val ans = plusMinus(numLi, opeLi)

        return if (ceil(ans) == floor(ans)) {
            ans.toInt().toString()
        } else {
            ans.toString()
        }
    }

    private fun createElementsList(str: String): Pair<ArrayList<Double>, ArrayList<Char>> {
        val numList = ArrayList<Double>()
        val opeList = ArrayList<Char>()
        var strnum = ""

        for (c in str) {
            if (c in '0'..'9' || c == '.') {
                strnum += c
            }
            else {
                opeList.add(c)
                numList.add(strnum.toDouble())
                strnum = ""
            }
        }
        numList.add(strnum.toDouble())
        return Pair(numList, opeList)
    }

    private fun power(numList: ArrayList<Double>, opeList: ArrayList<Char>): Pair<ArrayList<Double>, ArrayList<Char>> {
        var i = 0
        while (i < opeList.size) {
            if (opeList[i] == '^') {
                var b: BigDecimal = BigDecimal.valueOf(1)
                for (j in 1..numList[i+1].toInt()) {
                    b = b.times(BigDecimal.valueOf(numList[i]))
                }
                numList[i] = b.toDouble()
                numList.removeAt(i + 1)
                opeList.removeAt(i)
            }
            else {
                i++
            }
        }
        return Pair(numList, opeList)
    }

    private fun timesDiv(numList: ArrayList<Double>, opeList: ArrayList<Char>): Pair<ArrayList<Double>, ArrayList<Char>> {
        var i = 0
        while (i < opeList.size) {
            when {
                opeList[i] == '×' -> {
                    val b1: BigDecimal = BigDecimal.valueOf(numList[i])
                    val b2: BigDecimal = BigDecimal.valueOf(numList[i+1])
                    numList[i] = b1.times(b2).toDouble()
                    numList.removeAt(i + 1)
                    opeList.removeAt(i)
                }
                opeList[i] == '÷' -> {
                    val b1: BigDecimal = BigDecimal.valueOf(numList[i])
                    val b2: BigDecimal = BigDecimal.valueOf(numList[i+1])
                    numList[i] = b1.div(b2).toDouble()
                    numList.removeAt(i + 1)
                    opeList.removeAt(i)
                }
                else -> i++
            }
        }
        return Pair(numList, opeList)
    }

    private fun plusMinus (numList: ArrayList<Double>, opeList: ArrayList<Char>): Double {
        for (j in 0 until opeList.size) {
            if (opeList[0] == '+') {
                val b1: BigDecimal = BigDecimal.valueOf(numList[0])
                val b2: BigDecimal = BigDecimal.valueOf(numList[1])
                numList[0] = b1.plus(b2).toDouble()
                numList.removeAt(1)
                opeList.removeAt(0)
            }
            else if (opeList[0] == '-') {
                val b1: BigDecimal = BigDecimal.valueOf(numList[0])
                val b2: BigDecimal = BigDecimal.valueOf(numList[1])
                numList[0] = b1.minus(b2).toDouble()
                numList.removeAt(1)
                opeList.removeAt(0)
            }
        }
        return numList[0]
    }
}
