/*
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * {@link PaperTrailCallback} provides an alternative way for users to perform
 * any operation they want with the {@link PaperTrail} which is created during
 * the paper trail mechanism.
 *
 * @param <PT>
 *          the type of a PaperTrail implementation
 * @deprecated Since 0.3.0, please use {@link AfterPaperTrailCallback} instead
 */
@Deprecated
public interface PaperTrailCallback<PT extends PaperTrail> {

  /**
   * The callback of the paper trail mechanism.
   * 
   * @param paperTrail
   *          a {@link PaperTrail}
   * @param request
   *          a {@link HttpServletRequest}
   * @param response
   *          a {@link HttpServletResponse}
   */
  public void doWithPaperTrail(PT paperTrail, HttpServletRequest request,
      HttpServletResponse response);

}
