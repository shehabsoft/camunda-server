CHART_REPO := https://nexus.diracatom.com:8443
DOCKER_REGISTRY := nexus.diracatom.com:18444
CURRENT=$(pwd)
NAME := membership
OS := $(shell uname)
RELEASE_VERSION := $(shell cat ../VERSION)
CHARTMUSEUM_CREDS_USR := admin
CHARTMUSEUM_CREDS_PSW := byszLQH9PWkFzag
build: clean
	rm -rf requirements.lock
	helm dependency build
	helm lint

install: clean build
	helm install . --name ${NAME}

upgrade: clean build
	helm upgrade ${NAME} .

delete:
	helm delete --purge ${NAME}

clean:
	rm -rf charts
	rm -rf ${NAME}*.tgz

release: clean
	helm dependency build
	helm lint
	helm package .
	curl --fail -v -F file="@$(NAME)-$(shell sed -n 's/^version: //p' Chart.yaml).tgz" -u $(CHARTMUSEUM_CREDS_USR):$(CHARTMUSEUM_CREDS_PSW)  $(CHART_REPO)/service/rest/v1/components?repository=helm-repo
	rm -rf ${NAME}*.tgz%

tag:
ifeq ($(OS),Darwin)
	sed -i "" -e "s/version:.*/version: $(RELEASE_VERSION)/" Chart.yaml
	sed -i "" -e "s/tag:.*/tag: $(RELEASE_VERSION)/" values.yaml
else ifeq ($(OS),Linux)
	sed -i -e "s/version:.*/version: $(RELEASE_VERSION)/" Chart.yaml
	sed -i -e "s|repository:.*|repository: $(DOCKER_REGISTRY)\/membership|" values.yaml
	sed -i -e "s/tag:.*/tag: $(RELEASE_VERSION)/" values.yaml
else
	echo "platfrom $(OS) not supported to release from"
	exit -1
endif
	git add --all
	git commit -m "release $(RELEASE_VERSION)" --allow-empty # if first release then no verion update is performed
	git tag -fa v$(RELEASE_VERSION) -m "Release version $(RELEASE_VERSION)"
	git push origin v$(RELEASE_VERSION)
