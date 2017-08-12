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

/**
 * Tests the {@link MapperConfigurer} class.
 *
 * @author Idan Rozenfeld
 */

import com.github.rozidan.springboot.modelmapper.dtos.UserDto;
import com.github.rozidan.springboot.modelmapper.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Idan Rozenfeld
 */
@RunWith(SpringRunner.class)
public class MapperConfigurerTest {

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void shouldInstantiateMapper() {
        assertThat(modelMapper, is(notNullValue()));
    }

    @Test
    public void shouldMapUserEntityWithoutNulls() {
        final User user = new User(null, 23);
        final UserDto userDto = new UserDto("John", "Doe", 24, "??");
        modelMapper.map(user, userDto);
        assertThat("John", equalTo(userDto.getFirstName()));
        assertThat("Doe", equalTo(userDto.getLastName()));
    }

    @Configuration
    @EnableAutoConfiguration
    public static class Application {

        @Bean
        public TypeMapConfigurer<User, UserDto> userMapping() {
            return new TypeMapConfigurer<User, UserDto>() {

                @Override
                public void configure(TypeMap<User, UserDto> typeMap) {
                    typeMap.addMapping(User::getAge, UserDto::setAge);
                    typeMap.addMapping(User::getName, UserDto::setFirstName);
                    typeMap.addMapping(User::getName, UserDto::setLastName);
                }
            };
        }

        @Bean
        public MapperConfigurer mapperConfiguration() {
            return new MapperConfigurer() {
                @Override
                public void configure(org.modelmapper.config.Configuration configuration) {
                    configuration.setSkipNullEnabled(true);
                }
            };
        }
    }
}
