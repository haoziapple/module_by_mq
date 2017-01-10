package com.fzrj.schedule.client.bean;

import java.lang.reflect.Method;

import com.fzrj.schedule.client.util.JsonUtil;
import com.fzrj.schedule.client.util.SpringContextUtil;

/**
 * @className:com.fzrj.schedule.client.bean.SpringJobExecutor
 * @description:Spring定时任务执行器
 * @version:v1.0.0
 * @date:2017年1月5日 上午9:16:52
 * @author:WangHao
 */
public class SpringJobExecutor
{
	/**
	 * @Description:调用spring容器中bean的方法
	 * @param springJobBean
	 * @version:v1.0
	 * @author:WangHao
	 * @throws Exception
	 * @date:2017年1月5日 上午9:18:42
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void invokeBeanMethod(SpringJobBean springJobBean) throws Exception
	{
		Object springBean = SpringContextUtil.getBean(springJobBean.getBeanName());
		Method[] mArray = springBean.getClass().getDeclaredMethods();
		boolean methodFound = false;
		for (Method m : mArray)
		{
			if (m.getName().equals(springJobBean.getMethodName()))
			{
				methodFound = true;
				// 获取参数类型表
				Class[] clzzArray = m.getParameterTypes();
				if (clzzArray.length == 0)
				{
					// 调用无参方法
					m.invoke(springBean);
				}
				else if (clzzArray.length == 1)
				{
					// 调用只有一个参数的方法
					Class clzz = m.getParameterTypes()[0];
					m.invoke(springBean, JsonUtil.convertStringToObj(springJobBean.getParamBean(), clzz));
				}
				else
				{
					throw new IllegalArgumentException("任务方法参数错误:只允许无参数和一个参数的方法" + springJobBean);
				}
				break;
			}
		}
		if (!methodFound)
		{
			throw new IllegalArgumentException("任务方法名错误" + springJobBean);
		}
	}

	/**
	 * @Description:调用spring容器中bean的方法，并将返回值转为json
	 * @param springJobBean
	 * @return
	 * @throws Exception
	 * @version:v1.0
	 * @author:WangHao
	 * @date:2017年1月10日 下午4:11:38
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String RPCInvokeBeanMethod(SpringJobBean springJobBean) throws Exception
	{
		Object springBean = SpringContextUtil.getBean(springJobBean.getBeanName());
		Method[] mArray = springBean.getClass().getDeclaredMethods();
		Object resultBean = null;
		String resultJson = null;
		boolean methodFound = false;
		for (Method m : mArray)
		{
			if (m.getName().equals(springJobBean.getMethodName()))
			{
				methodFound = true;
				// 获取参数类型表
				Class[] clzzArray = m.getParameterTypes();
				if (clzzArray.length == 0)
				{
					// 调用无参方法
					resultBean = m.invoke(springBean);
				}
				else if (clzzArray.length == 1)
				{
					// 调用只有一个参数的方法
					Class clzz = m.getParameterTypes()[0];
					resultBean = m.invoke(springBean, JsonUtil.convertStringToObj(springJobBean.getParamBean(), clzz));
				}
				else
				{
					throw new IllegalArgumentException("任务方法参数错误:只允许无参数和一个参数的方法" + springJobBean);
				}
				resultJson = JsonUtil.convertObjToString(resultBean);
				break;
			}
		}
		if (!methodFound)
		{
			throw new IllegalArgumentException("任务方法名错误" + springJobBean);
		}
		return resultJson;
	}
}
