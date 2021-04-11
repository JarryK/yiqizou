/**
 * 用户相关服务
 */
const util = require('./util.js');
const api = require('../request/api.js');
const app = getApp();// 引入app


/**
 * Promise封装wx.checkSession
 */
function checkSession() {
  return new Promise(function (resolve, reject) {
    wx.checkSession({
      success: function () {
        resolve(true);
      },
      fail: function () {
        reject(false);
      }
    })
  });
}

/**
 * Promise封装wx.login
 */
function login() {
  return new Promise(function (resolve, reject) {
    wx.login({
      success: function (res) {
        if (res.code) {
          resolve(res);
        } else {
          reject(res);
        }
      },
      fail: function (err) {
        reject(err);
      }
    });
  });
}

/**
 * 调用微信登录
 */
function loginByWeixin(userInfo) {
  return new Promise(function (resolve, reject) {
    return login().then((res) => {
      //登录
      util.post(api.login, {code: res.code,userInfo: userInfo }).then(res => {
        if (res.code == 200) {
          //存储用户信息
          setUserData(userInfo);

          console.log(res.data.token);
          var a = res.data.token;
          wx.setStorage({
            key:'userToekn', 
            data:a})
          resolve(res);
          wx.navigateBack({
            delta: 1
          })
          util.showSuccessToast("登录成功")
        } else {
          reject(res);
        }
      }).catch((err) => {
        reject(err);
      });
    }).catch((err) => {
      reject(err);
      console.error(err)
    })
  });
}

/**
 * 判断用户是否登录
 */
function checkLogin() {
  return new Promise(function (resolve, reject) {
    if (wx.getStorageSync('userInfo') && wx.getStorageSync('token')) {
      checkSession().then(() => {
        var app =getApp()
        resolve(true);        
        app.globalData.hasLogin = true;
      }).catch(() => {
        reject(false);
      });
    } else {
      reject(false);
    }
  });
}
function setUserData(userInfo){
  wx.setStorageSync('userInfo', userInfo);
}
module.exports = {
  loginByWeixin,
  checkLogin,
};