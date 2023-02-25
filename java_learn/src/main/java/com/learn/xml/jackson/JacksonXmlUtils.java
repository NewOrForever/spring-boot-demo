package com.learn.xml.jackson;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.learn.xml.jackson.XmlDeclarationAttribute.XML_DECLARATION_DEFAULT;

/**
 * ClassName:JacksonXmlUtils
 * Package:com.learn.xml
 * Description:
 *
 * @Date:2023/2/22 16:56
 * @Author:qs@1.com
 */
public final class JacksonXmlUtils {
    static ObjectMapper mapper = new XmlMapper();

    static {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @JsonRootName("p")
    public static class Person {
        @JacksonXmlProperty(localName = "n", isAttribute = true)
        private String name;
        private Integer age;
        @JacksonXmlProperty(localName = "m")
        private Map<String, Object> map;

        public Map<String, Object> getMap() {
            return map;
        }

        public void setMap(Map<String, Object> map) {
            this.map = map;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        Person person = new Person();
        person.setAge(1);
        person.setName("test001");
        Map<String, Object> permap = new HashMap<>();
        permap.put("steven", "库里");
        person.setMap(permap);
        System.out.println(toXml(person));
        System.out.println(toXml(person, new XmlDeclarationAttribute().put("standalone", "yes")));

        Map<String, Object> map = new HashMap<>();
        map.put("test001", "001");
        List<Person> list = new ArrayList<>();
        map.put("list", list);
        list.add(person);
        System.out.println(toXml(map));
    }

    public static <T> T toObj(String xml, Class<T> targetClass) {
        try {
            return mapper.readValue(xml, targetClass);
        } catch (Exception e) {
            throw new XmlDeserializationException(targetClass, e);
        }
    }

    public static <T> T toObj(String json, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (Exception e) {
            throw new XmlDeserializationException(e);
        }
    }

    public static String toXml(Object obj) {
        return toXml(obj, null);
    }

    public static String toXml(Object obj, XmlDeclarationAttribute declarationAttribute) {
        try {
            String xml = mapper.writeValueAsString(obj);
            if (declarationAttribute == null) {
                return xml;
            }
            StringBuilder extendAttrSb = new StringBuilder();
            if (declarationAttribute.size() > 0) {
                declarationAttribute.forEach((key, value) -> {
                    extendAttrSb.append(" ").append(key).append("=").append("\"").append(value).append("\"");
                });
            }
            String xmlDeclaration = String.format(XML_DECLARATION_DEFAULT,
                    declarationAttribute.getVersion(), declarationAttribute.getEncoding(), extendAttrSb);
            return xmlDeclaration + xml;
        } catch (JsonProcessingException e) {
            throw new XmlSerializationException(e);
        }
    }

}
