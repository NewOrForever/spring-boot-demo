//package com.learn.xml;
//
//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.io.xml.DomDriver;
//import org.springframework.util.StringUtils;
//
//import javax.xml.stream.XMLOutputFactory;
//import javax.xml.stream.XMLStreamWriter;
//import java.io.StringWriter;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.logging.Logger;
//
///**
// * ClassName:AnotherXmlUtils
// * Package:com.learn.xml
// * Description:
// *
// * @Date:2023/2/22 13:33
// * @Author:qs@1.com
// */
//public class AnotherXmlUtils {
//    private static final long serialVersionUID = 1L;
//    private static final Logger logger = Logger.getLogger(XmlUtil.class.getName());
//
//
//    /**
//     * 转换对象为xml
//     *
//     * @param obj
//     * @return
//     */
//    public static <T> String objToXmlByXStream(T obj) {
//        return getXStream().toXML(obj);
//    }
//
//
//    /**
//     * 转换xml为对象
//     *
//     * @param cla
//     * @param xml
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    public static <T> T xmlToObjByXStream(Class<T> cla, String xml) {
//        return (T) getXStream().fromXML(xml);
//    }
//
//    /**
//     * 转换xml为map
//     *
//     * @param xml
//     * @return
//     * @throws Exception
//     */
//    public static Map<String, Object> xmlToMapByXStream(String xml) throws Exception {
//        return xmlToMapByXStream(xml, null, false);
//    }
//
//    /**
//     * 转换xml为map
//     *
//     * @param xml
//     * @param addAliasName
//     * @return
//     * @throws Exception
//     */
//    public static Map<String, Object> xmlToMapByXStream(String xml, boolean addAliasName) throws Exception {
//        return xmlToMapByXStream(xml, null, addAliasName);
//    }
//
//    /**
//     * 转换xml为map
//     *
//     * @param xml
//     * @param aliasName
//     * @return
//     * @throws Exception
//     */
//    public static Map<String, Object> xmlToMapByXStream(String xml, String aliasName) throws Exception {
//        return xmlToMapByXStream(xml, aliasName, false);
//    }
//
//    /**
//     * 转换xml为map
//     *
//     * @param xml
//     * @param aliasName
//     * @param addAliasName
//     * @return
//     * @throws Exception
//     */
//    public static Map<String, Object> xmlToMapByXStream(String xml, String aliasName, boolean addAliasName) throws Exception {
//        try {
//            return doXmlToMapByXStream(xml, aliasName, addAliasName);
//        } catch (Exception e) {
//            logger.log("获取xml根节点进行处理", e);
//            String[] rootAry = JavaUtil.split(e.getMessage(), ":");
//            String newAliasName = "";
//            for (String root : rootAry) {
//                if (CheckUtil.isNotNull(root)) {
//                    newAliasName = root;
//                    break;
//                }
//            }
//            return doXmlToMapByXStream(xml, newAliasName, addAliasName);
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    private static Map<String, Object> doXmlToMapByXStream(String xml, String aliasName, boolean addAliasName) {
//        XStream xstream = getXStream();
//        xstream.alias(aliasName, Map.class);
//        xstream.registerConverter(new XStreamConverter());
//        Object obj = xstream.fromXML(xml);
//        Map<String, Object> xmlMapNew = new HashMap<String, Object>();
//        Map<String, Object> xmlMap = null;
//        if (obj instanceof Map) {
//            // 添加别名只对map有效
//            xmlMap = (Map<String, Object>) obj;
//            if (addAliasName) {
//                xmlMapNew.put(aliasName, xmlMap);
//            } else {
//                xmlMapNew.putAll(xmlMap);
//            }
//        } else {
//            // 系统对list自动添加别名
//            xmlMap = new HashMap<String, Object>();
//            List<Object> xmlList = (List<Object>) obj;
//            if (CheckUtil.isNull(aliasName)) {
//                aliasName = XmlConstant.LIST_ALIAS_MAP_KEY;
//            }
//            xmlMap.put(aliasName, xmlList);
//            xmlMapNew.putAll(xmlMap);
//        }
//        return xmlMapNew;
//    }
//
//    // /**
//    // * 转换xml为map
//    // *
//    // * @param xml
//    // * @param aliasName
//    // * @param classType
//    // * @return
//    // * @throws Exception
//    // */
//    // @SuppressWarnings("unchecked")
//    // public static Map<String, Object> xmlToMapByXStream(String xml, String aliasName, Class<?> classType) throws Exception {
//    // XStream xstream = getXStream();
//    // xstream.alias(aliasName, classType);
//    // xstream.registerConverter(new XStreamConverter());
//    // return (Map<String, Object>) xstream.fromXML(xml);
//    // }
//    /**
//     * 转换map为xml
//     *
//     * @see 如果有list元素,则设置zj.xml.util.XmlConstant.LIST_ALIAS_MAP_KEY为list元素的第一个值为别名,否则默认child
//     * @param xml
//     * @param aliasName
//     * @return
//     * @throws Exception
//     */
//    public static String mapToXmlByXStream(Map<String, Object> map, String aliasName) throws Exception {
//        XStream xstream = getXStream();
//        xstream.alias(aliasName, Map.class);
//        xstream.registerConverter(new XStreamConverter());
//        return xstream.toXML(map);
//    }
//
//    /**
//     * 转换map为xml
//     *
//     * @param map
//     * @return
//     * @throws Exception
//     */
//    public static String mapToXmlByXMLStream(Map<String, Object> map) throws Exception {
//        return mapToXmlByXMLStream(map, null);
//    }
//
//    /**
//     * 转换map为xml
//     *
//     * @param map
//     * @param callBack
//     * @return
//     * @throws Exception
//     */
//    public static String mapToXmlByXMLStream(Map<String, Object> map, ICallBackMap2xmlByXMLStream callBack) throws Exception {
//        StringWriter sw = null;
//        XMLStreamWriter xsw = null;
//        try {
//            sw = new StringWriter(100);
//            xsw = XMLOutputFactory.newInstance().createXMLStreamWriter(sw);
//            if (callBack != null) {
//                callBack.call(xsw);
//            }
//            for (String key : map.keySet()) {
//                callMapKeyValue(xsw, key, map.get(key), callBack);
//            }
//        } finally {
//            xsw.flush();
//            sw.flush();
//            xsw.close();
//            sw.close();
//        }
//        String xml = sw.getBuffer().toString();
//        return xml;
//    }
//
//    /**
//     * 转换map为xml添加编码头标识
//     *
//     * @param map
//     * @param charsetName
//     * @return
//     * @throws Exception
//     */
//    public static String mapToXmlTitleByXMLStream(Map<String, Object> map, final String charsetName) throws Exception {
//        return mapToXmlTitleByXMLStream(map, charsetName, null);
//    }
//
//    /**
//     * 转换map为xml添加编码头标识
//     *
//     * @param map
//     * @param charsetName
//     * @param listSuffixes
//     * @return
//     * @throws Exception
//     */
//    public static String mapToXmlTitleByXMLStream(Map<String, Object> map, final String charsetName, final Map<String, Object> listAttrMap) throws Exception {
//        return mapToXmlByXMLStream(map, new ICallBackMap2xmlByXMLStream() {
//            @Override
//            public void loop(XMLStreamWriter xsw, String key, Object value) throws Exception {
//            }
//
//            @Override
//            public void call(XMLStreamWriter xsw) throws Exception {
//                xsw.writeStartDocument(charsetName, "1.0");
//            }
//
//            @Override
//            public Map<String, Object> listAttrMap() {
//                return listAttrMap;
//            }
//        });
//    }
//
//    /**
//     * 转换map为xml添加GBK头标识
//     *
//     * @param map
//     * @return
//     * @throws Exception
//     */
//    public static String mapToXmlGBKTitleByXMLStream(Map<String, Object> map) throws Exception {
//        return mapToXmlTitleByXMLStream(map, "GBK");
//    }
//
//    /**
//     * 转换map为xml添加UTF-8头标识
//     *
//     * @param map
//     * @return
//     * @throws Exception
//     */
//    public static String mapToXmlUTF8TitleByXMLStream(Map<String, Object> map) throws Exception {
//        return mapToXmlTitleByXMLStream(map, "UTF-8");
//    }
//
//    /**
//     * 回调map的key-value
//     *
//     * @param xsw
//     * @param key
//     * @param value
//     * @param callBack
//     * @throws Exception
//     */
//    @SuppressWarnings("unchecked")
//    private static void callMapKeyValue(XMLStreamWriter xsw, String key, Object value, ICallBackMap2xmlByXMLStream callBack) throws Exception {
//        if (callBack != null) {
//            callBack.loop(xsw, key, value);
//        }
//        xsw.writeStartElement(key);
//        if (value instanceof Map) {
//            Map<String, Object> mapNew = (Map<String, Object>) value;
//            for (String keyNew : mapNew.keySet()) {
//                Object valueNew = mapNew.get(keyNew);
//                callMapKeyValue(xsw, keyNew, valueNew, callBack);
//            }
//        } else if (value instanceof List) {
//            List<Object> list = (List<Object>) value;
//            String startNode = null;
//            for (int i = 0; i < list.size(); i++) {
//                Object subvalue = list.get(i);
//                if (i == 0) {
//                    if (subvalue instanceof String) {
//                        String tempValue = (String) subvalue;
//                        if (tempValue.length() > XmlConstant.LIST_ALIAS_MAP_KEY.length()) {
//                            startNode = tempValue.substring(XmlConstant.LIST_ALIAS_MAP_KEY.length());
//                        }
//                    } else if (subvalue instanceof Map) {
//                        Map<String, Object> tempMap = (Map<String, Object>) subvalue;
//                        startNode = (String) tempMap.get(XmlConstant.LIST_ALIAS_MAP_KEY);
//                    } else {
//                        // 其它类型默认child
//                    }
//                    if (StringUtils.hasText(startNode)) {
//                        final Map<String, Object> listAttrMap = callBack.listAttrMap();
//                        if (listAttrMap != null && listAttrMap.size() > 0) {
//                            startNode = JavaUtil.objToStr(listAttrMap.get(key));
//                        }
//                        if (StringUtils.hasText(startNode)) {
//                            startNode = "child";
//                        }
//                    } else {
//                        // 赋值后继续
//                        continue;
//                    }
//                }
//                xsw.writeStartElement(startNode);
//                if (subvalue instanceof String) {
//                    xsw.writeCharacters(subvalue == null ? "" : "" + subvalue);
//                } else if (subvalue instanceof Map) {
//                    Map<String, Object> map = (Map<String, Object>) subvalue;
//                    for (String keyNew : map.keySet()) {
//                        callMapKeyValue(xsw, keyNew, map.get(keyNew), callBack);
//                    }
//                } else {
//                    logger.warn("不支持转换类型,直接赋值:" + subvalue);
//                    xsw.writeCharacters(subvalue == null ? "" : "" + subvalue);
//                }
//                xsw.writeEndElement();
//            }
//        } else {
//            // 其它类型先不考虑,则当成字符串输出
//            xsw.writeCharacters(value == null ? "" : "" + value);
//        }
//        xsw.writeEndElement();
//    }
//
//    /**
//     * 回调
//     *
//     * @author zhangjun
//     *
//     */
//    public static interface ICallBackMap2xmlByXMLStream {
//        /**
//         * 一般设置
//         */
//        public void call(XMLStreamWriter xsw) throws Exception;
//
//        /**
//         * 在循环中回调
//         */
//        public void loop(XMLStreamWriter xsw, String key, Object value) throws Exception;
//
//        /**
//         * list后缀
//         *
//         * @return
//         */
//        public Map<String, Object> listAttrMap();
//    }
//
//    // 以下方法可以一般不使用
//    /**
//     * 获取xStream对象
//     *
//     * @return
//     */
//    public static XStream getXStream() {
//        return getXStream(null);
//    }
//
//    /**
//     * 重命名xml标签名
//     *
//     * @param types
//     * @return
//     */
//    public static XStream getXStream(Class<?> types[]) {
//        XStream xStream = new XStream(new DomDriver());
//        aliasByXStream(xStream, types);
//        return xStream;
//    }
//
//    /**
//     * 重命名xml标签名
//     *
//     * @param xStream
//     * @param types
//     */
//    public static XStream aliasByXStream(XStream xStream, Class<?> types[]) {
//        if (types == null)
//            return xStream;
//        for (int i = 0; i < types.length; i++) {
//            Class<?> type = types[i];
//            String className = type.getName();
//            try {
//                String classNames[] = className.split(".");
//                xStream.alias(classNames[classNames.length - 1], type);
//            } catch (Exception ex) {
//                xStream.alias(className, type);
//                ex.printStackTrace();
//            }
//        }
//        return xStream;
//    }
//
//}
