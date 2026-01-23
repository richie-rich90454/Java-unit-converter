# Contributing

Thank you for your interest in contributing to Java Unit Converter! We welcome improvements, bug fixes, documentation updates, and ideas.

Please follow these guidelines to make the contribution process smooth and fast.

- Fork the repository and create a branch for your change:
  - git checkout -b feature/short-description
- Keep changes small and focused — one feature or fix per PR.
- Write a clear PR title and description explaining the problem and your solution.
- If your change modifies behavior, include steps to reproduce and screenshots where useful.

How to build and run locally
- JDK 8+ is required.
- From the project root:
  - Compile:
    mkdir -p out
    find src -name "*.java" > sources.txt
    javac -d out @sources.txt
  - Create a runnable JAR:
    jar --create --file=app.jar -C out .
  - Run:
    java -jar app.jar

Code style
- Use clear, descriptive names.
- Keep GUI logic and conversion logic separated when possible.
- Add comments where intent is not obvious.
- Follow standard Java formatting (4-space indent, braces on same line, etc.).

Branching & commits
- Branches: feature/*, fix/*, docs/*
- Commit messages: short summary + optional body. Consider Conventional Commits (e.g., feat: add Kelvin support).

Pull request checklist
- [ ] My changes build locally
- [ ] I added or updated documentation if needed
- [ ] I included tests where applicable (or explained why not)
- [ ] I followed contribution guidelines

Where to get help
- Open an issue describing the problem or idea.
- For quick questions, you can open a discussion (if enabled) or open an issue with the "question" label.

Thank you for contributing!
