# Solution

Navigate to the `start` directory.

```
$ cd start
```

Create the `WORKSPACE` file in the root directory. There's no need to add content to the file.

```
$ touch WORKSPACE
```

The `src/main/java/com/bmuschko/app/BUILD` file looks as follows. It needs to have a package dependency on the java library created by the `config` package.

```
java_binary(
    name = "app-binary",
    srcs = ["Application.java"],
    main_class = "com.bmuschko.app.Application",
    deps = [
        "//src/main/java/com/bmuschko/app/config:app-lib"
    ]
)
```

The `src/main/java/com/bmuschko/app/config/BUILD` file looks as follows. It needs to have a dependency on the java library created by the `resources` package. Notice that we only need the resources at runtime and not compile-time. To be consumed, this package needs to change its visibility.

```
java_library(
    name = "app-lib",
    srcs = glob(["*.java"]),
    visibility = ["//src/main/java/com/bmuschko/app:__pkg__"],
    runtime_deps = [
        "//src/main/resources:app-resources"
    ]
)
```

The `src/main/resources/BUILD` file looks as follows. It needs to bundle the property files and make itself consumable from the `config` package.

```
java_library(
    name = "app-resources",
    srcs = glob(["*.properties"]),
    visibility = ["//src/main/java/com/bmuschko/app/config:__pkg__"]
)
```

Executing the build produces a compilation error because the external library is not available on the classpath.

```
$ bazel run //src/main/java/com/bmuschko/app:app-binary
INFO: Analyzed target //src/main/java/com/bmuschko/app:app-binary (26 packages loaded, 670 targets configured).
INFO: Found 1 target...
ERROR: /Users/bmuschko/dev/projects/getting-started-with-bazel/exercises/04-java-dependency/start/src/main/java/com/bmuschko/app/config/BUILD:1:13: Building src/main/java/com/bmuschko/app/config/libapp-lib.jar (2 source files) failed: (Exit 1): java failed: error executing command external/remotejdk11_macos/bin/java -XX:+UseParallelOldGC -XX:-CompactStrings '--patch-module=java.compiler=external/remote_java_tools_darwin/java_tools/java_compiler.jar' ... (remaining 15 argument(s) skipped)
src/main/java/com/bmuschko/app/config/PropertiesConfigurationReader.java:3: error: package org.apache.commons.configuration2 does not exist
import org.apache.commons.configuration2.FileBasedConfiguration;
                                        ^
src/main/java/com/bmuschko/app/config/PropertiesConfigurationReader.java:4: error: package org.apache.commons.configuration2 does not exist
import org.apache.commons.configuration2.PropertiesConfiguration;
                                        ^
src/main/java/com/bmuschko/app/config/PropertiesConfigurationReader.java:5: error: package org.apache.commons.configuration2.builder does not exist
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
                                                ^
src/main/java/com/bmuschko/app/config/PropertiesConfigurationReader.java:6: error: package org.apache.commons.configuration2.builder.fluent does not exist
import org.apache.commons.configuration2.builder.fluent.Parameters;
                                                       ^
src/main/java/com/bmuschko/app/config/PropertiesConfigurationReader.java:7: error: package org.apache.commons.configuration2.ex does not exist
import org.apache.commons.configuration2.ex.ConfigurationException;
                                           ^
src/main/java/com/bmuschko/app/config/PropertiesConfigurationReader.java:16: error: cannot find symbol
        FileBasedConfiguration configuration;
        ^
  symbol:   class FileBasedConfiguration
  location: class PropertiesConfigurationReader
src/main/java/com/bmuschko/app/config/PropertiesConfigurationReader.java:17: error: cannot find symbol
        Parameters params = new Parameters();
        ^
  symbol:   class Parameters
  location: class PropertiesConfigurationReader
src/main/java/com/bmuschko/app/config/PropertiesConfigurationReader.java:17: error: cannot find symbol
        Parameters params = new Parameters();
                                ^
  symbol:   class Parameters
  location: class PropertiesConfigurationReader
src/main/java/com/bmuschko/app/config/PropertiesConfigurationReader.java:21: error: cannot find symbol
            FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
            ^
  symbol:   class FileBasedConfigurationBuilder
  location: class PropertiesConfigurationReader
src/main/java/com/bmuschko/app/config/PropertiesConfigurationReader.java:21: error: cannot find symbol
            FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                                          ^
  symbol:   class FileBasedConfiguration
  location: class PropertiesConfigurationReader
src/main/java/com/bmuschko/app/config/PropertiesConfigurationReader.java:22: error: cannot find symbol
                    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        ^
  symbol:   class FileBasedConfigurationBuilder
  location: class PropertiesConfigurationReader
src/main/java/com/bmuschko/app/config/PropertiesConfigurationReader.java:22: error: cannot find symbol
                    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                                                      ^
  symbol:   class FileBasedConfiguration
  location: class PropertiesConfigurationReader
src/main/java/com/bmuschko/app/config/PropertiesConfigurationReader.java:22: error: cannot find symbol
                    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                                                                              ^
  symbol:   class PropertiesConfiguration
  location: class PropertiesConfigurationReader
src/main/java/com/bmuschko/app/config/PropertiesConfigurationReader.java:26: error: cannot find symbol
        } catch (ConfigurationException e) {
                 ^
  symbol:   class ConfigurationException
  location: class PropertiesConfigurationReader
Target //src/main/java/com/bmuschko/app:app-binary failed to build
Use --verbose_failures to see the command lines of failed build steps.
INFO: Elapsed time: 17.905s, Critical Path: 4.83s
INFO: 11 processes: 6 internal, 4 darwin-sandbox, 1 worker.
FAILED: Build did NOT complete successfully
FAILED: Build did NOT complete successfully
```

