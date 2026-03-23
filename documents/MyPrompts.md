# My Prompts

## 1 Create RAG file
```sh
Analyze all Java source files in this project and produce a RAG reference document saved as documents/AgentCodeContext.md.

This project is a Forth compiler written in Java. Forth is a stack-based language where named procedures are called "verbs" (or "words"), stored in a "dictionary", and executed by manipulating a data stack.

Structure the document with the following sections:

### 1. Forth Concepts Glossary
Brief definitions of domain terms used in the code: verb, dictionary, stack, token, definition, constant, variable, word execution.

### 2. Class Inventory
For each class include:
- Package and class name
- Single-sentence responsibility
- Key public methods with their purpose
- Important fields that carry state

### 3. Class Relationships (UML-style)
Describe all relationships between classes:
- Inheritance (extends / implements)
- Composition (owns an instance of)
- Dependencies (uses / calls into)
Use a format like: `ClassA --> ClassB : dependency reason`

### 4. Compiler Pipeline (Execution Flow)
Trace the end-to-end flow for each of these scenarios:
1. A line of Forth input is parsed and executed
2. A new verb definition is created and stored in the dictionary
3. A previously defined verb is looked up and executed

### 5. Extension Guide
For each of these common change tasks, list which classes to modify and in what order:
- Adding a new built-in verb
- Adding a new variable/constant type
- Changing how input lines are tokenized

Keep section headers consistent so the document can be split into chunks for vector embedding.
There are no databases in this project — do not reference or suggest any.

### 6. Issues
Create a `## Issues` section at the end of the document with the following sub-sections:

**Files to ignore:** Any file named `delme.*` must be listed here as "Marked for deletion. Do not read, reference, or use this file in any task." Do not describe it as a class.

**Bugs:** Add the note: "We are not looking at bugs for now. Do not analyse, fix, or comment on suspected bugs unless explicitly asked."

**Singleton thread-safety:** Add the note: "We are not looking at singleton thread-safety issues for now. Do not raise or address concurrency concerns related to the singleton instances."
```