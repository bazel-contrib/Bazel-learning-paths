# Exercise 4

In this exercise, you will declare package dependencies and external Maven dependencies for a Java-based project. We'll want to model the directories `src/main/java/com/bmuschko/app`, `src/main/java/com/bmuschko/app/config`, and `src/main/resources` as fine-grained Bazel packages with dependencies to each other as needed. The class `com.bmuschko.app.config.PropertiesConfigurationReader` uses the external library [Apache Commons Configuration](https://commons.apache.org/proper/commons-configuration/) which needs to be defined as external dependency for the project.

The following image shows the high-level architecture.

![java-binary](imgs/java-dependency.png)

Reference the documentation of the [java_binary rule](https://docs.bazel.build/versions/main/be/java.html#java_binary) and [java_library rule](https://docs.bazel.build/versions/main/be/java.html#java_library) for more information.

1. Inspect the existing source code files in the `start` directory.
2. Define a `WORKSPACE` file in the root directory of `start`. Leave the file empty for now.
3. Define a `BUILD` file for the individual directories mentioned above. The package `src/main/java/com/bmuschko/app` should use the java_binary rule. The package `src/main/java/com/bmuschko/app/config` and `src/main/resources` should use the java_library rule. Define package dependencies and visibility as you see fit.
4. Run the main class `com.bmuschko.app.Application` of the compiled program. Ensure that the error message indicates that the external dependency cannot be resolved.
5. Add the [rules_jvm_external](https://github.com/bazelbuild/rules_jvm_external) to the `WORKSPACE` file with the latest version. Declare the dependency `org.apache.commons:commons-configuration2:2.7` and the repository [Maven Central](https://repo1.maven.org/maven2). Reference the dependency in the package `src/main/java/com/bmuschko/app/config`.
6. Run the main class `com.bmuschko.app.Application` of the compiled program. What error message do you see? Discuss the root cause of the issue and a fix for it.
7. After fixing all the issues, run the program again. You should see the output of the program on the console.