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

import static java.util.Collections.unmodifiableList;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpMethod;

/**
 * 
 * {@link PaperTrailService} contains the main logic about the paper trail
 * mechanism.
 *
 */
public final class PaperTrailService {

  private static final Logger log =
      LoggerFactory.getLogger(PaperTrailService.class);

  private final ApplicationContext appCtx;
  private final Class<? extends PaperTrail> paperTrailEntityClass;
  private final List<HttpMethod> targetMethods;
  private final CrudRepository<? super PaperTrail, ?> paperTrailRepo;
  @SuppressWarnings("rawtypes")
  private final Map<String, PaperTrailCallback> callbacks;

  @Autowired(required = false)
  private PaperTrailUserIdStrategy userIdStrategy;

  @SuppressWarnings("unchecked")
  public PaperTrailService(ApplicationContext appCtx) {
    this.appCtx = appCtx;
    EnablePaperTrail ept = getEnablePaperTrailAnno();
    targetMethods = unmodifiableList(Arrays.asList(ept.targetMethods()));
    paperTrailEntityClass = ept.value();
    paperTrailRepo = appCtx.getBean(PaperTrailCrudRepository.class);
    callbacks = appCtx.getBeansOfType(PaperTrailCallback.class);
  }

  @SuppressWarnings("unchecked")
  public void audit(HttpServletRequest request, HttpServletResponse response) {
    if (!targetMethods.contains(HttpMethod.valueOf(request.getMethod())))
      return;

    PaperTrail paperTrail = newPaperTrail();
    paperTrail.setUserId(userIdStrategy == null ? getUserTypedId(request)
        : userIdStrategy.getUserId());
    paperTrail.setRemoteAddr(request.getRemoteAddr());
    paperTrail.setHttpMethod(HttpMethod.valueOf(request.getMethod()));
    paperTrail.setRequestURI(request.getRequestURI());
    paperTrail.setHttpStatus(response.getStatus());
    paperTrailRepo.save(paperTrail);

    if (!callbacks.isEmpty()) {
      for (@SuppressWarnings("rawtypes")
      PaperTrailCallback callback : callbacks.values()) {
        callback.doWithPaperTrail(paperTrail, request, response);
      }
    }
  }

  private EnablePaperTrail getEnablePaperTrailAnno() {
    String clz = appCtx.getBeanNamesForAnnotation(EnablePaperTrail.class)[0];
    Annotation anno = AnnotationUtils
        .findAnnotation(appCtx.getBean(clz).getClass(), EnablePaperTrail.class);
    return (EnablePaperTrail) anno;
  }

  private PaperTrail newPaperTrail() {
    PaperTrail paperTrail = null;
    try {
      paperTrail = (PaperTrail) paperTrailEntityClass.newInstance();
    } catch (Exception e) {
      log.error("PaperTrail entity can not be instantiated", e);
      throw new RuntimeException(e);
    }
    return paperTrail;
  }

  private String getUserTypedId(HttpServletRequest request) {
    return request.getUserPrincipal() == null ? null
        : request.getUserPrincipal().getName();
  }

}
