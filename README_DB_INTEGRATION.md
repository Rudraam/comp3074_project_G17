# Simple Room Database Integration

This project has a local Room database for storing `PocketItem` records.

## Added Components
- `PocketItem` (Entity)
- `PocketItemDao` (DAO with Flow-based query)
- `AppDatabase` (Singleton RoomDatabase)
- `PocketItemRepository` (Abstraction for CRUD operations)
- `PocketItemViewModel` (Sample ViewModel using coroutines & StateFlow)
- `DatabaseProvider` (Convenient repository provider)

## Gradle Changes
Added to `app/build.gradle.kts`:
```kotlin
id("kotlin-kapt")
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
kapt("androidx.room:room-compiler:2.6.1")
```

```kotlin
val repo = DatabaseProvider.pocketItemRepository(context)
val vm = PocketItemViewModel(repo)

// Collect uiState in Compose:
val items by vm.uiState.collectAsState()
vm.add("Sample Title", "Optional description")
```



