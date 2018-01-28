
package com.faw_qm.cderp.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;

/**
 *��ȡxml�ļ��ࡣ
 * @author ������
 * @version 1.0
 */ 
public class PublishReadXML
{
  
    /**
     * 
     * �����ļ�����ڱ��ص��ļ������ơ�
     * 
     */
    private static String publishFathName = "";
    private static String publishBackFathName = "";
    /**
     * ȱʡ���캯����
     */
    public PublishReadXML()
    {
        super();
    }
	  public Vector  readXml(){
		
			  Vector docList = new Vector();
			  Vector map = new Vector();
			  try {
				 docList =  getDirectoryList();
				 if(docList.size()>0){
					 for(int i=0;i<docList.size();i++){
						 Document document = (Document)docList.elementAt(i);
						 //��õ�һ�������ڵ�
						Element ele = document.getRootElement();
						List recordMessage = ele.elements();
						map = getRecordElement(recordMessage);
					 }
				 }else{
					 //System.out.println("û��xml�ļ�");
				 }
			} catch (IOException e) {
				e.printStackTrace();
			}
			  catch(Exception cc){
					cc.printStackTrace();
					return null;
				}
			  return map;

	  }
	  /**
	     * ��ȡ��ǩ����
	     * @throws DocumentException 
	     * @throws FileNotFoundException 
	     */
		private  Vector getRecordElement (List listroot)
				throws Exception {
                Vector map = new Vector();
                //ѭ���ڶ���
				for (int j = 0; j < listroot.size(); j++) {
					Element eleName = (Element) listroot.get(j);
					List listName = eleName.elements();
					System.out.println("listName.size()="+listName.size());
					//ѭ��������
					for (int k = 0; k < listName.size(); k++) {
						Element eleRow = (Element) listName.get(j);
						List listRow = eleRow.elements();
						String[] array = new String[listRow.size()];
						////ѭ�����ļ�
						System.out.println("listRow.size()="+listRow.size());
						for (int i = 0; i < listRow.size(); i++) {
							Element el = (Element) listRow.get(i);
							//��ñ�ǩ����
							String arr = el.getTextTrim();
							System.out.println("arr="+arr);
							//map.put(String.valueOf(i), arr);
							array[i]=arr;
						}
						map.add(array);
						System.out.println("map="+map);
					}
					
				}
			
			return map;
		}
		
    /**
     * ��ȡ��ǰ·�����ļ��б�
     * @throws DocumentException 
     * @throws FileNotFoundException 
     */
        public Vector getDirectoryList() throws IOException   {
        	  FileInputStream is = null;
        	  Vector vec = new Vector();
        	//  File file_in = null;
        	  try
              {
	        	  publishFathName=(String) RemoteProperty.getProperty("changeBasePath");
	        	  
	        	  
	        	  //System.out.println("publishFathName="+publishFathName);
	              File file_in = new File(publishFathName);
	              File[] list = file_in.listFiles();
	  
	              SAXReader reader = new SAXReader();

	//��ȡ�̶�·�����ļ��б�
	              for (int i=0; i<list.length; i++) {
	
	                   if (list[i].isFile()) {

	                	   Document document = reader.read(list[i]);// ��ȡXML�ļ�
	                	   vec.add(document);
	                	   //���ļ����е��������ϼ�
	                	   cutGeneralFile(list[i]);
	                   }
	
	              }
              }
              catch (DocumentException ex)
              {
            	  try {
					throw new QMException(ex);
				} catch (QMException e) {
					e.printStackTrace();
				}
              }
              finally
              {
                  if(is != null)
                  {
                      try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
                  }
                 
              }
              
              return vec;
      }
     
        /**  
             * ���з���������+ɾ��  
           *   
            * @param destDir  
           *            ͬ��  
            */  
          public static boolean cutGeneralFile(File srcPath ) {   
        	  publishBackFathName=(String) RemoteProperty.getProperty("changeBasebackPath");
              if (!copyGeneralFile(srcPath)) {   
                  System.out.println("����ʧ�ܵ��¼���ʧ��!");   
                 //return false;   
              }   
               if (!deleteGeneralFile(srcPath)) {   
                  System.out.println("ɾ��Դ�ļ�(�ļ���)ʧ�ܵ��¼���ʧ��!");   
                  return false;   
                }   
       
              //System.out.println("���гɹ�!");   
               return true;   
            }   
         
            
              /**  
               * �����ļ�  
               *   
               * @param srcPath  
              *            Դ�ļ�����·��  
               * @param destDir  
               *            Ŀ���ļ�����Ŀ¼  
               * @return boolean  
               */  
             private static boolean copyGeneralFile(File srcFile) {   
                  boolean flag = false;   
          
                //  File srcFile = new File(srcPath);   
                  if (!srcFile.exists()) { // Դ�ļ�������   
                     System.out.println("Դ�ļ�������");   
                      return false;   
                  }   
                  // ��ȡ�������ļ����ļ���   
                  String fileName = srcFile.getName();   
                 String destPath = publishBackFathName+ "/" + fileName;   
                 
                 String srcPath = publishFathName+ "/" + fileName;  
            
                  File destFileDir = new File(publishBackFathName);   
                           
                     if (destFileDir.exists()){
                    	 
                     }else{
                    	  destFileDir.mkdirs();
                     }
                  File destFile = new File(destPath);   
                  if (destFile.exists() && destFile.isFile()) { // ��·�����Ѿ���һ��ͬ���ļ�   
                      System.out.println("Ŀ��Ŀ¼������ͬ���ļ�!");   
                      return false;   
                  }   
                  try {   
                      FileInputStream fis = new FileInputStream(srcPath);   
                      FileOutputStream fos = new FileOutputStream(destFile);   
                      byte[] buf = new byte[1024];   
                      int c;   
                      while ((c = fis.read(buf)) != -1) {   
                          fos.write(buf, 0, c);   
                      }   
                      fis.close();   
                      fos.close();   
            
                      flag = true;   
                  } catch (IOException e) {   
                      //   
                  }   
            
                  if (flag) {   
                      System.out.println("�����ļ��ɹ�!");   
                  }   
            
                  return flag;   
              }   
             /**  
                  * ɾ���ļ����ļ���  
                  *   
                  * @param path  
                  *            ��ɾ�����ļ��ľ���·��  
                  * @return boolean  
                  */  
                 public static boolean deleteGeneralFile(File file) {   
                     boolean flag = false;   
                 
                     if (!file.exists()) { // �ļ�������   
                         System.out.println("Ҫɾ�����ļ������ڣ�");   
                     }   

                         flag =  file.delete();  

                     if (flag) {   
                         System.out.println("ɾ���ļ����ļ��гɹ�!");   
                     }   
               
                     return flag;   
                 }   
              
       

}
