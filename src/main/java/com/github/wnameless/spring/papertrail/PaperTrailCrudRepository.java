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

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 
 * {@link PaperTrailCrudRepository} must be extended by the {@link Repository}
 * of the {@link PaperTrail} implementation, otherwise an error will be occurred
 * during the initialization phase.
 *
 * @param <PT>
 *          the implementation type of {@link PaperTrail}
 * @param <PK>
 *          the type of primary key
 */
@NoRepositoryBean
public interface PaperTrailCrudRepository<PT extends PaperTrail, PK extends Serializable>
    extends CrudRepository<PT, PK> {}
