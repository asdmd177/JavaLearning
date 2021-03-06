package com.fja.parse;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

/**
 * 解析的方式：
 * DOM解析：
 * 	1. JAXP	
 * 	2. JDOM
 * 	3. DOM4J (三大框架默认用DOM4J读取xml配置)
 * 	DOM解析原理：
 * 		将xml文档加载到内存中，然后在内存中构建一颗document树，把各个部分封装成对象（Node），
 * 		通过Document对象访问这些结点，从而操作xml文档。 由于要加载整个xml文档，因此解析速度较慢
 * 		节点分为标签节点(Element)，属性节点(Attribute)，文本节点(Text)。
 * 
 * ------|Node
 * ---------|Text			文本节点
 * ---------|Comment		注释节点
 * ---------|Element		标签节点
 * ---------|Document		文档节点
 * ---------|Attribute		属性节点
 */
public class Parse {
	/**
	 * 基本使用 
	 */
	@Test
	public void baseProcess(){
		//创建一个xml解析器对象。
		SAXReader reader = new SAXReader();
		
		//read()可以传入一个File对象，返回一个Document对象
		try {
			Document doc = reader.read(new File("./src/com/fja/parse/xml/students.xml"));
			
			//通过nodeIterator()得到当前节点下所有的子节点对象。【注意】只有标签节点才能调用nodeIterator()
			Iterator<Node> it = doc.nodeIterator();
			
			//dom4j结合了java中的集合
			while(it.hasNext()){
				
				Node node = it.next();
				
				String name = node.getName();
				
				System.out.println(name);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  利用递归，读取根节点下面所有的子标签
	 */
	@Test
	public void traveral(){
		SAXReader reader = new SAXReader();
		
		try {
			Document doc = reader.read(new File("./src/com/fja/parse/xml/students.xml"));
			
			//获得根标签，方法返回的是Element对象，不是属性节点也不是文本节点
			Element rootElement = doc.getRootElement();
			
			getChildNodes(rootElement);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public void getChildNodes(Element elem){
		
		System.out.println(elem.getName());
		
		Iterator<Node> it = elem.nodeIterator();
		
		while(it.hasNext()){
			
			Node node = it.next();
			//是标签节点，开始递归
			if(node instanceof Element){
				
				getChildNodes((Element)node);
			}
		}
	}
	
	
	/**
	 * 获取指定标签节点 
	 */
	@Test
	public void getTag(){
		SAXReader reader = new SAXReader();
		
		try {
			Document doc = reader.read(new File("./src/com/fja/parse/xml/students.xml"));
			
			Element rootElement = doc.getRootElement();
			
			//得到当前标签下制定名称的第一个子标签,【注意】有同名标签存在，返回第一个
			Element stu = rootElement.element("student");
			System.out.println(stu.getName());
			
			//得到当前标签下指定名称的所有子标签，【注意】是所有，返回多个。
			Iterator<Element> it = rootElement.elementIterator("student");
			while(it.hasNext()){
				Element elem = it.next();
				System.out.println(elem.getName());
			}
			
			//当前标签下的所有子标签
			List<Element> list = rootElement.elements();
			
			for(Element e : list){
				System.out.println(e.getName());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取属性节点 
	 */
	@Test
	public void getAttribute(){
		SAXReader reader = new SAXReader();
		
		try {
			Document doc = reader.read(new File("./src/com/fja/parse/xml/students.xml"));
			
			Element rootElement = doc.getRootElement();
			
			//想要拿到属性节点，需要先获取属性所在的标签节点
			Element stu = rootElement.element("student");
			
			//拿到指定名称的属性节点对象
			Attribute attribute = stu.attribute("id");
			
			System.out.println(attribute);//org.dom4j.tree.DefaultAttribute@58d25a40 [Attribute: name id value "001"]
			//属性值
			String value = attribute.getValue();
			//属性名
			String name = attribute.getName();
			
			System.out.println(name+" = "+value);//001
			
			//直接拿到指定名称的属性节点的值
			String attributeValue = stu.attributeValue("id");
			
			System.out.println(attributeValue);//001
			
			
			//获得标签下所有的属性，两种方法:
			//方法一：
			List<Attribute> list = stu.attributes();
			
			for(Attribute attr : list){
				
				System.out.println(attr.getName()+" = "+attr.getValue());
			}
			
			//方法二：
			Iterator<Attribute> attributeIterator = stu.attributeIterator();
			
			while(attributeIterator.hasNext()){
				
				Attribute attr = attributeIterator.next();
				
				System.out.println(attr.getName()+" = "+attr.getValue());
			}
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 获取文本节点
	 */
	@Test
	public void getText(){
		SAXReader reader = new SAXReader();
		
		try {
			Document doc = reader.read(new File("./src/com/fja/parse/xml/students.xml"));
			
			//获取根节点
			Element rootElement = doc.getRootElement();
			
			//和属性节点一样，要拿到文本节点首先要拿到标签节点
			Element stu = rootElement.element("student");
			//方法一：
			Element nameElement = stu.element("name");
			
			//获得当前标签节点的文本内容
			String text = nameElement.getText();
			
			System.out.println("方法一： "+text);
			
			//方法二：得到指定子标签名的文本内容
			String elementText = stu.elementText("name");
			
			System.out.println("方法二： "+elementText);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
