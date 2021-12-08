package com.example.boot.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * ClassName:UserImportSelector
 * Package:com.example.boot.selector
 * Description:
 *
 * @Date:2021/11/29 17:07
 * @Author:qs@1.com
 */
public class UserImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{UserBean.class.getName(), RoleBean.class.getName()};
    }
}
