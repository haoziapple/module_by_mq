package com.fzrj.schedule.bean;

/**
 * @className:com.fzrj.schedule.bean.ArgChecker
 * @description:参数检验类
 * @version:v1.0.0
 * @date:2016年12月30日 下午1:30:09
 * @author:WangHao
 */
public final class ArgChecker
{
	private ArgChecker()
	{
	}

	public static void checkArgument(boolean expression)
	{
		if (!expression)
		{
			throw new IllegalArgumentException();
		}
	}

	public static void checkArgument(boolean expression, String errorMessage)
	{
		if (!expression)
		{
			throw new IllegalArgumentException(errorMessage);
		}
	}

}
