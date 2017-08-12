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
package com.github.rozidan.springboot.modelmapper.testing;

import com.github.rozidan.springboot.modelmapper.ConfigurationConfigurer;
import com.github.rozidan.springboot.modelmapper.ModelMapperAutoConfiguration;
import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * <p>
 * Annotation that can be used in combination with @RunWith(SpringRunner.class) in case model mapper
 * component are needed for testing.
 * </p>
 *
 * @author Idan Rozenfeld
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ImportAutoConfiguration(ModelMapperAutoConfiguration.class)
@ComponentScan(useDefaultFilters = false,
        includeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = {TypeMapConfigurer.class, ConfigurationConfigurer.class}))
public @interface WithModelMapper {

    /**
     * Base packages to scan for mapper's components.
     * <p>
     * Use {@link #basePackageClasses} for a type-safe alternative to
     * String-based package names.
     */
    @AliasFor(annotation = ComponentScan.class)
    String[] basePackages() default {};

    /**
     * Type-safe alternative to {@link #basePackages} for specifying the packages
     * to scan for annotated components. The package of each class specified will be scanned.
     * <p>
     * Consider creating a special no-op marker class or interface in each package
     * that serves no purpose other than being referenced by this attribute.
     */
    @AliasFor(annotation = ComponentScan.class)
    Class<?>[] basePackageClasses() default {};
}
