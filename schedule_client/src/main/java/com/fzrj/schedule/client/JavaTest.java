package com.fzrj.schedule.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @className:com.fzrj.schedule.client.JavaTest
 * @description:一些java规范的测试
 * @version:v1.0.0
 * @date:2017年1月18日 上午10:14:13
 * @author:WangHao
 */
public class JavaTest
{
	// 测试用foreach方法删除list元素
	// 正确的做法应该用iterator
	public static void TestListForEach()
	{
		List<String> a = new ArrayList<String>();
		a.add("1");
		a.add("2");

		for (String temp : a)
		{
			if ("1".equals(temp))
				a.remove(temp);
		}
		System.out.println(a);
	}

	// 
	public static void TestSearchMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		map.put("key3", "value3");

		Set<Entry<String, String>> se = map.entrySet();
		for (Entry<String, String> e : se)
		{
			System.out.println(e.getKey());
			System.out.println(e.getValue());
		}
	}

	public static void main(String[] args)
	{
		TestListForEach();
	}
}
