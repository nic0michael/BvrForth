# Issues and bugs

---

## ForthStack.java






**BUG** — power() pushes double result from Math.pow() via int path
[src/main/java/za/co/bvr/forth/stack/ForthStack.java](../src/main/java/za/co/bvr/forth/stack/ForthStack.java#L218)
Lines 218–222: Method takes two ints but pushes the double returned by `Math.pow()` without casting.



---

## VerbProcessor.java


---

## ForthDictionary.java

**BUG** — removePreviousVerbFromHistory() condition is always true
[src/main/java/za/co/bvr/forth/dictionary/ForthDictionary.java](../src/main/java/za/co/bvr/forth/dictionary/ForthDictionary.java#L214)
Line 214: `verb.equals(verb)` — variable compared to itself — is always `true`. History removal never works correctly.

**ISSUE** — System verb name has trailing comma typo
[src/main/java/za/co/bvr/forth/dictionary/ForthDictionary.java](../src/main/java/za/co/bvr/forth/dictionary/ForthDictionary.java#L270)
Line 270: `addSystemVerb("D/,", ...)` — verb name should be `"D/"`.

**ISSUE** — D> has incorrect description
[src/main/java/za/co/bvr/forth/dictionary/ForthDictionary.java](../src/main/java/za/co/bvr/forth/dictionary/ForthDictionary.java#L271)
Line 271: `D>` described as "Floating point multiplication" — it is a comparison verb.

**ISSUE** — OCT described as base 16 instead of base 8
[src/main/java/za/co/bvr/forth/dictionary/ForthDictionary.java](../src/main/java/za/co/bvr/forth/dictionary/ForthDictionary.java#L425)
Line 425: Description says "BASE 16" — OCTAL is base 8.

**ISSUE** — BASE32ENCODE described as DECODE
[src/main/java/za/co/bvr/forth/dictionary/ForthDictionary.java](../src/main/java/za/co/bvr/forth/dictionary/ForthDictionary.java#L351)
Line 351: Description says "BASE32 DECODE" for an ENCODE verb.

**ISSUE** — Typo in description for <<
[src/main/java/za/co/bvr/forth/dictionary/ForthDictionary.java](../src/main/java/za/co/bvr/forth/dictionary/ForthDictionary.java#L288)
Line 288: "BORROWEWD" should be "BORROWED".

---

## IfStatementProcessor.java

**BUG** — ELSE increments nrOfThenFound instead of its own counter
[src/main/java/za/co/bvr/forth/processor/implemented/IfStatementProcessor.java](../src/main/java/za/co/bvr/forth/processor/implemented/IfStatementProcessor.java#L146)
Line 146: `nrOfThenFound++` executed on ELSE detection; corrupts IF/THEN nesting count for any `IF … ELSE … THEN` construct.

**ISSUE** — lineOfTheElseStatement never reset between calls
[src/main/java/za/co/bvr/forth/processor/implemented/IfStatementProcessor.java](../src/main/java/za/co/bvr/forth/processor/implemented/IfStatementProcessor.java#L159)
Line 159: Appended to without being cleared before processing a new IF statement; stale content carries over.

**ISSUE** — stackValues assigned but never used
[src/main/java/za/co/bvr/forth/processor/implemented/IfStatementProcessor.java](../src/main/java/za/co/bvr/forth/processor/implemented/IfStatementProcessor.java#L49)
Lines 49, 56, 59, 61, 71, 73: `stackValues` is assigned from `stack.show()` but never read; dead code.

---

## DoLoopProcessor.java

**ISSUE** — Loop body StringBuilders never reset between executions
[src/main/java/za/co/bvr/forth/processor/implemented/DoLoopProcessor.java](../src/main/java/za/co/bvr/forth/processor/implemented/DoLoopProcessor.java#L27)
Lines 27–29: `lineOfTheLoop`, `preProcessLine`, `postProcessLine` are class-level fields never cleared before a new `DO … LOOP`; content accumulates.

**BUG** — NullPointerException if loop executed without prior DO
[src/main/java/za/co/bvr/forth/processor/implemented/DoLoopProcessor.java](../src/main/java/za/co/bvr/forth/processor/implemented/DoLoopProcessor.java#L210)
Line 210: `loopIterations.get(FIRST_LOOP_NAME)` returns null if no `DO` has populated the map; unboxing null to `int` throws NPE.

**ISSUE** — stackValues assigned but never used
[src/main/java/za/co/bvr/forth/processor/implemented/DoLoopProcessor.java](../src/main/java/za/co/bvr/forth/processor/implemented/DoLoopProcessor.java#L43)
Lines 43, 45, 48, 50, 56, 58: `stackValues` assigned from `stack.show()` but never read; dead code.

---

## Inputprocessor.java

**ISSUE** — retrievingVariable flag never explicitly reset to false
[src/main/java/za/co/bvr/forth/processor/implemented/Inputprocessor.java](../src/main/java/za/co/bvr/forth/processor/implemented/Inputprocessor.java#L37)
Line 37: `retrievingVariable` set to `true` but has no corresponding reset path.

**BUG** — storingString() assumes quoted string is always at split index 1
[src/main/java/za/co/bvr/forth/processor/implemented/Inputprocessor.java](../src/main/java/za/co/bvr/forth/processor/implemented/Inputprocessor.java#L113)
Line 113: `if (index == 1)` hard-codes the string to always be the second segment after splitting on `"`.

**BUG** — Wrong flag assigned after VariableAndConstantProcessor completes
[src/main/java/za/co/bvr/forth/processor/implemented/Inputprocessor.java](../src/main/java/za/co/bvr/forth/processor/implemented/Inputprocessor.java#L63)
Line 63: `definingStringVariable = processor.getDefinitionIsNotComplete()` — should assign to `definingVariable`.

---

## VariableAndConstantProcessor.java

**BUG** — NullPointerException when variableType is null
[src/main/java/za/co/bvr/forth/processor/implemented/VariableAndConstantProcessor.java](../src/main/java/za/co/bvr/forth/processor/implemented/VariableAndConstantProcessor.java#L57)
Line 57: `variableType.equals("VARIABLE")` called without a null guard; throws NPE if no type keyword was encountered first.

**BUG** — @ and $@ do not set the current variable name before fetching
[src/main/java/za/co/bvr/forth/processor/implemented/VariableAndConstantProcessor.java](../src/main/java/za/co/bvr/forth/processor/implemented/VariableAndConstantProcessor.java#L36)
Lines 36–40: `@` calls `variables.getValue(variableName)` without first calling `setCurrentvariableName()`.
Lines 42–46: `$@` has the same defect.

---

## VariablesStore.java

**BUG** — addVariable(String) stores the variable name as its initial value
[src/main/java/za/co/bvr/forth/variables/VariablesStore.java](../src/main/java/za/co/bvr/forth/variables/VariablesStore.java#L38)
Line 38: Stores `variableName.toUpperCase()` as the value instead of an empty string or zero; fetching an uninitialised variable returns its own name.

**ISSUE** — getValue() returns null for unknown variables
[src/main/java/za/co/bvr/forth/variables/VariablesStore.java](../src/main/java/za/co/bvr/forth/variables/VariablesStore.java#L26)
Line 26: Returns `null` when the key is absent; most callers do not null-check, risking NPE downstream.

**ISSUE** — addStringConstant() exists but is never called
[src/main/java/za/co/bvr/forth/variables/VariablesStore.java](../src/main/java/za/co/bvr/forth/variables/VariablesStore.java)
`VariableAndConstantProcessor` never calls `addStringConstant()`; `$CONSTANT` declarations are silently ignored.

**ISSUE** — Deprecated Integer and Double constructors
[src/main/java/za/co/bvr/forth/variables/VariablesStore.java](../src/main/java/za/co/bvr/forth/variables/VariablesStore.java#L80)
Lines 80, 87, 124, 131: `new Integer(value)` and `new Double(value)` are deprecated in Java 17.

---


## DefineVerbProcessor.java

**ISSUE** — State fields not reset after a failed compilation
[src/main/java/za/co/bvr/forth/processor/implemented/DefineVerbProcessor.java](../src/main/java/za/co/bvr/forth/processor/implemented/DefineVerbProcessor.java#L19)
Lines 19–20: `verbDefinition` and `verbName` are instance fields; partial values from a failed definition corrupt the next verb definition.

---

## FileUtils.java

**BUG** — NullPointerException in appendFile finally block
[src/main/java/za/co/bvr/forth/utils/FileUtils.java](../src/main/java/za/co/bvr/forth/utils/FileUtils.java#L186)
Line 186: `fileWriter.close()` called in finally without null check; if `FileWriter` construction fails, `fileWriter` is null and this throws NPE.

**ISSUE** — Resource leak in readFile()
[src/main/java/za/co/bvr/forth/utils/FileUtils.java](../src/main/java/za/co/bvr/forth/utils/FileUtils.java#L34)
Lines 34–40: If an `IOException` occurs during the read loop, the `InputStream` is never closed.

---

## StringUtils.java

**ISSUE** — Resource leak: reader streams never closed
[src/main/java/za/co/bvr/forth/utils/StringUtils.java](../src/main/java/za/co/bvr/forth/utils/StringUtils.java#L10)
Lines 10–11: `InputStreamReader` and `BufferedReader` are created but never closed; leaks on every REPL call.
