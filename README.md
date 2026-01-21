# findMovies

A Kotlin Multiplatform (KMP) app designed for interview-style clean architecture and MVVM. The app has two screens:

- Search movies from OMDb.
- View movie details.

## Architecture

The shared module follows clean architecture and keeps the platform UIs thin.

- core: Common helpers (result wrapper, dispatcher provider, StateFlow bridge).
- data: OMDb API client, DTOs, mappers, and repository implementations.
- domain: Models, repository contracts, and use cases.
- presentation: UI state + ViewModels (MVVM).
- di: AppModule for manual dependency wiring.

## Modules

- shared: Business logic + networking for both platforms.
- androidApp: Jetpack Compose UI.
- iosApp: SwiftUI UI.

## Project structure

```
shared/src/commonMain/kotlin/com/george/kmpmoviepoc/
  core/
  data/
    mapper/
    remote/
    repository/
  di/
  domain/
    model/
    repository/
    usecase/
  presentation/
    detail/
    search/
```

## API key setup

OMDb requires an API key. Configure it per platform:

- Android: add `OMDB_API_KEY=your_key_here` to `local.properties`.
- iOS: set `OMDB_API_KEY` inside `iosApp/iosApp/Info.plist` or update the fallback in `iosApp/iosApp/AppConfig.swift`.

## Run

- Android: open the project in Android Studio and run `androidApp`.
- iOS: open `iosApp/iosApp.xcodeproj` in Xcode and run.

## Notes

- AppModule is a lightweight manual DI container to keep the sample small.
- ViewModels expose `StateFlow` for Compose and a small `CStateFlow` bridge for SwiftUI.
- API errors are surfaced as user-friendly messages in the UI.
