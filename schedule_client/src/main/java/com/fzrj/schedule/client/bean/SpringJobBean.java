package com.fzrj.schedule.client.bean;

/**
 * @className:com.fzrj.schedule.client.bean.SpringJobBean
 * @description:spring定时任务bean
 * @version:v1.0.0
 * @date:2016年12月23日 上午10:58:45
 * @author:WangHao
 */
public class SpringJobBean
{
	private String beanName;

	private String methodName;

	private String paramBean;

	public SpringJobBean()
	{
		super();
	}

	public SpringJobBean(String beanName, String methodName, String paramBean)
	{
		this.beanName = beanName;
		this.methodName = methodName;
		this.paramBean = paramBean;
	}

	public String getBeanName()
	{
		return beanName;
	}

	public String getParamBean()
	{
		return paramBean;
	}
	
	public String getMethodName()
	{
		return methodName;
	}

	@Override
	public String toString()
	{
		return "SpringJobBean [beanName=" + beanName + ", methodName=" + methodName + ", paramBean=" + paramBean + "]";
	}
}
