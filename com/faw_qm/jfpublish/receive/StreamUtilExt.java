package com.faw_qm.jfpublish.receive;

/**
 * <p>Title: </p>
 * <p>Description: 流处理类扩展</p>
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
   * 获取连接
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
   * 向StreamData对象中写流数据 <BR>
   * 如果不知道流数据长度，则指定长度为0L
   *
   * @param streamID
   *            String StreamData对象ID
   * @param is
   *            InputStream 流数据
   * @param len
   *            long 流数据长度
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
      // 当没有指定流字节数时
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
        // 如果字节流特别大，则可能丢信息
        pm.setBinaryStream(1, inStream, (int) length);
        pm.setString(2, streamID);
        count = pm.executeUpdate();
      }
      else {
        conn = getConnection();
        pm = conn.prepareStatement(getUpdateSQL());
        // 如果字节流特别大，则可能丢信息
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
   * 获取更新StreamData数据的SQL语言
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
