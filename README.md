## android-twine

This plugin provides a Gradle task to easily generate multi language string resources with [twine](https://github.com/scelis/twine). The plugin is backward compatible to older twine vesions.

## Preconditions

* A working [twine](https://github.com/scelis/twine) installation

## Installation

Add the dependency to your project level `build.gradle`

```groovy
buildscript {
  repositories {
    jcenter()
  }
  dependencies {
    classpath 'eu.appcom.gradle:android-twine:0.3.0'
  }
}
```

Add the plugin to your module level `build.gradle`

```groovy
apply plugin: 'eu.appcom.gradle.android-twine'
```

## Usage

By default the plugin will expect the [twine-conforming](https://github.com/scelis/twine#twine-file-format) **localisation.txt** file in a subfolder of your project root directory named 'localisation'.

```
$project_root/localisation/localisation.txt
```

You can change this behaviour by set a custom `inputFilePath` (see [Configuration](https://github.com/appcom-interactive/android-twine#configuration)).

### Tasks

Generate Android strings.xml files from localization file
```
./gradlew generateStrings
```

Print the currently installed twine version
```
./gradlew printTwineVersion
```

## Configuration

You can optionally customize the default configuration inside your module level `build.gradle`
```
twineplugin {
  inputFilePath = "localisation_file_path" // default "/localisation/localisation.txt"
  outputResPath = "res_path_inside_project" // default "/app/src/main/res"
  validate = false // default true
}
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
