package com.faw_qm.jfpublish.receive;

/**
 * <p>Title: 数据发布--日志类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author ShangHaiFeng
 * @version 1.0
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.integration.util.InteException;
import com.faw_qm.integration.util.QMProperties;

public class PublishPartsLog {

	//private static boolean LOG = true;

	private static String separator = "#:#"; // 缺省的分隔符

	private static SimpleDateFormat logDateFormat = new SimpleDateFormat(
			"yyyy/MM/dd-HH:mm:ss.SSS");

	//private static SimpleDateFormat logDate = new SimpleDateFormat("yyyy-MM-dd");

	private static String logFile = null;

	// 用于用户查看的日志文件目录
	private String userLogFile = null;

	private static PrintWriter out=null;

	private PrintWriter userout = null;

	private static String userLogBase = null;
	static {
		try {
			QMProperties qmProps = QMProperties.getLocalProperties();
			//LOG = qmProps.getProperty("com.faw_qm.PublishParts.log", true);
			logFile = qmProps.getProperty("com.faw_qm.PublishParts.logFile",
					(String) null)
					+ (new java.sql.Date((new Long(System.currentTimeMillis()))
							.longValue())) + ".log";
            userLogBase = qmProps.getProperty(
					"com.faw_qm.publishParts.userLogFile", (String) null);

			// System.out.println(logFile);
			// 日志设置
			if (logFile != null) {
				try {
					out = new PrintWriter(new FileWriter(logFile, true), true);
				} catch (IOException ioEx) {
					System.err.println("Unable to open " + logFile
							+ ", writing to System.out");
					out = new PrintWriter(System.out, true);

				}
			}

		} catch (IOException ioEx) {
			//LOG = true;
            ioEx.printStackTrace();
		}
	}

	public PublishPartsLog(String userLogFileName) {
        try {
            init(userLogFileName);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	public static void log(Object msg) {
		if (out == null) {
			// 日志设置
			if (logFile != null) {
				try {
					out = new PrintWriter(new FileWriter(logFile, true), true);
				} catch (IOException ioEx) {
					System.err.println("Unable to open " + logFile
							+ ", writing to System.out");
					out = new PrintWriter(System.out, true);
				}
			}
		}
		if (msg instanceof Throwable) {
			out.println(getFormattedMessage((Throwable) msg));
		} else {
			out.println(getFormattedMessage(msg));
		}
	}

	public void init(String logName) throws IOException,
			NumberFormatException {
		if (logName == null || logName.length() == 0) {
			userLogFile = userLogBase + "changepublish0.log";
			File file = new File(userLogFile);
			if (file.exists() && file.isFile()) {
		  	//CCBegin by liunan 2008-09-03
		  	//为输出语句添加开关。
		  	if (PublishHelper.VERBOSE)
		  	//CCEnd by liunan 2008-09-03
				System.out.println("*******3* the user log file is :"
						+ userLogFile);
				int m = 0;
				int n = 0;
				File file1 = new File(userLogBase);
				File[] files = file1.listFiles();
				for (int i = 0; i < files.length; i++) {
					int j = files[i].getPath().indexOf("changepublish");
					int k = files[i].getPath().lastIndexOf(".");
					if (j != -1) {
						String s = files[i].getPath().substring(j + 13, k);
						m = Integer.valueOf(s).intValue();
						if (m > n) {
							n = m;
							// path=files[i].getPath();
						}
					}

				}
				userLogFile = userLogBase + "changepublish" + (n + 1) + ".log";

			}

		} else {
			userLogFile = userLogBase + "userpublish" + logName + "0.log";
			File file = new File(userLogFile);
			if (file.exists() && file.isFile()) {
		  	//CCBegin by liunan 2008-09-03
		  	//为输出语句添加开关。
		  	if (PublishHelper.VERBOSE)
		  	//CCEnd by liunan 2008-09-03
				System.out.println("*******1* the user log file is :"
						+ userLogFile);
				int m = 0;
				int n = 0;
				File file1 = new File(userLogBase);
				File[] files = file1.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].getName().endsWith(".log")) {
						int j = files[i].getPath().indexOf(logName);
						if (j != -1) {
							String s = files[i].getPath().substring(
									j + logName.length(),
									j + logName.length() + 1);
							m = Integer.valueOf(s).intValue();
							if (m > n) {
								n = m;
								// path=files[i].getPath();
							}
						}
					}
				}
				userLogFile = userLogBase + "userpublish" + logName + (n + 1)
						+ ".log";

			}
		}

		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("*******2* the user log file is :" + userLogFile);

		userout = new PrintWriter(new FileWriter(userLogFile, true), true);

	}

	public void userLog(Object msg) {
		if (userout == null) {
			// 日志设置
			if (userLogFile != null) {
				try {
					// System.out.println("userLogFile=" + userLogFile);
					userout = new PrintWriter(
							new FileWriter(userLogFile, true), true);

				} catch (IOException ioEx) {
		    	//CCBegin by liunan 2008-09-03
		    	//为输出语句添加开关。
	    		if (PublishHelper.VERBOSE)
		    	//CCEnd by liunan 2008-09-03
					System.err.println("Unable to open " + userLogFile
							+ ", writing to System.out");
					userout = new PrintWriter(System.out, true);
				}
			}
		}

		if (msg instanceof Throwable) {
			userout.println(getFormattedMessage((Throwable) msg));
		} else {
			userout.println(msg);

		}
		userout.flush();
	}

	public static void logError(Object msg) {
		if (out == null) {
			// 日志设置
			if (logFile != null) {
				try {
					out = new PrintWriter(new FileWriter(logFile, true), true);
				} catch (IOException ioEx) {
					System.err.println("Unable to open " + logFile
							+ ", writing to System.out");
					out = new PrintWriter(System.out, true);
				}
			}
		}

		if (msg instanceof Throwable) {
			out.println(getFormattedMessage((Throwable) msg));
		} else {
			out.println(getFormattedMessage(msg));
		}
	}

	public static void closeLog() {
		try {
			// 关闭日志
			out.close();
		} catch (Exception exception) {
		}
	}

	private static String getFormattedMessage(Object msg) {
		StringBuffer sb = new StringBuffer();
		sb.append(logDateFormat.format(new Date()));
		sb.append(separator);
		sb.append(msg);
		return sb.toString();
	}

	private static String getFormattedMessage(Throwable msg) {
		/*
		 * StringWriter sw = new StringWriter(); PrintWriter pw = new
		 * PrintWriter(sw); msg.printStackTrace(pw); pw.flush(); return
		 * sw.toString();
		 */
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		while (msg != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			msg.printStackTrace(pw);
			pw.flush();
			if (first) {
				sb.append(sw.toString());
			} else {
				sb.append(sw.toString());
				sb.append("\r\n");
				first = false;
			}

			if (msg instanceof InteException) {
				msg = (Throwable) ((InteException) msg).getNestedThrowable();
			} else if (msg instanceof QMException) {
				msg = (Throwable) ((QMException) msg).getNestedException();
			} else {
				break;
			}
		}
		return sb.toString();

	}

}
