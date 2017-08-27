开发一个发送报文模拟器
请求报文格式

请求地址，使用接口方式组装

接口：
service_id:
service_scene:

参数格式
biz_seq_no:
sys_seq_no:
content:{json格式字段}


------------
流程：
1、post请求到主控
2、主控获取 service_id,service_scene,转发到对于类

3、服务器实现，service_id,service_scene与实际出来类对于