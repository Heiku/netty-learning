
### Channel

Channel 类似与流，但又有些不同：

* 可以从 channel 中读取数据，又可以写数据到通道中，但流的读写时单向的
* channel 可以异步读写
* channel 中的数据需要借助于 byteBuffer，即读取到 byteBuffer，写入到 byteBuffer

实现:

* FileChannel: 从文件中读取数据
* DatagramChannel: 通过 UDP 读写网络中的数据
* SocketChannel: 通过 TCP 读写网络中的数据
* ServerSocketChannel: 可以监听新进来的 TCP 连接，对于每一个新进来的连接都会创建一个 SocketChannel


### ByteBuffer

ByteBuffer 本质上就是一个内存块，可写可读，包含了三个属性 capacity, position, limit

* position: 读写指针，每写入一个字节，position 向后移动，position 最大为 capacity - 1，当切换成 _读模式_ 时，
position 重置为0， 读取一样后移。
* limit: 写模式下，limit = capacity 代表最多能写入的数据，当切换成 _读模式_ 时，limit 会被设置之前写入的最大值，即 position

```
flip(): 从写模式切换成读模式
clear(): 清空整个缓冲区
compact(): 清除已经读取过的数据，未读取的数据将被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面

channel.read(byteBuffer)    读取 channel 中的数据到 byteBuffer 中
channel.write(byteBuffer)   将 byteBuffer 中的数据写入到 channel


flip() vs rewind(): 都会将 position 设置为0，但不同的是
flip() 会将读取的最大值 limit 设置成之前写模式的 position
rewind() 则不会改变，还是设置成 capacity 

mark() & reset():
通过 mark() 标记 Buffer 中一个特定的 position，之后可以通过 reset() 恢复到这个 position
```

### Scatter / Gather

* Scattering Reads

Scattering Reads 指从一个 Channel 读取到多个 buffer 中，

```
ByteBuffer header = ByteBuffer.allocate(128);
ByteBuffer body   = ByteBuffer.allocate(1024);

// 按照数组中的顺序从 channel 中读取的数据写入到 buffer中，当 header 写满之后，再写入到 body 中
ByteBuffer[] bufferArray = { header, body };        

channel.read(bufferArray);
```

* Gathering Writes

Gathering Writes 是指将数据从多个 buffer 写入到同一个 channel。

```
ByteBuffer header = ByteBuffer.allocate(128);
ByteBuffer body   = ByteBuffer.allocate(1024);
ByteBuffer[] bufferArray = { header, body };        

channel.write(bufferArray);

```

### Selector

Selector 是 NIO 中能够检测一到多个 Channel，并能够知晓通道是否能为诸如读写事件做好准备的组件。这样，一个单独的线程就
可以管理多个 channel，从而管理多个网络连接。减少了多线程的内存占用及上下文切换带来的性能开销。

* 事件

```
SelectionKey.OP_CONNECT：    某个 channel 成功连接到另一个服务器
SelectionKey.OP_ACCEPT：     serverSocketChannel 准备好接收新进入的连接
SelectionKey.OP_READ：       一个数据可读的通道
SelectionKey.OP_WRITE：      等待写数据的通道
``` 

* 操作

```
select()： 一旦向 Selector 注册了一或多个 channel，就可以调用 select() 返回已经准备就绪的 channel

select():               阻塞到至少一个 channel 在注册的事件上就绪了
select(long timeout):   和 select() 一样，最长会阻塞 timeout ms
selectNow():            不会阻塞，不管什么通道就绪都立即返回，没有就绪直接返回0

selectedKeys(): 返回多个通道就绪的 set 集合，需要通过 iterator遍历，并 iter.remove() 删除 SelectionKey 实例

wakeUp(): 某个线程调用 select() 之后阻塞了，只需要在调用 select 那个selector 对象上调用 Selector.wakeUp() 就可立即返回

close(): 使用完毕关闭 


selectionKey.attach():      可以给一个对象或者更多信息附着到 SelectionKey 上
```





### 引用

[Java NIO系列教程](http://ifeve.com/overview/)