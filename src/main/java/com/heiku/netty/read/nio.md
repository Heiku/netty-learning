
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

#### 原理





### 引用

[Java NIO系列教程](http://ifeve.com/overview/)