package com.faw_qm.jfpublish.receive;

/**
 * <p>Title: </p>
 * <p>Description: ����������չ</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.faw_qm.content.exception.ContentException;
import com.faw_qm.content.util.StreamUtil;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.persist.util.PersistUtil;
import com.faw_qm.util.JNDIUtil;

public class StreamUtilExt
    extends StreamUtil {

  public StreamUtilExt() {
  }

  /**
   * ��ȡ����
   *
   * @throws SQLException
   * @throws QMException
   * @return Connection
   */
  protected static Connection getConnection() throws SQLException,
      QMException {
    return PersistUtil.getConnection();
  }

  /**
   * ��StreamData������д������ <BR>
   * �����֪�������ݳ��ȣ���ָ������Ϊ0L
   *
   * @param streamID
   *            String StreamData����ID
   * @param is
   *            InputStream ������
   * @param len
   *            long �����ݳ���
   * @throws QMException
   * @throws IOException
   * @return long
   */
  public static long writeData(String streamID, InputStream is, long len) throws
      QMException, IOException {
    Connection conn = null;
    PreparedStatement pm = null;
    long length = 0L;
    int count = 0;
    try {
      // ��û��ָ�����ֽ���ʱ
      if (len == 0) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte buf[] = new byte[8192];
        int i = 0;
        do {
          i = is.read(buf);
          if (i == -1) {
            break;
          }
          bos.write(buf, 0, i);
        }
        while (true);
        bos.flush();
        byte[] data = bos.toByteArray();
        length = data.length;
        ByteArrayInputStream inStream = new ByteArrayInputStream(data);
        conn = getConnection();
        pm = conn.prepareStatement(getUpdateSQL());
        // ����ֽ����ر������ܶ���Ϣ
        pm.setBinaryStream(1, inStream, (int) length);
        pm.setString(2, streamID);
        count = pm.executeUpdate();
      }
      else {
        conn = getConnection();
        pm = conn.prepareStatement(getUpdateSQL());
        // ����ֽ����ر������ܶ���Ϣ
        pm.setBinaryStream(1, is, (int) len);
        pm.setString(2, streamID);
        count = pm.executeUpdate();

      }
    }
    catch (SQLException ex) {
      throw new ContentException(ex, "6", null);
    }
    finally {
      try {
        if (pm != null) {
          pm.close();
        }
        if (conn != null) {
          conn.close();
        }
      }
      catch (Exception ex) {
      }
    }
    return length;

  }

  /**
   * ��ȡ����StreamData���ݵ�SQL����
   *
   * @throws QMException
   * @throws ContentException
   * @return String
   */
  protected static String getUpdateSQL() throws QMException, ContentException {
    StringBuffer buffer = new StringBuffer();
    buffer.append("update ");
    String streamTableName = JNDIUtil.getTableName("StreamData");
    String columnName1 = JNDIUtil
        .getColumnName("StreamData", "dataContent");
    String columnName2 = JNDIUtil.getColumnName("StreamData", "bsoID");
    if (streamTableName == null || columnName1 == null
        || columnName2 == null) {
      throw new ContentException("14", null);
    }
    buffer.append(streamTableName);
    buffer.append(" set ");
    buffer.append(columnName1);
    buffer.append(" =?");
    buffer.append(" where ");
    buffer.append(columnName2);
    buffer.append(" =?");
    return buffer.toString();
  }

}
