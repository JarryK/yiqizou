var api = require('../request/api');
var app = getApp();

function formatTime(date) {
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var day = date.getDate();

  var hour = date.getHours();
  var minute = date.getMinutes();


  return [year, month, day].map(formatNumber).join('-') + ' ' + [hour, minute].map(formatNumber).join(':')
}

function formatNumber(n) {
  n = n.toString();
  return n[1] ? n : '0' + n
}

/**
 * 封装微信的的request
 */
function request(url, data = {}, method = "POST") {
  return new Promise(function (resolve, reject) {
    wx.request({
      url: url,
      data: data,
      method: method,
      header: {
        'Content-Type': 'application/json'
      },
      success: function (res) {
        if (res.data.success) {
          resolve(res.data);
        } else {
          if (res.data.message == "LOGIN") {
            // 清除登录相关内容
            try {
              wx.removeStorageSync('userInfo');
              wx.removeStorageSync('token');
            } catch (e) {
              // Do something when catch error
            }
            // 切换到登录页面
            wx.navigateTo({
              url: '/pages/login/login'
            });
          } else {
            reject(res.data.message);
          }
        }
      },
      fail: function (err) {
        reject(err)
      }
    })
  });
}

function post(url, data) {
  var _data = data == null ? {} : data;
  return new Promise(function (resolve, reject) {
    wx.request({
      url: url,
      data: _data,
      method: 'POST',
      header: {
        'Content-Type': 'application/json'
      },
      success: function (res) {
        console.log(res)
        if (res.data.success) {
          resolve(res.data);
        } else {
          reject(res.data.msg);
        }
      },
      fail: function (err) {
        reject(err)
      }
    })
  });
}

function redirect(url) {
  //判断页面是否需要登录
  if (false) {
    wx.redirectTo({
      url: '/pages/auth/login/login'
    });
    return false;
  } else {
    wx.redirectTo({
      url: url
    });
  }
}

function showErrorToast(msg) {
  wx.showToast({
    title: msg,
    image: '/images/error.png'
  })
}

function showSuccessToast() {
  wx.showToast({
    title: '成功',
    icon: 'success',
    duration: 2000
  })
}
module.exports = {
  formatTime,
  request,
  redirect,
  showErrorToast,
  showSuccessToast,
  post
};