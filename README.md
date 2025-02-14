[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.wnameless.spring/spring-paper-trail/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.wnameless.spring/spring-paper-trail)

spring-paper-trail
=============
Add automatic audit trail to all stateful HTTP requests by the annotation @EnablePaperTrail

## Purpose
Log essential information about any stateful request which includes:

1. User ID: the user name<br/>
2. Remote Address: the user IP address<br/>
3. HTTP Method: POST, DELETE, PUT or PATCH<br/>
4. Request URI: the URI of user request<br/>
5. HTTP status: status code, ex: 201, 404<br/>
6. Timestamp: the date and time of this paper trail

## Features
1. Supports all kinds of databases
2. Provides callbacks for sophisticated operations
3. Here are v0.4.x for Spring Boot v3.x.x and v0.3.x for Spring Boot v2.x.x

## Maven Repo
```xml
<dependency>
	<groupId>com.github.wnameless.spring</groupId>
	<artifactId>spring-paper-trail</artifactId>
	<version>${newestVersion}</version>
	<!-- Newest version shows in the maven-central badge above -->
</dependency>
```


# Quick Start

Add @EnablePaperTrail to enable paper trail,<br/>
JpaPaperTrail.class is an entity class which implements the PaperTrail interface
```java
@Configuration
@EnablePaperTrail(JpaPaperTrail.class)
public class WebConfig {
  ...
}
```

JPA example:
```java
@Entity
public class JpaPaperTrail extends AbstractJpaPaperTrail {}
```
AbstractJpaPaperTrail is a convenient abstract class for JPA PaperTrail

Don't forget to provide a PaperTrailCrudRepository for any PaperTrail entity you just created
```java
public interface PaperTrailJpaRepository
    extends PaperTrailCrudRepository<JpaPaperTrail, Long> {}
```

MongoDB example:
```java
@Entity
public class MongoPaperTrail extends AbstractPaperTrail {
  @Id private String id;
}
```
or
```java
@Entity
public class MongoPaperTrail implements PaperTrail {
  ...
}
```
FYI, AbstractMongoPaperTrail is also provided in this library

Again, don't forget the PaperTrailCrudRepository
```java
public interface PaperTrailMongoRepository
    extends PaperTrailCrudRepository<MongoPaperTrail, String> {}
```

That's all you need!

## Advanced Usage
By default, spring-paper-trail only log the POST, DELETE, PUT and PATCH methods,<br/>
however you can change its behavior by doing this:
```java
@EnablePaperTrail(value=JpaPaperTrail.class, targetMethods={HttpMethod.GET, HttpMethod.POST})
```

The user ID is retrieved by the HttpServletRequest#getUserPrincipal,<br/>
but you can override this mechanism by providing a PaperTrailUserIdStrategy bean:
```java
@Bean
public PaperTrailUserIdStrategy paperTrailUserIdStrategy() {
  return new PaperTrailUserIdStrategy(HttpServletRequest request) {
    public String getUserId() {...}
  };
}
```

If you wish to use the paper trail information to do more things,<br/>
there are callbacks you can use:
```java
@Bean
public BeforePaperTrailCallback<JpaPaperTrail> beforePaperTrailCallback() {
  return new beforePaperTrailCallback<JpaPaperTrail>() {
    public void beforePaperTrail(JpaPaperTrail paperTrail,
        HttpServletRequest request, HttpServletResponse response) {...}
  };
}

@Bean
public AfterPaperTrailCallback<JpaPaperTrail> afterPaperTrailCallback() {
  return new AfterPaperTrailCallback<JpaPaperTrail>() {
    public void afterPaperTrail(JpaPaperTrail paperTrail,
        HttpServletRequest request, HttpServletResponse response) {...}
  };
}

@Bean
public AroundPaperTrailCallback<JpaPaperTrail, PaperTrailJpaRepository> aroundTrailCallback() {
  return new AroundPaperTrailCallback<JpaPaperTrail, PaperTrailJpaRepository>() {
      public void aroundPaperTrail(PaperTrailJpaRepository paperTrailRepo,
          JpaPaperTrail paperTrail, HttpServletRequest request,
          HttpServletResponse response) {
          ...
          paperTrailRepo.save(paperTrail);
          ...
      }
  };
}
```
