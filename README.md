# Hospital simulator

## Building a fat jar

`gradle clean shadowJar`

## Running tests

`gradle cleanTest test`

## Running with parameters

`java -jar build/libs/hospital-1.0-SNAPSHOT-all.jar ch.evooq.hospital.Main <parameter1> <parameter2>`

### Input parameters format

#### Parameter 1

List of patients' health status codes, separated by a comma. e.g. “D,F,F” means we have 3
patients, one with diabetes and two with fever.

##### Patients' health status codes

- F: Fever
- H: Healthy
- D: Diabetes
- T: Tuberculosis
- X: Dead

#### Parameter 2

List of drugs codes, separated by a comma, e.g. “As,I” means patients will be treated with
Aspirin and Insulin.

##### Drugs codes

- As: Aspirin
- An: Antibiotic
- I: Insulin
- P: Paracetamol

## Notes

- Application is using Java 8
- Jacoco test report is available after running tests in `build/reports/tests` directory
- Main logic is based on [EasyRule](https://github.com/j-easy/easy-rules) rules engine
- Results of different test scenarios depend on the order in which rules are applied (their priorities)
  - For ex.: Patient with Tuberculosis cured with Insulin, Antibiotic and Paracetamol can either:
    - Heal Tuberculosis with Antibiotic, get Fever from Insulin and Antibiotic and heal Fever with Paracetamol OR
    - Get Paracetamol with no effect, heal Tuberculosis with Antibiotic and get Fever from Insulin and Antibiotic
- Since it was not explicitly specified in what order rules should be applied and common sense doesn't give any clear answer either I assumed order in which they were specified in the problem description document
- Because of large number of iterations that testDeadBecomesHealthy does, it takes relatively long to run it, time can be significantly shortened by turning off debug logs
- `testDeadBecomesHealthy` is not guaranteed to pass, it is only statistically likely. In order to increase stability, it's enough to set `EXECUTION_MULTIPLIER` in test to a greater value.
- [EasyRule](https://github.com/j-easy/easy-rules) engine can be configured with couple of options changing rules matching logic:
  - The `skipOnFirstAppliedRule` parameter tells the engine to skip next rules when a rule is applied
  - The `skipOnFirstFailedRule` parameter tells the engine to skip next rules when a rule fails
  - The `skipOnFirstNonTriggeredRule` parameter tells the engine to skip next rules a rule is not triggered
  - The `rulePriorityThreshold` parameter tells the engine to skip next rules if priority exceeds the defined threshold