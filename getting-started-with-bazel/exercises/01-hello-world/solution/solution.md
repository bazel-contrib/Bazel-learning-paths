# Solution

Navigate to the `start` directory.

```
$ cd start
```

Executing the target prints the message "Hello World!" to the console.

```
$ bazel build //:hello_world
DEBUG: /Users/bmuschko/dev/projects/getting-started-with-bazel/exercises/01-hello-world/start/rule.bzl:2:10: Hello World!
INFO: Analyzed target //:hello_world (4 packages loaded, 6 targets configured).
INFO: Found 1 target...
Target //:hello_world up-to-date (nothing to build)
INFO: Elapsed time: 0.694s, Critical Path: 0.01s
INFO: 1 process: 1 internal.
INFO: Build completed successfully, 1 total action
```

The rule does not create specific artifacts apart from Bazel's internal action hashes.

To pass a different message, change the value of the parameter `content` in the `BUILD` file:

```
message(
    name = "hello_world",
    content = "My message",
)
```

Running the target again, will print the changed message. The following console output renders the message "My message".

```
$ bazel build //:hello_world
DEBUG: /Users/bmuschko/dev/projects/getting-started-with-bazel/exercises/01-hello-world/start/rule.bzl:2:10: My message
INFO: Analyzed target //:hello_world (1 packages loaded, 1 target configured).
INFO: Found 1 target...
Target //:hello_world up-to-date (nothing to build)
INFO: Elapsed time: 0.098s, Critical Path: 0.00s
INFO: 1 process: 1 internal.
INFO: Build completed successfully, 1 total action
```

To change the name of the target, modify the value of the paramrter `name`. Here, we set the name to `my_message`:

```
message(
    name = "my_message",
    content = "My message",
)
```

The label provided to the `build` command needs to use the changed target name.

```
$ bazel build //:my_message
DEBUG: /Users/bmuschko/dev/projects/getting-started-with-bazel/exercises/01-hello-world/start/rule.bzl:2:10: My message
INFO: Analyzed target //:my_message (1 packages loaded, 1 target configured).
INFO: Found 1 target...
Target //:my_message up-to-date (nothing to build)
INFO: Elapsed time: 0.128s, Critical Path: 0.00s
INFO: 1 process: 1 internal.
INFO: Build completed successfully, 1 total action
```