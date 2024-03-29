# Логирование исходящих запросов
Для логирования исходящих запросов было 2 решения
1. Установить уровень логов для `org.springframework.web.client.RestTemplate` в `DEBUG`
2. Написать свой `ClientHttpRequestInterceptor`.
Второй вариант был выбран просто для разнообразия, т.к. нечто похожее на 1ый вариант было использовано для
логирования входящих запросов. Также при использовании варианта №1 получается довольно много логов, которые в
данной ситуации скорее всего излишни.
# ExchangeRate и CurrencyInfo
В данные модели намеренно не были добавлены маппинги связей(OneToMany, ManyToOne), т.к. они лишь усложнят код, а пользы
не принесут(в контексте поставленной задачи)
# Работа с  API
Для получения курса валюты необходимо отправить GET запрос по адресу `localhost:8080/money/exchange/rate/{mnemonics}`, где 
`{mnemonics}` заменить на мнемоника нужной валюты. Например `localhost:8080/money/exchange/rate/usd`. 
* Также поддерживается возможность получения отдельно курса покупки или продажи. Для этого необходимо использовать URL
`localhost:8080/money/exchange/rate/{mnemonics}/buy` или `localhost:8080/money/exchange/rate/{mnemonics}/sell`
## Ответы
* `200` - курс для указанной валюті успешно найден и предоставлен в виде JSON:
    ```
    {
        "buy": "25.0",
        "sell": "26.0"
    }
    ```
* `400` - указаная мнемоника(валюта) не поддерживается
* `500` - неизвестная ошибка
* `503` - данные в локальной БД не найдены, а со стороннего API информацию получить не удалось

В случае ошибки возвращается JSON с описанием ошибки:
```
{
    "message": "Some error message"
}
```
