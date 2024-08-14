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

* 267e1b0 (HEAD -> main, origin/main, origin/HEAD) edit Dockerfile and compose.yml
* a57bd00 fix
* b56d3fb Create README.md
* 170671a add repository
* 922d874 add repository
* 5674c86 add repository config
* 659e2fd solve client api problem
* b5aec88 full project view
* 819fbb6 add gitignore
* ec6b7f6 add Dockerfile
* 02eabce (translate-issue) add translate service via yandex translate api
* 58502bb add echo controller
* e9c38ed add echo controller
* 06a8629 Initial commit