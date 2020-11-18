//Page Object
var QQMapWX = require('../../lib/qqmap-wx-jssdk.js');
var qqmapsdk;
const chooseLocation = requirePlugin('chooseLocation');
const app = getApp();// 引入app
const mapKey = app.globalData.qqmap.key;
var user_location;
Page({
  data: {
    
  },
  //options(Object)
  onLoad: function(options){
    qqmapsdk = new QQMapWX({
      key: mapKey
  });
  },
  onReady: function(){
    
  },
  onShow: function(){
    qqmapsdk.search({
      keyword: '酒店',
      success: function (res) {
          console.log(res);
      },
      fail: function (res) {
          console.log(res);
      },
      complete: function (res) {
        console.log(res);
    }
});
  user_location = app.getLocation()

},
  onHide: function(){

  },
  onUnload: function(){

  },
  onPullDownRefresh: function(){

  },
  onReachBottom: function(){

  },
  onShareAppMessage: function(){

  },
  onPageScroll: function(){

  },
  //item(index,pagePath,text)
  onTabItemTap:function(item){
  },
  whereAreYou:function(_location){
    wx.getLocation({
      type: "gcj02",
      success (user_location) {
          console.log(user_location)
          var longitude = user_location.longitude;
          var latitude = user_location.latitude;
          var location =  JSON.stringify({
            longitude:longitude,
            latitude:latitude
          }
            );
          // let location = JSON.stringify({}) : _location;
          let key = mapKey; //使用在腾讯位置服务申请的key
          let referer = '选择位置'; //调用插件的app的名称
          let category = '生活服务,娱乐休闲';
          wx.navigateTo({
            url: 'plugin://chooseLocation/index?key=' + key + '&referer=' + referer + '&location=' + location + '&category=' + category
          });
      }
    })
  },
  whereAreYouGoing:function(_location){

    let key = mapKey;  //使用在腾讯位置服务申请的key
    let referer = '一起走';   //调用插件的app的名称
     //终点
    let endPoint =  JSON.stringify({
      'name': '吉野家(北京西站北口店)',
      'latitude': 39.89631551,
      'longitude': 116.323459711
    });
    wx.navigateTo({
        url: 'plugin://routePlan/index?key=' + key + '&referer=' + referer + '&endPoint=' + endPoint
    })
  }
});
