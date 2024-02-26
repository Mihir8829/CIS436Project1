package com.example.project1
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project1.databinding.ActivityMainBinding
import kotlin.random.Random
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var diceIds: Array<Int>
    private var num1 = 0
    private var num2 = 0
    private var operation = "+"
    private var curPlayer = "P1"
    private var playerOneScore = 0
    private var playerTwoScore = 0
    private var jackpot = 5
    private var index = 0
    private var message = "Roll the Dice!"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        diceIds = arrayOf(R.drawable.dice_1,R.drawable.dice_2,R.drawable.dice_3,R.drawable.dice_4,R.drawable.dice_5,R.drawable.dice_6 )
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.jackpot.text = jackpot.toString()
        binding.playerOneScore.text = playerOneScore.toString()
        binding.playerTwoScore.text = playerTwoScore.toString()
        binding.msg.text = message

        fun add(num1: Int, num2: Int):Int{
            return num1 + num2
        }
        fun sub(num1: Int, num2: Int):Int{
            return num1 - num2
        }
        fun multiply(num1: Int, num2: Int):Int{
            return num1 * num2
        }
        fun addOp(){
            num1 = Random.nextInt(0, 100)
            num2 = Random.nextInt(0, 100)
            operation = "+"
        }
        fun subOp(){
            num1 = Random.nextInt(0, 100)
            num2 = Random.nextInt(0, num1)
            operation = "-"
        }
        fun multiplyOp(){
            num1 = Random.nextInt(0, 21)
            num2 = Random.nextInt(0, 21)
            operation = "x"
        }
        fun assignValues(n1: Int, n2: Int, op: String){
            binding.num1.text = n1.toString()
            binding.num2.text = n2.toString()
            binding.operation.text = op
        }
        fun assignScore(){
            if (jackpot >= 5 && index == 5) {
                if (curPlayer == "P1") playerOneScore += jackpot else playerTwoScore += jackpot
                jackpot = 5
            } else if(index == 3){
                if (curPlayer == "P1") playerOneScore += 2 else  playerTwoScore += 2
            } else {
                if (curPlayer == "P1") playerOneScore += 1 else playerTwoScore += 1
            }
        }
        binding.btnRoll.setOnClickListener{
            index = Random.nextInt(0,6)
            binding.dice.setImageResource(diceIds[index])

            when (index) {
                0 -> {
                    message = "Add the two Numbers"
                    addOp()
                }
                1 -> {
                    message = "Subtract the two Numbers"
                    subOp()
                }
                2 -> {
                    message = "Multiply the two Numbers"
                    multiplyOp()
                }
                3 -> {
                    message = "Get Double or Nothing!"
                        when (Random.nextInt(0,3)) {
                            0 -> {
                                addOp()
                                assignValues(num1, num2, operation)
                            }
                            1 -> {
                                subOp()
                                assignValues(num1, num2, operation)
                            }
                            else -> {
                                multiplyOp()
                                assignValues(num1, num2, operation)
                            }
                        }
                }
                4 -> {
                    message = "Sorry Player ${curPlayer}, you lost your chance"
                    binding.msg.text = message
                    curPlayer = if(curPlayer == "P1") "P2" else "P1"
                    binding.player.text = curPlayer
                    return@setOnClickListener
                }
                5 -> {
                    message = "Try for JACKPOT!"
                    binding.msg.text = message
                    when (Random.nextInt(0,3)) {
                        0 -> {
                            addOp()
                            assignValues(num1, num2, operation)
                            return@setOnClickListener
                        }
                        1 -> {
                            subOp()
                            assignValues(num1, num2, operation)
                            return@setOnClickListener
                        }
                        else -> {
                            multiplyOp()
                            assignValues(num1, num2, operation)
                            return@setOnClickListener
                        }
                    }
                }
            }
            assignValues(num1, num2, operation)
            binding.msg.text = message
        }
        binding.btnGuess.setOnClickListener{
            val answer = binding.ans.text.toString().toInt()
            when (operation) {
                "+" -> {
                    if (answer == add(num1, num2)) {
                        assignScore()
                    } else { jackpot += if (index == 3) 2 else 1 }
                }
                "-" -> {
                    if (answer == sub(num1, num2)) {
                        assignScore()
                    } else { jackpot += if (index == 3) 2 else 1 }
                }
                "x" -> {
                    if (answer == multiply(num1, num2)) {
                        assignScore()
                    } else { jackpot += if (index == 3) 2 else 1 }
                }
            }

            binding.ans.text.clear()
            binding.playerOneScore.text = playerOneScore.toString()
            binding.playerTwoScore.text = playerTwoScore.toString()
            binding.jackpot.text = jackpot.toString()
            curPlayer = if (curPlayer == "P1") "P2" else "P1"
            binding.player.text = curPlayer
            binding.msg.text = "Roll the dice $curPlayer"

            if (playerOneScore >= 20) {
                binding.msg.text = "Player 1 WON!!!"
                binding.playerOneScore.text = 0.toString()
                binding.playerTwoScore.text = 0.toString()
                binding.jackpot.text = 5.toString()
            }
            else if (playerTwoScore >= 20){
                binding.msg.text = "Player 2 WON!!!"
                binding.playerOneScore.text = 0.toString()
                binding.playerTwoScore.text = 0.toString()
                binding.jackpot.text = 5.toString()
            }
        }
    }
}

