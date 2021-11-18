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