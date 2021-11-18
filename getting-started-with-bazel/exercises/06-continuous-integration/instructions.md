# Exercise 7

The goal of this exercise is to set up a Continuous Integration job for Bazel build on GitHub Actions. A push to any branch and opening a pull request should trigger a build execution. Use [Bazelisk](https://github.com/bazelbuild/bazelisk) to download the Bazel runtime.

1. Create a new repository on GitHub. You can use any name you'd like. Clone the repository to your local machine.
2. Copy the source code of the `solution` folder in exercise 5 to the locally checked out repository. Create a GitHub Actions workflow file in the appropriate directory.
3. The job in the workflow file should run on Ubuntu Linux 20.04. Use the runner with the label `ubuntu-20.04`.
4. Define steps for checking out the code, installing Bazel 4.2.1 using Bazelisk, caching the build output across multiple invocations using `actions/cache@v2`, compiling the code, and executing the tests.
5. Commit and push the code to the remote repository.
6. Check the Actions tab in the UI of the GitHub repository. The build should have been triggered and pass.