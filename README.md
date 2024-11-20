# SSComposeExpandableListView

[![Platform-badge]][Android]
[![Jetpack Compose-badge]][Android]
[![API-badge]][Android]
[![kotlin]](https://kotlinlang.org)
[![](https://jitpack.io/v/SimformSolutionsPvtLtd/SSComposeExpandableListView.svg)](https://jitpack.io/#SimformSolutionsPvtLtd/SSComposeExpandableListView)

<!-- Description -->
Welcome to our SSCompose-Expandable-ListView Library! :tada:
Expandable ListView in Jetpack Compose, providing an easy and flexible way to display hierarchical data in an interactive, expandable list format.

## :zap: Features
- :white_check_mark: **Multiple Item Selection**: Supports selecting multiple items at once, allowing users to interact with the list more efficiently.
- :wrench: **Custom Icons**: Easily customizable icons for the header, expand/collapse actions, and selected items, providing a more personalized and visually engaging experience.
- :art: **Customization Options**: Full flexibility to customize the header and list items, including options for text color, background color, and font style, allowing seamless integration with your app's design.
- :page_facing_up: **HTML Text Support**: The list supports rendering HTML-formatted text, making it easier to display rich content like links, bold, italics, and more within list items.

## :framed_picture: Preview
<a href="https://github.com/SimformSolutionsPvtLtd/SSComposeExpandableListView/blob/d557ac99738b14a1a92297883114b33d2ecc9c6a/app/src/main/java/com/app/sscomposeexpandablelistview/ui/MainActivity.kt#L54C13-L54C45"><img src="ExpandableListView.gif" height="500px"/></a>

# :books: How it works:
1. Add the dependency in your app's build.gradle file
    ```kotlin
    dependencies {
        implementation("com.github.SimformSolutionsPvtLtd:SSComposeExpandableListView:<LATEST_TAG>")
    }
    ```
2. Add the JitPack repository
   For latest Android Studio, in **settings.gradle** file
   inside **`dependencyResolutionManagement`** block

    ```kotlin
    dependencyResolutionManagement {
        repositories {
            ...
            maven { url = uri("https://jitpack.io") }
        }
    }
     ```
   
3. Create ExpandableListView with default values or with custom values.
   ```kotlin
   ComposeExpandableListView(
      modifier = Modifier
                     .fillMaxWidth(),
      expandableListData = uiState.simpleExpandableListData,
      onStateChanged = mainViewModel::updateExpandStatus,
      onListItemClicked = mainViewModel::listItemSelected,
      onListItemLongClick = this@MainActivity::listItemLongClicked
   )
   ``` 
4. If you want to use this component in LazyColumn then you can use a variant scoped to LazyListScope as below.
   ```kotlin
   LazyColumn {
      composeExpandableListView(
         expandableListData = uiState.simpleExpandableListData,
         onStateChanged = mainViewModel::updateExpandStatus,
         onListItemClicked = mainViewModel::listItemSelected,
         onListItemLongClicked = this@MainActivity::listItemLongClicked
      )
   }
   ```
 
**Note:** If you have a scrollable Column as a parent then you need to convert your scrollable parent to LazyColumn and use `LazyListScope.composeExpandableListView()` variant which is scoped to LazyListScope.

## :art: Customization

### You can customize the Expandable listview by providing below parameters.

| Parameter Name            | Parameter Type                                                  | Description                                                                                                 | Default Value                    |
|---------------------------|-----------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|----------------------------------|
| expandableListData        | ExpandableListData                                              | List of [ExpandableListData] representing the expandable list's headers and child items.                    | N/A                              |
| headerStylingAttributes   | HeaderStylingAttributes                                         | Defines styling for headers, including appearance and layout.                                               | defaultHeaderStylingAttributes   |
| listItemStylingAttributes | ListItemStylingAttributes                                       | Defines styling for list items, including appearance and layout.                                            | defaultListItemStylingAttributes |
| contentAnimation          | ContentAnimation                                                | Defines expand/collapse animation for expandable list content.                                              | defaultContentAnimation          |
| expandedIcon              | Int                                                             | Drawable resource ID for the icon when a header is expanded.                                                | up arrow                         |
| collapseIcon              | Int                                                             | Drawable resource ID for the icon when a header is collapsed.                                               | down arrow                       |
| itemSelectedIcon          | Int                                                             | Drawable resource ID for the icon when a item is selected.                                                  | check mark                       |
| onStateChanged            | (headerIndex: Int, isExpanded: Boolean) -> Unit                 | Callback for when a header's expand/collapse state changes, providing the header index and expanded state.  | Empty Lambda                     |
| onListItemClicked         | (headerIndex: Int, itemIndex: Int, isSelected: Boolean) -> Unit | Callback for when a list item is clicked, providing the header index, item index, and selection state.      | Empty Lambda                     |
| onListItemLongClicked     | (headerIndex: Int, itemIndex: Int, isSelected: Boolean) -> Unit | Callback for when a list item is long pressed, providing the header index, item index, and selection state. | Empty Lambda                     |


## :heart: Find this samples useful?

Support it by joining [stargazers] :star: for this repository.

## :handshake: How to Contribute?

Whether you're helping us fix bugs, improve the docs, or a feature request, we'd love to have you! :
muscle: \
Check out our __[Contributing Guide]__ for ideas on contributing.

## :lady_beetle: Bugs and Feedback

For bugs, feature requests, and discussion use [GitHub Issues].

## :rocket: Other Mobile Libraries

Check out our other libraries [Awesome-Mobile-Libraries].

## :balance_scale: License

Distributed under the MIT license. See [LICENSE] for details.
<!-- Reference links -->

[Android]:                    https://www.android.com/

[Android App Architecture]:   https://developer.android.com/topic/architecture

[stargazers]:                 https://github.com/SimformSolutionsPvtLtd/SSComposeExpandableListView/stargazers

[Contributing Guide]:         CONTRIBUTING.md

[Github Issues]:              https://github.com/SimformSolutionsPvtLtd/SSComposeExpandableListView/issues

[Awesome-Mobile-Libraries]:   https://github.com/SimformSolutionsPvtLtd/Awesome-Mobile-Libraries

[license]:                    LICENSE

<!-- Badges -->

[Platform-badge]:             https://img.shields.io/badge/Platform-Android-green.svg?logo=Android

[Jetpack Compose-badge]:      https://img.shields.io/badge/Jetpack_Compose-v1.9.2-1c274a.svg?logo=jetpackcompose&logoColor=3ddc84

[API-badge]:                  https://img.shields.io/badge/API-24+-51b055

[kotlin]:                     https://img.shields.io/badge/Kotlin-v1.9.0-blue.svg