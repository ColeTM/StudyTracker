package com.coletm.studytracker

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.coletm.studytracker.ui.theme.StudyTrackerTheme
import com.coletm.studytracker.databinding.ActivityMainBinding
import java.lang.Float.min

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val studyGoal = 120
        var minutesStudiedEnglish = 0
        var minutesStudiedMath = 0
        var minutesStudiedScience = 0
        var minutesStudiedHistory = 0

        // initialize all progress text (will be 0% at first)
        updateEnglishProgress(minutesStudiedEnglish, studyGoal)
        updateMathProgress(minutesStudiedMath, studyGoal)
        updateScienceProgress(minutesStudiedScience, studyGoal)
        updateHistoryProgress(minutesStudiedHistory, studyGoal)

        // set the values in the dropdown menu
        val subjects = listOf("Select", "English", "Math", "Science", "History")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjects)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.subjectDropdown.adapter = adapter

        // check the values of the subject and minutes when user tries to add study time
        binding.logStudyTimeBtn.setOnClickListener {
            val subjectInput = binding.subjectDropdown.selectedItem
            val minsInput = binding.minutesInput.text.toString().toIntOrNull() ?: 0
            // if no valid value for number of minutes, print error message
            if (minsInput == 0) {
                binding.errorText.text = "please enter some number of minutes"
                return@setOnClickListener
            }
            // clear error message if there is no issue
            binding.errorText.text = ""

            // update time studied for appropriate subject
            // progress percentage does not go above 100%
            when (subjectInput.toString()) {
                "Select" -> binding.errorText.text = "please choose a subject"  // make sure user chose a subject
                "English" -> {
                    minutesStudiedEnglish += minsInput
                    updateEnglishProgress(minutesStudiedEnglish, studyGoal)
                }
                "Math" -> {
                    minutesStudiedMath += minsInput
                    updateMathProgress(minutesStudiedMath, studyGoal)
                }
                "Science" -> {
                    minutesStudiedScience += minsInput
                    updateScienceProgress(minutesStudiedScience, studyGoal)
                }
                "History" -> {
                    minutesStudiedHistory += minsInput
                    updateHistoryProgress(minutesStudiedHistory, studyGoal)
                }
            }
        }  // end log study button code

        // reset buttons for each button to reset studying progress to 0
        binding.englishResetBtn.setOnClickListener {
            minutesStudiedEnglish = 0
            updateEnglishProgress(minutesStudiedEnglish, studyGoal)
        }

        binding.mathResetBtn.setOnClickListener {
            minutesStudiedMath = 0
            updateMathProgress(minutesStudiedMath, studyGoal)
        }

        binding.scienceResetBtn.setOnClickListener {
            minutesStudiedScience = 0
            updateScienceProgress(minutesStudiedScience, studyGoal)
        }

        binding.historyResetBtn.setOnClickListener {
            minutesStudiedHistory = 0
            updateHistoryProgress(minutesStudiedHistory, studyGoal)
        }

    }

    // functions to update text and progress bar for each subject
    fun updateEnglishProgress(minutes: Int, goal: Int) {
        val percentage = min(100f, minutes.toFloat() / goal.toFloat() * 100)
        binding.progressText1.text = "Progress: $minutes of $goal minutes ($percentage%)"
        binding.progressBar1.setProgress(percentage.toInt(), true)
    }

    fun updateMathProgress(minutes: Int, goal: Int) {
        val percentage = min(100f, minutes.toFloat() / goal.toFloat() * 100)
        binding.progressText2.text = "Progress: $minutes of $goal minutes ($percentage%)"
        binding.progressBar2.setProgress(percentage.toInt(), true)
    }

    fun updateScienceProgress(minutes: Int, goal: Int) {
        val percentage = min(100f, minutes.toFloat() / goal.toFloat() * 100)
        binding.progressText3.text = "Progress: $minutes of $goal minutes ($percentage%)"
        binding.progressBar3.setProgress(percentage.toInt(), true)
    }

    fun updateHistoryProgress(minutes: Int, goal: Int) {
        val percentage = min(100f, minutes.toFloat() / goal.toFloat() * 100)
        binding.progressText4.text = "Progress: $minutes of $goal minutes ($percentage%)"
        binding.progressBar4.setProgress(percentage.toInt(), true)
    }
}