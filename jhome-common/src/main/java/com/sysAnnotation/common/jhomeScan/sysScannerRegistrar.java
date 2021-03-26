package com.sysAnnotation.common.jhomeScan;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 解析器
 */
public class sysScannerRegistrar implements ImportSelector {
    public sysScannerRegistrar() {
        System.out.println(String.format("来自Jhome消息：JhomeAnnotation注入容器------  >..<   OK "));
    }
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        //System.out.println(annotationMetadata);
        return new String[0];
    }
    public void registersysScanner(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(sysScan.class.getName()));
        if (mapperScanAttrs != null) {
            System.out.println(mapperScanAttrs);
            //this.registerBeanDefinitions(mapperScanAttrs, registry);
        }
    }
}
