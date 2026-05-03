### Hexlet tests and linter status:
[![Actions Status](https://github.com/2DimBer3/qa-auto-engineer-java-project-71/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/2DimBer3/qa-auto-engineer-java-project-71/actions)

### Sonar badges

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=2DimBer3_qa-auto-engineer-java-project-71&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=2DimBer3_qa-auto-engineer-java-project-71)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=2DimBer3_qa-auto-engineer-java-project-71&metric=bugs)](https://sonarcloud.io/summary/new_code?id=2DimBer3_qa-auto-engineer-java-project-71)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=2DimBer3_qa-auto-engineer-java-project-71&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=2DimBer3_qa-auto-engineer-java-project-71)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=2DimBer3_qa-auto-engineer-java-project-71&metric=coverage)](https://sonarcloud.io/summary/new_code?id=2DimBer3_qa-auto-engineer-java-project-71)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=2DimBer3_qa-auto-engineer-java-project-71&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=2DimBer3_qa-auto-engineer-java-project-71)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=2DimBer3_qa-auto-engineer-java-project-71&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=2DimBer3_qa-auto-engineer-java-project-71)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=2DimBer3_qa-auto-engineer-java-project-71&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=2DimBer3_qa-auto-engineer-java-project-71)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=2DimBer3_qa-auto-engineer-java-project-71&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=2DimBer3_qa-auto-engineer-java-project-71)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=2DimBer3_qa-auto-engineer-java-project-71&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=2DimBer3_qa-auto-engineer-java-project-71)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=2DimBer3_qa-auto-engineer-java-project-71&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=2DimBer3_qa-auto-engineer-java-project-71)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=2DimBer3_qa-auto-engineer-java-project-71&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=2DimBer3_qa-auto-engineer-java-project-71)

### Пример работы программы

#### Сравнение JSON-файлов

<u>file1.json</u>
```json
{
  "host": "hexlet.io",
  "timeout": 50,
  "proxy": "123.234.53.22",
  "follow": false
}
```

<u>**file2.json**</u>
```json
{
  "timeout": 20,
  "verbose": true,
  "host": "hexlet.io"
}
```

<u>Результат выполнения программы:</u>

![img.png](app/images/json.png)

#### Сравнение YAML-файлов

<u>file1.yml</u>
```yaml
host: hexlet.io
timeout: 50
proxy: 123.234.53.22
follow: false
```

<u>file2.yaml</u>
```yaml
timeout: 20
verbose: true
host: hexlet.io
```

<u>Результат выполнения программы:</u>

![img.png](app/images/yaml.png)
