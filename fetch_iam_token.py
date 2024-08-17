import requests
from datetime import datetime, timezone
import time
import json
import os

def load_config():
    with open('config.json') as f:
        config = json.load(f)
    return config

def get_iam_token(oath_token : str):
    url = "https://iam.api.cloud.yandex.net/iam/v1/tokens"
    payload = {
        "yandexPassportOauthToken": oath_token
    }

    response = requests.post(url, json=payload)

    if response.status_code == 200:
        data = response.json()
        iam_token = data["iamToken"]
        expires_at = data["expiresAt"]

        with open('/app/iam_token/token.txt', 'w') as f:
            f.write(str(iam_token))


        return iam_token, expires_at
    else:
        return None, None

def main():
    config = load_config()
    oath_token = config.get("yandexPassportOauthToken")

    if not oath_token:
        os.system("echo \"config.json get yandexPassportOauthToken error\"")
        return

    while True:
        iam_token, expires_at = get_iam_token(oath_token)

        if iam_token is None:
            time.sleep(60)
            continue


        expires_at_dt = datetime.fromisoformat(expires_at.replace("Z", "+00:00"))

        current_time = datetime.now(timezone.utc)
        time_to_wait = (expires_at_dt - current_time).total_seconds()


        time.sleep(time_to_wait)

if __name__ == "__main__":
    main()
