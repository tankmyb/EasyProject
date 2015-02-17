package com.xml.xstream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class XStreamTest {
    
    private XStream xstream = null;
    private ObjectOutputStream  out = null;
    private ObjectInputStream in = null;
    
    private Student bean = null;
    
    /**
     * <b>function:</b>初始化资源准备
     * @author hoojo
     * @createDate Nov 27, 2010 12:16:28 PM
     */
    @Before
    public void init() {
        try {
            xstream = new XStream();
            //xstream = new XStream(new DomDriver()); // 需要xpp3 jar
        } catch (Exception e) {
            e.printStackTrace();
        }
        bean = new Student();
        bean.setAddress("china");
        bean.setEmail("jack@email.com");
        bean.setId(1);
        bean.setName("jack");
    }
    
    /**
     * <b>function:</b>释放对象资源
     * @author hoojo
     * @createDate Nov 27, 2010 12:16:38 PM
     */
    @After
    public void destory() {
        xstream = null;
        bean = null;
        try {
            if (out != null) {
                out.flush();
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.gc();
    }
    
    public final void fail(String string) {
        System.out.println(string);
    }
    
    public final void failRed(String string) {
        System.err.println(string);
    }
    @Test
    public void writeBean2XML() {
        try {
            fail("------------Bean->XML------------");
            fail(xstream.toXML(bean));
            fail("重命名后的XML");
            //类重命名
            //xstream.alias("account", Student.class);
            //xstream.alias("生日", Birthday.class);
            //xstream.aliasField("生日", Student.class, "birthday");
            //xstream.aliasField("生日", Birthday.class, "birthday");
            //fail(xstream.toXML(bean));
            //属性重命名
            xstream.aliasField("邮件", Student.class, "email");
            //包重命名
            xstream.alias("aa", Student.class);
            fail(xstream.toXML(bean));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}