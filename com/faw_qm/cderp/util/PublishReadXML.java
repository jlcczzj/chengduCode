
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
 *读取xml文件类。
 * @author 刘家坤
 * @version 1.0
 */ 
public class PublishReadXML
{
  
    /**
     * 
     * 发布文件存放在本地的文件夹名称。
     * 
     */
    private static String publishFathName = "";
    private static String publishBackFathName = "";
    /**
     * 缺省构造函数。
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
						 //获得第一级，根节点
						Element ele = document.getRootElement();
						List recordMessage = ele.elements();
						map = getRecordElement(recordMessage);
					 }
				 }else{
					 //System.out.println("没有xml文件");
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
	     * 获取标签内容
	     * @throws DocumentException 
	     * @throws FileNotFoundException 
	     */
		private  Vector getRecordElement (List listroot)
				throws Exception {
                Vector map = new Vector();
                //循环第二级
				for (int j = 0; j < listroot.size(); j++) {
					Element eleName = (Element) listroot.get(j);
					List listName = eleName.elements();
					System.out.println("listName.size()="+listName.size());
					//循环第三级
					for (int k = 0; k < listName.size(); k++) {
						Element eleRow = (Element) listName.get(j);
						List listRow = eleRow.elements();
						String[] array = new String[listRow.size()];
						////循环第四级
						System.out.println("listRow.size()="+listRow.size());
						for (int i = 0; i < listRow.size(); i++) {
							Element el = (Element) listRow.get(i);
							//获得标签内容
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
     * 获取当前路径下文件列表。
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

	//获取固定路径下文件列表
	              for (int i=0; i<list.length; i++) {
	
	                   if (list[i].isFile()) {

	                	   Document document = reader.read(list[i]);// 读取XML文件
	                	   vec.add(document);
	                	   //将文件剪切到备份资料夹
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
             * 剪切方法：复制+删除  
           *   
            * @param destDir  
           *            同上  
            */  
          public static boolean cutGeneralFile(File srcPath ) {   
        	  publishBackFathName=(String) RemoteProperty.getProperty("changeBasebackPath");
              if (!copyGeneralFile(srcPath)) {   
                  System.out.println("复制失败导致剪切失败!");   
                 //return false;   
              }   
               if (!deleteGeneralFile(srcPath)) {   
                  System.out.println("删除源文件(文件夹)失败导致剪切失败!");   
                  return false;   
                }   
       
              //System.out.println("剪切成功!");   
               return true;   
            }   
         
            
              /**  
               * 复制文件  
               *   
               * @param srcPath  
              *            源文件绝对路径  
               * @param destDir  
               *            目标文件所在目录  
               * @return boolean  
               */  
             private static boolean copyGeneralFile(File srcFile) {   
                  boolean flag = false;   
          
                //  File srcFile = new File(srcPath);   
                  if (!srcFile.exists()) { // 源文件不存在   
                     System.out.println("源文件不存在");   
                      return false;   
                  }   
                  // 获取待复制文件的文件名   
                  String fileName = srcFile.getName();   
                 String destPath = publishBackFathName+ "/" + fileName;   
                 
                 String srcPath = publishFathName+ "/" + fileName;  
            
                  File destFileDir = new File(publishBackFathName);   
                           
                     if (destFileDir.exists()){
                    	 
                     }else{
                    	  destFileDir.mkdirs();
                     }
                  File destFile = new File(destPath);   
                  if (destFile.exists() && destFile.isFile()) { // 该路径下已经有一个同名文件   
                      System.out.println("目标目录下已有同名文件!");   
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
                      System.out.println("复制文件成功!");   
                  }   
            
                  return flag;   
              }   
             /**  
                  * 删除文件或文件夹  
                  *   
                  * @param path  
                  *            待删除的文件的绝对路径  
                  * @return boolean  
                  */  
                 public static boolean deleteGeneralFile(File file) {   
                     boolean flag = false;   
                 
                     if (!file.exists()) { // 文件不存在   
                         System.out.println("要删除的文件不存在！");   
                     }   

                         flag =  file.delete();  

                     if (flag) {   
                         System.out.println("删除文件或文件夹成功!");   
                     }   
               
                     return flag;   
                 }   
              
       

}
