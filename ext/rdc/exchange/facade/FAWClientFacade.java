package ext.rdc.exchange.facade;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ext.rdc.exchange.component.encrypt.EncryptHelper;
import ext.rdc.exchange.util.ClientGetContent;

public class FAWClientFacade {

    //private static final String HOSTNAME = "fawpdm-test.fagcrdc.com";
    private static final String HOSTNAME = "fawpdm.fagcrdc.com";

    private static final String ROLE = "SECONDARY";

    /**
     * 下载为文件
     * 
     * @param oid
     * @param cioids
     * @param tempDir
     * @return
     * @throws IOException
     */
    public static String getContentFile(String contentUrl, String tempDir) throws IOException {
        if (contentUrl == null || contentUrl.length() == 0 || contentUrl.equals("null")) {
            return null;
        }
        String username = EncryptHelper.getEncryptProperty(HOSTNAME + ".username");
        String password = EncryptHelper.getEncryptProperty(HOSTNAME + ".password");

        ClientGetContent getter = new ClientGetContent(HOSTNAME, username, password);

        String strs[] = contentUrl.split("#");
        String oid = "OR:wt.part.WTPart:" + strs[0];
        String cioids = "wt.content.ApplicationData:" + strs[1];
        return getter.getContentFile(oid, cioids, ROLE, tempDir);

    }

    /**
     * 获取下载内容的文件名
     * 
     * @param oid
     * @param cioids
     * @return
     * @throws IOException
     */
    public static String getContentFileName(String contentUrl) throws IOException {
        if (contentUrl == null || contentUrl.length() == 0 || contentUrl.equals("null")) {
            return null;
        }

        String username = EncryptHelper.getEncryptProperty(HOSTNAME + ".username");
        String password = EncryptHelper.getEncryptProperty(HOSTNAME + ".password");

        ClientGetContent getter = new ClientGetContent(HOSTNAME, username, password);

        String strs[] = contentUrl.split("#");
        String oid = "OR:wt.part.WTPart:" + strs[0];
        String cioids = "wt.content.ApplicationData:" + strs[1];

        return getter.getContentFileName(oid, cioids, ROLE);

    }

    /**
     * 获取下载数据流
     * 
     * @param oid
     * @param cioids
     * @param outStream
     * @throws IOException
     */
    public static void saveContent(String contentUrl, OutputStream outStream) throws IOException {
        if (contentUrl == null || contentUrl.length() == 0 || contentUrl.equals("null")) {
            return;
        }

        String username = EncryptHelper.getEncryptProperty(HOSTNAME + ".username");
        String password = EncryptHelper.getEncryptProperty(HOSTNAME + ".password");

        ClientGetContent getter = new ClientGetContent(HOSTNAME, username, password);

        String strs[] = contentUrl.split("#");
        String oid = "OR:wt.part.WTPart:" + strs[0];
        String cioids = "wt.content.ApplicationData:" + strs[1];

        getter.saveContent(oid, cioids, ROLE, outStream);
    }

    /**
     * 
     * 查询跨单位的用户
     * Map<String, String>存放用户信息
     * 
     * userOid - 用户oid
     * userName - 用户名
     * userFullName - 用户全名
     * userDept - 用户科室（技术中心）
     * 
     * @param matchName
     * @param matchFullName
     * @return
     * @throws IOException
     */
    public static List queryRdcUser(String matchName, String matchFullName)
            throws IOException {

        System.out.println("matchName=" + matchName);
        System.out.println("matchFullName=" + matchFullName);

        if (matchName == null) {
            matchName = "";
        }
        if (matchFullName == null) {
            matchFullName = "";
        }

        if (matchName.length() == 0 && matchFullName.length() == 0) {
            return null;
        }

        String username = EncryptHelper.getEncryptProperty(HOSTNAME + ".username");
        String password = EncryptHelper.getEncryptProperty(HOSTNAME + ".password");

        ClientGetContent getter = new ClientGetContent(HOSTNAME, username, password);

        String uri = "http://" + HOSTNAME
                + "/Windchill/netmarkets/jsp/ext/rdc/exchange/process/cooperate/queryRdcUser.jsp?matchName="
                + matchName + "&matchFullName=" + matchFullName;

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        getter.downloadContent(uri, output);

        String jsonStr = null;
        try {
            jsonStr = output.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("jsonStr=" + jsonStr);
        if (jsonStr == null || jsonStr.length() == 0) {
            return null;
        }

        List users = new ArrayList();
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObj.getJSONArray("users");

            for (int i = 0; i < jsonArray.length(); i++) {
                Map m = new HashMap();
                JSONObject user = (JSONObject) jsonArray.get(i);

                m.put("userOid", user.get("userOid"));
                m.put("userName", user.get("userName"));
                m.put("userFullName", user.get("userFullName"));
                m.put("userDept", user.get("userDept"));

                users.add(m);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return users;
    }
    
    public static List queryRdcUsersByPartNumber(String partNumber)
            throws IOException {

        System.out.println("partNumber=" + partNumber);

        if (partNumber == null) {
        	partNumber = "";
        }

        String username = EncryptHelper.getEncryptProperty(HOSTNAME + ".username");
        String password = EncryptHelper.getEncryptProperty(HOSTNAME + ".password"); 
        System.out.println("username=="+username);
        System.out.println("password=="+password);


        ClientGetContent getter = new ClientGetContent(HOSTNAME, username, password);

        String uri = "http://" + HOSTNAME
                + "/Windchill/netmarkets/jsp/ext/rdc/exchange/process/cooperate/queryRdcUserForChangeIssue.jsp?partNumber="
                + partNumber;

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        getter.downloadContent(uri, output);

        String jsonStr = null;
        try {
            jsonStr = output.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("jsonStr=" + jsonStr);
        if (jsonStr == null || jsonStr.length() == 0) {
            return null;
        }

        List users = new ArrayList();
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObj.getJSONArray("users");

            for (int i = 0; i < jsonArray.length(); i++) {
                Map m = new HashMap();
                JSONObject user = (JSONObject) jsonArray.get(i);

                m.put("userOid", user.get("userOid"));
                m.put("userName", user.get("userName"));
                m.put("userFullName", user.get("userFullName"));
                m.put("userDept", user.get("userDept"));

                users.add(m);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return users;
    }
}
