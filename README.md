# WebDictionary
当前进度：
	登录，注册，使用API获取单词释义，点赞排序，获取用户列表已经基本没有问题
	
已解决：
	1、新用户注册之后第一次登录用户列表显示离线。
	2、客户端高频率发送请求导致服务器抛异常Connection reset
	3、客户端关闭程序需要通知服务器断开连接
	4、修改用户信息（重置密码）待完成（放弃）
	5、单词卡转发功能待完成
	6、搜索单词之前对单词的检查待完善（目前只支持纯英文单词，类似 a lot of，A.D.均不支持）
	
未完成：
	1、单词卡图片的生成。	--client/home/tools/CreateImage.java
	2、Bing翻译接口的整合	--client/search/Biying.java Translate();