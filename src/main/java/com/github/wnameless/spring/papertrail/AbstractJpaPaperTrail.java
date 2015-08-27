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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.http.HttpMethod;

@MappedSuperclass
public abstract class AbstractJpaPaperTrail extends AbstractPersistable<Long>
    implements PaperTrail {

  private static final long serialVersionUID = 1L;

  @Column
  private String userId;

  @Column(nullable = false)
  private String remoteAddr;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private HttpMethod httpMethod;

  @Column(nullable = false)
  private String requestUri;

  @Column(nullable = false)
  private int httpStatus;

  private Date createdAt = new Date();

  @Override
  public String getUserId() {
    return userId;
  }

  @Override
  public void setUserId(String userId) {
    this.userId = userId;
  }

  @Override
  public String getRemoteAddr() {
    return remoteAddr;
  }

  @Override
  public void setRemoteAddr(String remoteAddr) {
    this.remoteAddr = remoteAddr;
  }

  @Override
  public HttpMethod getHttpMethod() {
    return httpMethod;
  }

  @Override
  public void setHttpMethod(HttpMethod httpMethod) {
    this.httpMethod = httpMethod;
  }

  @Override
  public String getRequestURI() {
    return requestUri;
  }

  @Override
  public void setRequestURI(String requestUri) {
    this.requestUri = requestUri;
  }

  @Override
  public int getHttpStatus() {
    return httpStatus;
  }

  @Override
  public void setHttpStatus(int httpStatus) {
    this.httpStatus = httpStatus;
  }

  @Override
  public Date getCreatedAt() {
    return createdAt;
  }

  @Override
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{userId=" + getUserId()
        + ", remoteAddr=" + getRemoteAddr() + ", httpMethod=" + getHttpMethod()
        + ", requestUri=" + getRequestURI() + ", httpStatus=" + getHttpStatus()
        + ", createdAt=" + getCreatedAt() + "}";
  }

}