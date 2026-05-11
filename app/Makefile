.PHONY: build

run-json:
	@gradlew installDist
	@build\install\app\bin\app file1.json file2.json

run-yaml:
	@gradlew installDist
	@build\install\app\bin\app file1.yml file2.yml

run-format-stylish:
	@gradlew installDist
	@build\install\app\bin\app file1_test_format.json file2_test_format.json

run-format-plain:
	@gradlew installDist
	@build\install\app\bin\app -f plain file1_test_format.json file2_test_format.json

run-format-json:
	@gradlew installDist
	@build\install\app\bin\app -f json file1_test_format.json file2_test_format.json

test:
	@gradlew test jacocoTestCoverageVerification -i

test-report:
	@gradlew jacocoTestReport

build:
	@gradlew clean build

checkstyle:
	@gradlew checkstyleMain checkstyleTest