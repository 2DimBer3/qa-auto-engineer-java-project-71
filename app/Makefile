.PHONY: build

run:
	@gradlew installDist
	@build\install\app\bin\app file1.json file2.json

test:
	@gradlew test jacocoTestCoverageVerification -i

test-report:
	@gradlew jacocoTestReport

build:
	@gradlew clean build

checkstyle:
	@gradlew checkstyleMain checkstyleTest