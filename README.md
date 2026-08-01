# To Do List App (Android)

A native Android app built with **Kotlin + Jetpack Compose**, matching the UI you shared:
Welcome, Login, Sign Up, Forgot Password, Home, Calendar, Routine, Create Routine,
My Schedule, Create Schedule, Profile, and Settings.

## 100% Local Storage
No backend, no internet permission required.
- **Room database** stores your account and every schedule/routine directly on the device.
- **DataStore** remembers your logged-in session and notification toggles.
- Passwords are hashed (SHA-256) before being stored — never saved in plain text.
- "Forgot password" is a local flow: it checks the email exists on-device, then lets you set a new password (no real email is sent, since everything stays on your phone).

## How to open & run
1. Install **Android Studio** (Koala/2024.1 or newer recommended).
2. Choose **Open**, and select the `ToDoListApp` folder (this project root).
3. Let Gradle sync (it will download the Android Gradle Plugin, Kotlin, Compose, Room, Navigation, and DataStore dependencies automatically).
4. Run the `app` configuration on an emulator or a physical device (minimum Android 7.0 / API 24).

## Project structure
```
app/src/main/java/com/yourbrand/todolist/
├── MainActivity.kt                 # Entry point
├── TodoApplication.kt              # Holds shared DB/repository singletons
├── data/
│   ├── local/                      # Room entities, DAOs, database, converters
│   ├── repository/                 # UserRepository, ScheduleRepository
│   ├── PreferencesManager.kt       # DataStore session + settings
│   └── PasswordHasher.kt
├── viewmodel/                      # AuthViewModel, ScheduleViewModel
├── navigation/                     # Screen routes + NavGraph
└── ui/
    ├── theme/                      # Colors, typography, Material theme
    ├── components/                 # Reusable widgets (buttons, cards, bottom nav, etc.)
    └── screens/
        ├── welcome/
        ├── auth/                   # Login, SignUp, ForgotPassword
        ├── home/
        ├── calendar/
        ├── routine/                # RoutineScreen, CreateRoutineScreen
        ├── schedule/                # MyScheduleScreen, CreateScheduleScreen
        ├── profile/
        └── settings/
```

## Features
- **Sign up / Log in** with a locally stored account (no backend needed)
- **Home**: today's schedule count + list, pulled live from Room
- **Calendar**: month grid, tap any day to see that day's schedules (including recurring routines)
- **Routine**: create a weekly recurring schedule (choose category + day of week + time)
- **My Schedule**: category grid (Personal, Work, Study, Home, Movie, Travel) with live counts, filterable list, "Create Schedule" for one-off items
- **Profile**: view/edit your name, username, email, phone
- **Settings**: notification toggles (persisted), log out

## Notes / next steps you may want
- Add date/time pickers (currently plain text fields, e.g. `2026-08-01`, `09:00`) — swap in `DatePickerDialog` / `TimePickerDialog` for a native picker feel.
- Add app icon artwork (a simple placeholder adaptive icon is included).
- Add local notifications/reminders using `WorkManager` if you want alerts before a schedule starts.
