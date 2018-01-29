#!/bin/bash

set -e


#定义变量
USERNAME="admin"
PASSWORD="admin"

PORT=10013
AUTH_HEADER_PARAM="api_key"

echo "============================================================================================================================="
echo "LOGIN with response"
echo ""

# 请求认证
RAW_TOKEN_RESPONSE=$(curl -s -H "Content-Type: application/json" -X POST  -H "X-Requested-With: XMLHttpRequest"  -d '{"username": "'${USERNAME}'", "password": "'${PASSWORD}'"}' http://localhost:${PORT}/api/auth/login)
echo ${RAW_TOKEN_RESPONSE} | jq -C "."

#定义变量
ACCESS_TOKEN=$(echo ${RAW_TOKEN_RESPONSE} | jq -r '.token')
REFRESH_TOKEN=$(echo ${RAW_TOKEN_RESPONSE} | jq -r '.refreshToken')


echo "ME"
curl  -s -X GET -H "${AUTH_HEADER_PARAM}: Bearer ${ACCESS_TOKEN}" -H "Cache-Control: no-cache" "http://localhost:${PORT}/api/v1/me"  | jq -C '.'


echo "REFRESH TOKEN"
curl  -s -X GET -H "${AUTH_HEADER_PARAM}: Bearer ${REFRESH_TOKEN}" -X GET -H "Cache-Control: no-cache" "http://localhost:${PORT}/api/auth/refreshToken"  | jq -C '.'


echo "============================================================================================================================="
















#测试显示为有颜色的内容



# echo "刷新令牌 REFRESH TOKEN"
# REFRESH_TOKEN=$(echo ${TOKEN} | jq -C  '.refreshToken')

# echo ${REFRESH_TOKEN}


# echo "===================================================================================="


# TOKEN_COLOR=$(echo ${TOKEN} | jq -C '.')
# echo ${TOKEN_COLOR}