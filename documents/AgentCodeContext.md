# BvrForth — Agent Code Context

> RAG reference for AI agents working on this codebase.
> Sections use consistent headers for chunk-based embedding.
> No databases are used in this project.

---

## Section 1: Forth Concepts Glossary

### Forth Language Terms

| Term | Syntax | Class | Meaning |
|---|---|---|---|
| **Verb** | any word | `ForthDictionary`, `VerbProcessor` | A named procedure. Every Forth command is a verb. |
| **Dictionary** | — | `ForthDictionary` | Registry of all verbs. Two tiers: system (built-in, immutable) and user-defined. |
| **Stack** | — | `ForthStack` | Global LIFO structure. The only way to pass values between verbs. All operations read/write here. |
| **TOS** | — | `ForthStack` | Top Of Stack — the most recently pushed value. |
| **Token** | — | `LineProcessor` | A single whitespace-delimited word on an input line, e.g. `DUP`, `42`, `MYVERB`. |
| **Definition** | `: name body ;` | `DefineVerbProcessor` | Human-readable source form of a verb body, e.g. `DUP *`. |
| **Compiled Definition** | — | `ForthCompiler` | Resolved form of a body where each token is replaced by its own compiled definition. Used at execution time. |
| **System Verb** | — | `ForthDictionary.loadDictionary()` | Built-in verb registered at startup. Cannot be redefined. |
| **Variable** | `VARIABLE name` | `VariablesStore` | Named numeric slot. Fetched with `@`, stored with `!`. |
| **Constant** | `CONSTANT name` | `VariablesStore` | Named read-only numeric value. |
| **String Variable** | `$VARIABLE name` | `VariablesStore` | Named string slot. Fetched with `$@`, stored with `$!`. |
| **Word execution** | — | `VerbProcessor.executeVerb()` | Dispatching a verb token to its implementation via a `switch` statement. |

### Build & Dependency Notes

Agents reading or writing code in this project must know:

| Concern | Detail |
|---|---|
| **Build tool** | Gradle |
| **Lombok `@Data`** | Auto-generates getters, setters, `equals`, `hashCode`, `toString`. Do not write these manually. Present on `Verb`, `ExecutionPojo`. |
| **Lombok `@Log`** | Injects a `java.util.logging.Logger` named `log`. Present on most processors. |
| **Apache Commons Lang** | `StringUtils.isEmpty()`, `StringUtils.isNotEmpty()` used throughout. |
| **Apache Commons Codec** | `Base64` used in `ForthStack` for `base64Encode/Decode`. |
| **All values are String** | The stack stores everything as `String`. Type coercion (`popInt()`, `popDouble()`) happens at point of use. |

---

## Section 2: Class Inventory

### Subsystem Overview

| Subsystem | Classes |
|---|---|
| **Entry Points** | `BvrForth`, `Forth`, `ProcessInput` |
| **Input Routing** | `Inputprocessor` |
| **Processor Abstractions** | `AbstractProcessor`, `AbstractLoopProcessor` |
| **Execution Pipeline** | `LineProcessor`, `VerbPreProcessor`, `VerbProcessor` |
| **Construct Processors** | `DefineVerbProcessor`, `VariableAndConstantProcessor`, `IfStatementProcessor`, `DoLoopProcessor` |
| **Compiler** | `ForthCompiler` |
| **Storage** | `ForthDictionary`, `Verb`, `ForthStack`, `VariablesStore` |
| **DTOs & Enums** | `ExecutionPojo`, `ExecutionType`, `Dto`, `MapDto`, `StringListDto` |
| **Utilities** | `Utilities`, `DateUtilities`, `StringUtils`, `FileUtils`, `JsonUtilities` |
| **Exceptions** | `LineIsEmptyException`, `StackIsEmptyException`, `UnknownProcessorType`, `UnknownVerb`, `VerbNotInDictionaryException` |

---

### Entry Points

**`za.co.bvr.forth.BvrForth`** — Human-facing REPL. Loops reading stdin, calls `Forth.processInput()`, prints result. Exits on `BYE`. Stateless.

**`za.co.bvr.forth.Forth`** — Public API facade. Single method: `processInput(String) : String`. All external callers use this class.

