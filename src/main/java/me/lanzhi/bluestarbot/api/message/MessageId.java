package me.lanzhi.bluestarbot.api.message;

/**
 * 消息的ID,由两个long组成,是消息的唯一标识
 */
public final class MessageId implements Cloneable
{
    public final long id0;
    public final long id1;

    public MessageId(long id0,long id1)
    {
        this.id0=id0;
        this.id1=id1;
    }

    @Override
    public int hashCode()
    {
        return Long.hashCode(id0)^Long.hashCode(id1);
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof MessageId&&((MessageId) obj).id0==id0&&((MessageId) obj).id1==id1;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return new MessageId(id0,id1);
    }

    @Override
    public String toString()
    {
        return "MessageId{"+"id0="+id0+",id1="+id1+'}';
    }
}