Add the rules_jvm_external to the WORKSPACE file with the latest version and declare the dependency with the GAV `org.apache.commons:commons-configuration2:2.7`.

```
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "4.0"
RULES_JVM_EXTERNAL_SHA = "31701ad93dbfe544d597dbe62c9a1fdd76d81d8a9150c2bf1ecf928ecdf97169"

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    sha256 = RULES_JVM_EXTERNAL_SHA,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        "org.apache.commons:commons-configuration2:2.7",
    ],
    repositories = [
        "https://repo1.maven.org/maven2",
    ],
)
```

Modify the `config` package definition by adding the external dependency.

```
java_library(
    name = "app-lib",
    srcs = glob(["*.java"]),
    visibility = ["//src/main/java/com/bmuschko/app:__pkg__"],
    deps = [
        "@maven//:org_apache_commons_commons_configuration2",
    ],
    runtime_deps = [
        "//src/main/resources:app-resources"
    ]
)
```

Executing the `run` command results in a runtime error. The class `org/apache/commons/beanutils/DynaBean` cannot be found on the runtime classpath. The rules_jvm_external do not resolve the transitive closure of dependencies based on the provided POM and therefore requires an explicit declaration of transitive dependencies.

```
$ bazel run //src/main/java/com/bmuschko/app:app-binary
INFO: Analyzed target //src/main/java/com/bmuschko/app:app-binary (8 packages loaded, 299 targets configured).
INFO: Found 1 target...
Target //src/main/java/com/bmuschko/app:app-binary up-to-date:
  bazel-bin/src/main/java/com/bmuschko/app/app-binary.jar
  bazel-bin/src/main/java/com/bmuschko/app/app-binary
INFO: Elapsed time: 5.219s, Critical Path: 0.67s
INFO: 7 processes: 4 internal, 1 darwin-sandbox, 2 worker.
INFO: Build completed successfully, 7 total actions
INFO: Build completed successfully, 7 total actions
Exception in thread "main" java.lang.NoClassDefFoundError: org/apache/commons/beanutils/DynaBean
	at java.base/java.lang.Class.forName0(Native Method)
	at java.base/java.lang.Class.forName(Class.java:315)
	at com.sun.proxy.$Proxy0.<clinit>(Unknown Source)
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:490)
	at java.base/java.lang.reflect.Proxy.newProxyInstance(Proxy.java:1022)
	at java.base/java.lang.reflect.Proxy.newProxyInstance(Proxy.java:1008)
	at org.apache.commons.configuration2.builder.fluent.Parameters.createParametersProxy(Parameters.java:306)
	at org.apache.commons.configuration2.builder.fluent.Parameters.properties(Parameters.java:245)
	at com.bmuschko.app.config.PropertiesConfigurationReader.read(PropertiesConfigurationReader.java:23)
	at com.bmuschko.app.Application.main(Application.java:11)
Caused by: java.lang.ClassNotFoundException: org.apache.commons.beanutils.DynaBean
	at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:583)
	at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:178)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:521)
	... 13 more
```

To fix the issue, add the dependency with the GAV `commons-beanutils:commons-beanutils:1.9.2` to the list of dependencies in the `WORKSPACE` file.

```
maven_install(
    artifacts = [
        "org.apache.commons:commons-configuration2:2.7",
        "commons-beanutils:commons-beanutils:1.9.2",
    ],
    repositories = [
        "https://repo1.maven.org/maven2",
    ],
)
```

In the `config` package, reference the artifact as a runtime dependency.

```
java_library(
    name = "app-lib",
    srcs = glob(["*.java"]),
    visibility = ["//src/main/java/com/bmuschko/app:__pkg__"],
    deps = [
        "@maven//:org_apache_commons_commons_configuration2",
        "@maven//:commons_beanutils_commons_beanutils",
    ],
    runtime_deps = [
        "//src/main/resources:app-resources"
    ]
)
```


You can now properly run the program.

```
$ bazel run //src/main/java/com/bmuschko/app:app-binary
INFO: Analyzed target //src/main/java/com/bmuschko/app:app-binary (1 packages loaded, 8 targets configured).
INFO: Found 1 target...
Target //src/main/java/com/bmuschko/app:app-binary up-to-date:
  bazel-bin/src/main/java/com/bmuschko/app/app-binary.jar
  bazel-bin/src/main/java/com/bmuschko/app/app-binary
INFO: Elapsed time: 0.170s, Critical Path: 0.02s
INFO: 4 processes: 4 internal.
INFO: Build completed successfully, 4 total actions
INFO: Build completed successfully, 4 total actions
profile: production
version: 1.0.0
```