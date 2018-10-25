package hhxk.pojo

//用户登录信息
data class UserInfo(var phone: String, var pwd: String)

//登录头像信息
data class UserImg(var phone: String, var img: String)

//消息列表(朋友)
data class FMessage(var img: String, var time: String, var name: String, var message: String)

//新闻
data class News(var id: String, var img: String, var name: String, var source: String, var time: String)

//农机
data class Car(var id: String, var img: String, var number: String, var address: String)

//保单
data class Bill(var img: String, var name: String)

//测亩记录
data class Record(var number: String)

//地址
data class Address(var name: String, var phone: String, var address: String)

//分享给
data class ShareName(var img: String, var name: String)