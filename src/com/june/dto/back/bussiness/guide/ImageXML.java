/**
 * JUNE软件有限公司<br>
 * com.june.dto.back.bussiness.guide.ImageXML.java
 * 日期:2016年7月4日
 */
package com.june.dto.back.bussiness.guide;

import java.io.StringReader;
import java.util.TreeSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 一个操作指南图文描述步骤XML文件类 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年7月4日 下午9:07:33
 */
@XmlRootElement(name = "conf")
public class ImageXML {
	private TreeSet<Step> step;// 步骤集合
	private int size;// 共有几步
	private String path;// 存放FTP的路径

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getSize() {
		size = step == null ? 0 : step.size();
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public TreeSet<Step> getStep() {
		return step;
	}

	public void setStep(TreeSet<Step> step) {
		this.step = step;
	}

	@Override
	public String toString() {
		return "[" + this.path + "," + this.size + "," + this.step.toString() + "]";
	}

	/**
	 * 
	 */
	public ImageXML() {
	}

	/**
	 * @param args
	 * @date 2016年7月4日 下午9:07:33
	 * @writer wjw.happy.love@163.com
	 */
	public static void main(String[] args) {
		//Step step1 = new Step(3, "3.jpg", "第3步描述");
		//step1.setPath("aaa");
		//Step step2 = new Step(2, "2.jpg", "第2步描述");
		//step1.setPath("bbb");
		Step step3 = new Step(1, "1.jpg", "第1步描述");
		//step1.setPath("ccc");
		TreeSet<Step> list = new TreeSet<>();
		//list.add(step1);
		//list.add(step2);
		list.add(step3);
		ImageXML xml = new ImageXML();
		xml.setStep(list);
		xml.setPath("/车型A1/基本操作/文件");
		try {
			JAXBContext context = JAXBContext.newInstance(ImageXML.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(xml, System.out);
			String con = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><conf><path>/车型A1/基本操作/文件</path><size>2</size><step id=\"6\"><description>第一步描述</description><image>1.jpg</image></step></conf>";
			Unmarshaller unmarshaller = context.createUnmarshaller();
			xml = (ImageXML) unmarshaller.unmarshal(new StringReader(con));
			System.out.println();
			System.out.println(xml.step);
			System.err.println(xml);
			//JSONObject jsonObject = JSONObject.fromObject(xml);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
