#UltraPtrHeaderCollection
[![master Build Status](https://travis-ci.org/Muyangmin/UltraPtrHeaderCollection.svg?branch=master)](https://travis-ci.org/Muyangmin/UltraPtrHeaderCollection)
[![dev Build Status](https://travis-ci.org/Muyangmin/UltraPtrHeaderCollection.svg?branch=dev)](https://travis-ci.org/Muyangmin/UltraPtrHeaderCollection)
[ ![Download](https://api.bintray.com/packages/muyangmin/maven/ultra-ptr-headers/images/download.svg) ](https://bintray.com/muyangmin/maven/ultra-ptr-headers/_latestVersion)
<a href="http://www.methodscount.com/?lib=org.ptrheader.library%3Aultra-ptr-headers%3A0.2.0"><img src="https://img.shields.io/badge/Methods and size-136 | 19 KB-e91e63.svg"></img></a>

#####TODOs and Plans
加入使用wiki；加入更多的header。欢迎关注和加入。

##Introduction
Ultra-Ptr是一个伟大的下拉刷新库，在稳定性、通用性、可扩展性和代码的可理解性上都远比很多其他下拉刷新库（包括官方的SwipeRefreshLayout）要优秀很多。
如果你还不知道Ultra-Ptr库，请戳这里:[liaohuqiu/android-Ultra-Pull-To-Refresh](https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh)

虽然Ultra-Ptr库内置了比较实用的DefaultHeader，也有比较酷的MD和StoreHouse风格的header
，但是对于实际项目的需求来说可能还是不够用。这里我们发起一个Header的开源收集库，减少重复造轮子的工作，希望能够开启更多好玩的Header :)

##Convention
我们约定：  
1. 所有的库都在library module中定义;  
2. 其他的依赖可以在library中添加，但提倡使用较少依赖的header;  
3. 提倡同时支持XML和Java代码使用。

##Map/Navigation
目前已经实现的效果有：  
* 下拉可旋转的彩色小球和一张宣传图片: **BallSloganHeader**。  
* 仿网易新闻加载动画的小球效果: **NetEaseMarsView**  
* 仿网易新闻加载头部【与上条类似，但多出一个最近更新时间说明】: **NetEaseNewsHeader**  

具体效果可以参照下图（Gif图片有压缩和失真，真机上可以看到更好的效果）：  
![](https://github.com/Muyangmin/UltraPtrHeaderCollection/blob/master/Demo.gif)

##Gradle
```Groovy  
compile 'org.ptrheader.library:ultra-ptr-headers:0.2.0'
```

##Contribution
如果在使用上有问题或发现了Bug，或者你有很棒的Header效果实现或推荐，欢迎提issue/PR，但是请在dev分支上提供PR。

##Licence
Apache Licence V2.0