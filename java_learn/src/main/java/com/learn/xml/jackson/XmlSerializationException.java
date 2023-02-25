package com.learn.xml.jackson;

/**
 * ClassName:XmlDeserializationException
 * Package:com.learn.xml.jackson
 * Description:
 *
 * @Date:2023/2/23 9:34
 * @Author:qs@1.com
 */
public class XmlSerializationException extends XmlRuntimeException {
    public static final int ERROR_CODE = 100;


    private static final String DEFAULT_MSG = "xml serialize failed. ";

    private static final String MSG_FOR_SPECIFIED_CLASS = "xml serialize for class [%s] failed. ";

    private Class<?> serializedClass;

    public XmlSerializationException() {
        super(ERROR_CODE, DEFAULT_MSG);
    }

    public XmlSerializationException(Class<?> serializedClass) {
        super(ERROR_CODE, String.format(MSG_FOR_SPECIFIED_CLASS, serializedClass.getName()));
        this.serializedClass = serializedClass;
    }

    public XmlSerializationException(Throwable throwable) {
        super(ERROR_CODE, throwable);
    }

    public XmlSerializationException(Class<?> serializedClass, Throwable throwable) {
        super(ERROR_CODE, String.format(MSG_FOR_SPECIFIED_CLASS, serializedClass.getName()), throwable);
        this.serializedClass = serializedClass;
    }

    public Class<?> getSerializedClass() {
        return serializedClass;
    }
}
