package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PromptActivity : AppCompatActivity() {

    private lateinit var showCorrectAnswerButton: Button
    private lateinit var answerTextView: TextView
    private var correctAnswer: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_prompt)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        correctAnswer = intent.getBooleanExtra("correctAnswer", false)
        showCorrectAnswerButton = findViewById(R.id.show_question)
        answerTextView = findViewById(R.id.question_answer)


        showCorrectAnswerButton.setOnClickListener {
            val answer = if (correctAnswer) R.string.button_true else R.string.button_false
            answerTextView.setText(answer)

            // Zwróć informację do MainActivity
            val resultIntent = Intent()
            resultIntent.putExtra("hintShown", true)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
