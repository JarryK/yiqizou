// 以下是业务服务器API地址
// 本机开发时使用
 var apiRoot = 'http://localhost:8085/';
module.exports = {
  login: apiRoot + 'yqz/user/wxLogin', //微信登录
  showFuzzyOrder: apiRoot + 'yqz/order/fuzzy', //按起点 终点显示行程 模糊匹配
  publish : apiRoot +'yqz/order/publish', //发布订单
  queryOrder : apiRoot+'yqz/order/query', //查询订单
  own: apiRoot + 'yqz/order/own', //查询我的订单
  join: apiRoot + 'yqz/order/join', //加入行程
  cancel: apiRoot + 'yqz/order/cancel', //取消行程
  save:apiRoot+'yqz/template/save', //保存formid
  advice:apiRoot+'yqz/order/advice' //推荐行程
};