**`za.co.bvr.forth.process.ProcessInput`** — Thin bridge between `Forth` and `Inputprocessor`. Composes an `Inputprocessor` instance.

---

### Input Routing

**`za.co.bvr.forth.processor.implemented.Inputprocessor`** — Main router. Inspects each input line via boolean flags and dispatches to the correct processor.

| Flag | Triggered by | Routes to |
|---|---|---|
| `definingNewVerb` | `:` in line | `DefineVerbProcessor` |
| `definingLoop` | `DO` in line | `DoLoopProcessor` |
| `definingIfStatement` | `IF` in line | `IfStatementProcessor` |
| `definingVariable` | `VARIABLE` or `CONSTANT` | `VariableAndConstantProcessor` |
| `definingStringVariable` | `$VARIABLE` | `VariableAndConstantProcessor` |
| `definingString` | `"` in line | `storingString()` then `LineProcessor` |
| _(default)_ | anything else | `LineProcessor` |

Key method: `storingString(String)` — extracts text between `"` quotes, pushes to stack, returns remaining tokens.

---

### Processor Abstractions

**`za.co.bvr.forth.processor.AbstractProcessor`** _(abstract)_ — Contract for all processors: `preProcess()`, `process()`, `postProcess()`, `getDefinitionIsNotComplete()`.

**`za.co.bvr.forth.processor.AbstractLoopProcessor`** _(abstract, extends AbstractProcessor)_ — Adds loop segment fields (`lineBeforeLoop`, `lineOfTheLoop`, `lineAfterLoop`) and abstract segment setters.

---

### Execution Pipeline

**`za.co.bvr.forth.processor.implemented.LineProcessor`** — Core token classifier and executor.
- `process(String)` — splits line on space, classifies each token as `NUMBER` / `VARIABLE` / `VERB`, builds `List<ExecutionPojo>`.
- `postProcess(List<ExecutionPojo>)` — pushes numbers to stack; delegates variables and verbs to `VerbPreProcessor`.
- Uses: `ForthDictionary` (lookup), `ForthStack` (push), `VariablesStore` (check), `VerbPreProcessor` (execute).

**`za.co.bvr.forth.processor.implemented.VerbPreProcessor`** — Secondary dispatcher called by `LineProcessor`. Pushes numbers, sets variable cursor, or calls `VerbProcessor.executeVerb()`.
- Holds `VerbProcessor.INSTANCE`.

**`za.co.bvr.forth.processor.implemented.VerbProcessor`** _(singleton)_ — The execution engine. One large `switch` on the verb name calls the matching `ForthStack` method or utility. Returns any output string.
- `executeVerb(String) : String` — the only entry point.
- **To add a built-in verb:** add a `case` here AND call `addSystemVerb()` in `ForthDictionary.loadDictionary()`.

---

### Construct Processors

**`za.co.bvr.forth.processor.implemented.DefineVerbProcessor`** — Handles `: name body ;`. Accumulates tokens until `;`, then calls `ForthCompiler.compile()` and stores the result in `ForthDictionary`.
- Key fields: `boolean definitionIsNotComplete`, `StringBuilder verbDefinition`, `String verbName`.

**`za.co.bvr.forth.processor.implemented.VariableAndConstantProcessor`** — Handles `VARIABLE`, `$VARIABLE`, `CONSTANT`, `@`, `$@`, `!`, `$!`. Parses the declaration/access line and updates `VariablesStore`. Delegates remaining tokens to `LineProcessor`.

**`za.co.bvr.forth.processor.implemented.IfStatementProcessor`** — Handles `IF ... THEN` and `IF ... ELSE ... THEN`. Splits line into pre-IF / IF-body / ELSE-body / post-THEN. Pops decision from stack (1 = true branch, 0 = false/else branch). Delegates each segment to `LineProcessor`.

**`za.co.bvr.forth.processor.implemented.DoLoopProcessor`** _(extends AbstractLoopProcessor)_ — Handles `DO ... LOOP` including nested loops. Pops iteration count from stack. Stores loop body in `loopDefinitions` map keyed as `LOOP_N`. Executes body N times via `runLoop()` / `runLoopOnce()`, which call `LineProcessor`.
- Key fields: `Map<String,String> loopDefinitions`, `Map<String,Integer> loopIterations`.

