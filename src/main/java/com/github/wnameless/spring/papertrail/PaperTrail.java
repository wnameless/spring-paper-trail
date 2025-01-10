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

import java.util.Date;

/**
 * 
 * {@link PaperTrail} defines the common APIs of any paper trail entity should have.
 *
 */
public interface PaperTrail {

  /**
   * Returns the user ID of a request.
   * 
   * @return a user ID
   */
  String getUserId();

  /**
   * Sets the user ID of a request.
   * 
   * @param userId a user ID
   */
  void setUserId(String userId);

  /**
   * Returns the remote IP address of a request.
   * 
   * @return an IP address
   */
  String getRemoteAddr();

  /**
   * Sets the remote IP address of a request.
   * 
   * @param remoteAddr an IP address
   */
  void setRemoteAddr(String remoteAddr);

  /**
   * Returns the HTTP method of a request.
   * 
   * @return a HTTP method
   */
  String getHttpMethod();

  /**
   * Sets the HTTP method of a request.
   * 
   * @param httpMethod a HTTP method
   */
  void setHttpMethod(String httpMethod);

  /**
   * Returns the URI of a request.
   * 
   * @return an URI
   */
  String getRequestUri();

  /**
   * Sets the URI of a request.
   * 
   * @param requestUri an URI
   */
  void setRequestUri(String requestUri);

  /**
   * Returns the HTTP status of a request.
   * 
   * @return a HTTP status
   */
  int getHttpStatus();

  /**
   * Sets the HTTP status of a request.
   * 
   * @param httpStatus a HTTP status
   */
  void setHttpStatus(int httpStatus);

  /**
   * Returns the time stamp of a request.
   * 
   * @return a time stamp
   */
  Date getCreatedAt();

  /**
   * Sets the time stamp of a request.
   * 
   * @param createdAt a time stamp
   */
  void setCreatedAt(Date createdAt);

}
