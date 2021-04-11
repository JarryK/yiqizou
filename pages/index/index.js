//Page Object
var QQMapWX = require('../../lib/qqmap-wx-jssdk.js');
var qqmapsdk;
const chooseLocation = requirePlugin('chooseLocation');
var user = require('../../utils/user');
const app = getApp();// 引入app
const mapKey = app.globalData.qqmap.key;
var user_location;
Page({
  data: {
    start_lo:'',
    end_lo:'',
    is_start:'',
    is_end:'',
    start_det:'',
    end_det:''
  },
  //options(Object)
  onLoad: function(options){
    qqmapsdk = new QQMapWX({
      key: mapKey
  });
  },
  onReady: function(){
    user.checkLogin().catch(() => {
      wx.navigateTo({
         url: "/pages/login/login"
       });
 
     });
  },
  onShow: function(){
    user_location = chooseLocation.getLocation();
    console.log(user_location);
    if(this.data.is_start && !this.data.is_end){
      this.setData({
        start_lo:user_location.name,
        start_det:user_location
      })
    }else if(!this.data.is_start && this.data.is_end){
      this.setData({
        end_lo:user_location.name
      })
    }
},
  onHide: function(){

  },
  onUnload: function(){
    chooseLocation.setLocation(null);
    user_location = '';  
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
  },
  checkMap:function(e){
    var a = e.currentTarget.dataset.type;
    console.log(a);
    if(a==0){
      this.setData({
        is_start:true,
        is_end:false
      })    
    }else if(a==1){
      this.setData({
        is_start:false,
        is_end:true
      })   
    }
    this.whereAreYou();
  },
  
  submitFrom:function(e){
    console.log(e.detail.value)
    console.log(e.detail.value.startLoc)
    console.log(e.detail.value.endLoc)
    console.log(e.detail.value.startTime)
    console.log(e.detail.value.platform)
    console.log(e.detail.value.number)
    console.log(e.detail.value.remark)
    console.log(e.detail.value.code)
    if(st == '' || st == null || end == null || end == ''){
      return  wx.showModal({
        title: '提示',
        content: '当前没有输入起始地或者目的地',
        success: function(res) {
          if (res.confirm) {
          console.log('用户点击确定')
          } else if (res.cancel) {
          console.log('用户点击取消')
          }
        }
      })
    }
    wx.showModal({
			title: '提示',
			content: '出发地点为:'+ st + ',目的地为：'+ end,
			success: function(res) {
				if (res.confirm) {
				console.log('用户点击确定')
				} else if (res.cancel) {
				console.log('用户点击取消')
				}
			}
		})
   
  }
});