---

### Compiler

**`za.co.bvr.forth.compiler.ForthCompiler`** — Resolves a raw verb body into its compiled form. Splits body on space; for each token calls `ForthDictionary.getCompiledDefinition()` and concatenates results.

---

### Storage

**`za.co.bvr.forth.dictionary.ForthDictionary`** _(singleton)_ — Registry of all verbs. Separate maps for user verbs and system verbs.
- `addVerbToDictionary(Verb)` — add/replace user verb; saves previous to `verbHistory`.
- `addSystemVerb(String name, String desc)` — register a built-in.
- `getDefinition(String) : String` — raw definition lookup.
- `getCompiledDefinition(String) : String` — compiled definition lookup (throws `VerbNotInDictionaryException`).
- `forget(String) : String` — remove a user verb.
- `clearDictionary()` — reset user verbs (test use).
- `loadDictionary()` _(private)_ — populates all ~150 system verbs at construction time.
- Key fields: `verbDefinitions`, `verbCompiledDefinitions`, `systemVerbDefinitions`, `systemVerbCompiledDefinitions`, `List<Verb> verbHistory`.

**`za.co.bvr.forth.dictionary.Verb`** — Immutable value object for a dictionary entry. `@Data` + `@ToString` (Lombok). Fields: `name`, `definition`, `compiledDefinition`, `description`, `uuid`. Custom `equals()` overloads for name-only and full match. `guidEquals()` for history tracking.

**`za.co.bvr.forth.stack.ForthStack`** _(singleton)_ — Global LIFO stack. All values stored as `String`; coercion on pop.
- Push: `push(String/int/double/byte[])`.
- Pop: `pop()`, `popInt()`, `popDouble()`.
- Stack ops: `dup()`, `drop()`, `swap()`, `rot()`, `over()`, `qdup()`.
- Arithmetic (int): `add()`, `subtract()`, `multiply()`, `divide()`, `modulus()`.
- Arithmetic (double): `addDoubles()` … `modulusDoubles()`, `squareDoubles()`.
- Comparisons: `equals()`, `greaterThan()`, `smallerThan()`, `equalsOrGreaterThan()`, `smallerThanOrEquals()`, `not()` — push `1` (true) or `0` (false).
- Math: `sin()`, `cos()`, `tan()`, `log()`, `logBase10()`, `sqrt()`, `round()`, `floor()`, `ceil()`, `power()`, `random()`.
- Encoding: `base64Encode()`, `base64Decode()`.
- Base conversion: `convertToBinary()`, `convertToHex()`, `convertToOctal()`, `convertHexToDecimal()`, `convertOctalToDecimal()`, `convertBinaryToDecimal()`.
- Display mode: `setModeToDecimal/Hex/Octal/Binary()` — affects `pop()` output format.
- `show() : String` — non-destructive display of all stack items.
- Inner enum: `mode {DECIMAL, HEX, OCTAL, BINARY}`.

**`za.co.bvr.forth.variables.VariablesStore`** _(singleton)_ — Holds all runtime variables and constants. Separate maps: `variableNames`, `constantNames`, `stringVariableNames`, `stringConstantNames`. All values in unified `valueStore`.
- `addVariable(String)` / `addStringVariable(String)` / `addConstant(String,String)` — declare.
- `updateVariable(String, String/int/double)` / `updateStringVariable()` — set value.
- `getValue(String) : String` — fetch from `valueStore`.
- `isVariable(String) : boolean` — checks all four type maps.
- `setCurrentvariableName(String)` / `getCurrentvariableValue()` — cursor used by `@` and `!` verbs.
- `showVariables() : String` — lists all variables and values.

---

### DTOs & Enums

**`za.co.bvr.forth.dtos.ExecutionPojo`** (`@Data`) — Carries one token (`lineItem : String`) and its type (`executionType : ExecutionType`). Created by `LineProcessor` for each token.

**`za.co.bvr.forth.enums.ExecutionType`** — `VERB`, `VARIABLE`, `STRING`, `NUMBER`.

