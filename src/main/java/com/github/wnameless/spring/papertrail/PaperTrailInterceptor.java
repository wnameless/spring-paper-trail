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

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 
 * {@link PaperTrailInterceptor} applies necessary paper trail to all HTTP requests.
 *
 */
public class PaperTrailInterceptor implements HandlerInterceptor {

  private final PaperTrailService paperTrailService;

  /**
   * Creates a {@link PaperTrailInterceptor} by given {@link PaperTrailService}.
   * 
   * @param paperTrailService a {@link PaperTrailService}
   */
  public PaperTrailInterceptor(PaperTrailService paperTrailService) {
    this.paperTrailService = paperTrailService;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      @Nullable ModelAndView modelAndView) throws Exception {
    paperTrailService.audit(request, response);
  }

}
