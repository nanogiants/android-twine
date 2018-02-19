## What is this?

This plugin provides a gradle task to generate multi language string resources easily. 
Please read the [twine documentation](https://github.com/scelis/twine) for more information! 

## Preconditions

* A working [twine](https://github.com/scelis/twine) installation

## Installation

Add the following to your project level build.gradle

```groovy
buildscript {
  repositories {
    jcenter()
  }
  dependencies {
    classpath 'eu.appcom.gradle:android-twine:0.2.1'
  }
}
```

Add the following to the module level build.gradle

```groovy
apply plugin: 'eu.appcom.gradle.android-twine'
```

## Usage

Place your [twine-conforming](https://github.com/scelis/twine#twine-file-format) **localisation.txt** file in a subfolder of the project root directory named 'localisation'.

```
$project_root/localisation/localisation.txt
```

Open a terminal at your project root folder (we recommend using the android studio terminal) and run the task 'generateStrings'

```
./gradlew generateStrings
```

## License

Copyright (c) 2017 appcom interactive GmbH

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
