# autotest-example
Тестовое задание - автоматизация кейса по поиску

Для запуска необходимо положить актуальный chromedriver.exe в корневую директорию и указать его название в параметре driverPath файла /src/test/java/resources/testng.xml.
(chromedriver для chrome версий 62-64 прилагаю вместе с тестовым заданием для удобства)


Для генерации отчета allure запустить команду mvn clean test site
Сгеренированный отчет можно найти в /target/site/allure-maven-plugin/index.html
