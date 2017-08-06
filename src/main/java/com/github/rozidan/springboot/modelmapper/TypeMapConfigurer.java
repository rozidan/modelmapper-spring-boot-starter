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
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;
import org.modelmapper.internal.typetools.TypeResolver;

public abstract class TypeMapConfigurer<S, D> {

    public String getTypeMapName() {
        return null;
    }

    public Configuration getConfiguration() {
        return null;
    }

    public abstract void configure(TypeMap<S, D> typeMap);

    @SuppressWarnings("unchecked")
    void configureImpl(ModelMapper mapper) {
        Class<?>[] typeArguments = TypeResolver.resolveRawArguments(TypeMapConfigurer.class, getClass());
        String typeMapName = getTypeMapName();
        Configuration configuration = getConfiguration();

        if (typeMapName == null && configuration == null) {
            configure(mapper.createTypeMap((Class<S>) typeArguments[0], (Class<D>) typeArguments[1]));
        } else if (typeMapName == null) {
            configure(mapper.createTypeMap((Class<S>) typeArguments[0], (Class<D>) typeArguments[1], configuration));
        } else if (configuration == null) {
            configure(mapper.createTypeMap((Class<S>) typeArguments[0], (Class<D>) typeArguments[1], typeMapName));
        } else {
            configure(mapper.createTypeMap((Class<S>) typeArguments[0], (Class<D>) typeArguments[1], typeMapName, configuration));
        }
    }
}
