/**
 * ���ɳ��� SearchResult.java    1.0    2007/10/10
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.util;

import java.util.Collection;


/**
* <p>Title: ��</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2007</p>
* <p>Company: һ������</p>
* @author ������
* @version 1.0
*/
public class SearchResult
{
    private String key;
    private Collection value;
    private Object[] obj;

    public SearchResult()
    {
    }

    public SearchResult(String key0, Collection value0)
    {
        this.key = key0;
        this.value = value0;
    }

    public SearchResult(String key0, Object[] obj0)
    {
        this.key = key0;
        this.obj = obj0;
    }
    
    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public Collection getValue()
    {
        return value;
    }

    public void setValue(Collection value)
    {
        this.value = value;
    }

    public Object[] getObj()
    {
        return obj;
    }

    public void setObj(Object[] obj)
    {
        this.obj = obj;
    }
}

