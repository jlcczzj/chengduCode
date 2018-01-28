package com.faw_qm.jfpublish.receive;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Properties;

import com.faw_qm.integration.model.Request;
import com.faw_qm.integration.model.object.InteMultipartInputStream;
import com.faw_qm.integration.model.object.InteRequest;
import com.faw_qm.integration.util.InteException;
import com.faw_qm.integration.util.InteObjectSAXReader;
import com.faw_qm.integration.util.QMProperties;

public class RequestIn {
	private static String XML_PATH="C:\\PhosphorPDM\\scripts\\xml\\";
	/*static {
		try {
			XML_PATH = QMProperties.getLocalProperties().getProperty(
					"publish.xml.path");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	public static InteRequest readRequest(String fileName) {
		FileInputStream in = null;
		try {
			in = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] requestBytes = null;
		try {
			requestBytes = new byte[in.available()];
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			in.read(requestBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataInputStream dataIn = new DataInputStream(new ByteArrayInputStream(
				requestBytes));
		InteObjectSAXReader xmlReader = new InteObjectSAXReader();
		xmlReader.setInputDocument(((InputStream) (dataIn)));
		try {
			InteRequest request = (InteRequest) xmlReader.getInteObject();
			Request req = new Request(request);
		  //CCBegin by liunan 2008-09-03
		  //为输出语句添加开关。
		  if (PublishHelper.VERBOSE)
		  {
		  	System.out.println("=====>Name: " + request.getName());
		  	System.out.println("=====>Type: " + request.getType());
		  	System.out.println("=====>Meta: "
					+ request.getMeta("info-secret").getDatum().getNodeValue());
		  	System.out.println("====================>VDB:");
	  	}
		  //CCEnd by liunan 2008-09-03
			req.getScript().getVdb().toXML(new PrintWriter(System.out, true));
		  //CCBegin by liunan 2008-09-03
		  //为输出语句添加开关。
		  if (PublishHelper.VERBOSE)
		  //CCEnd by liunan 2008-09-03
			System.out.println("====================>CONTEXT:");
			req.getScript().getContext().toXML(
					new PrintWriter(System.out, true));
			// req.getScript().getGroupIn().toXML(new
			// PrintWriter(System.out,true),true);
		} catch (InteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static InteRequest readRequest2(String fileName) {
		try {
			FileInputStream in = new FileInputStream(XML_PATH + fileName);
			InteMultipartInputStream inteIn = new InteMultipartInputStream(in);
			InteRequest request = inteIn.readRequest();
			Request req = new Request(request);
			// System.out.println("=====>Name: " + request.getName());
			// System.out.println("=====>Type: " + request.getType());
			// System.out.println("=====>Meta: "
			// + request.getMeta("info-secret").getDatum().getNodeValue());
			// System.out.println("====================>VDB:");
			req.getScript().getVdb().toXML(new PrintWriter(System.out, true));
			FileOutputStream out = new FileOutputStream(XML_PATH + fileName
					+ ".info");
			PrintWriter writer = new PrintWriter(out, true);
			req.getScript().getVdb().toXML(writer);
			if (in != null) {
				in.close();
			}
			if (inteIn != null) {
				inteIn.close();
			}
			if (out != null) {
				out.close();
			}
			if (writer != null) {
				writer.close();
			}
			// System.out.println("====================>CONTEXT:");
			// req.getScript().getContext().toXML(
			// new PrintWriter(System.out, true));

			// req.getScript().getGroupIn().toXML(new
			// PrintWriter(System.out,true),true);
			return request;
		} catch (InteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String strDecoding(String source, String charset) {
		if (source == null) {
			return null;
		}
		if (source.equals("")) {
			return source;
		}
		if (charset == null || charset.equals("")) {
			charset = "ISO8859-1";

		}
		try {
			String temp_p = source;
			byte[] temp_t = temp_p.getBytes(charset);
			String temp = new String(temp_t);
			return temp;
		} catch (Exception e) {

			return source;
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readRequest2("CAGP1203P7K2L11T1A_Z00013.xml");
		/*Properties old = new Properties();
		try {
			old.load(new FileInputStream(
							new File(
									"C:\\phosphorpdm\\config\\integration\\mappingreceive.properties")));
			Enumeration e = old.keys();
			if (PublishHelper.VERBOSE)
				System.out.println("======>old pro amount: " + old.size());
			FileOutputStream keyout = new FileOutputStream("C:\\PhosphorPDM\\scripts\\xml\\partkey.csv");
			FileOutputStream valueout = new FileOutputStream("C:\\PhosphorPDM\\scripts\\xml\\partvalue.csv");
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				if(key.startsWith("doc.location"))
				{
					PrintWriter keywriter = new PrintWriter(keyout, true);
					PrintWriter valuewriter = new PrintWriter(valueout, true);
				    keywriter.println(strDecoding(key.substring(13), "ISO8859-1"));
				    valuewriter.println(strDecoding(old.getProperty(key).substring(6),"ISO8859-1"));
				}
			}
			keyout.close();
			valueout.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
