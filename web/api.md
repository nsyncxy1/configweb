#用户管理系统
## api接口规范
### 新增/修改返回结果
    { 
        "success": true, 
        "code": 200, 
        "message": "成功", 
        "data": {} 
    }
## 菜单
### 获取所有菜单 
>http://172.16.30.62:8021/userService/menu/getAllMenu

    {
      "success": true,
      "code": 200,
      "message": "成功",
      "data": {
        "menuList": [
          {
            "id": 1,
            "pid": 0,
            "systemId": "225225",//系统标识码
            "systemName": "精密智控",//系统名称
            "menuRoute": "menu1",//菜单路由
            "component": "@/views/nested/menu1/index",//组件路径
            "menuName": "一级菜单",//菜单名称
            "icon": null,//图标
            "status": 0,//状态（0默认，1隐藏）
            "type": 1,//类型(1:菜单,2:按钮)
            "permissionValue": null,//权限值
            "createTime": "2021-03-03T04:26:03.000+00:00",
            "updateTime": "2021-03-12T06:13:56.000+00:00",
            "level": 1,//层级
            "children": [//下级
              {
                "id": 4,
                "pid": 1,
                "systemId": "225225",
                "systemName": "精密智控",
                "menuRoute": "menu1-2",
                "component": "@/views/nested/menu1/menu1-2",
                "menuName": "二级菜单",
                "icon": null,
                "status": 0,
                "type": 1,
                "permissionValue": "",
                "createTime": "2021-03-12T08:31:10.000+00:00",
                "updateTime": "2021-03-12T08:34:43.000+00:00",
                "level": 2,
                "children": [],
                "select": false
              }
                ],
                "select": false
              }
            ],
          }
    }
### 新增菜单
>/userService/menu/save  
>post

    {
      "component": "string",//组件路径
      "icon": "string",//图标
      "menuName": "string",//页面名称
      "menuRoute": "string",//页面路由
      "permissionValue": "string",//权限值
      "systemId": "string",//系统标识码
      "systemName": "string",//系统名称
      "type": 0,//类型(1:菜单,2:按钮)
      "pid":1//父级菜单
    }
### 修改菜单
>/userService/menu/update  
>post

    {
      "id":1,//id必传
      "component": "string",//组件路径
      "icon": "string",//图标
      "menuName": "string",//页面名称
      "menuRoute": "string",//页面路由
      "permissionValue": "string",//权限值
      "systemId": "string",//系统标识码
      "systemName": "string",//系统名称
      "type": 0//类型(1:菜单,2:按钮),
       "status":1状态（0默认，1隐藏）
    }
### 删除菜单
>/userService/menu/remove/{id}  
>delete
>id(int)
## 角色
### 获取角色分页列表
>/userService/role/{page}/{limit}  
>get  
>page：当前页码;
 limit：每页记录数;
 roleName：角色名称
>
    {
         "success": true,
         "code": 200,
         "message": "成功",
         "data": {
             "total": 0,
             "items": [
                 {
                     "id": 1,
                     "roleName": "测试角色",
                     "status": 0,
                     "note": null,
                     "createTime": "2021-01-07T16:00:00.000+00:00",
                     "updateTime": "2021-01-07T16:00:00.000+00:00"
                 }
             ]
         }
     }
### 获取特定角色详情
>/userService/role/get/{id}  
>get  
>id
>
    {
        "success": true,
        "code": 200,
        "message": "成功",
        "data": {
            "item": {
                "id": 1,
                "roleName": "测试角色",//角色名称
                "status": 0,//状态，0默认，1停用
                "note": null,//备注
                "createTime": "2021-01-07T16:00:00.000+00:00",
                "updateTime": "2021-01-07T16:00:00.000+00:00"
            }
        }
    }
### 新增角色
> /userService/role/save  
> post
    
    {
      "note": "备注",
      "roleName": "角色名称"
    }
### 修改角色
> /userService/role/update      
> post

    {
      "id": 2,//id
      "note": "备注",//备注
      "roleName": "管理员",//角色名字
      "status": 0//状态
    }
### 删除单个角色
>/userService/role/remove/{id}  
>delete
### 批量删除角色
>/userService/role/batchRemove  
>delete  
>[id,id,...]
### 给角色分配权限
>/userService/menu/doAssign  
>post  
>roleId：角色id（Integer，必填）
>menuId：菜单id（Integer[]）
### 获取角色权限
>/userService/menu/toAssign/{roleId}        
>get

    {
      "success": true,
      "code": 200,
      "message": "成功",
      "data": {
        "menuList": [      {
            "id": 1,
            "pid": 0,
            "systemId": "225225",
            "systemName": "精密智控",
            "menuRoute": "menu1",
            "component": "@/views/nested/menu1/index",
            "menuName": "一级菜单",
            "icon": null,
            "status": 0,（0默认，1隐藏）
            "type": 1,
            "permissionValue": null,
            "createTime": "2021-03-03T04:26:03.000+00:00",
            "updateTime": "2021-03-12T06:13:56.000+00:00",
            "level": 1,
            "children": [          {
                "id": 2,
                "pid": 1,
                "systemId": "225225",
                "systemName": "精密智控",
                "menuRoute": "menu1-1",
                "component": "@/views/nested/menu1/menu1-1",
                "menuName": "二级菜单",
                "icon": null,
                "status": 0,
                "type": 1,
                "permissionValue": null,
                "createTime": "2021-03-12T06:11:16.000+00:00",
                "updateTime": "2021-03-12T06:11:16.000+00:00",
                "level": 2,
                "children": []
                "select": true//角色是否有这个权限（true有，false无）
              },
            ],
            "select": true
          }
        ]
      }
    }
## 用户
### 获取用户分页列表
>/userService/user/{page}/{limit}       
>get  
>page（当前页码），limit（每页记录数），username（用户名）
    
    {
        "success": true,
        "code": 200,
        "message": "成功",
        "data": {
            "total": 1,
            "items": [
                {
                    "id": 1,
                    "username": "测试",//用户名
                    "password": "123",//密码
                    "sex": "男",//性别
                    "phone": "",//手机号
                    "status": 0//状态(0默认，1停用)
                }
            ]
        }
    }
### 新增用户信息
> /userService/user/save  
> post

    {
      "password": "密码",
      "phone": "手机号",
      "sex": "性别（男，女）",
      "username": "用户名"
    }
### 修改用户信息
> /userService/user/update  
> post

    {
      "id": 3,
      "password": "密码",
      "phone": "手机号",
      "sex": "性别",
      "status": 状态(0默认，1停用),
      "username": "用户名"
    }
### 删除单个用户信息
>/userService/user/remove/{id}  
>delete
### 批量删除用户信息
>/userService/user/batchRemove  
>delete  
>[id,id,...]
### 为用户分配角色
>/userService/user/doAssign  
>post
>userId：用户id（Integer）
 roleIds：菜单id（Integer[],多个）
### 用户角色详情
>/userService/user/toAssign/{userId}  
>get

    {
      "success": true,
      "code": 200,
      "message": "成功",
      "data": {
        "roleList": [
          {
            "id": 1,
            "roleName": "测试角色",//角色名称
            "status": 0,//状态 0默认，1停用
            "note": "测试",//备注
            "createTime": "2021-03-17T05:37:14.000+00:00",
            "updateTime": "2021-03-17T05:37:14.000+00:00",
            "select": true//是否拥有这个角色（true拥有，false没有）
          }
        ]
      }
    }