**`za.co.bvr.forth.dtos.Dto`** _(interface)_ — Base contract: `getGuid()`, `setGuid()`, `getDescription()`, `setDescription()`.

**`za.co.bvr.forth.dtos.MapDto`** — Implements `Dto`. Wraps a `Map<String,String>`.

**`za.co.bvr.forth.dtos.StringListDto`** — Implements `Dto`. Wraps a `List<String>`.

---

### Utilities

**`za.co.bvr.forth.utils.Utilities`** — Static helpers: `removeUnwantedSpaces()` (normalises whitespace, throws `LineIsEmptyException`), `isNumeric()`, `isEmpty()`, `isInteger()`, `isDouble()`, `getComputerName()`, `getComputerIpAddress()`, `getIpAddressOfHosst()`.

**`za.co.bvr.forth.utils.DateUtilities`** — Static date/time formatters called by date verbs in `VerbProcessor`: `dateY2k()`, `dateBritish()`, `dateUSA()`, `time()`, `now()`, `timeStamp()`, `day()`, `month()`, `year()`.

**`za.co.bvr.forth.utils.StringUtils`** — Console input: `getStringFromUser(String prompt) : String` reads a line from stdin. Used only by `BvrForth`.

**`za.co.bvr.forth.utils.FileUtils`** — File I/O: `readFile(String)`, `lineReadFile(String)`, `writeFile(String, String)`, `appendFile(String, String)`.

**`za.co.bvr.forth.utils.JsonUtilities`** — JSON utility (present in compiled output; source not fully analysed).

---

### Exceptions

| Class | When thrown |
|---|---|
| `LineIsEmptyException` | Input line is null or empty — thrown by `Utilities.removeUnwantedSpaces()` |
| `StackIsEmptyException` | `ForthStack.pop()` called on empty stack |
| `UnknownProcessorType` | Unhandled dispatch path in `Inputprocessor` |
| `UnknownVerb` | Verb token not recognised |
| `VerbNotInDictionaryException` | Dictionary lookup fails — thrown by `getCompiledDefinition()` and `getDefinition()` |

---

## Section 3: Class Relationships (UML-style)

```
BvrForth --> Forth : calls processInput()
BvrForth --> StringUtils : calls getStringFromUser()

Forth --> ProcessInput : delegates to process()

ProcessInput --> Inputprocessor : composes, delegates to process()

Inputprocessor --> DefineVerbProcessor : creates, routes verb definitions
Inputprocessor --> DoLoopProcessor : creates, routes DO...LOOP
Inputprocessor --> IfStatementProcessor : creates, routes IF...THEN
Inputprocessor --> VariableAndConstantProcessor : creates, routes VARIABLE/CONSTANT/@/!
Inputprocessor --> LineProcessor : creates, routes plain execution lines
Inputprocessor --> ForthStack : uses (push string literals)
Inputprocessor --|> AbstractProcessor : extends

LineProcessor --> ForthDictionary : reads getCompiledDefinition()
LineProcessor --> ForthStack : pushes NUMBER tokens
LineProcessor --> VariablesStore : checks isVariable()
LineProcessor --> VerbPreProcessor : delegates VERB and VARIABLE executions
LineProcessor --> ExecutionPojo : creates for each token
LineProcessor --|> AbstractProcessor : extends

VerbPreProcessor --> VerbProcessor : composes (singleton), calls executeVerb()
VerbPreProcessor --> ForthStack : pushes NUMBER tokens
VerbPreProcessor --> ForthDictionary : reads
VerbPreProcessor --> VariablesStore : sets currentvariableName
VerbPreProcessor --|> AbstractProcessor : extends

VerbProcessor --> ForthStack : all stack operations
VerbProcessor --> ForthDictionary : reads showVerbs(), forget()
VerbProcessor --> VariablesStore : reads/writes variable values
VerbProcessor --> DateUtilities : calls for date verbs
VerbProcessor --> Utilities : calls for network/computer verbs
VerbProcessor --|> AbstractProcessor : extends

DefineVerbProcessor --> ForthDictionary : calls addVerbToDictionary()
DefineVerbProcessor --> ForthCompiler : creates, calls compile()
DefineVerbProcessor --> Verb : creates new instance
DefineVerbProcessor --|> AbstractProcessor : extends

ForthCompiler --> ForthDictionary : reads getCompiledDefinition() per token
ForthCompiler --> Utilities : calls removeUnwantedSpaces()

IfStatementProcessor --> ForthStack : pops decision value
IfStatementProcessor --> LineProcessor : creates, delegates branch execution
IfStatementProcessor --|> AbstractProcessor : extends

DoLoopProcessor --> ForthStack : pops iteration count
DoLoopProcessor --> LineProcessor : creates, runs loop body each iteration
DoLoopProcessor --|> AbstractLoopProcessor : extends
AbstractLoopProcessor --|> AbstractProcessor : extends

VariableAndConstantProcessor --> VariablesStore : creates variables/constants
VariableAndConstantProcessor --> ForthStack : pushes fetched values, pops stored values
VariableAndConstantProcessor --> LineProcessor : delegates remaining tokens
VariableAndConstantProcessor --|> AbstractProcessor : extends

ForthDictionary --> Verb : stores List<Verb> verbHistory, Map<String,String> definitions
ForthStack (singleton) -- used by: LineProcessor, VerbPreProcessor, VerbProcessor, IfStatementProcessor, DoLoopProcessor, VariableAndConstantProcessor, Inputprocessor
ForthDictionary (singleton) -- used by: ForthCompiler, LineProcessor, VerbPreProcessor, VerbProcessor, DefineVerbProcessor
VariablesStore (singleton) -- used by: LineProcessor, VerbPreProcessor, VerbProcessor, VariableAndConstantProcessor

ExecutionPojo --> ExecutionType : has (enum field)
MapDto --|> Dto : implements
StringListDto --|> Dto : implements
```

