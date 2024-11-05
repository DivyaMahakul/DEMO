
## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
## Execution Process
The program starts in the `main` method of `CalculatingAgent`, where the JADE (Java Agent Development Framework) environment is initialized. A new `Runtime` instance is created, and a JADE `Profile` is defined to set up the main container. This container then creates and starts an agent named "CalculatorAgent" of type `CalculatingAgent`. When the `CalculatorAgent` begins execution, it calls its `setup` method, which prints a message to indicate it’s ready. It then retrieves the agent’s `AID` (Agent Identifier) and logs a definition-use (Def-Use) pair for it, marking where it’s defined and used. The program checks if `AID` is non-null; if it is, the agent's name is retrieved and printed, and additional Def-Use pairs are recorded. If `AID` is null, a message is printed, and a corresponding Def-Use pair is logged. This initial Def-Use information is printed to the console, and a `CalculatorGui` instance is created and shown, displaying a GUI for user interaction.
The `CalculatorGui` class extends `JFrame` and provides a simple interface with a text field for number input and three buttons: "Square," "Cube," and "Log(x)." Each button has an `ActionListener` that reads the user input, converts it to a numerical value, and calls a calculation method on the agent. For example, the "Square" button retrieves the input, parses it as an integer, and calls `calculateSquare`, while the "Log(x)" button parses it as a double and calls `calculateLog`. If there’s an error during parsing (such as non-numeric input), a dialog displays an error message.
Each calculation method in `CalculatingAgent` (`calculateSquare`, `calculateCube`, and `calculateLog`) executes a `OneShotBehaviour`, which calculates the result based on the specified operation: square, cube, or base-10 logarithm. It then prints the result in the console, adds Def-Use pairs for the input and result variables, and calls `printDefUsePairs` to show the updated Def-Use pairs in the console.
Throughout the agent’s execution, the `addDefUsePair` method logs where each variable is defined and used, capturing the context of its usage across different methods. The `printDefUsePairs` method iterates through these pairs, displaying them in the console to provide a clear record of variable usage patterns. The program remains active, allowing users to perform further calculations, and terminates when the GUI is closed or the JADE environment stops the agent.
