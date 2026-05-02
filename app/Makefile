.PHONY: build

build:
	@gradlew clean build

checkstyle:
	@gradlew checkstyleMain checkstyleTest

run:
	@gradlew installDist
	@build\install\app\bin\app file1.json file2.json