
### netty 传输方式

Netty 内置的传输方式主要有 NIO、Epoll、OIO、Local 和 Embedded 等方式，具体可以看 `EventLoopGroup` 的实现，如下：

* NIO (NioEventLoopGroup)

使用 java.nio.channels 包作为基础，基于选择器的方式

* Epoll (EpollEventLoopGroup) 

由 JNI 驱动的 epoll() 和非阻塞 io，这种传输只有 Linux 才支持，比 NIO 传输速度更快，而且是完全非阻塞的。

类似的还有 `KQueueEventLoopGroup`

* OIO (OIOEventLoopGroup)

使用 java.net 包为基础，阻塞

* Local (LocalEventLoopGroup)

可以在 VM 内部通过管道进行通信的本地服务

* Embedded (EmbeddedEventLoop)

Embedded 传输，允许使用 ChannelHandler 而又不需要一个真正的基于网络的传输，测试 ChannelHandler 实现的时候非常有用.