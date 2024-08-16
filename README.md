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

## Progress

- docker-compose :white_check_mark:
- change API
- parallel tasking
- frontend

## Recent commits

* 279f3af (HEAD -> main, origin/main, origin/HEAD) edit gitignore
* 039ab04 (dev-pretty) add css, js to translate.html, add run mvn clean package before java -jar ... in Dockerfile
* 70e0609 add pretty output format
* f29011e rename message painter
* c88724c minor changes
* 6f1cad6 (front-end-dev) add Postmapping translate from html form
* b77f248 add get request from form
* c710239 add index.html and translate.html
