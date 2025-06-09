# jlox

A Java implementation of a tree-walking interpreter for the Lox programming language, following Bob Nystrom's "Crafting Interpreters" book.

## Features

- **Complete Lox Language Support**: Variables, functions, classes, inheritance, closures, and control flow
- **Interactive REPL**: Run Lox code interactively or execute script files
- **AST Generation**: Includes tooling to generate Abstract Syntax Tree classes
- **Comprehensive Test Suite**: Covers all language features with example `.lox` files

## Quick Start

### Prerequisites
- Java 8 or higher
- [just](https://github.com/casey/just) command runner (optional but recommended)

### Running with just

```bash
# Compile the interpreter
just compile

# Run a Lox script
just run src/test/test_print.lox

# Start interactive REPL
just repl

# Run specific tests
just test test_functions.lox
