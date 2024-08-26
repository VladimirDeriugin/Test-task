# Test-task

## Setting Up Tests

1. Ensure you have the correct version of Java installed. This project requires Java 21. You can check your Java version by running `java -version` in your terminal.

2. Install Maven if you haven't already. You can download it from [here](https://maven.apache.org/download.cgi). After installation, verify it by running `mvn -v` in your terminal.

3. Clone the project from GitHub. You can do this by running `git clone <repository-url>` in your terminal.

4. Navigate to the project directory in your terminal.

5. Run `mvn clean install` to download the necessary dependencies and build the project.

## Running Tests

1. Ensure you are in the project directory in your terminal.

2. Run `mvn test` to execute the tests.

3. During test execution don't touch the mouse or keyboard as the tests are sensitive to user input. 

4. Test duration average time is 1 minute.


Please note that the tests are configured to run on Chrome browser. Make sure you have the Chrome browser installed and the ChromeDriver placed in the correct path as specified in the `Setup()` method in the `TestTask` class.