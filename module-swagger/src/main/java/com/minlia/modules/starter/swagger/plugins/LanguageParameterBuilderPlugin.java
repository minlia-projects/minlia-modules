package com.minlia.modules.starter.swagger.plugins;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import springfox.documentation.schema.ModelReference;
import springfox.documentation.schema.ResolvedTypes;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.contexts.ModelContext;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;

public class LanguageParameterBuilderPlugin implements ParameterBuilderPlugin {

  private final TypeNameExtractor nameExtractor;
  private final TypeResolver resolver;

  @Autowired
  public LanguageParameterBuilderPlugin(TypeNameExtractor nameExtractor, TypeResolver resolver) {
    this.nameExtractor = nameExtractor;
    this.resolver = resolver;
  }

  @Override
  public boolean supports(DocumentationType delimiter) {
    return true;
  }

  private Function<ResolvedType, ? extends ModelReference>
  createModelRefFactory(ParameterContext context) {
    ModelContext modelContext = ModelContext
        .inputParam(context.methodParameter().getParameterType(),
            context.getDocumentationType(),
            context.getAlternateTypeProvider(),
            context.getGenericNamingStrategy(),
            context.getIgnorableParameterTypes());
    return ResolvedTypes.modelRefFactory(modelContext, nameExtractor);
  }


  /**
   * 所有请求都应携带
   * @param context
   */
  @Override
  public void apply(ParameterContext context) {
    MethodParameter parameter = context.methodParameter();
//        Class<?> type = parameter.getParameterType();
//        if (type != null && Pageable.class.isAssignableFrom(type)) {
    Function<ResolvedType, ? extends ModelReference> factory =
        createModelRefFactory(context);

    ModelReference intModel = factory.apply(resolver.resolve(Integer.TYPE));
    ModelReference stringModel = factory.apply(resolver.resolve(List.class, String.class));

    List<Parameter> parameters = Lists.newArrayList(
        context.parameterBuilder()
            .parameterType("header").name("X-REQUEST-LANGUAGE").modelRef(intModel)
            .description("请求语言")
            .build()
//              ,
//                    context.parameterBuilder()
//                            .parameterType("query").name("queryConditionJson").modelRef(stringModel).allowMultiple(true)
//                            .description("search json [{\"fieldName\":\"name\",\"attrType\":\"String\",\"fieldNode\":\"\",\"operate\":\"like\",\"weight\":0,\"value\":\"g\"},{\"fieldName\":\"status\",\"attrType\":\"Integer\",\"fieldNode\":\"\",\"operate\":\"in\",\"weight\":0,\"value\":\"-1\"}]}")
//                            .build()
//
    );

    context.getOperationContext().operationBuilder().parameters(parameters);
//        }
  }

}
