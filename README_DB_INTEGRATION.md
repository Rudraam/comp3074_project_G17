# Simple Room Database Integration

This project now includes a local Room database for storing `PocketItem` records.

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

## Usage Example (Compose / Activity)
```kotlin
val repo = DatabaseProvider.pocketItemRepository(context)
val vm = PocketItemViewModel(repo)

// Collect uiState in Compose:
val items by vm.uiState.collectAsState()
vm.add("Sample Title", "Optional description")
```

## Suggested Integration Steps
1. Inject `PocketItemRepository` into your existing ViewModel(s) (consider using Hilt later).
2. Replace any in-memory lists with `vm.uiState` from `PocketItemViewModel`.
3. Perform writes via `vm.add`, `vm.delete`, `vm.clearAll`.
4. If you add more entities: extend `@Database(entities = [...])` and create new DAO & repository.

## Building
From project root (Windows PowerShell):
```powershell
./gradlew.bat clean assembleDebug
```

## Notes
- Migration strategy: currently uses `fallbackToDestructiveMigration()`; replace with proper migrations before releasing.
- Threading: All writes are dispatched via `viewModelScope` (coroutines). Reads use `Flow` so they are automatically asynchronous.
- If you introduce DI (Hilt), remove `DatabaseProvider` and provide `AppDatabase` & DAO via modules.

