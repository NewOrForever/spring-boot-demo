package com.learn.xml.xstream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.springframework.util.StringUtils;

/**
 *
 * @author zhangjun
 *
 */
public class XStreamConverter implements Converter {
	private Logger log = Logger.getLogger(this.getClass().getName());

	public XStreamConverter() {
		super();
	}

	/**
	 * 只要可以被转换,则将值放入MAP中
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class clazz) {
		if (Map.class.isAssignableFrom(clazz) || Collection.class.isAssignableFrom(clazz) || IXStreamBeanConverter.class.isAssignableFrom(clazz))
			return true;
		else
			return false;
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		map2xml(value, writer, context);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		return populateMap(reader, context);
	}

	/**
	 * 如果是list,则判断第一个元素(以下三种情况)
	 *
	 * @第一种情况 String:zj.xml.util.XmlConstant.LIST_ALIAS_MAP_KEY+别名
	 * @第二种情况 Map<String,Object>:别名=map.get(zj.xml.util.XmlConstant.LIST_ALIAS_MAP_KEY)值
	 * @第三种情况 其它情况/没有设置第一个元素/设置了但别名为空:以child作为别名,第一个元素生效
	 * @param value
	 * @param writer
	 * @param context
	 */
	@SuppressWarnings("unchecked")
	protected void map2xml(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		String key = null;
		if (value instanceof List) {
			List<Object> list = (List<Object>) value;
			String startNode = null;
			for (int i = 0; i < list.size(); i++) {
				Object subvalue = list.get(i);
				if (i == 0) {
					if (subvalue instanceof String) {
						String tempValue = (String) subvalue;
						if (tempValue.length() > XmlConstant.LIST_ALIAS_MAP_KEY.length()) {
							startNode = tempValue.substring(XmlConstant.LIST_ALIAS_MAP_KEY.length());
						}
					} else if (subvalue instanceof Map) {
						Map<String, Object> tempMap = (Map<String, Object>) subvalue;
						startNode = (String) tempMap.get(XmlConstant.LIST_ALIAS_MAP_KEY);
					} else {
						// 其它类型默认child
					}
					if (StringUtils.isEmpty(startNode)) {
						startNode = "child";
					} else {
						// 赋值后继续
						continue;
					}
				}
				writer.startNode(startNode);
				if (subvalue instanceof String) {
					writer.setValue((String) subvalue);
				} else {
					map2xml(subvalue, writer, context);
				}
				writer.endNode();
			}
		} else {
			if (value instanceof Map) {
				Map<String, Object> map = (Map<String, Object>) value;
				for (Iterator<Entry<String, Object>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
					Entry<String, Object> entry = (Entry<String, Object>) iterator.next();
					key = (String) entry.getKey();
					Object subvalue = entry.getValue();
					writer.startNode(key);
					if (subvalue instanceof String) {
						writer.setValue((String) subvalue);
					} else {
						map2xml(subvalue, writer, context);
					}
					writer.endNode();
				}
			} else {
				log.info("不支持转换类型:" + value);
				if (value == null) {
					writer.setValue("");
				} else {
					String newValue = "";
					if (newValue == null) {
						writer.setValue(value.toString());
					} else {
						writer.setValue(newValue);
					}
				}
				// writer.startNode(XmlConstant.NOT_SUPPORT_TAG);
				// writer.setValue(value == null ? "" : "" + value);
				// writer.endNode();
			}
		}
		writer.flush();
		writer.close();
	}

	protected Object populateMap(HierarchicalStreamReader reader, UnmarshallingContext context) {
		// 先不解决
		// Iterator<String> attrNamesIt = reader.getAttributeNames();
		// while(attrNamesIt.hasNext()){
		// String name= attrNamesIt.next();
		// System.out.println("attrName:"+name+",attrValue:"+reader.getAttribute(name));
		// }
		boolean bMap = true;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			String key = reader.getNodeName();
			Object value = null;
			if (reader.hasMoreChildren()) {
				value = populateMap(reader, context);
			} else {
				value = reader.getValue();
			}
			if (bMap) {
				if (map.containsKey(key)) {
					// convert to list
					bMap = false;
					Iterator<Entry<String, Object>> iter = map.entrySet().iterator();
					while (iter.hasNext())
						list.add(iter.next().getValue());
					// insert into list
					list.add(value);
				} else {
					// insert into map
					map.put(key, value);
				}
			} else {
				// insert into list
				list.add(value);
			}
			reader.moveUp();
		}
		if (bMap)
			return map;
		else
			return list;
	}
}