---

## Section 4: Compiler Pipeline (Execution Flows)

### Flow 1: Plain line execution (e.g. `3 4 + .`)

```
BvrForth.main()
  → Forth.processInput("3 4 + .")
    → ProcessInput.process()
      → Inputprocessor.process()
          detects no special keywords → routes to LineProcessor
        → LineProcessor.process("3 4 + .")
            splits into tokens: ["3", "4", "+", "."]
            "3" → isNumeric=true → ExecutionPojo(NUMBER, "3")
            "4" → isNumeric=true → ExecutionPojo(NUMBER, "4")
            "+" → dict.getCompiledDefinition("+") = "+" → ExecutionPojo(VERB, "+")
            "." → dict.getCompiledDefinition(".") = "." → ExecutionPojo(VERB, ".")
          → LineProcessor.postProcess(executions)
              NUMBER "3" → stack.push("3")
              NUMBER "4" → stack.push("4")
              VERB "+" → VerbPreProcessor.process("+")
                → VerbProcessor.executeVerb("+") → stack.add() → pops 3,4 → pushes 7
              VERB "." → VerbPreProcessor.process(".")
                → VerbProcessor.executeVerb(".") → result.append(stack.pop()) → "7"
          returns "7"
```

---

### Flow 2: User verb definition (e.g. `: SQUARE DUP * ;`)

```
BvrForth.main()
  → Forth.processInput(": SQUARE DUP * ;")
    → ProcessInput.process()
      → Inputprocessor.process()
          detects ":" → definingNewVerb=true → routes to DefineVerbProcessor
        → DefineVerbProcessor.process(": SQUARE DUP * ;")
            ":" → reset, definitionIsNotComplete=true
            "SQUARE" → verbName = "SQUARE"
            "DUP" → verbDefinition = "DUP"
            "*" → verbDefinition = "DUP *"
            ";" → definitionIsNotComplete=false
              → ForthCompiler.compile("DUP *")
                  "DUP" → dict.getCompiledDefinition("DUP") = "DUP"
                  "*" → dict.getCompiledDefinition("*") = "*"
                  compiledDefinition = "DUP *"
              → new Verb("SQUARE", "DUP *", "DUP *", "User defined verb")
              → ForthDictionary.addVerbToDictionary(verb)
                  verbDefinitions.put("SQUARE", "DUP *")
                  verbCompiledDefinitions.put("SQUARE", "DUP *")
          returns "Ok"
      → Inputprocessor: definingNewVerb = false (getDefinitionIsNotComplete() = false)
```

