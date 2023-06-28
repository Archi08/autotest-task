


<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->




<!-- END doctoc generated TOC please keep comment here to allow auto update -->





### **Run Tests With Gradle**
> `./gradlew clean test allureReport`<br/>

Task `allureReport`: Build report from `autotest-task/build/allure-results` folder

#### **Perform On Browsers**
- chrome
- edge
- firefox
- ie
- safari

> If run safari, you must enable the 'Allow Remote Automation' option in Safari's Develop menu to control Safari via WebDriver.

Able to select browser by passing system property `selenide.browser`<br/>
> `./gradlew clean test -Dselenide.browser=firefox allureReport`

#### **Filter Tests**
You can filter tests by using option `--tests`<br/>
Giving values can be `TestPackage`, `TestClass`, `TestMethod`
> `./gradlew clean test -Dselenide.browser=firefox --tests ExampleTest.passedTest allureReport`
