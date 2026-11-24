# Roadmap

## Done (this pass)
- [x] Removed the GNews and Alpha Vantage keys hard-coded in source; all keys now come from `local.properties` or CI env vars
- [x] **All-Time cards always showed 0**: `calculateAllTimeStats()` queried `getByMonth("", "")`, which matches no rows. Now uses `SUM()` over the whole table
- [x] Month filters used UTC (`strftime(..., 'unixepoch')`), so transactions near midnight landed in the wrong month for users in Vietnam/US. Now uses `'localtime'`
- [x] News refresh spinner never stopped on network errors (error LiveData was never posted)
- [x] Chat replies parsed by a tested `AiReplyParser`: handles ```json fences, text around the JSON, `"$2,000"` and decimal amounts, and refuses to save a 0 amount
- [x] Removed the reflection hack that guessed `ChatMessage.Sender` constants
- [x] Background callbacks no longer crash when the user leaves the screen mid-request (`UiThread.post`)
- [x] CSV export escapes commas/quotes in notes; file streams are closed with try-with-resources
- [x] Save failures are shown to the user instead of being swallowed
- [x] Thread-safe singletons for Retrofit/OkHttp clients
- [x] `usesCleartextTraffic=false` (all APIs are HTTPS)
- [x] Repo cleanup: removed `res.zip` and tracked `.idea/` files, unused `libs.versions.toml`; `gradlew` is now executable
- [x] JVM unit tests and GitHub Actions build

## Phase 1: correctness and data safety
- [x] Replaced `fallbackToDestructiveMigration()` with explicit migrations (`Migrations.java`) and `exportSchema = true`; v2 adds date indexes
- [x] Robolectric tests for the DAOs and the v1→v2 migration
- [ ] Store amounts in cents (`long`) so $5.50 is not rounded to $6
- [ ] Use LiveData/Flow queries from Room so screens update without manual `refreshDatabaseData()` calls
- [x] Optional Cloudflare Worker proxy (`proxy/`) keeps the AI key off the device, pins the model and caps tokens; tested in CI

## Phase 2: code health
- [ ] Rename package `com.example.savemoneytime` to `com.danghoang.budgetmate` (needed before a Play Store release)
- [ ] Split `StatsFragment` (600 lines) into a chart builder per chart type
- [ ] Replace `findViewById` with the already-enabled View Binding
- [ ] Upgrade AGP 8.1 → 8.7+, Gradle 8.4 → 8.10+, target SDK 35
- [ ] Robolectric tests for the ViewModel

## Phase 3: product
- [ ] Monthly budgets per category with progress bars and alerts
- [ ] Recurring transactions (rent, salary)
- [ ] Multi-currency (USD/VND) with a user setting
- [ ] Export through the system share sheet / MediaStore Downloads
- [ ] Screenshots and a short demo GIF in the README
