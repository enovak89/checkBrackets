# Описаение проекта
Данный проект реализован с целью участия в конкурсе https://beautifulcode.sber.ru/
### Спецификация API находится здесь [checkBrackets.YAML](.checkBrackets.YAML)<br>

### Зависимости и техническая часть
- SpringFramework
    - starter-web
    - starter-test
- springdoc
- Java ver. 17
### Функционал приложения:
- Принимает в теле POST запроса /api/checkBrackets JSON-обьект с полем "text", 
проверяет в нем корректность расставления скобок и возвращает JSON-обьект с полем "isCorrect".
- В случае перадачи пустого текста или null поля "text" выдается исключение 
и возвращается JSON-обьект с полем "exceptionMessage".