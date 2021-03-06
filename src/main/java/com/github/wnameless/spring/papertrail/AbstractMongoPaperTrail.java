/*
 *
q * Copyright 2015 Wei-Ming Wu
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

import org.springframework.data.annotation.Id;

/**
 * 
 * {@link AbstractMongoPaperTrail} is a MongoDB implementation of
 * {@link PaperTrail}.
 *
 */
public abstract class AbstractMongoPaperTrail extends AbstractPaperTrail {

  @Id
  private String id;

  /**
   * Returns the document ID.
   * 
   * @return a document ID
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the document ID.
   * 
   * @param id
   *          a document ID
   */
  protected void setId(String id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    int hashCode = 27;
    hashCode += null == getId() ? 0 : getId().hashCode() ^ 31;
    return hashCode;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (o == null) return false;
    if (!getClass().equals(o.getClass())) return false;
    AbstractMongoPaperTrail that = (AbstractMongoPaperTrail) o;
    return null == getId() ? false : getId().equals(that.getId());
  }

}
