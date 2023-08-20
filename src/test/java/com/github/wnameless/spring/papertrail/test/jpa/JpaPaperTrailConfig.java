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
package com.github.wnameless.spring.papertrail.test.jpa;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.wnameless.spring.papertrail.AfterPaperTrailCallback;
import com.github.wnameless.spring.papertrail.AroundPaperTrailCallback;
import com.github.wnameless.spring.papertrail.BeforePaperTrailCallback;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@Configuration
public class JpaPaperTrailConfig {

  @Bean
  public AroundPaperTrailCallback<PaperTrailJpaRepository, JpaPaperTrail> aroundTrailCallback() {
    return new AroundPaperTrailCallback<PaperTrailJpaRepository, JpaPaperTrail>() {

      @Override
      public void aroundPaperTrail(PaperTrailJpaRepository paperTrailRepo, JpaPaperTrail paperTrail,
          HttpServletRequest request, HttpServletResponse response) {
        if (paperTrail.getRequestUri().equals("/around")) {
          paperTrail.setUserId("AROUND");
          paperTrailRepo.save(paperTrail);
        } else {
          paperTrailRepo.save(paperTrail);
        }
      }

    };
  }

  @Bean
  public BeforePaperTrailCallback<PaperTrailJpaRepository, JpaPaperTrail> beforePaperTrailCallback() {
    return new BeforePaperTrailCallback<PaperTrailJpaRepository, JpaPaperTrail>() {

      @Override
      public void beforePaperTrail(PaperTrailJpaRepository paperTrailRepo, JpaPaperTrail paperTrail,
          HttpServletRequest request, HttpServletResponse response) {
        if (paperTrail.getRequestUri().equals("/before")) paperTrail.setUserId("BEFORE");
      }

    };
  }


  @Bean
  public AfterPaperTrailCallback<PaperTrailJpaRepository, JpaPaperTrail> afterPaperTrailCallback() {
    return new AfterPaperTrailCallback<PaperTrailJpaRepository, JpaPaperTrail>() {

      @Override
      public void afterPaperTrail(PaperTrailJpaRepository paperTrailRepo, JpaPaperTrail paperTrail,
          HttpServletRequest request, HttpServletResponse response) {
        if (paperTrail.getRequestUri().equals("/after")) paperTrail.setUserId("AFTER");
        paperTrailRepo.save(paperTrail);
      }

    };
  }

}
