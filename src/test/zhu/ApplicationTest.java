package test.zhu;

import com.zhu.bean.TestBeanA;
import com.zhu.bean.TestBeanB;
import com.zhu.main.BeanFactory;
import com.zhu.main.impl.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * 2019/5/10
 * test.zhu
 */
public class ApplicationTest {
	@Test
	public void t1(){
		BeanFactory bf = new ClassPathXmlApplicationContext("/ringContext.xml");
		TestBeanB testBeanB = (TestBeanB) bf.getBean("TestBeanB");
		TestBeanA testBeanA = (TestBeanA) bf.getBean("TestBeanA");
		System.out.println(testBeanA.getName());
		System.out.println(bf.getBean("TestBeanA").toString());
		System.out.println(testBeanB.getName());
		System.out.println(bf.getBean("TestBeanB").toString());
	}
}
