package com.learn.xml.jackson;

/**
 * ClassName:XmlDeserializationException
 * Package:com.learn.xml.jackson
 * Description:
 *
 * @Date:2023/2/23 9:34
 * @Author:qs@1.com
 */
public class XmlDeserializationException extends XmlRuntimeException {
    public static final int ERROR_CODE = 101;


    private static final String DEFAULT_MSG = "xml deserialize failed. ";

    private static final String MSG_FOR_SPECIFIED_CLASS = "xml deserialize for class [%s] failed. ";

    private Class<?> targetClass;

    public XmlDeserializationException() {
        super(ERROR_CODE, DEFAULT_MSG);
    }

    public XmlDeserializationException(Class<?> targetClass) {
        super(ERROR_CODE, String.format(MSG_FOR_SPECIFIED_CLASS, targetClass.getName()));
        this.targetClass = targetClass;
    }

    public XmlDeserializationException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public XmlDeserializationException(Class<?> targetClass, Throwable throwable) {
        super(ERROR_CODE, String.format(MSG_FOR_SPECIFIED_CLASS, targetClass.getName()), throwable);
        this.targetClass = targetClass;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }
}
