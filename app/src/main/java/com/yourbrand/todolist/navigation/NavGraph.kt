package com.yourbrand.todolist.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yourbrand.todolist.TodoApplication
import com.yourbrand.todolist.ui.components.BottomNavBar
import com.yourbrand.todolist.ui.screens.auth.ForgotPasswordScreen
import com.yourbrand.todolist.ui.screens.auth.LoginScreen
import com.yourbrand.todolist.ui.screens.auth.SignUpScreen
import com.yourbrand.todolist.ui.screens.calendar.CalendarScreen
import com.yourbrand.todolist.ui.screens.home.HomeScreen
import com.yourbrand.todolist.ui.screens.profile.ProfileScreen
import com.yourbrand.todolist.ui.screens.routine.CreateRoutineScreen
import com.yourbrand.todolist.ui.screens.routine.RoutineScreen
import com.yourbrand.todolist.ui.screens.schedule.CreateScheduleScreen
import com.yourbrand.todolist.ui.screens.schedule.MyScheduleScreen
import com.yourbrand.todolist.ui.screens.settings.SettingsScreen
import com.yourbrand.todolist.ui.screens.welcome.WelcomeScreen
import com.yourbrand.todolist.viewmodel.AuthViewModel
import com.yourbrand.todolist.viewmodel.ScheduleViewModel

private val bottomNavRoutes = setOf(
    Screen.Home.route, Screen.Calendar.route, Screen.Routine.route,
    Screen.MySchedule.route, Screen.Profile.route
)

@Composable
fun AppNavGraph(app: TodoApplication) {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModel.Factory(app.userRepository, app.preferencesManager)
    )
    val loggedInUserId by authViewModel.loggedInUserId.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()

    LaunchedEffect(loggedInUserId) {
        loggedInUserId?.let { authViewModel.loadUser(it) }
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = currentRoute in bottomNavRoutes

    val startDestination = if (loggedInUserId != null) Screen.Home.route else Screen.Welcome.route

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(currentRoute = currentRoute) { route ->
                    if (route != currentRoute) {
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(bottom = padding.calculateBottomPadding())
        ) {
            composable(Screen.Welcome.route) {
                WelcomeScreen(onGetStarted = { navController.navigate(Screen.Login.route) })
            }
            composable(Screen.Login.route) {
                LoginScreen(
                    authViewModel = authViewModel,
                    onLoginSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    },
                    onSignUp = { navController.navigate(Screen.SignUp.route) },
                    onForgotPassword = { navController.navigate(Screen.ForgotPassword.route) }
                )
            }
            composable(Screen.SignUp.route) {
                SignUpScreen(
                    authViewModel = authViewModel,
                    onSignUpSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    },
                    onSignIn = { navController.popBackStack() }
                )
            }
            composable(Screen.ForgotPassword.route) {
                ForgotPasswordScreen(
                    authViewModel = authViewModel,
                    onBackToLogin = { navController.popBackStack(Screen.Login.route, inclusive = false) }
                )
            }

            composable(Screen.Home.route) {
                val userId = loggedInUserId ?: return@composable
                val scheduleViewModel: ScheduleViewModel = viewModel(
                    factory = ScheduleViewModel.Factory(app.scheduleRepository, userId)
                )
                HomeScreen(
                    scheduleViewModel = scheduleViewModel,
                    currentUser = currentUser,
                    onSettingsClick = { navController.navigate(Screen.Settings.route) }
                )
            }
            composable(Screen.Calendar.route) {
                val userId = loggedInUserId ?: return@composable
                val scheduleViewModel: ScheduleViewModel = viewModel(
                    factory = ScheduleViewModel.Factory(app.scheduleRepository, userId)
                )
                CalendarScreen(
                    scheduleViewModel = scheduleViewModel,
                    onSettingsClick = { navController.navigate(Screen.Settings.route) }
                )
            }
            composable(Screen.Routine.route) {
                val userId = loggedInUserId ?: return@composable
                val scheduleViewModel: ScheduleViewModel = viewModel(
                    factory = ScheduleViewModel.Factory(app.scheduleRepository, userId)
                )
                RoutineScreen(
                    scheduleViewModel = scheduleViewModel,
                    currentUser = currentUser,
                    onCreateRoutine = { navController.navigate(Screen.CreateRoutine.route) },
                    onSettingsClick = { navController.navigate(Screen.Settings.route) }
                )
            }
            composable(Screen.CreateRoutine.route) {
                val userId = loggedInUserId ?: return@composable
                val scheduleViewModel: ScheduleViewModel = viewModel(
                    factory = ScheduleViewModel.Factory(app.scheduleRepository, userId)
                )
                CreateRoutineScreen(
                    scheduleViewModel = scheduleViewModel,
                    onCreated = { navController.popBackStack() }
                )
            }
            composable(Screen.MySchedule.route) {
                val userId = loggedInUserId ?: return@composable
                val scheduleViewModel: ScheduleViewModel = viewModel(
                    factory = ScheduleViewModel.Factory(app.scheduleRepository, userId)
                )
                MyScheduleScreen(
                    scheduleViewModel = scheduleViewModel,
                    onCreateSchedule = { navController.navigate(Screen.CreateSchedule.route) },
                    onSettingsClick = { navController.navigate(Screen.Settings.route) }
                )
            }
            composable(Screen.CreateSchedule.route) {
                val userId = loggedInUserId ?: return@composable
                val scheduleViewModel: ScheduleViewModel = viewModel(
                    factory = ScheduleViewModel.Factory(app.scheduleRepository, userId)
                )
                CreateScheduleScreen(
                    scheduleViewModel = scheduleViewModel,
                    onClose = { navController.popBackStack() },
                    onCreated = { navController.popBackStack() }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    authViewModel = authViewModel,
                    currentUser = currentUser,
                    onSettingsClick = { navController.navigate(Screen.Settings.route) }
                )
            }
            composable(Screen.Settings.route) {
                SettingsScreen(
                    currentUser = currentUser,
                    preferencesManager = app.preferencesManager,
                    onBack = { navController.popBackStack() },
                    onLogOut = {
                        authViewModel.logOut {
                            navController.navigate(Screen.Welcome.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }
                )
            }
        }
    }
}
