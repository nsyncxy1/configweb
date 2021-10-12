/**
 * Created by yujie on 2019/4/17 11:34
 */

// 根对象
//const root = window.$rootScope ? window.$rootScope : {};

const root =  {
  // rest 根路径
  restRoot: process.env["VUE_APP_BASE_API"],
  //用户信息
  userInfo: {}
}

// rest 根路径
const restRoot = root.restRoot;

// get 方法
const get = (key) => {
  return root[key];
};

// set 方法
const set = (key, value) => {
  if(key){
    root[key] = value;
  }
};


export default {
  root,
  restRoot,
  get,
  set,
}
