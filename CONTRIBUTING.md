# Contributing

Thank you for your interest in contributing to Java Unit Converter. All types of contributions are welcome -- bug reports, feature suggestions, documentation improvements, and code changes.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Environment](#development-environment)
- [Build and Run Locally](#build-and-run-locally)
- [Code Style](#code-style)
- [Branching and Commits](#branching-and-commits)
- [Pull Request Process](#pull-request-process)
- [Pull Request Checklist](#pull-request-checklist)
- [Where to Get Help](#where-to-get-help)

## Code of Conduct

This project is governed by the [Contributor Covenant Code of Conduct](CODE_OF_CONDUCT.md). By participating, you are expected to uphold this code. Report unacceptable behavior to rjiang880@gmail.com.

## Getting Started

1. Fork the repository.
2. Clone your fork locally.
3. Create a branch for your change.
4. Make your changes.
5. Open a pull request.

## Development Environment

- **Java Development Kit (JDK) 8 or later** -- download from [Adoptium](https://adoptium.net/).
- **Maven 3.6+** (optional, recommended) -- download from [Apache Maven](https://maven.apache.org/download.cgi).
- **No external dependencies** -- the project uses only the Java standard library.

## Build and Run Locally

### With Maven (recommended)

```shell
# Build the executable JAR
mvn clean package

# Compile and run in one step
mvn clean compile exec:java -Dexec.mainClass="com.unitconverter.App"

# The built JAR is at target/UnitConverter.jar
```

### Quick Start Without Maven

#### Windows (PowerShell or CMD)
```powershell
.\launch.bat
```

The launch script detects whether Maven is available and falls back to direct `javac` compilation automatically.

#### macOS / Linux

On macOS and Linux, use Maven or the manual compilation steps below. There is no shell launcher script for these platforms yet.

### Manual Compilation Without Maven

#### Windows (PowerShell)
```powershell
# Create output directory
New-Item -ItemType Directory -Path target\classes -Force

# Collect and compile sources
Get-ChildItem -Recurse -Filter *.java src\main\java | ForEach-Object { $_.FullName } | Out-File -Encoding ascii sources.txt
javac -d target\classes -encoding UTF-8 @sources.txt

# Copy resources
Copy-Item -Recurse src\main\resources\* target\classes\

# Run
java -cp target\classes com.unitconverter.App
```

#### macOS / Linux
```shell
# Create output directory
mkdir -p target/classes

# Collect and compile sources
find src/main/java -name "*.java" > sources.txt
javac -d target/classes -encoding UTF-8 @sources.txt

# Copy resources
cp -r src/main/resources/* target/classes/

# Run
java -cp target/classes com.unitconverter.App
```

## Code Style

- Use clear, descriptive names for classes, methods, and variables.
- Keep GUI layout logic and conversion logic in separate methods.
- Add comments only where the intent is not obvious from the code.
- Follow standard Java formatting: 4-space indentation, opening braces on the same line.
- Maximum line length is 120 characters.
- Avoid adding external dependencies. The project intentionally uses only the Java standard library.

## Branching and Commits

- Branch naming: `feature/short-description`, `fix/short-description`, `docs/short-description`.
- Keep each commit focused on a single logical change.
- Use descriptive commit messages. Consider [Conventional Commits](https://www.conventionalcommits.org/):
  - `feat: add support for Celsius to Kelvin conversion`
  - `fix: correct volume ratio for U.S. Cup`
  - `docs: update README with cross-platform instructions`
  - `refactor: extract conversion logic into separate methods`

## Pull Request Process

1. Ensure your branch is based on the latest `main`.
2. Verify the project builds and runs on your platform.
3. If your change modifies behavior, include screenshots of the updated UI.
4. For bug fixes, describe the root cause and how the fix addresses it.
5. Keep pull requests small and focused on a single concern. If you have multiple unrelated changes, submit separate PRs.
6. A maintainer will review your PR. Address any feedback with additional commits.

## Pull Request Checklist

- [ ] My changes build locally without errors (`mvn clean package` on the command line)
- [ ] I tested the application manually to verify the change works as expected
- [ ] I added or updated documentation where necessary
- [ ] My commits follow the project's commit message conventions
- [ ] My branch is up to date with the latest `main`

## Where to Get Help

- Open an issue for bug reports, feature requests, or questions.
- For quick questions, use the Discussions tab (if enabled on this repository).
- For security vulnerabilities, follow the process in [SECURITY.md](SECURITY.md) -- do not open a public issue.

Thank you for contributing.
