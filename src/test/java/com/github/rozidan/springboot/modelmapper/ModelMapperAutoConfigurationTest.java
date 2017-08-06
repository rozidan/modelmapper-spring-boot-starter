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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests the registration of {@link ModelMapper}.
 *
 * @author Idan Rozenfeld
 */
@RunWith(SpringRunner.class)
public class ModelMapperAutoConfigurationTest {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private List<TypeMapConfigurer> configurers;

    @Test
    public void shouldInstantiateMapper() {
        assertThat(modelMapper, is(notNullValue()));
    }

    @Test
    public void shouldInvokeConfigurer() {

        for (TypeMapConfigurer configurer : configurers) {
            verify(configurer).configureImpl(any(ModelMapper.class));
        }
    }

    @Configuration
    @EnableAutoConfiguration
    public static class Application {
        @Bean
        public TypeMapConfigurer typeMapConfigurer() {
            return mock(TypeMapConfigurer.class);
        }
    }
}