#!/usr/bin/env bash
curl \
--header "Content-type: application/json" \
--request POST \
--data '{"aaa": "postAAA", "bbb": "postBBB"}' \
http://localhost:8080/issue

