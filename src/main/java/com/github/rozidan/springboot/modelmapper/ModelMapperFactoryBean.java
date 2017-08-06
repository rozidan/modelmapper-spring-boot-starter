/**
 * Copyright (C) 2017 Idan Rozenfeld the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.rozidan.springboot.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * A factory bean that instantiates the {@link ModelMapper} and configures it by delegating to registered
 * {@link TypeMapConfigurer} instances.
 *
 * @author Idan Rozenfeld
 */
public class ModelMapperFactoryBean implements FactoryBean<ModelMapper> {

    /**
     * The global configuration used for customizing the behaviour of the model mapper.
     */
    @Autowired(required = false)
    private MapperConfigurer mapperConfigurer;

    /**
     * The list of configurers used for customizing the behaviour of the model mapper.
     */
    @Autowired(required = false)
    private List<TypeMapConfigurer> configurers;

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelMapper getObject() throws Exception {
        final ModelMapper modelMapper = new ModelMapper();
        configure(modelMapper);
        return modelMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getObjectType() {
        return ModelMapper.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * Configures the model mapper instance be delegating toward registered {@link TypeMapConfigurer} instances.
     *
     * @param modelMapper the model mapper
     */
    private void configure(ModelMapper modelMapper) {
        if (mapperConfigurer != null) {
            mapperConfigurer.configureImpl(modelMapper);
        }

        if (configurers != null) {
            configurers.forEach(typeMapConfigurer -> typeMapConfigurer.configureImpl(modelMapper));
        }
    }
}
