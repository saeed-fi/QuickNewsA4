📱 QuickNews - Android News Application
👨‍💻 Developer Information

Name: Abdullah Saeed
Platform: Android | Language: Kotlin | Architecture: MVVM


📋 Project Overview
QuickNews is a modern Android application that fetches news articles from a REST API (JSONPlaceholder), stores them locally using SQLite, and presents them through an intuitive Material Design interface. The app demonstrates comprehensive Android development skills including API integration, database management, lifecycle handling, and responsive UI design.

✨ Key Features
1. Theme Management
Three customizable themes (Light, Dark, Custom) with runtime switching via Options Menu. Theme preferences persist across app restarts using SharedPreferences.
2. User Authentication
Secure login system with input validation, authentication flag storage, and automatic navigation based on login state. Maintains session during configuration changes.
3. API Integration & Offline Support
Fetches 100 posts from JSONPlaceholder REST API using Retrofit. All data is cached in Room (SQLite) database for seamless offline access. Implements error handling for network failures.
4. Advanced UI Components

RecyclerView with custom adapter displaying articles efficiently
SearchView for real-time article filtering
WebView integration for in-app browsing with JavaScript support
Three menu types: Options Menu (global actions), Context Menu (long-press), Popup Menu (item actions)

5. CRUD Operations
Full database management: Create (fetch from API), Read (display articles), Update (edit titles), Delete (remove articles). All operations reflect immediately in UI.
6. Lifecycle Management
Robust handling of configuration changes (screen rotation), background/foreground transitions, and process death. Uses ViewModel + LiveData for state preservation without data loss.

🔄 Navigation Flow
Login Screen → Validates credentials → Main Screen (article list with search) → Click article → Detail Screen (full content + WebView) → Back navigation → Options Menu (theme/logout)

🏗️ Technical Stack
UI Layer: Activities, Fragments, XML Layouts, ViewBinding
Business Logic: ViewModel, LiveData, Coroutines
Data Layer: Room Database, Retrofit, SharedPreferences
Libraries: Material Design 3, Gson, RecyclerView

📦 Requirements Fulfilled
✅ Multiple themes with persistence
✅ Login authentication with SharedPreferences
✅ REST API integration (GET requests)
✅ SQLite database with CRUD operations
✅ Custom RecyclerView adapter
✅ Options/Context/Popup menus
✅ WebView with JavaScript enabled
✅ Input validation and controls
✅ Configuration change handling
✅ Offline mode support