---

### Flow 3: Executing a user-defined verb (e.g. `5 SQUARE`)

```
BvrForth.main()
  → Forth.processInput("5 SQUARE")
    → ProcessInput.process()
      → Inputprocessor.process()
          no special keywords → routes to LineProcessor
        → LineProcessor.process("5 SQUARE")
            "5" → isNumeric → ExecutionPojo(NUMBER, "5")
            "SQUARE" → NOT numeric, NOT variable
              → dict.getCompiledDefinition("SQUARE") = "DUP *"
              → "DUP" → ExecutionPojo(VERB, "DUP")
              → "*" → ExecutionPojo(VERB, "*")
          → LineProcessor.postProcess(executions)
              NUMBER "5" → stack.push("5")
              VERB "DUP" → VerbPreProcessor → VerbProcessor.executeVerb("DUP")
                → stack.dup() → stack: [5, 5]
              VERB "*" → VerbPreProcessor → VerbProcessor.executeVerb("*")
                → stack.multiply() → pops 5,5 → pushes 25
                → stack: [25]
          returns ""
```

---

## Section 5: Extension Guide

### Adding a new built-in verb (e.g. `DOUBLE`)

1. **[ForthDictionary.java](../src/main/java/za/co/bvr/forth/dictionary/ForthDictionary.java)**
   Add in `loadDictionary()`:
   ```java
   addSystemVerb("DOUBLE", "Multiply TOS by 2");
   ```

2. **[ForthStack.java](../src/main/java/za/co/bvr/forth/stack/ForthStack.java)** _(if the verb needs a new stack operation)_
   Add the implementation method, e.g.:
   ```java
   public void doubleTop() throws StackIsEmptyException {
       int val = popInt();
       push(val * 2);
   }
   ```

3. **[VerbProcessor.java](../src/main/java/za/co/bvr/forth/processor/implemented/VerbProcessor.java)**
   Add a `case` in `executeVerb()`:
   ```java
   case "DOUBLE":
       stack.doubleTop();
       break;
   ```

---

### Adding a new variable/constant type

1. **[VariablesStore.java](../src/main/java/za/co/bvr/forth/variables/VariablesStore.java)**
   Add a new `Map<String,String>` for the type. Add `addXxx()`, `updateXxx()`, and extend `isVariable()` to check the new map.

2. **[VariableAndConstantProcessor.java](../src/main/java/za/co/bvr/forth/processor/implemented/VariableAndConstantProcessor.java)**
   Add recognition of the new keyword (e.g. `MYVARIABLE`) in `process()` and handle `!`/`@` accordingly.

3. **[Inputprocessor.java](../src/main/java/za/co/bvr/forth/processor/implemented/Inputprocessor.java)**
   Add detection logic so the new keyword routes to `VariableAndConstantProcessor`.

4. **[ForthDictionary.java](../src/main/java/za/co/bvr/forth/dictionary/ForthDictionary.java)**
   Register the new keyword as a system verb in `loadDictionary()`.

---

### Changing how input lines are tokenised

Tokenisation happens in two places:

1. **[Inputprocessor.java](../src/main/java/za/co/bvr/forth/processor/implemented/Inputprocessor.java)**
   - `Utilities.removeUnwantedSpaces()` normalises whitespace before any routing.
   - `storingString()` handles `"string"` literals by splitting on `"`.

2. **[LineProcessor.java](../src/main/java/za/co/bvr/forth/processor/implemented/LineProcessor.java)**
   - `line.split(" ")` splits the normalised line into tokens.
   - Token classification order: `isNumeric()` → `isVariable()` → dictionary lookup.

To change tokenisation (e.g. support a different delimiter or multi-word strings), modify `Utilities.removeUnwantedSpaces()` and the `split(" ")` calls in `LineProcessor.process()` and `VerbPreProcessor.process()`.

---

## Section 6: Singleton Instances

All core runtime state is held in singletons. Each is accessed via a public static `INSTANCE` field on its class. Constructors are private.

