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
package com.github.wnameless.spring.papertrail.test.jpa;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.google.common.io.BaseEncoding;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JpaApplication.class)
@WebIntegrationTest
public class JpaPaperTrailTest {

  RestTemplate template = new RestTemplate();
  String host = "http://localhost:8080";
  String encodedAuth =
      "Basic " + BaseEncoding.base64().encode("test:123456".getBytes());

  @Autowired
  PaperTrailJpaRepository repo;

  List<String> testStringList = JpaPaperTrailConfig.testStringList;

  @Before
  public void setUp() throws Exception {
    testStringList.clear();
  }

  @Test
  public void testPost() throws Exception {
    RequestEntity<Void> req = RequestEntity.post(new URI(host + "/post"))
        .header("Authorization", encodedAuth).build();
    template.exchange(req, String.class);

    assertEquals(1, repo.count());
    JpaPaperTrail trail = repo.findAll().iterator().next();
    assertEquals("test", trail.getUserId());
    assertEquals("127.0.0.1", trail.getRemoteAddr());
    assertEquals("POST", trail.getHttpMethod().toString());
    assertEquals("/post", trail.getRequestUri());
    assertEquals(201, trail.getHttpStatus());
    assertNotNull(trail.getCreatedAt());

    assertEquals(newArrayList("ysysysys", "hahahaha"), testStringList);
  }

}
