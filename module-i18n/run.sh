
#!/bin/bash

set -e

# set username and password
UNAME="test"
UPASS="test"

# get token to be able to talk to Docker Hub
TOKEN=$(curl -s -H "Content-Type: application/json" -X POST  -H "X-Requested-With: XMLHttpRequest"  -d '{"username": "'${UNAME}'", "password": "'${UPASS}'"}' http://localhost:10010/api/auth/login)
# echo ${TOKEN}
ID_TOKEN=$(echo ${TOKEN} | jq -r '.token')
TOKEN_COLOR=$(echo ${TOKEN} | jq -C '.')
echo ${TOKEN_COLOR}

echo "With response "
curl  -s -X GET -H "api_key: Bearer ${ID_TOKEN}" -H "Cache-Control: no-cache" "http://localhost:10010/api/v1/me"  | jq -C '.'


