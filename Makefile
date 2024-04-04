.ONESHELL:
SHELL := /bin/bash
# .SHELLFLAGS = -c

.PHONY: download-gitversion run-git-tag list-built-tags build-images tag-and-build

download-gitversion:
	docker pull gittools/gitversion:latest
	docker pull gcr.io/kaniko-project/executor:latest
	sudo apt install jq -y

list-build-tags:
	./scripts/list-build-tags.sh

last-build-tag:
	./scripts/last-build-tag.sh

run-git-tag:
	docker run -v "$$(pwd):/repo" -w /repo --entrypoint /bin/sh -u "$$(id -u):$$(id -g)" gittools/gitversion:6.0.0 -c "./scripts/git-tag-semver.sh"

push-tags:
	git push origin --tags

build-images:
	./scripts/build-images.sh

tag-and-build: run-git-tag push-tags build-images