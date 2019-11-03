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

    private lateinit var numList: ArrayList<Double>
    private lateinit var opeList: ArrayList<Char>

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
            formulaText.text = calculate()
        }
    }

    private fun calculate(): String {
        if (!createElementsList()) {
            return formula
        }

        power()
        timesDiv()
        plusMinus()

        if (ceil(numList[0]) == floor(numList[0])) {
            formula = numList[0].toInt().toString()
            return numList[0].toInt().toString()
        }
        else {
            formula = numList[0].toString()
            return numList[0].toString()
        }
    }

    private fun createElementsList(): Boolean {
        numList = ArrayList()
        opeList = ArrayList()
        var strnum = ""
        var isNum = false
        var isDecimal = false
        var isPower = false
        val toast = Toast.makeText(applicationContext, "Invalid formula!", Toast.LENGTH_SHORT)

        for (c in formula) {
            if (!isNum && c !in '0'..'9') {
                toast.show()
                return false
            }
            if (c in '0'..'9' || c == '.') {
                strnum += c
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
                opeList.add(c)
                numList.add(strnum.toDouble())
                strnum = ""
                isNum = false
                isDecimal = false
            }
        }
        numList.add(strnum.toDouble())
        return true
    }

    private fun power() {
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
    }

    private fun timesDiv() {
        var i = 0
        while (i < opeList.size) {
            if (opeList[i] == '×') {
                var b1: BigDecimal = BigDecimal.valueOf(numList[i])
                var b2: BigDecimal = BigDecimal.valueOf(numList[i+1])
                numList[i] = b1.times(b2).toDouble()
                numList.removeAt(i + 1)
                opeList.removeAt(i)
            }
            else if (opeList[i] == '÷') {
                var b1: BigDecimal = BigDecimal.valueOf(numList[i])
                var b2: BigDecimal = BigDecimal.valueOf(numList[i+1])
                numList[i] = b1.div(b2).toDouble()
                numList.removeAt(i + 1)
                opeList.removeAt(i)
            }
            else {
                i++
            }
        }
    }

    private fun plusMinus () {
        for (j in 0 until opeList.size) {
            if (opeList[0] == '+') {
                var b1: BigDecimal = BigDecimal.valueOf(numList[0])
                var b2: BigDecimal = BigDecimal.valueOf(numList[1])
                numList[0] = b1.plus(b2).toDouble()
                numList.removeAt(1)
                opeList.removeAt(0)
            }
            else if (opeList[0] == '-') {
                var b1: BigDecimal = BigDecimal.valueOf(numList[0])
                var b2: BigDecimal = BigDecimal.valueOf(numList[1])
                numList[0] = b1.minus(b2).toDouble()
                numList.removeAt(1)
                opeList.removeAt(0)
            }
        }
    }
}
