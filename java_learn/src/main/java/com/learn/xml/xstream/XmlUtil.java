package com.learn.xml.xstream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAliasType;
import com.thoughtworks.xstream.security.AnyTypePermission;

import javax.xml.stream.XMLStreamException;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:XmlUtil
 * Package:com.learn.xml
 * Description:
 *
 * @Date:2023/2/22 10:35
 * @Author:qs@1.com
 */
public class XmlUtil {

    @XStreamAliasType("person")
    public static class Person {
        private String name;
        private Integer age;

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

    public static void main(String[] args) throws XMLStreamException {
        Person person = new Person();
        person.setAge(1);
        person.setName("test001");
        String xml = XmlUtil.toXml(person, false);
        System.out.println(xml);

        Map<String, Person> map = new HashMap<>();
        map.put("person01", person);
        System.out.println(toXml(map));

        /******************反序列化*******************/
        Person person1 = fromXml(xml, Person.class);
        System.out.println(person1);

    }

    /**
     * @param obj 要序列化的对象
     * @return 序列化后的xml字符串
     */
    public static String toXml(Object obj) throws XMLStreamException {
        return toXml(obj, "1.0", "utf-8", "yes", true);
    }

    public static String toXml(Object obj, String encoding) {
        return toXml(obj, "1.0", encoding, "yes", true);
    }

    public static String toXml(Object obj, boolean cleanXml) {
        return toXml(obj, "1.0", "utf-8", "yes", cleanXml);
    }

    public static String toXml(Object obj, String encoding, boolean cleanXml) {
        return toXml(obj, "1.0", encoding, "yes", cleanXml);
    }

    public static String toXml(Object obj, String version, String encoding, String standalone, boolean cleanXml) {
        String xmlHeadStr = "<?xml version=\"" + version + "\" encoding=\"" + encoding + "\" standalone=\"" + standalone + "\" ?>";
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true); // 自动发现注解
        try {
            return xmlHeadStr + (cleanXml ? xStream.toXML(obj).replaceAll("\\s+", "") : ("\r\n" + xStream.toXML(obj)));
        } catch (Exception e) {
            throw new RuntimeException("Serialization object to xml error: " + e.getMessage());
        }
    }

    /**
     * @param xml          XML字符串
     * @param targetClazz xml反序列化的目标类
     * @param <T>
     * @return 目标类对象
     */
    public static <T> T fromXml(String xml, Class<T> targetClazz) {

        XStream xStream = new XStream();
        xStream.registerConverter(new XStreamConverter());


        // 允许反序列化的类
        // xStream.allowTypes(new Class[]{Person.class});
        xStream.addPermission(new AnyTypePermission());

        xStream.processAnnotations(targetClazz); // 反序列化的类

        try {
            return (T) xStream.fromXML(xml);
        } catch (Exception e) {
            throw new RuntimeException("Deserialization from xml to object error: " + e.getMessage());
        }
    }
}
