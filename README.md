# Java Spring Boot RESTfull service + a little bit of frontend

## Start

### Clone & goto repo directory

```bash
git clone https://github.com/PawKorchagin/rest-translate-service.git
cd rest-translate-service
```

### Create json config file

Via terminal:

```bash
touch config.json && echo "{\"yandexPassportOauthToken\": \"oath-token here, ask @PawKorchagin\"}" > config.json
```

Or manually create config.json file in repository root and add:

```json
{
    "yandexPassportOauthToken": "oath-token here, ask @PawKorchagin"
}
```

### Use docker-compose to up localhost

Simply run command:

```bash
docker-compose up --build
```

## Usage
Simply make post request to /api/translate with such body:
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

**Or follow by [link](http://localhost:8080)**.

## Progress

- docker-compose :white_check_mark:
- change API
- parallel tasking
- frontend

## Recent commits

* 693f88f (HEAD -> main, origin/main, origin/HEAD) forgot main fetcher py script lol
* fedd177 (dev-iam-fetcher) add iam-fetcher service
* 981e11e shit happened
* 26d36c2 minor changes
* 6bf80f7 (dev-docker) edit docker-compose configuration
* 8570d60 (dev-docker) edit Dockerfile
* 2150bca Update README.md
* 279f3af edit gitignore
* 039ab04 (dev-pretty) add css, js to translate.html, add run mvn clean package before java -jar ... in Dockerfile
