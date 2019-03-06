# ActiveMQ反序列化

以TcpTransport为例，它的run方法会执行，只要未停止，就会一直循环。
```java
@Override
public void run() {
    LOG.trace("TCP consumer thread for " + this + " starting");
    this.runnerThread=Thread.currentThread();
    try {
        while (!isStopped()) {
            doRun();
        }
    // ...
    }finally {
        stoppedLatch.get().countDown();
    }
}

protected void doRun() throws IOException {
    try {
        Object command = readCommand();
        doConsume(command);
    } catch (SocketTimeoutException e) {
    } catch (InterruptedIOException e) {
    }
}

protected Object readCommand() throws IOException {
    return wireFormat.unmarshal(dataIn);
}
```

循环体里readCommand()用来接收消息，并进行反序列化。TcpTransport用的是OpenWireFormat。
进入其unmarshal方法
```java
@Override
public Object unmarshal(DataInput dis) throws IOException {
    DataInput dataIn = dis;
    // 省略
    return doUnmarshal(dataIn);
}

public Object doUnmarshal(DataInput dis) throws IOException {
    byte dataType = dis.readByte();
    if (dataType != NULL_TYPE) {
        DataStreamMarshaller dsm = dataMarshallers[dataType & 0xFF];
        if (dsm == null) {
            throw new IOException("Unknown data type: " + dataType);
        }
        Object data = dsm.createObject();
        if (this.tightEncodingEnabled) {
            BooleanStream bs = new BooleanStream();
            bs.unmarshal(dis);
            dsm.tightUnmarshal(this, data, dis, bs);
        } else {
            dsm.looseUnmarshal(this, data, dis);
        }
        return data;
    } else {
        return null;
    }
}
```

假如dataType是TEXT，此处的DataStreamMarshaller应为ActiveMQTextMessageMarshaller。
先调用dsm.createObject(）创建一个空对象
```java
public DataStructure createObject() {
    return new ActiveMQTextMessage();
}
```
接下来从DataInput中读取数据并填充Message对象，关键逻辑在MessageMarshaller和BaseDataStreamMarshaller这两个类中。
首先是MessageMarshaller的looseUnmarshal。
```java
public void looseUnmarshal(OpenWireFormat wireFormat, Object o, DataInput dataIn) throws IOException {
    super.looseUnmarshal(wireFormat, o, dataIn);

    Message info = (Message)o;

    info.beforeUnmarshall(wireFormat);
    
    info.setProducerId((org.apache.activemq.command.ProducerId) looseUnmarsalCachedObject(wireFormat, dataIn));
    info.setDestination((org.apache.activemq.command.ActiveMQDestination) looseUnmarsalCachedObject(wireFormat, dataIn));
    info.setTransactionId((org.apache.activemq.command.TransactionId) looseUnmarsalCachedObject(wireFormat, dataIn));
    info.setOriginalDestination((org.apache.activemq.command.ActiveMQDestination) looseUnmarsalCachedObject(wireFormat, dataIn));
    info.setMessageId((org.apache.activemq.command.MessageId) looseUnmarsalNestedObject(wireFormat, dataIn));
    info.setOriginalTransactionId((org.apache.activemq.command.TransactionId) looseUnmarsalCachedObject(wireFormat, dataIn));
    info.setGroupID(looseUnmarshalString(dataIn));
    info.setGroupSequence(dataIn.readInt());
    info.setCorrelationId(looseUnmarshalString(dataIn));
    info.setPersistent(dataIn.readBoolean());
    info.setExpiration(looseUnmarshalLong(wireFormat, dataIn));
    info.setPriority(dataIn.readByte());
    info.setReplyTo((org.apache.activemq.command.ActiveMQDestination) looseUnmarsalNestedObject(wireFormat, dataIn));
    info.setTimestamp(looseUnmarshalLong(wireFormat, dataIn));
    info.setType(looseUnmarshalString(dataIn));
    info.setContent(looseUnmarshalByteSequence(dataIn));
    info.setMarshalledProperties(looseUnmarshalByteSequence(dataIn));
    info.setDataStructure((org.apache.activemq.command.DataStructure) looseUnmarsalNestedObject(wireFormat, dataIn));
    info.setTargetConsumerId((org.apache.activemq.command.ConsumerId) looseUnmarsalCachedObject(wireFormat, dataIn));
    info.setCompressed(dataIn.readBoolean());
    info.setRedeliveryCounter(dataIn.readInt());

    if (dataIn.readBoolean()) {
        short size = dataIn.readShort();
        org.apache.activemq.command.BrokerId value[] = new org.apache.activemq.command.BrokerId[size];
        for( int i=0; i < size; i++ ) {
            value[i] = (org.apache.activemq.command.BrokerId) looseUnmarsalNestedObject(wireFormat,dataIn);
        }
        info.setBrokerPath(value);
    }
    else {
        info.setBrokerPath(null);
    }
    info.setArrival(looseUnmarshalLong(wireFormat, dataIn));
    info.setUserID(looseUnmarshalString(dataIn));
    info.setRecievedByDFBridge(dataIn.readBoolean());
    info.setDroppable(dataIn.readBoolean());

    if (dataIn.readBoolean()) {
        short size = dataIn.readShort();
        org.apache.activemq.command.BrokerId value[] = new org.apache.activemq.command.BrokerId[size];
        for( int i=0; i < size; i++ ) {
            value[i] = (org.apache.activemq.command.BrokerId) looseUnmarsalNestedObject(wireFormat,dataIn);
        }
        info.setCluster(value);
    }
    else {
        info.setCluster(null);
    }
    info.setBrokerInTime(looseUnmarshalLong(wireFormat, dataIn));
    info.setBrokerOutTime(looseUnmarshalLong(wireFormat, dataIn));
    info.setJMSXGroupFirstForConsumer(dataIn.readBoolean());

    info.afterUnmarshall(wireFormat);

}
```
looseUnmarsalCachedObject会在解析出一个Message对象，类似ActiveMQTextMessage。

看下解析消息内容的部分
```java
info.setContent(looseUnmarshalByteSequence(dataIn));
```
调用了BaseDataStreamMarshaller的looseUnmarshalByteSequence
```java
protected ByteSequence looseUnmarshalByteSequence(DataInput dataIn) throws IOException {
    ByteSequence rc = null;
    if (dataIn.readBoolean()) {
        int size = dataIn.readInt();
        byte[] t = new byte[size];
        dataIn.readFully(t);
        rc = new ByteSequence(t, 0, size);
    }
    return rc;
}
```
通过dataIn.readInt()读取到接下来内容的长度，然后调用dataIn.readFully(t)读取指定长度的内容。
readFully(byte[] b)方法是读取流上指定长度的字节数组，如果声明了长度为len的字节数组，
readFully(byte[] b)方法只有读取len长度个字节的时候才返回，否则阻塞等待，如果超时，则会抛出异常 EOFException。

所以是通过 字段长度 + DataInput的readFully方法 解决了粘包拆包的问题，然后在MessageMarshaller内部
通过读取DataInput将消息内容一一设置完成。


