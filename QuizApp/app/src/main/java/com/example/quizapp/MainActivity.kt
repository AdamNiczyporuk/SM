package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var questionList: List<Question>
    private var currentQuestionIndex = 0
    private var correctAnswersCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val questionTextView: TextView = findViewById(R.id.question_Texview)
        val buttonTrue: Button = findViewById(R.id.button_true)
        val buttonFalse: Button = findViewById(R.id.button_false)
        val buttonNext: Button = findViewById(R.id.button_next)

        // Lista pytań
        questionList = listOf(
            Question(R.string.q_activity, true),
            Question(R.string.q_find_resources, false),
            Question(R.string.q_listener, true),
            Question(R.string.q_resources, true),
            Question(R.string.q_version, false)
        )

        displayQuestion(questionTextView)

        // Obsługa kliknięcia przycisku True
        buttonTrue.setOnClickListener {
            checkAnswer(true)
        }

        // Obsługa kliknięcia przycisku False
        buttonFalse.setOnClickListener {
            checkAnswer(false)
        }

        // Obsługa kliknięcia przycisku Next
        buttonNext.setOnClickListener {
            currentQuestionIndex++  // Przejdź do następnego pytania
            if (currentQuestionIndex < questionList.size) {
                displayQuestion(questionTextView)  // Wyświetl nowe pytanie
            } else {
                displayResult()  // Wyświetl wynik na końcu quizu
            }
        }
    }

    private fun displayQuestion(questionTextView: TextView) {
        if (currentQuestionIndex < questionList.size) {
            val currentQuestion = questionList[currentQuestionIndex]
            questionTextView.setText(currentQuestion.questionId) // Używamy questionId do ustawienia tekstu pytania
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val currentQuestion = questionList[currentQuestionIndex]

        // Sprawdzamy, czy odpowiedź użytkownika jest prawidłowa
        if (userAnswer == currentQuestion.trueAnswer) {
            correctAnswersCount++  // Zwiększamy liczbę poprawnych odpowiedzi
            Toast.makeText(this, "Dobra odpowiedź!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Zła odpowiedź!", Toast.LENGTH_SHORT).show()
        }
    }
    private fun displayResult() {
        val resultMessage = "Koniec Quizu!!Twój wynik: $correctAnswersCount/${questionList.size} poprawnych odpowiedzi"
        Toast.makeText(this, resultMessage, Toast.LENGTH_LONG).show()
    }
}
