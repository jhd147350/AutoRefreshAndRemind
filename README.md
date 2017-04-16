# 请先看以下内容
- 要下载和浏览器相对应版本的chrome webdriver，还要将webdriver目录配置到系统环境变量
- 不用程序要点注销。
- 提醒的声音不能终止，只能关闭程序（ctrl + c 或直接关闭）注销后再次运行一遍
- 程序运行中 操作页面，会导致后面程序出错，请注销后关闭再次运行。
- 程序刷新间隔为75s。

# AutoRefreshAndRemind
先导出jar包，然后执行exportJavaConsole转成exe程序(需要安转exe4j) 

## v1.2
- 模拟登陆
- 然后定时获取数据
- 分析数据
- 得到预定的数据或出错，程序都会播放contra.mp3这个文件  
 
## v1.3  
- 增加程序异常提示（SuperMarioBros.mp3）：StaleElementReferenceException、NoSuchElementException、ChromeDriver驱动报错等异常的捕获处理。
- 增加稳定性，StaleElementReferenceException这个错误总是出现，初步分析是我在点击打开后等了5s（怕网络延时在点击后不能立刻获取到正确数据会报错），现在改为了3s，因为remedy还存在自动刷新，可能在这等待的过程中，正好碰到了自动刷新 导致获取数据失败，发生这种事情的概率还是比较大的，现在将时间改为了3s。
- 优化输出格式

## v1.4
- 程序还是经常报StaleElementReference，将模拟点击“打开”改为刷新整个页面。
- 将刷新间隔改为60s

## v1.5 
20170416更新：
- 优化显示效果，增加倒计时和刷新的提示，能看出程序的运行状态
- 刷新间隔改为75s

---------
https://github.com/jhd147350/AutoRefreshAndRemind