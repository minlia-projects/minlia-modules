package com.minlia.cloud.data.jpa.support.auditing;

import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.context.MappingContext;
//@EnableJpaAuditing
//@Import(JpaAuditingHandlerRegistrar.class)
public class JpaAuditingHandler extends org.springframework.data.auditing.AuditingHandler {

    public JpaAuditingHandler(MappingContext<? extends PersistentEntity<?, ?>, ? extends PersistentProperty<?>> mappingContext) {
        super(mappingContext);
    }

    @Override
    public void markCreated(Object source) {
        System.out.println("markCreated");
//        System.out.println(application);
    }

    @Override
    public void markModified(Object source) {
        System.out.println("markModified");
//        System.out.println(application);
    }
}