| Singleton | Access | What it holds |
|---|---|---|
| `ForthStack` | `ForthStack.INSTANCE` | The global data stack — all values in flight |
| `ForthDictionary` | `ForthDictionary.INSTANCE` | All verb definitions (system + user) |
| `VariablesStore` | `VariablesStore.INSTANCE` | All variables and constants |
| `VerbProcessor` | `VerbProcessor.INSTANCE` | The verb execution engine |

### What this means for agents writing code

- **State persists across calls.** Every call to `Forth.processInput()` shares the same stack, dictionary, and variable store as the previous call. A verb defined in call 1 is available in call 2.
- **Processors are not singletons.** `Inputprocessor`, `LineProcessor`, `DefineVerbProcessor`, etc. are instantiated fresh per call. Only the four classes above persist state.
- **All four singletons are linked.** `VerbProcessor` holds references to all three storage singletons. Any code that touches one often affects others.

### Testing reset

Before each test, reset shared state:
```java
ForthDictionary.INSTANCE.clearDictionary();
ForthStack.INSTANCE.clear();
```
`VariablesStore` has no `clear()` method — tests that define variables may need to account for carry-over.

---

## Issues

> This section records known items that are deferred or out of scope. Do not act on these without explicit instruction.

### Files to ignore

| File | Status |
|---|---|
| `src/main/java/za/co/bvr/forth/processor/implemented/delme.java` | Marked for deletion. Do not read, reference, or use this file in any task. |

### Bugs

We are not looking at bugs for now. Do not analyse, fix, or comment on suspected bugs unless explicitly asked.

### Singleton thread-safety

We are not looking at singleton thread-safety issues for now. Do not raise or address concurrency concerns related to the singleton instances.

---

## Section 7: Unit Tests

### Test Infrastructure

| Item | Detail |
|---|---|
| **Framework** | JUnit 5 (`org.junit.jupiter.api.Test`) |
| **Entry point under test** | `Forth.processInput(String) : String` — all tests go through this single method |
| **Test package** | `za.co.bvr.bvr.forth` (note: double `bvr` in path) |
| **Test source root** | `src/test/java/za/co/bvr/bvr/forth/` |
| **Assertions used** | `assertEquals`, `assertTrue`, `assertFalse`, `assertThrows` |

### Test Classes

#### `ForthTest`
**File:** [src/test/java/za/co/bvr/bvr/forth/ForthTest.java](../src/test/java/za/co/bvr/bvr/forth/ForthTest.java)

| Test method | Input | Expected output | What it covers |
|---|---|---|---|
| `processInput()` | `"1 2 + ."` | `"3"` | Integer addition + stack pop and print |

### Test Pattern

All tests follow this structure:
```java
@Test
public void testName() throws Exception {
    String result = Forth.processInput("forth expression here");
    assertEquals("expected output", result);
}
```

For tests that expect an exception:
```java
@Test
public void testName() {
    assertThrows(SomeException.class, () -> Forth.processInput("bad input"));
}
```

### State Reset Between Tests

Tests that define verbs, variables, or push to the stack must reset singletons. Add this to a `@BeforeEach` method:
```java
@BeforeEach
void reset() {
    ForthDictionary.INSTANCE.clearDictionary();
    ForthStack.INSTANCE.clear();
}
```
`VariablesStore` has no `clear()` — tests that declare variables should use unique names to avoid collision.

### Coverage Gaps

The following areas currently have no test coverage and are safe targets when adding tests:

| Area | Forth expression to test |
|---|---|
| User verb definition | `: DOUBLE DUP + ; 3 DOUBLE .` → `"6"` |
| IF/THEN | `1 1 = IF 99 . THEN` → `"99"` |
| IF/ELSE/THEN | `0 1 = IF 1 . ELSE 2 . THEN` → `"2"` |
| DO/LOOP | `0 3 DO 1 + LOOP .` → `"3"` |
| VARIABLE declare + store + fetch | `VARIABLE X 42 X ! X @ .` → `"42"` |
| String push + print | `" hello" .` → `"hello"` |
| Stack operations | `1 2 SWAP . .` → `"1 2"` |
| Arithmetic (doubles) | `1.5 2.5 D+ .` → `"4.0"` |
