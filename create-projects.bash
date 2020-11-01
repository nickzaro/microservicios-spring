#!/usr/bin/env bash

mkdir microservices
cd microservices

spring init \
--boot-version=2.1.0.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=product-service \
--package-name=com.nick.microservices.core.product \
--groupId=com.nick.microservices.core.product \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
product-service

spring init \
--boot-version=2.1.0.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=review-service \
--package-name=com.nick.microservices.core.review \
--groupId=com.nick.microservices.core.review \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
review-service

spring init \
--boot-version=2.1.0.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=recommendation-service \
--package-name=com.nick.microservices.core.recommendation \
--groupId=com.nick.microservices.core.recommendation \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
recommendation-service

spring init \
--boot-version=2.1.0.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=product-composite-service \
--package-name=com.nick.microservices.composite.product \
--groupId=com.nick.microservices.composite.product \
--dependencies=actuator,webflux \
--version=1.0.0-SNAPSHOT \
product-composite-service

cd ..

spring init \
--boot-version=2.1.0.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=api \
--package-name=com.nick.microservices.api \
--groupId=com.nick.microservices.api \
--dependencies=webflux \
--version=1.0.0-SNAPSHOT \
api

spring init \
--boot-version=2.1.0.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=util \
--package-name=com.nick.microservices.util \
--groupId=com.nick.microservices.util \
--dependencies=webflux \
--version=1.0.0-SNAPSHOT \
util
