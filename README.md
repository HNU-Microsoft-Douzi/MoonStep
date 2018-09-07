## 圆月App

### descriptipon

圆月App致力于打造一种全新的社交性领域，在多样功能性的基础上，增加几大相互嵌套的体系，致力于改善当今人们生活中出现的无聊情绪，独特的地图搜寻体系，奖励体系，权限体系相互嵌套，构筑完美圆月世界。

###　程序界面

- 引导页

![](/images/guide_page0.png)

![](/images/guide_page1.png)

![](/images/guide_page2.png)

![](/images/guide_page3.png)

![](/images/guide_page4.png)

- 登录界面

![](/images/login_page.png)

- 侧滑栏

![](/images/drawable_page.png)

### 项目开发流程

#### 产品需求

**需求分析**

> 需求了解

> 功能划分
	
> 技术思考

#### 交互设计

> 方案选择
	
  从根本上，打造全中国独一无二的游戏型社交类APP,多重体系相互嵌套，构建完美社交app，纠正当今青年人的畸形娱乐观。

> 结构设计
   
  五大种族，七大体系，相互嵌套，各种完善的系统，优质多样化的玩法，更是衡量于游戏和社交二者的平衡性能，多重考虑，用完善体系铸就骨架，用心打造灵魂，用极致UI构建外在。

> 详细设计
	
  详细内容见设计文档。
	
> 设计文档
	
  ![]()

#### 视觉设计

(UI界面设计好后从设计的灵感上来阐述这一部分的内容)

#### 开发
- 编码

	UTF-8

- 框架搭建

	fanLayoutManager

	ViewPager

	CircleImageView

	recycleView

	JellyToggleButton

	CarouselLayoutManager

	PullToRefreshView

 	Butterknife

- 帐号申请
- 官网运营
- 代码混淆
- 优化重构
- 开发工具
   
	Android Stdio
   

UI设计工具：墨刀
#### 测试

- 视觉验收
- 交互验收
- 产品验收

#### 发布

#### 开发流程一览

7/31/2018 12:36:59 PM 

第一次开创项目，定下立项，构建目标，利用工具开始打造圆月UI界面。

8/4/2018 3:22:30 PM 

墨刀打造UI界面基本完成，未进行效果优化，开始编写结构代码。

8/12/2018 5:23:48 PM 

引导页+登录页面基本完成，引导页使用了Fragment来便于管理生命周期，登录页面进行优化，注册页面完成，注册功能尚未完善。

8/15/2018 2:56:53 PM 

主页面架构大体完成，利用Fragment嵌套和切换，实现侧滑菜单中的菜单项共联。

8/18/2018 9:47:22 AM 

处理完新一轮的bug,菜单栏第三项已经完成，采用FanLayoutManager的新框架，打造风车布局recyclerView，优化界面，并在其中完成了多重fragment的设计和切换。

8/21/2018 9:18:45 AM 

客服页面完成，还是调用的RecyclerView的多重item模式，数据的传输尚未完成，因为客服交互的平台尚未搭建好，预计未来会使用网络+数据解析的方式完成数据的传递。

8/23/2018 8:19:01 AM 

背包界面完成，采用网格布局，内置功能尚未完成，方格采用ImageView的组件实现，物品栏目使用前景进行填充，并对每个item进行监控，点击后进行弹窗，尚未进行设置。

2018/9/6 22:07:21

解决Butterknife与android 3.0冲突的问题，成功使用Butterknife Zeleny插件，减少了每个页面要写findViewById()的冗杂。

2018/9/7 12:13:17 

使用Mob平台的短信SDK，动态架构进需要使用的gradle配置层，并且在登陆页面实现手机验证的功能。

#### Bug流程一览

- 8/10/2018 5:22:07 PM

Bug类型：`OOM + Launcher3 has stopped`

Bug原因：Icon设置大小未调节

Bug解决时间： 8/12/2018 2:23:07 PM

- 8/17/2018 14:27:27 PM 

Bug类型：`没有报错+fragment切换失效`

Bug原因：主fragment向activity建立依赖not work,原因是没有调用一个自定义的`newInstance`的实例。

Bug解决时间： 8/18/2018 9:50:37 AM 

- 8/19/2018 3:16:14 PM 

Bug类型：`称号详情页面无法去除来自主activity的标题栏`

Bug原因：称号页面由单独的fragment通过 replace的方法来产生，然而，也正是在这样的情况下，它的产生于否取决于用户是否对某一称号进行点击，如果点击的话，才会决定该Fragment的生成，如果要在一个activity中去隐藏当前的标题栏，那是一个很容易的事情，但是如果在一个后来生成的单独的Fragment中做到这一点，那几乎是无法完成的事情，因为由于调用顺序的原因，会爆出一个RunTimeException，原因是setFeature()的顺序必须排在adding Content的前面，前后两者出现逻辑矛盾，几乎不可实现。（当前想法：对fragment和activity进行通信，如果fragment产生的时候，在activity中接收讯息，然后对其进行标题栏的隐藏，关闭的时候在监听，进行标题栏的显示，尚未实行。）

Bug解决时间： 尚未解决

- 8/21/2018 22:05:47 PM 

Bug类型：`Android字体切换失效`

Bug原因：欲切换界面字体，采用缓冲区的方式对字体进行缓冲，然而不能修改字体。

Bug解决时间：尚未解决

- 8/24/2018 19:24:13 PM 

Bug类型：`PullToRefreshView一直刷新无法取消`

Bug原因：进入社交页面，PullToRefreshView一直在刷新，无法取消刷新状态，调试也无法进入相应的触点函数。。

Bug解决时间：8/25/2018 9:39:31 AM 