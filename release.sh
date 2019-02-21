#!/usr/bin/env bash

set -e

git pull
lein sass4clj once
lein -U release
git push