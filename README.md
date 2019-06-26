# UpdateChecker-Android
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/vlad1m1r990/Lemniscate/blob/master/LICENSE)
[![](https://jitpack.io/v/guy-4444/UpdateChecker-Android.svg)](https://jitpack.io/#guy-4444/UpdateChecker-Android)
[![API](https://img.shields.io/badge/API-18%2B-green.svg?style=flat)]()

A library for simple implementation of available update on the store.

Checks for an update in the Google Play store and displays a message with a link to the store.

#### Only One Line!
<img src="https://raw.githubusercontent.com/guy-4444/SmartRateUsDialog-Android/master/sc_1.png" width="320">
<img src="https://raw.githubusercontent.com/guy-4444/SmartRateUsDialog-Android/master/sc_2.png" width="320">

<img src="https://raw.githubusercontent.com/guy-4444/SmartRateUsDialog-Android/master/sc_5.png" width="320">
<img src="https://raw.githubusercontent.com/guy-4444/SmartRateUsDialog-Android/master/sc_6.png" width="320">


## Setup
Step 1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
	maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency:

```
dependencies {
    implementation 'com.github.guy-4444:UpdateChecker-Android:1.00.01'
}
```
## Usage

###### How to
```java
    // Call with simple line
    UpdateChecker.checkForUpdate(this);

    // Or create custom strings for dialog - 
    String title = "Update Available";
    String message = "There is update available. We strongly recommend you update to the latest version.";
    String updateButton = "UPDATE";
    String cancelButton = "No, Thanks";
    UpdateChecker.checkForUpdate(MainActivity.this, title, message, updateButton, cancelButton);

```

## License

    Copyright 2019 Guy Isakov

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


