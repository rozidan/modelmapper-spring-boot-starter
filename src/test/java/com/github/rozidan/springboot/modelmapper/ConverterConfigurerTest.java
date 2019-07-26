/**
 * Copyright (C) 2019 Idan Roz the original author or authors
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

import com.github.rozidan.springboot.modelmapper.dtos.ProdTypeDto;
import com.github.rozidan.springboot.modelmapper.dtos.ProductDto;
import com.github.rozidan.springboot.modelmapper.entities.ProdType;
import com.github.rozidan.springboot.modelmapper.entities.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the {@link ConverterConfigurer} class.
 *
 * @author Idan Rozenfeld
 */
@RunWith(SpringRunner.class)
public class ConverterConfigurerTest {

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void shouldInstantiateMapper() {
        assertThat(modelMapper, is(notNullValue()));
    }

    @Test
    public void shouldMapProductEntity() {
        final Product product = new Product("Banana", ProdType.T1);
        final ProductDto productDto = modelMapper.map(product, ProductDto.class);
        assertThat(productDto.getName(), equalTo("Banana"));
        assertThat(productDto.getType(), equalTo(ProdTypeDto.TYPE1));
    }

    @Configuration
    @EnableAutoConfiguration
    public static class Application {

        @Bean
        public ConverterConfigurer<ProdType, ProdTypeDto> userMapping() {
            return new ConverterConfigurer<ProdType, ProdTypeDto>() {
                @Override
                public Converter<ProdType, ProdTypeDto> converter() {
                    return new AbstractConverter<ProdType, ProdTypeDto>() {
                        @Override
                        protected ProdTypeDto convert(ProdType source) {
                            switch (source) {
                                case T1:
                                    return ProdTypeDto.TYPE1;
                                case T2:
                                    return ProdTypeDto.TYPE2;
                            }
                            return null;
                        }
                    };
                }
            };
        }
    }
}