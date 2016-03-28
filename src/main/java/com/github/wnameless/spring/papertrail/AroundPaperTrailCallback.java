/*
 *
 * Copyright 2016 Wei-Ming Wu
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
 * {@link AroundPaperTrailCallback} provides an alternative way for users to
 * perform any operation they want with the {@link PaperTrail} which is created
 * during the paper trail mechanism AROUND this {@link PaperTrail} saved.
 *
 * @param <PT>
 *          the type of a PaperTrail implementation
 */
public interface AroundPaperTrailCallback<PT extends PaperTrail, R extends PaperTrailCrudRepository<PT, ?>> {

  /**
   * The callback of the paper trail mechanism AROUND this {@link PaperTrail}
   * saved.<br>
   * <br>
   * Don't forget to call paperTrailRepo.save(paperTrail), otherwise this
   * paperTrail won't be preserved.
   * 
   * @param paperTrailRepo
   *          a {@link PaperTrailCrudRepository}
   * @param paperTrail
   *          a {@link PaperTrail}
   * @param request
   *          a {@link HttpServletRequest}
   * @param response
   *          a {@link HttpServletResponse}
   */
  public void aroundPaperTrail(R paperTrailRepo, PT paperTrail,
      HttpServletRequest request, HttpServletResponse response);

}
