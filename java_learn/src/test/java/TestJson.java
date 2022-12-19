import com.alibaba.fastjson2.*;

import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:TestJson
 * Package:PACKAGE_NAME
 * Description:
 *
 * @Date:2022/10/18 14:36
 * @Author:qs@1.com
 */
public class TestJson {
    @Test
    public void testJsonArrayToList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Map<String, Object> map = new HashMap<>(2);
            list.add(map);
            map.put("key0" + i, "a"+ i);
        }
        String json = JSON.toJSONString(list);
        System.out.println(json);

        JSONArray jsonArray = JSON.parseArray(json);
        List<JSONObject> jsonObjectList = jsonArray.toList(JSONObject.class);
        System.out.println(JSON.toJSONString(jsonObjectList));

    }

    @Test
    public void testPhone() {
        Pattern PHONE_PATTERN = Pattern.compile(
                "^((13[0-9])|(14[0,1,4-9])|(15[^4])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$"
        );
        Matcher matcher = PHONE_PATTERN.matcher("15501693701");
        System.out.println(matcher.matches());
    }

    @Test
    public void testTime() {
        Date date = new Date();
        System.out.println(date.getYear());
        System.out.println(date.getYear()+1900);
    }

    @Test
    public void testBitCalc() {
        int features = 0;
        features |= MySerialFeature.test001.mask;
        features |= MySerialFeature.test002.mask;
        features |= MySerialFeature.test003.mask;
        int val = features & MySerialFeature.test004.mask;
        String s = Integer.toBinaryString(features);
        System.out.println(s);
    }

    @Test
    public void testJsonSerializeAndDerialize() {
        Map map = new HashMap<>();
        map.put("key001", "val001");
        map.put("key003", "val003");
        map.put("key002", "val002");
        map.put("key005", "val005");
        map.put("key004", "val004");
        System.out.println(JSON.toJSONString(map));
        System.out.println(JSON.toJSONString(map, JSONWriter.Feature.MapSortField));

        String jsonStr = "{\"key004\":\"val004\",\"key003\":\"val003\",\"key005\":\"val005\",\"key002\":\"val002\",\"key001\":\"val001\"}";
        Map map1 = (Map) JSON.parse(jsonStr, JSONReader.Feature.SupportAutoType);

    }

    enum MySerialFeature {
        test001,test002,test003,test004;

        MySerialFeature() {
            mask = (1<<ordinal());
        }
        public final int mask;

    }
}
