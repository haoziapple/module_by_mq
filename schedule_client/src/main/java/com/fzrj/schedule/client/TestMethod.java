package com.fzrj.schedule.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;

public class TestMethod
{
	public static class BeanSource
	{
		private String source1 = "";

		private String source2 = "";

		private long s3 = 11;
		private BeanTarget bean;

		public String getSource1()
		{
			return source1;
		}

		public void setSource1(String source1)
		{
			this.source1 = source1;
		}

		public String getSource2()
		{
			return source2;
		}

		public void setSource2(String source2)
		{
			this.source2 = source2;
		}

		public long getS3()
		{
			return s3;
		}

		public void setS3(long s3)
		{
			this.s3 = s3;
		}

		public BeanTarget getBean()
		{
			return bean;
		}

		public void setBean(BeanTarget bean)
		{
			this.bean = bean;
		}

		@Override
		public String toString()
		{
			return "BeanSource [source1=" + source1 + ", source2=" + source2 + ", s3=" + s3 + ", bean=" + bean + "]";
		}
	}

	public static class BeanTarget implements Serializable
	{
		/**
		*
		*/
		private static final long serialVersionUID = 1L;

		private String target1 = "";

		private String target2 = "";

		private int s3;

		private BeanTarget bean;

		public String getTarget1()
		{
			return target1;
		}

		public void setSource1(String target1)
		{
			this.target1 = target1;
		}

		public String getTarget2()
		{
			return target2;
		}

		public void setTarget2(String target2)
		{
			this.target2 = target2;
		}

		public int getS3()
		{
			return s3;
		}

		public void setS3(int s3)
		{
			this.s3 = s3;
		}

		public BeanTarget getBean()
		{
			return bean;
		}

		public void setBean(BeanTarget bean)
		{
			this.bean = bean;
		}

		@Override
		public String toString()
		{
			return "BeanTarget [target1=" + target1 + ", target2=" + target2 + ", s3=" + s3 + ", bean=" + bean + "]";
		}

	}

	public void changeInt(int i)
	{
		i = i + 1;
	}

	public void changeInteger(Integer i)
	{
		i = i + 1;
	}

	public void changeBigDecimal(BigDecimal bigDecimal)
	{
		bigDecimal = bigDecimal.divide(new BigDecimal("4"), BigDecimal.ROUND_HALF_DOWN);
		System.out.println(bigDecimal);
	}

	public void changeBean(BeanSource beanSource)
	{
		beanSource.setSource1("s1");
		beanSource.setSource2("s2");
	}

	public Object deepClone(Object o) throws IOException, ClassNotFoundException
	{
		/* 写入当前对象的二进制流 */
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(o);

		/* 读出二进制流产生的新对象 */
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		return ois.readObject();
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException
	{
		Integer i = 1;
		TestMethod test = new TestMethod();
		test.changeInteger(i);
		System.out.println(i);

		BigDecimal bigDecimal = new BigDecimal("14");
		test.changeBigDecimal(bigDecimal);
		System.out.println(bigDecimal);

		BeanSource beanSource = new BeanSource();
		test.changeBean(beanSource);
		beanSource.setS3(22);
		System.out.println(beanSource);

		// 使用BeanUtils进行Bean的属性拷贝
		BeanTarget beanTarget = new BeanTarget();
		BeanTarget beanTarget2 = new BeanTarget();
		// 拷贝时是根据get，set的方法名进行拷贝的
		beanTarget2.setSource1("0");
		beanTarget2.setTarget2("0");
		beanSource.setBean(beanTarget2);
		BeanUtils.copyProperties(beanSource, beanTarget);
		System.out.println(beanTarget);
		System.out.println(beanSource);

		// Bean的拷贝为引用拷贝
		beanTarget.getBean().setSource1("1");
		System.out.println(beanTarget);
		System.out.println(beanSource);

		// 递归定义,调用toString方法时造成内存溢出
		// beanTarget.setBean(beanTarget);
		// System.out.println(beanTarget);

		// 深拷贝
		BeanTarget beanTarget3 = (BeanTarget) test.deepClone(beanTarget);
		beanTarget3.getBean().setSource1("3");
		System.out.println(beanTarget);
		System.out.println(beanTarget3);
	}
}
