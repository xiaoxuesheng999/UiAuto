allure指令
报告生成并存储
allure generate ./allure-results -o ./reports --clean
报告在线生成
allure serve allure-results
--------------------------------------------------------------
moco指令
搭建接口环境
cd D:\workspace\Idea-workspace\xiaohe\MyApiTest\src\main\java\com\mock
java -jar ./moco-runner-0.11.0-standalone.jar http -p 8888 -c test.json

----------------------------------------------------------------
mvn指令
执行所有用例
mvn clean test
执行指定用例
mvn test -Dtest=TestModule
执行所有用例并输出allure报告
mvn clean test allure:report