# Complexity Summary (Time / Space)

This file summarizes the time and space complexity of the main operations.
Network cost is not measured here (treated as O(1) locally).

| Method / Operation | File | Time | Space | Reasoning |
|---|---|---|---|---|
| `SearchViewModel.search()` | `shared/.../SearchViewModel.kt` | O(1) local | O(1) | Delegates to use case; no loops. |
| `MovieRepositoryImpl.searchMovies()` | `shared/.../MovieRepositoryImpl.kt` | O(n) | O(n) | `map` over results creates a new list. |
| `OmdbMovieDto.toDomain()` | `shared/.../MovieMapper.kt` | O(1) | O(1) | Field-to-field mapping. |
| `DetailViewModel.load()` | `shared/.../DetailViewModel.kt` | O(1) local | O(1) | Delegates to use case; no loops. |
| `OmdbDetailResponse.toDomain()` | `shared/.../MovieMapper.kt` | O(1) | O(1) | Field-to-field mapping. |
| `LazyColumn(items)` | `androidApp/.../SearchScreen.kt` | O(n) | O(n) | Renders each list item. |
| `ForEach(0..<movieCount)` | `iosApp/.../SearchView.swift` | O(n) | O(n) | Renders each list item. |
| `posterUrl(...)` | `androidApp/.../PosterUrl.kt` | O(1) | O(1) | String concatenation. |
| `posterUrl(...)` | `iosApp/.../PosterUrl.swift` | O(1) | O(1) | String concatenation. |

---

### Notes
- `n` = number of movies returned by the API.
- UI render cost is proportional to list size.
