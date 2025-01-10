/*
 *
 * Copyright 2016 Wei-Ming Wu
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
package com.github.wnameless.spring.papertrail.test.jpa;

import static org.junit.jupiter.api.Assertions.*;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import com.google.common.io.BaseEncoding;

@SpringBootTest(classes = {JpaApplication.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
public class AfterPaperTrailCallbackTest {

  @LocalServerPort
  int port;

  RestTemplate template = new RestTemplate();
  String host;
  String encodedAuth = "Basic " + BaseEncoding.base64().encode("test:123456".getBytes());

  @Autowired
  PaperTrailJpaRepository repo;

  @BeforeEach
  public void before() {
    host = "http://localhost:" + port;
  }

  @Test
  public void testAfter() throws Exception {
    RequestEntity<Void> req =
        RequestEntity.post(new URI(host + "/after")).header("Authorization", encodedAuth).build();
    template.exchange(req, String.class);

    JpaPaperTrail trail = repo.findByRequestUri("/after");
    assertEquals("AFTER", trail.getUserId());
    assertEquals("127.0.0.1", trail.getRemoteAddr());
    assertEquals("POST", trail.getHttpMethod());
    assertEquals("/after", trail.getRequestUri());
    assertEquals(201, trail.getHttpStatus());
    assertNotNull(trail.getCreatedAt());
  }

}
