# Solution

Navigate to the `start` directory.

```
$ cd start
```

Modify the existing `WORKSPACE` file by added the JUnit dependency.

```
maven_install(
    artifacts = [
        "org.apache.commons:commons-configuration2:2.7",
        "commons-beanutils:commons-beanutils:1.9.2",
        "junit:junit:4.13.2"
    ],
    repositories = [
        "https://repo1.maven.org/maven2",
    ],
)
```

Create the `BUILD` in the directory `src/test/java/com/bmuschko/app/config`. Add the relevant configuration to the `java_test` rule.

```
java_test(
    name = "app-config-test",
    srcs = glob(["*.java"]),
    test_class = "com.bmuschko.app.config.AllTests",
    deps = [
        "//src/main/java/com/bmuschko/app/config:app-lib",
        "@maven//:junit_junit"
    ]
)
```

Executing the `test` command will notify you about the inadequate visibility of the package under test.

```
$ bazel test //src/test/java/com/bmuschko/app/config:app-config-test
ERROR: /Users/bmuschko/dev/projects/getting-started-with-bazel/exercises/05-java-test/start/src/test/java/com/bmuschko/app/config/BUILD:1:10: in java_test rule //src/test/java/com/bmuschko/app/config:app-config-test: target '//src/main/java/com/bmuschko/app/config:app-lib' is not visible from target '//src/test/java/com/bmuschko/app/config:app-config-test'. Check the visibility declaration of the former target if you think the dependency is legitimate
ERROR: Analysis of target '//src/test/java/com/bmuschko/app/config:app-config-test' failed; build aborted: Analysis of target '//src/test/java/com/bmuschko/app/config:app-config-test' failed
INFO: Elapsed time: 13.129s
INFO: 0 processes.
FAILED: Build did NOT complete successfully (31 packages loaded, 740 targets configured)
````

Change the visibility for the `config` package by exposing it to the corresponding test package.

```
java_library(
    name = "app-lib",
    srcs = glob(["*.java"]),
    visibility = [
        "//src/main/java/com/bmuschko/app:__pkg__",
        "//src/test/java/com/bmuschko/app/config:__pkg__"
    ],
    deps = [
        "@maven//:org_apache_commons_commons_configuration2"
    ],
    runtime_deps = [
        "//src/main/resources:app-resources",
        "@maven//:commons_beanutils_commons_beanutils"
    ]
)
```
Running the command again will execute the test suite. The single test case should show the `PASSED` status.

```
$ bazel test //src/test/java/com/bmuschko/app/config:app-config-test
INFO: Analyzed target //src/test/java/com/bmuschko/app/config:app-config-test (2 packages loaded, 7 targets configured).
INFO: Found 1 test target...
Target //src/test/java/com/bmuschko/app/config:app-config-test up-to-date:
  bazel-bin/src/test/java/com/bmuschko/app/config/app-config-test.jar
  bazel-bin/src/test/java/com/bmuschko/app/config/app-config-test
INFO: Elapsed time: 5.857s, Critical Path: 5.15s
INFO: 13 processes: 4 internal, 6 darwin-sandbox, 3 worker.
INFO: Build completed successfully, 13 total actions
//src/test/java/com/bmuschko/app/config:app-config-test                  PASSED in 0.5s

Executed 1 out of 1 test: 1 test passes.
There were tests whose specified size is too big. Use the --test_verbose_timeout_warnings command line option to see whi
INFO: Build completed successfully, 13 total actions
```