# MVI Compose Pokedex

## Stack
- **Jetpack Compose**
- **Jetpack Navigation**
- **Kotlin Coroutine**
- **Pagination 3** pokemon list
- **Room** save opaginated pokemons into db
- **Uniflow-ky** MVI architecture library
- **Retrofit**
- **Moshi** de/serialize onjects to/from json, used along with retrofit and room
- **Landscapist Glide** Glide for Jetpack Compose
- **Hilt** for DI
- **Google Truth && Turbine** unit tests

## Structure
This application has one activity: `MainActivity`, the composition starts from there

The code is structured as follow:
 - **data** containes servicea and repositories
 - **di** containes the hilt's modules
 - **utils** containes some helpers
 - **ui**:
   - **theme** containes the theme configurations
   - **shared** contains composable functions shared among multiple screens
   - **screen** containes all the screens

Every screen is sctructured as follow:
 - **\<name\>Screen** the screen composable function
 - **\<name\>ViewModel** the viewModel of the screen
 - **components** composables used by the screen (also personalization of shared composables)

## Architecture
The navigation inside the app is managed in `PokemonNavController`, used inside `MainActivity`
The navigation is managed by the router inside `PokemonComposeApplication`, listening for new `Route` in the `routerChannel` makes the `PokemonNavController` navige to new directions

Every ViewModel extends `AndroidIntentDataFlow`, this class is a customization of `AndroidDataFlow` to allow an antirection throught reactive patterns.

`AndroidIntentDataFlow` exposes:
- `routerChannel` a channel linked to the router where the VM can send new `Route` intents
- `intentChannel` a channel where anyone can send an `Intent`, an intent is an object used by ViewModels to launch an `Action`, this channel is useful to avoid composable funcations have too much callback parameters, with this channel they potentially could have always one parameter for every action
- `intentHandler` a lambda overridable from the other ViewModel that's called when a new intent is received, the aim of this function is to call the right `Action` for a specific `Intent`
- `events` a `LiveData` of `UIEvent` (the same for the `states` that `AndroidDataFlow` already exposes) NOTE: this is a simple `UIEvent`, not an `Event<UIEvent>` like in `LiveDataPublisher`
