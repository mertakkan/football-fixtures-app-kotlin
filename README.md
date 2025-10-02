# Football Fixtures App

A modern Android application built with Kotlin and Jetpack Compose that provides users with football match fixtures and user profile management capabilities.

## Overview

This is a comprehensive football fixtures application that allows users to view upcoming football matches while maintaining a personalized user experience through account management and profile customization. The app integrates with external football APIs to fetch real-time match data and provides a clean, modern interface for users to interact with.

## Key Features

### 🏠 Home Screen
- **Match Fixtures Display**: Shows upcoming football matches with team logos, names, and match details
- **Real-time Data**: Fetches live fixture data from the API-Football service
- **Modern UI**: Clean card-based layout displaying match information in an intuitive format
- **Team Information**: Displays both home and away team details including logos and names

### 👤 User Authentication
- **Sign Up/Login System**: Dual-mode authentication allowing users to create accounts or log in
- **Form Validation**: Ensures all required fields are completed before submission
- **Secure Storage**: User credentials are stored securely using Room database
- **Session Management**: Maintains user sessions across app navigation

### 📱 Profile Management
- **Profile Customization**: Users can edit their personal information including name and surname
- **Profile Picture**: Camera integration allowing users to capture and set profile photos
- **Permission Handling**: Proper camera permission management for photo capture
- **Data Persistence**: Profile changes are saved to local database

## Technical Architecture

### 🏗️ Architecture Pattern
- **MVVM (Model-View-ViewModel)**: Clean separation of concerns with ViewModels managing UI state
- **Repository Pattern**: Centralized data management with offline-first approach
- **Dependency Injection**: Manual dependency injection through AppContainer

### 🛠️ Core Technologies

#### Frontend
- **Jetpack Compose**: Modern declarative UI framework for building native Android interfaces
- **Material Design 3**: Latest Material Design components and theming
- **Navigation Compose**: Type-safe navigation between screens
- **Coil**: Image loading library for efficient image handling and caching

#### Backend & Data
- **Room Database**: Local SQLite database for offline data storage and user management
- **Retrofit**: HTTP client for API communication with football data services
- **Gson**: JSON serialization/deserialization for API responses
- **OkHttp**: HTTP client with logging and timeout configurations

#### State Management
- **LiveData**: Reactive data streams for UI updates
- **Coroutines**: Asynchronous programming for network calls and database operations
- **ViewModel**: Lifecycle-aware components for managing UI-related data

### 🌐 API Integration
- **API-Football Service**: Integration with RapidAPI's football data service
- **Real-time Fixtures**: Fetches upcoming matches for specified leagues and seasons
- **Team Data**: Retrieves team logos, names, and match details
- **Error Handling**: Robust error handling for network failures and API issues

### 📊 Data Models
- **User Entity**: Complete user profile with authentication and personal information
- **Fixture Models**: Structured data models for match information, teams, and status
- **Response Models**: API response wrappers for type-safe data handling

## App Flow

1. **Authentication**: Users start at the login screen where they can either sign up for a new account or log in with existing credentials
2. **Home Dashboard**: After successful authentication, users are taken to the home screen displaying upcoming football fixtures
3. **Profile Access**: Users can access their profile through the profile picture in the app bar
4. **Profile Management**: In the profile screen, users can edit their information and update their profile picture using the device camera
5. **Logout**: Users can log out from the profile screen, returning them to the login screen

## Key Components

### Screens
- **LoginScreen**: Handles user authentication with form validation
- **HomeScreen**: Displays football fixtures in a scrollable list format
- **ProfileScreen**: Manages user profile information and photo capture

### ViewModels
- **LoginViewModel**: Manages authentication state and user creation/login logic
- **MainViewModel**: Handles fixture data fetching and state management
- **ProfileViewModel**: Manages profile updates and user data persistence

### Data Layer
- **UserRepository**: Centralized user data management with offline capabilities
- **UserDatabase**: Room database configuration with DAO interfaces
- **ApiService**: Retrofit interface for football API communication

## Permissions
- **Internet**: Required for fetching football fixture data from external APIs
- **Camera**: Required for profile picture capture functionality

## Modern Android Development Practices

This application demonstrates several modern Android development practices:
- **Jetpack Compose** for declarative UI development
- **Material Design 3** for consistent, modern user interface
- **Room Database** for efficient local data storage
- **Retrofit** for type-safe API communication
- **Coroutines** for asynchronous programming
- **MVVM Architecture** for maintainable code structure
- **Navigation Compose** for type-safe navigation
- **Dependency Injection** for loose coupling and testability

The app provides a seamless user experience for football enthusiasts who want to stay updated with upcoming matches while maintaining a personalized profile within the application.
