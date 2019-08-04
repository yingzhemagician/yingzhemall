#### 1.Backstage administrator login

**/manage/user/login.do**


> request

```
String username,
String password
```

> response

success

```
{
    "status": 0,
    "data": {
        "id": 12,
        "username": "aaa",
        "email": "aaa@163.com",
        "phone": null,
        "role": 0,
        "createTime": 1479048325000,
        "updateTime": 1479048325000
    }
}
```

fail
```
{
    "status": 1,
    "msg": "Wrong password"
}
```

------


#### 2.User list

**/manage/user/list.do**


> request

```
pageSize(default=10)
pageNum(default=1)
```

> response

success

```
{
    "status": 0,
    "data": {
        "pageNum": 1,
        "pageSize": 3,
        "size": 3,
        "orderBy": null,
        "startRow": 1,
        "endRow": 3,
        "total": 16,
        "pages": 6,
        "list": [
            {
                "id":17,
                "username":"rosen",
                "password":"",
                "email":"rosen1@happymmall.com",
                "phone":"15011111111",
                "question":"hahaha",
                "answer":"hehehe",
                "role":0,
                "createTime":1489719093000,
                "updateTime":1513682138000
            },
            {
                "id":17,
                "username":"rosen",
                "password":"",
                "email":"rosen1@happymmall.com",
                "phone":"15011111111",
                "question":"hahaha",
                "answer":"hehehe",
                "role":0,
                "createTime":1489719093000,
                "updateTime":1513682138000
            }
        ],
        "firstPage": 1,
        "prePage": 0,
        "nextPage": 2,
        "lastPage": 6,
        "isFirstPage": true,
        "isLastPage": false,
        "hasPreviousPage": false,
        "hasNextPage": true,
        "navigatePages": 8,
        "navigatepageNums": [
          1,
          2,
          3,
          4,
          5,
          6
        ]
    }
}
```

fail
```
{
  "status": 10,
  "msg": "User is not logged in, please log in"
}


or

{
  "status": 1,
  "msg": "Permission denied"
}



```
------

#### 3.Template

**/REPLACE/.do**

> request

```
k
```

> response

success

```
k
```

fail
```
k
```

------