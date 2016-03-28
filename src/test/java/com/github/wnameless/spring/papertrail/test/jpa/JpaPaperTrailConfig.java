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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.wnameless.spring.papertrail.AfterPaperTrailCallback;
import com.github.wnameless.spring.papertrail.AroundPaperTrailCallback;
import com.github.wnameless.spring.papertrail.BeforePaperTrailCallback;
import com.github.wnameless.spring.papertrail.PaperTrailCallback;

@SuppressWarnings("deprecation")
@Configuration
public class JpaPaperTrailConfig {

  public static final List<String> testStringList = newArrayList();

  @Autowired
  PaperTrailJpaRepository paperTrailRepo;

  @Bean
  public AroundPaperTrailCallback<JpaPaperTrail, PaperTrailJpaRepository> aroundTrailCallback() {
    return new AroundPaperTrailCallback<JpaPaperTrail, PaperTrailJpaRepository>() {

      @Override
      public void aroundPaperTrail(PaperTrailJpaRepository paperTrailRepo,
          JpaPaperTrail paperTrail, HttpServletRequest request,
          HttpServletResponse response) {
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
  public BeforePaperTrailCallback<JpaPaperTrail> beforePaperTrailCallback() {
    return new BeforePaperTrailCallback<JpaPaperTrail>() {

      @Override
      public void beforePaperTrail(JpaPaperTrail paperTrail,
          HttpServletRequest request, HttpServletResponse response) {
        if (paperTrail.getRequestUri().equals("/before"))
          paperTrail.setUserId("BEFORE");
      }

    };
  }

  @Bean
  public AfterPaperTrailCallback<JpaPaperTrail> afterPaperTrailCallback() {
    return new AfterPaperTrailCallback<JpaPaperTrail>() {

      @Override
      public void afterPaperTrail(JpaPaperTrail paperTrail,
          HttpServletRequest request, HttpServletResponse response) {
        if (paperTrail.getRequestUri().equals("/after"))
          paperTrail.setUserId("AFTER");

        paperTrailRepo.save(paperTrail);
      }

    };
  }

  @Bean
  public PaperTrailCallback<JpaPaperTrail> paperTrailCallback1() {
    return new PaperTrailCallback<JpaPaperTrail>() {

      @Override
      public void doWithPaperTrail(JpaPaperTrail paperTrail,
          HttpServletRequest request, HttpServletResponse response) {
        testStringList.add("ysysysys");
      }

    };
  }

  @Bean
  public PaperTrailCallback<JpaPaperTrail> paperTrailCallback2() {
    return new PaperTrailCallback<JpaPaperTrail>() {

      @Override
      public void doWithPaperTrail(JpaPaperTrail paperTrail,
          HttpServletRequest request, HttpServletResponse response) {
        testStringList.add("hahahaha");
      }

    };
  }

}
