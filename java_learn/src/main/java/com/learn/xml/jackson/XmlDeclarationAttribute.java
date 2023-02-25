package com.learn.xml.jackson;

import java.util.HashMap;

/**
 * ClassName:XmlDeclarationAttribute
 * Package:com.learn.xml.jackson
 * Description:
 *
 * @Date:2023/2/23 9:07
 * @Author:qs@1.com
 */
public class XmlDeclarationAttribute extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public static final String VERSION_ATTR_NAME = "version";
    public static final String ENCODING_ATTR_NAME = "encoding";
    public static final String VERSION_ATTR_VALUE_DEFAULT = "1.0";
    public static final String ENCODING_ATTR_VALUE_DEFAULT = "utf-8";
    public static final String XML_DECLARATION_DEFAULT = "<?xml version=\"%s\" encoding=\"%s\" %s ?>";

    private String version = VERSION_ATTR_VALUE_DEFAULT;
    private String encoding = ENCODING_ATTR_VALUE_DEFAULT;

    public XmlDeclarationAttribute() {
    }

    public XmlDeclarationAttribute(String version, String encoding) {
        this.version = version;
        this.encoding = encoding;
    }


    @Override
    public XmlDeclarationAttribute put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
