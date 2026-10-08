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
        var englishPercentage = 0f
        var mathPercentage = 0f
        var sciencePercentage = 0f
        var historyPercentage = 0f

        // initialize all progress text (will be 0% at first)
        binding.progressText1.text = "Progress: $minutesStudiedEnglish of $studyGoal minutes (${min(100f,minutesStudiedEnglish.toFloat() / studyGoal.toFloat() * 100)}%)"
        binding.progressText2.text = "Progress: $minutesStudiedMath of $studyGoal minutes (${min(100f,minutesStudiedMath.toFloat() / studyGoal.toFloat() * 100)}%)"
        binding.progressText3.text = "Progress: $minutesStudiedScience of $studyGoal minutes (${min(100f,minutesStudiedScience.toFloat() / studyGoal.toFloat() * 100)}%)"
        binding.progressText4.text = "Progress: $minutesStudiedHistory of $studyGoal minutes (${min(100f,minutesStudiedHistory.toFloat() / studyGoal.toFloat() * 100)}%)"

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
                    englishPercentage = min(100f,minutesStudiedEnglish.toFloat() / studyGoal.toFloat() * 100)
                    binding.progressText1.text = "Progress: $minutesStudiedEnglish of $studyGoal minutes ($englishPercentage%)"
                    binding.progressBar1.setProgress(englishPercentage.toInt(), true)
                }
                "Math" -> {
                    minutesStudiedMath += minsInput
                    mathPercentage = min(100f,minutesStudiedMath.toFloat() / studyGoal.toFloat() * 100)
                    binding.progressText2.text = "Progress: $minutesStudiedMath of $studyGoal minutes ($mathPercentage%)"
                    binding.progressBar2.setProgress(mathPercentage.toInt(), true)
                }
                "Science" -> {
                    minutesStudiedScience += minsInput
                    sciencePercentage = min(100f,minutesStudiedScience.toFloat() / studyGoal.toFloat() * 100)
                    binding.progressText3.text = "Progress: $minutesStudiedScience of $studyGoal minutes ($sciencePercentage%)"
                    binding.progressBar3.setProgress(sciencePercentage.toInt(), true)
                }
                "History" -> {
                    minutesStudiedHistory += minsInput
                    historyPercentage = min(100f,minutesStudiedHistory.toFloat() / studyGoal.toFloat() * 100)
                    binding.progressText4.text = "Progress: $minutesStudiedHistory of $studyGoal minutes ($historyPercentage%)"
                    binding.progressBar4.setProgress(historyPercentage.toInt(), true)
                }
            }
        }

    }
}