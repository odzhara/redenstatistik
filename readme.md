## Statistic of speeches
### Example of spring boot application, that calculate various statistic based on downloaded scv-files


## Prerequisites

Windows/Linux, Java 8, Maven 3xx

## Table of Contents

* [Installation](#installation)
* [Usage](#usage)
* [Api](#api)
* [Documentation](#documentation)
* [License](#license)

## Installation

**BEFORE YOU INSTALL:** please read the [prerequisites](#prerequisites)

to build the project run
```bash
mvn package
```
or (in Windows)
```bash
build.cmd
```


## Usage

To run a project after it was [installed](#installation)
```bash
java -jar target/redenstatistik-0.0.1-SNAPSHOT.jar
```
or (in Windows)
```bash
run.cmd
```

## Api

Example of request:
```bash
GET /evaluation?url=https://dev-stefan.s3.amazonaws.com/politics.csv 
```

Example of response:
```bash
{
	"mostSpeeches": null,
	"mostSecurity": "Alexander Abel",
	"leastWordy": "Caesare Collins"
}
```

one can use more then one url at once
```bash
GET /evaluation?url=url1&&url=url2 
```



## Documentation

The documentation for building and running spring-boot projects one can find on [their site](https://projects.spring.io/spring-boot/).

## License

Free to use for everyone

