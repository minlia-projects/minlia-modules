/*
 *
 *   Copyright 2016 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.springframework.data.mybatis.mapping;

import org.springframework.data.mapping.*;
import org.springframework.data.mapping.model.BasicPersistentEntity;
import org.springframework.data.util.ParsingUtils;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;

/**
 * @author Jarvis Song
 */
public class MybatisPersistentEntityImpl<T> extends BasicPersistentEntity<T, MybatisPersistentProperty>
        implements MybatisPersistentEntity<T> {

    private final List<MybatisPersistentProperty> properties;
    private final Comparator comparator;

    private final MybatisMappingContext context;

    public MybatisPersistentEntityImpl(MybatisMappingContext context, TypeInformation<T> information) {
        super(information);
        this.context = context;
        this.properties = new ArrayList<MybatisPersistentProperty>();
        comparator=null;
    }

    public MybatisPersistentEntityImpl(MybatisMappingContext context, TypeInformation<T> information, Comparator<MybatisPersistentProperty> comparator) {
        super(information, comparator);
        this.context = context;

        this.properties = new ArrayList<MybatisPersistentProperty>();
        this.comparator = comparator;
    }



    @Override
    public MybatisMappingContext getContext() {
        return this.context;
    }



    /**
     * if no Id annotation then return isIdProperty
     * @param property
     * @return
     */
    @Override
    protected MybatisPersistentProperty returnPropertyIfBetterIdPropertyCandidateOrNull(MybatisPersistentProperty property) {
        return property.isIdProperty() ? property : null;
    }



    @Override
    public String getEntityName() {
        Entity entity = getType().getAnnotation(Entity.class);
        if (null != entity && StringUtils.hasText(entity.name())) {
            return entity.name();
        }
        return StringUtils.uncapitalize(getType().getSimpleName());
    }

    @Override
    public String getSequenceName() {
        return "seq_" + getTableName();
    }

    @Override
    public MybatisPersistentProperty findByColumnName(final String columnName) {
        final MybatisPersistentProperty[] result = new MybatisPersistentProperty[1];
        doWithProperties(new SimplePropertyHandler() {
            @Override
            public void doWithPersistentProperty(PersistentProperty<?> pp) {
                MybatisPersistentProperty property = (MybatisPersistentProperty) pp;
                if (columnName.equalsIgnoreCase(property.getColumnName())) {
                    result[0] = property;
                    return;
                }
            }
        });
        return result[0];
    }


    @Override
    public String getTableName() {
        Table table = getType().getAnnotation(Table.class);
        if (null != table && StringUtils.hasText(table.name())) {
            return table.name();
        }

        return ParsingUtils.reconcatenateCamelCase(getType().getSimpleName(), "_");
    }

















    private static final String AMBIGUOUS_FIELD_MAPPING = "Ambiguous field mapping detected! Both %s and %s map to the same field name %s! Disambiguate using @Field annotation!";




//    @Override
//    public void verify() {
//
//
//
//        if (comparator != null) {
//            Collections.sort(properties, comparator);
//        }
//
//
//        verifyFieldUniqueness();
////        verifyFieldTypes();
//
//    }

    private void verifyFieldUniqueness() {

        AssertFieldNameUniquenessHandler handler = new AssertFieldNameUniquenessHandler();

//        doWithProperties(handler);
//        doWithAssociations(handler);
    }

    private void verifyFieldTypes() {
        doWithProperties(new PropertyTypeAssertionHandler());
    }



    private static class AssertFieldNameUniquenessHandler
            implements PropertyHandler<MybatisPersistentProperty>  {

        private final Map<String, MybatisPersistentProperty> properties = new HashMap<String, MybatisPersistentProperty>();

        public void doWithPersistentProperty(MybatisPersistentProperty persistentProperty) {
            assertUniqueness(persistentProperty);
        }

//        public void doWithAssociation(Association<MybatisPersistentProperty> association) {
//            assertUniqueness(association.getInverse());
//        }

        private void assertUniqueness(MybatisPersistentProperty property) {

            String fieldName = property.getName();
            MybatisPersistentProperty existingProperty = properties.get(fieldName);

            if (existingProperty != null) {
                throw new RuntimeException(
                        String.format(AMBIGUOUS_FIELD_MAPPING, property.toString(), existingProperty.toString(), fieldName));
            }

            properties.put(fieldName, property);
        }
    }



    private static class PropertyTypeAssertionHandler implements PropertyHandler<MybatisPersistentProperty> {

        /*
         * (non-Javadoc)
         * @see org.springframework.data.mapping.PropertyHandler#doWithPersistentProperty(org.springframework.data.mapping.PersistentProperty)
         */
        @Override
        public void doWithPersistentProperty(MybatisPersistentProperty persistentProperty) {

//            potentiallyAssertTextScoreType(persistentProperty);
//            potentiallyAssertLanguageType(persistentProperty);
//            potentiallyAssertDBRefTargetType(persistentProperty);
        }

//        private static void potentiallyAssertLanguageType(MybatisPersistentProperty persistentProperty) {
//
//            if (persistentProperty.isExplicitLanguageProperty()) {
//                assertPropertyType(persistentProperty, String.class);
//            }
//        }
//
//        private static void potentiallyAssertTextScoreType(MybatisPersistentProperty persistentProperty) {
//
//            if (persistentProperty.isTextScoreProperty()) {
//                assertPropertyType(persistentProperty, Float.class, Double.class);
//            }
//        }
//
//        private static void potentiallyAssertDBRefTargetType(MybatisPersistentProperty persistentProperty) {
//
//            if (persistentProperty.isDbReference() && persistentProperty.getDBRef().lazy()) {
//                if (persistentProperty.isArray() || Modifier.isFinal(persistentProperty.getActualType().getModifiers())) {
//                    throw new MappingException(String.format(
//                            "Invalid lazy DBRef property for %s. Found %s which must not be an array nor a final class.",
//                            persistentProperty.getField(), persistentProperty.getActualType()));
//                }
//            }
//        }

        private static void assertPropertyType(MybatisPersistentProperty persistentProperty, Class<?>... validMatches) {

            for (Class<?> potentialMatch : validMatches) {
                if (ClassUtils.isAssignable(potentialMatch, persistentProperty.getActualType())) {
                    return;
                }
            }

            throw new RuntimeException(
                    String.format("Missmatching types for %s. Found %s expected one of %s.", persistentProperty.getField(),
                            persistentProperty.getActualType(), StringUtils.arrayToCommaDelimitedString(validMatches)));
        }
    }



}
