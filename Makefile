# Define the commandline invocation of Maven if necessary:
ifeq ($(MVN),)
    MVN  := mvn
endif

ifeq ($(GIT),)
    GIT  := git
endif

clean:
	$(MVN) clean

install: clean
	$(MVN) install -DskipTests

prepare-package: install	
	ren .\target\rest*.jar rest.jar

docker-build: prepare-package
	- docker container stop fityou-workspace
	- docker container prune -f
	- docker image rm -f rest:latest
	docker build --pull --rm -f "Dockerfile" -t rest:latest .