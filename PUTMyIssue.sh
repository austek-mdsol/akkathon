#!/usr/bin/env bash
curl \
--header "Content-type: application/json" \
--request PUT \
--data '{"aaa": "putAAA" : "bbb": "putBBB"}' \
http://localhost:8080/issue

