# feign-java9

[![Build Status](https://travis-ci.org/velo/feign-java9.svg?branch=master)](https://travis-ci.org/velo/feign-java9?branch=master) 
[![Coverage Status](https://coveralls.io/repos/github/velo/feign-java9/badge.svg?branch=master)](https://coveralls.io/github/velo/feign-java9?branch=master) 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.marvinformatics.feign/feign-java9/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.marvinformatics.feign/feign-java9/) 
[![Issues](https://img.shields.io/github/issues/velo/feign-java9.svg)](https://github.com/velo/feign-java9/issues) 
[![Forks](https://img.shields.io/github/forks/velo/feign-java9.svg)](https://github.com/velo/feign-java9/network) 
[![Stars](https://img.shields.io/github/stars/velo/feign-java9.svg)](https://github.com/velo/feign-java9/stargazers)


This module directs Feign's http requests to Java9 [New HTTP/2 Client](http://www.javamagazine.mozaicreader.com/JulyAug2017#&pageSet=39&page=0) that implements HTTP/2.

To use New HTTP/2 Client with Feign, use Java SDK 9 and add `feign.java9` module to your `module-info.java`. Then, configure Feign to use the Java9HttpClient:

```java
GitHub github = Feign.builder()
                     .client(new Java9HttpClient())
                     .target(GitHub.class, "https://api.github.com");
```
