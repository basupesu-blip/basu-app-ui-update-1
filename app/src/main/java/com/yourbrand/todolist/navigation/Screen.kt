package com.yourbrand.todolist.navigation

sealed class Screen(val route: String) {
    data object Welcome : Screen("welcome")
    data object Login : Screen("login")
    data object SignUp : Screen("signup")
    data object ForgotPassword : Screen("forgot_password")
    data object Home : Screen("home")
    data object Calendar : Screen("calendar")
    data object Routine : Screen("routine")
    data object CreateRoutine : Screen("create_routine")
    data object MySchedule : Screen("my_schedule")
    data object CreateSchedule : Screen("create_schedule")
    data object Profile : Screen("profile")
    data object Settings : Screen("settings")
}
