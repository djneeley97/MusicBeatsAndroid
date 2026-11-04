# MusicBeats for Android

Android Studio (Kotlin + Jetpack Compose) port of the MusicBeats iOS app.

## Open it
1. In Android Studio choose **File > Open** and pick `AndroidStudioProjects/MusicBeatsAndroid`.
2. Let Gradle sync (first time takes a few minutes), then press Run.

## Turn on Firebase (login / sign-up)
1. Firebase console > your MusicBeats project > **Add app > Android**.
2. Package name: `com.musicbeats`
3. Download `google-services.json` and put it in the `app/` folder.
4. Sync again. Until you do this, the app runs but login shows "Firebase isn't set up yet".

## Images
Copy your Xcode asset images into `app/src/main/res/drawable/`, with the same
names in lowercase (migos.png, drake.png, whitecar.png, spaceman.png ...).
Missing images show a colored placeholder.

## Where everything is
Swift file                  -> Kotlin file (app/src/main/java/com/musicbeats/)
BeatCard.swift              -> ui/views/BeatCard.kt
BeatCardSmall.swift         -> ui/views/BeatCardSmall.kt
CustomTabBar.swift          -> ui/views/CustomTabBar.kt
FavoritesView.swift         -> ui/views/FavoritesView.kt (+ Beat in data/Models.kt)
FirebaseHomeView.swift      -> ui/views/FirebaseHomeView.kt
FirebaseLoginView.swift     -> ui/views/FirebaseLoginView.kt
FirebaseSignupView.swift    -> ui/views/FirebaseSignupView.kt
HomePage.swift              -> ui/views/HomePage.kt
HomeView.swift              -> ui/views/HomeView.kt
ProfileView.swift           -> ui/views/ProfileView.kt
SearchResults.swift         -> ui/views/SearchResults.kt
SearchView.swift            -> ui/views/SearchView.kt
SectionView.swift           -> ui/views/SectionView.kt
SettingsView.swift          -> ui/views/SettingsView.kt
SongDetailView.swift        -> ui/views/SongDetailView.kt
TabBarButton.swift          -> ui/views/TabBarButton.kt
UploadView.swift            -> ui/views/UploadView.kt
(AuthService)               -> data/AuthService.kt
(SongService - stub)        -> data/SongService.kt
(app entry / navigation)    -> MainActivity.kt, MusicBeatsApp.kt
