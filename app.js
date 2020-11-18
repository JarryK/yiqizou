//app.js
App({
  //onLaunch,onShow: options(path,query,scene,shareTicket,referrerInfo(appId,extraData))
  onLaunch: function(options){
    
  },
  onShow: function(options){

  },
  onHide: function(){

  },
  onError: function(msg){

  },
  //options(path,query,isEntryPage)
  onPageNotFound: function(options){

  },
  getLocation:function(params) {
    wx.getLocation({
      type: "gcj02",
      success (res) {
          console.log(res)
          return JSON.stringify({res});
      }
    })
  },
  // 全局数据
  globalData: {
    qqmap:{
      key:'EKNBZ-WNTKR-Q6SWC-WSQV6-YES6H-4PBNX'
    }
  }
});