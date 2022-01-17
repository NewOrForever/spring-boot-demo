package com.learn.generics;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * ClassName:BaseService
 * Package:com.learn.generics
 * Description:
 *
 * @Date:2022/1/12 16:21
 * @Author:qs@1.com
 */
public class BaseService<O, S> {
    @Autowired
    protected O o;

    @Autowired
    protected S s;


}
