[![Codacy Badge](https://api.codacy.com/project/badge/Grade/33945d791ef14f41ae05740328d7bdb9)](https://www.codacy.com/app/jdi-testing/jdi-dark?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jdi-testing/jdi-dark&amp;utm_campaign=Badge_Grade)

# JDI Dark TestNg Test Template
For run new REST API test automation just download and open this project
Powered by [JDI Dark](https://github.com/jdi-testing/jdi-dark)

## Documentation
Can be found [here](https://jdi-docs.github.io/jdi-dark)

## Instruction:
1. Download template and unpack in appropriate folder

2. Open project in IDE (for example IntelliJIdea) by selecting **pom.xml** or **build.gradle**

3. For running demo test just use "mvn clean install" command for maven or "gradlew clean build" for Gradle or run via IDE by click on "example" folder in src/test and select "Run tests"
https://pix.my/KN34uk

4. Logs: Observe test run results in Console log
https://pix.my/SXy52C

5. Reporting: After running tests run **allure:serve** in maven plugins
https://pix.my/meAto8
or **allureServe** in gradle task
https://pix.my/6FXX7l

6. Use as template for your project: 
* just remove all content from **src/main/.../example** folder, add your package you Service Objects
* replace tests in **src/test/.../example** folder with your tests
7. For running parameterized builds (e.g. in CI/CD tools) you can use profiles:

* Gradle:</br>
  ``` gradlew clean build -PBUILD_PROFILE=name of your .properties file placed in src/test/resources ```
 </br> or </br>
  ``` gradlew clean build -DBUILD_PROFILE=name of your .properties file placed in src/test/resources ```
 </br> or </br>
  ``` EXPORT BUILD_PROFILE=name of your .properties file placed in src/test/resources && gradlew clean build ```
* Maven:</br>
  ``` mvn clean install -DBUILD_PROFILE=name of your .properties file placed in src/test/resources ```
 </br> or </br>
  ``` EXPORT BUILD_PROFILE=name of your .properties file placed in src/test/resources && mvn clean install ```
