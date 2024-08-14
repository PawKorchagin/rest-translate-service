# Java Spring Boot RESTfull service

## Usage
make request to /translate with such body:
```json
{
    "texts": ["текст", "амогус"],
    "sourceLanguageCode": "ru",
    "targetLanguageCode": "en"
}
```
Result:
```
{translations=[{text=text}, {text=amogus}]}
```

## TODO

- docker-compose
- change API
- parallel tasking
