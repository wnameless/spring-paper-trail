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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 
 * {@link PaperTrailConfig} is created for {@link EnablePaperTrail} to use. It
 * adds the {@link PaperTrailService} to each HTTP request by using the
 * {@link HandlerInterceptor}.
 *
 */
@Configuration
public class PaperTrailConfig extends WebMvcConfigurerAdapter {

  @Autowired
  private ApplicationContext appCtx;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new PaperTrailInterceptor(paperTrailService()));
  }

  @Bean
  public PaperTrailService paperTrailService() {
    return new PaperTrailService(appCtx);
  }

}
