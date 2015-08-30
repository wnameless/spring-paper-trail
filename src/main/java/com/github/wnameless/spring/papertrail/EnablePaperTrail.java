/**
 *
 * @author Wei-Ming Wu
 *
 *
 * Copyright 2015 Wei-Ming Wu
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package com.github.wnameless.spring.papertrail;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;

/**
 * 
 * {@link EnablePaperTrail} is designed to used with Spring Framework. It
 * requires a {@link PaperTrail} implementation class and a
 * {@link PaperTrailCrudRepository} should be defined properly as well.<br/>
 * <br/>
 * By default, it only log the stateful requests which implies the POST, DELETE,
 * PUT and PATCH methods only. However you can change it by giving the
 * targetMethods of your own.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(PaperTrailConfig.class)
public @interface EnablePaperTrail {

  Class<? extends PaperTrail>value();

  HttpMethod[]targetMethods() default { POST, DELETE, PUT, PATCH };

}
