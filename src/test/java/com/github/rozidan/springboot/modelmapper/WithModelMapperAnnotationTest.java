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

import com.github.rozidan.springboot.modelmapper.dtos.UserDto;
import com.github.rozidan.springboot.modelmapper.entities.User;
import com.github.rozidan.springboot.modelmapper.mapping.UserDtoMapping;
import com.github.rozidan.springboot.modelmapper.WithModelMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
public class WithModelMapperAnnotationTest {

    @Autowired
    private ModelMapper mapper;

    @Test
    public void annotationTest() {
        UserDto dto = new UserDto();
        dto.setAge(20);
        dto.setFirstName("aa");
        dto.setLastName("bb");
        dto.setMiddleName("cc");

        User user = new User();
        user.setAge(null);
        user.setName(null);

        mapper.map(user, dto);

        assertThat(dto.getFirstName(), equalTo("aa"));
        assertThat(dto.getLastName(), equalTo("bb"));
        assertThat(dto.getMiddleName(), equalTo("cc"));
        assertThat(dto.getAge(), equalTo(20));
    }

    @Configuration
    @WithModelMapper(basePackageClasses = UserDtoMapping.class)
    public static class Application {
    }
}
