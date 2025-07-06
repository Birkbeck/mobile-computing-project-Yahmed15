# Culinary Companion

A mobile app for managing and browsing personal recipes, built with Android, Kotlin, Room, and MVVM architecture.

## Features

- Add, view, edit, and delete recipes
- Categorise and filter recipes by type
- Search by recipe name or ingredients
- Mark favourite recipes
- View full recipe details
- Navigate between Dashboard, Profile, Login/Register, and Settings
- Local storage via Room database
- Live updates with LiveData and ViewModel
- Unit-tested logic for recipe creation

## Tech Stack

- Language: Kotlin  
- Architecture: MVVM (Model-View-ViewModel)  
- Database: Room  
- UI: XML Layouts, Material Components  
- Navigation: Android Jetpack Navigation Component  
- Testing: JUnit, AndroidX Test  

## Project Structure

app/
├── src/
│   ├── main/
│   │   ├── java/com/example/culinarycompanion/
│   │   │   ├── data/               # Room entities, DAOs, and repository
│   │   │   ├── ui/                 # Fragments (Dashboard, Detail, Add/Edit, Settings)
│   │   │   ├── util/               # Utility classes (e.g., RecipeUtils.kt)
│   │   │   └── viewmodel/          # ViewModel for shared recipe state
│   │   └── res/                    # Layouts, strings, styles, drawables
│   └── test/java/…/             # Unit tests

## Running Unit Tests

./gradlew test

Or run `RecipeUnitTest.kt` directly via Android Studio.

## Setup Instructions

1. Clone the repo:

git clone https://github.com/YOUR_USERNAME/culinary-companion.git
cd culinary-companion

2. Open in Android Studio (Arctic Fox or later recommended)

3. Ensure `jvmTarget = 17` is set in `app/build.gradle`

4. Build and run on emulator or physical device

## TODO / Future Enhancements

- User authentication and cloud sync
- Image support for recipes
- Import/export feature
- Sharing recipes with others

## Author

Yousuf Md  
Mobile Computing Coursework 2025  
Birkbeck University of London
