.PHONY: build

run-json:
	@gradlew installDist
	@build\install\app\bin\app file1.json file2.json

run-yaml:
	@gradlew installDist
	@build\install\app\bin\app file1.yml file2.yml

run-stylish:
	@gradlew installDist
	@build\install\app\bin\app file1_stylish.json file2_stylish.json

test:
	@gradlew test jacocoTestCoverageVerification -i

test-report:
	@gradlew jacocoTestReport

build:
	@gradlew clean build

checkstyle:
	@gradlew checkstyleMain checkstyleTest