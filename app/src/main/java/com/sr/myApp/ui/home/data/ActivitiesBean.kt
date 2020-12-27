package com.sr.myApp.ui.home.data

data class ActivitiesBean(
    val activities: List<Activity>?,
    val summary: Summary
)

data class Activity(
    val name: String,
    val calories: Int,
    val steps: Int
) {

    val getFormattedCaleries
        get() = "Calories: $calories burns"

    val getFormattedSteps
        get() = "Steps: $steps"
}

data class Summary(
    val activityCalories: Int
)