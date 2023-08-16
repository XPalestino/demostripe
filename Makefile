include docker/Makefile

SPRING_PROFILES_ACTIVE ?= local

build:
	./mvnw clean install -DskipTests=true

run:
	SPRING_PROFILES_ACTIVE=$(SPRING_PROFILES_ACTIVE) ./mvnw spring-boot:run

fmt:
	./mvnw spotless:apply

test:
	SPRING_PROFILES_ACTIVE=test ./mvnw test -Pskip-validation

lint:
	./mvnw spotless:check checkstyle:check

package:
	./mvnw clean package -Pprod -DskipTests=true -Pskip-validation

get-version:
	./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout