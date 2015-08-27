spring-paper-trail
=============
Add automatic audit trail to all stateful HTTP requests by the annotation @EnablePaperTrail.

##Purpose
Log essential information about any stateful request which includes:<br />
1. User ID
2. Remote Address
3. HTTP Method
4. Request URI
5. HTTP status
6. Timestamp

##Features
1. Supports all kinds of databases
2. Provides a callback for more sophisticated operations

##Maven Repo
```xml
<dependency>
	Not released yet
</dependency>
```


#Quick Start

Add @EnablePaperTrail to enable paper trail,<br/>
PaperTrailImpl.class is an entity class which implements the PaperTrail interface
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

Don't forget to provide a repository for any PaperTrail entity you just created
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

Again, don't forget the repository
```java
public interface PaperTrailMongoRepository
    extends PaperTrailCrudRepository<MongoPaperTrail, String> {}
```

That's all you need!

##Advanced Usage
By default, spring-paper-trail only log the POST, DELETE, PUT and PATCH methods,<br/>
however you can change its behavior by this:
```java
@EnablePaperTrail(value=JpaPaperTrail.class, targetMethods={HttpMethod.GET, HttpMethod.POST})
```

The user ID is retrieved by the HttpServletRequest#getUserPrincipal,<br/>
but you can have your mechanism by providing a PaperTrailUserIdStrategy bean:
```java
@Bean
public PaperTrailUserIdStrategy paperTrailUserIdStrategy() {
  return new PaperTrailUserIdStrategy() {
    public String getUserId() {...}
  };
}
```

If you wish to use the paper trail information to do more things,<br/>
there are callbacks you can use:
```java
@Bean
public PaperTrailCallback<JpaPaperTrail> paperTrailCallback() {
  return new PaperTrailCallback<JpaPaperTrail>() {
    public void doWithPaperTrail(JpaPaperTrail paperTrail,
        HttpServletRequest request, HttpServletResponse response) {...}
  };
}
```

