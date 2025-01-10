/*
 *
 * Copyright 2015 Wei-Ming Wu
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package com.github.wnameless.spring.papertrail;

import static java.util.Collections.unmodifiableList;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 
 * {@link PaperTrailService} contains the main logic about the paper trail mechanism.
 *
 */
public class PaperTrailService {

  private static final Logger log = LoggerFactory.getLogger(PaperTrailService.class);

  private final ApplicationContext appCtx;
  private final Class<? extends PaperTrail> paperTrailEntityClass;
  private final List<String> targetMethods;
  // private final PaperTrailCrudRepository<? super PaperTrail, ?> paperTrailRepo;

  @SuppressWarnings("rawtypes")
  @Autowired(required = false)
  private BeforePaperTrailCallback beforeCallback;

  @SuppressWarnings("rawtypes")
  @Autowired(required = false)
  private AfterPaperTrailCallback afterCallback;

  @SuppressWarnings("rawtypes")
  @Autowired(required = false)
  private AroundPaperTrailCallback aroundCallback;

  @Autowired(required = false)
  private PaperTrailUserIdStrategy userIdStrategy;

  public PaperTrailService(ApplicationContext appCtx) {
    this.appCtx = appCtx;
    EnablePaperTrail ept = getEnablePaperTrailAnno();
    targetMethods =
        unmodifiableList(Arrays.asList(ept.targetMethods()).stream().map(m -> m.name()).toList());
    paperTrailEntityClass = ept.value();
  }

  @Transactional
  @SuppressWarnings("unchecked")
  public void audit(HttpServletRequest request, HttpServletResponse response) {
    if (!targetMethods.contains(request.getMethod())) return;

    final PaperTrail paperTrail = newPaperTrail();
    paperTrail.setUserId(
        userIdStrategy == null ? getUserTypedId(request) : userIdStrategy.getUserId(request));
    paperTrail.setRemoteAddr(request.getRemoteAddr());
    paperTrail.setHttpMethod(request.getMethod());
    paperTrail.setRequestUri(request.getRequestURI());
    paperTrail.setHttpStatus(response.getStatus());

    // Before callbacks
    if (beforeCallback != null) {
      beforeCallback.beforePaperTrail(appCtx.getBean(PaperTrailCrudRepository.class), paperTrail,
          request, response);
    }

    // Around callback
    if (aroundCallback != null) {
      aroundCallback.aroundPaperTrail(appCtx.getBean(PaperTrailCrudRepository.class), paperTrail,
          request, response);
    } else {
      appCtx.getBean(PaperTrailCrudRepository.class).save(paperTrail);
    }

    // After callbacks
    if (afterCallback != null) {
      afterCallback.afterPaperTrail(appCtx.getBean(PaperTrailCrudRepository.class), paperTrail,
          request, response);
    }
  }

  private EnablePaperTrail getEnablePaperTrailAnno() {
    String clz = appCtx.getBeanNamesForAnnotation(EnablePaperTrail.class)[0];
    Annotation anno =
        AnnotationUtils.findAnnotation(appCtx.getBean(clz).getClass(), EnablePaperTrail.class);
    return (EnablePaperTrail) anno;
  }

  private PaperTrail newPaperTrail() {
    PaperTrail paperTrail = null;
    try {
      paperTrail = (PaperTrail) paperTrailEntityClass.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      log.error("PaperTrail entity can not be instantiated", e);
      throw new RuntimeException(e);
    }
    return paperTrail;
  }

  private String getUserTypedId(HttpServletRequest request) {
    return request.getUserPrincipal() == null ? null : request.getUserPrincipal().getName();
  }

}
