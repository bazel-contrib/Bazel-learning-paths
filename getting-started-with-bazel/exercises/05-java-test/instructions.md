# Exercise 5

In this exercise, you will declare a dependency on a test library for a Java-based project and execute the test classes. Model the directory `src/test/java/com/bmuschko/app/config` as a Bazel package by using the java_test rule.

The following image shows the high-level architecture.

![java-test](imgs/java-test.png)

Reference the documentation of the [java_test rule](https://docs.bazel.build/versions/main/be/java.html#java_test) for more information.

1. Inspect the existing source code files in the `start` directory.
2. Declare a dependency on `junit:junit:4.13.2` available in the repository [Maven Central](https://repo1.maven.org/maven2).
3. Create a `BUILD` file for the directory `src/test/java/com/bmuschko/app/config`. Assign the test class `com.bmuschko.app.config.AllTests` and the JUnit dependency. Ensure the proper visibility for the package under test.
4. Run the tests. All test cases should pass.