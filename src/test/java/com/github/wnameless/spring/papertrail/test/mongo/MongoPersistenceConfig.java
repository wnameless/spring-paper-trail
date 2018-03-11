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
package com.github.wnameless.spring.papertrail.test.mongo;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@Configuration
public class MongoPersistenceConfig {

  MongodStarter starter = MongodStarter.getDefaultInstance();

  @Bean(destroyMethod = "close")
  public MongoClient mongo() throws IOException {
    IMongodConfig mongodConfig = new MongodConfigBuilder()
        .version(Version.Main.PRODUCTION)
        .net(new Net("127.0.0.1", 12345, Network.localhostIsIPv6())).build();

    MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
    mongodExecutable.start();
    MongoClient mongo = new MongoClient("127.0.0.1", 12345);

    return mongo;
  }

  @Bean
  public MongoDbFactory mongoDbFactory() throws IOException {
    return new SimpleMongoDbFactory(mongo(), "EmbeddedMongoDB");
  }

  @Bean
  public MongoTemplate mongoTemplate() throws IOException {
    return new MongoTemplate(mongoDbFactory());
  }

}
