package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var questionList: List<Question>
    private var currentQuestionIndex = 0
    private var correctAnswersCount = 0
    private var currentIndex = 0  // Zmienna do przechowywania aktualnego indeksu
    companion object {
        const val KEY_CURRENT_INDEX = "currentIndex"  // Stała do klucza
        const val QUIZ_TAG = "QuizApp"  // Stała do logów
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Lifecycle", "onCreate called")

        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX)
        }

        val questionTextView: TextView = findViewById(R.id.question_Textview)
        val buttonTrue: Button = findViewById(R.id.button_true)
        val buttonFalse: Button = findViewById(R.id.button_false)
        val buttonNext: Button = findViewById(R.id.button_next)
        val buttonHint: Button = findViewById(R.id.button_hint)

        buttonHint.setOnClickListener {
            val intent = Intent(this, PromptActivity::class.java)
            intent.putExtra("correctAnswer", questionList[currentQuestionIndex].trueAnswer)
            startActivity(intent)
        }
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
    override fun onStart() {
        super.onStart()
        Log.d("Lifecycle", "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle", "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle", "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "onDestroy called")
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CURRENT_INDEX, currentQuestionIndex)
        Log.d(QUIZ_TAG, "Wywołana została metoda: onSaveInstanceState")
    }
}
