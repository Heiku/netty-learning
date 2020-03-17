### 创建服务端 Channel 

* bind() 

* initAndRegister()

    - channelFactory.newChannel()

    这里的 newChannel() 实际上是将传递的 `channel(NioServerSocketChannel.class)` class 对象通过反射的方式得到，
    注意： 这里将会调用 NioServerSocketChannel 的构造方法，就是 [下面的过程](#反射创建服务端Channel)
    
    - init()
    
    初始化 NioServerSocketChannel
    
        * set ChannelOptions, ChannelAttrs
        
        * set ChildOptions, ChildAttr
        
        * config handler 配置服务端 pipeline
        
        * add ServerBootstrapAcceptor   添加连接器

### 反射创建服务端Channel

* newSocket() 

通过 JDK 来创建底层的 jdk channel (ServerSocketChannel)

* NioServerSocketChannelConfig() 

进行 TCP 参数配置 (backlog...)

* AbstractNioChannel 

    - configureBlocking(false)
    
    设置非阻塞模式
    
    - AbstractChannel() 
    
    创建id, unsafe, pipeline(逻辑链)
