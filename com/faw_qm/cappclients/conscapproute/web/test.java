package com.faw_qm.cappclients.conscapproute.web;

/**
 * <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2004</p> <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */

public class test
{

    public test()
    {
        String[] originalIDs = new String[]{"A_2", "A_3", "A_1"};
        if(originalIDs != null && originalIDs.length > 0)
        {
            for(int j = originalIDs.length;j > 0;j--)
            {
                String temp;
                //String bsoID1;
                //String bsoID2;
                for(int i = 0;i < j - 1;i++)
                {
                    //bsoID1 = (String)originalIDs[i];
                    //bsoID2 = (String)originalIDs[i+1];
                    //����bsoid�������򣬰�bsoidȷ�����򡣣����ų�ͬһ�汾��ԭ���͸�����˳�򡣣�
                    if(originalIDs[i].toString().compareTo(originalIDs[i + 1].toString()) > 0)
                    {
                        //�����滻
                        temp = originalIDs[i];
                        originalIDs[i] = originalIDs[i + 1];
                        originalIDs[i + 1] = temp;
                    }
                }
            }
        }
        for(int j = 0;j < originalIDs.length;j++)
            System.out.println("��˳��" + originalIDs[j]);

    }

    public static void main(String[] args)
    {
        //test test1 = new test();
        System.out.println("\u5c5e\u6027\u503c\u5df2\u7ecf\u8d85\u51fa\u4e86\u5b83\u7684\u4e0a\u9650");
    }
}
