package com.example.sliding.mainview.Utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonUtils {

	private String jsonStringForList = null;
	private String jsonStringForObject = null;
	private int flag = 0;
	

	public JsonUtils(Object object){
			this.jsonStringForObject = serialize(object);
			this.flag = 1;
	}
	
	public JsonUtils(List list){
			this.jsonStringForList = serialize(list);
			this.flag = 2;
	}
	

	public  JSONObject getJsonObject(){
		JSONObject jsonObject = new JSONObject();
		if (this.flag == 1) {
			JSONObject messageObject = new JSONObject().parseObject(this.jsonStringForObject);
			JSONArray jsonArray = new JSONArray();
			jsonArray.add(messageObject);
			jsonObject.put("result",jsonArray);
			
		}else if (this.flag == 2) {
			JSONArray jsonArray = new JSONArray().parseArray(this.jsonStringForList);
			 jsonObject.put("result", jsonArray);
		}
		 return jsonObject;
	}
    public  <T> String serialize(T object) {
        return JSON.toJSONString(object);
    }
    

    
}
