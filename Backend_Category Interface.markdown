# Backend Category Management Interface

[TOC]

------

####1. Get subnode of category(same level)

http://localhost:8080/manage/category/get_category.do

http://localhost:8080/manage/category/get_category.do?categoryId=0

http://localhost:8080/manage/category/get_category.do?categoryId=2

**/manage/category/get_category.do**

> request

```
categoryId(default=0)
```

> response

success

```json

{
    "status": 0,
    "data": [
        {
            "id": 2,
            "parentId": 1,
            "name": "Mobile Phone",
            "status": true,
            "sortOrder": 3,
            "createTime": 1479622913000,
            "updateTime": 1479622913000
        },
        {
            "id": 4,
            "parentId": 1,
            "name": "Fixed telephone",
            "status": true,
            "sortOrder": 5,
            "createTime": 1480059936000,
            "updateTime": 1480491941000
        }
    ]
}

```


http://localhost:8080/manage/category/get_category.do?categoryId=19


fail
```json
{
    "status": 10,
    "msg": "User doesn't login, please login."
}
or
{
    "status": 1,
    "msg": "Cannot find this category subnode."
}
```

------

####2. Add category node

**/manage/category/add_category.do**

> request

```
parentId(default=0)
categoryName
```

> response

success

```json
{
    "status": 0,
    "msg": "Add category successfully."
}
```

fail
```json
{
    "status":1,
    "msg": "Add category failed."
}
```

------

####3. Set category name

http://localhost:8080/manage/category/set_category_name.do?categoryId=999&categoryName=%E5%98%BB%E5%98%BB
http://localhost:8080/manage/category/set_category_name.do?categoryId=1&categoryName=%E5%98%BB%E5%98%BB


**/manage/category/set_category_name.do**

> request

```
categoryId
categoryName
```

> response

success

```json
{
    "status": 0,
    "msg": "Updated category name successfully."
}
```

fail
```json
{
    "status": 1,
    "msg": "Updated category name failed."
}
```

------


####4. Get current category id and the subnode category id recursively

http://localhost:8080/manage/category/get_deep_category.do?categoryId=100001


**/manage/category/get_deep_category.do**

> request

```
categoryId
```

> response

success

```json
{
    "status": 0,
    "data": [
        100009,
        100010,
        100001,
        100006,
        100007,
        100008
    ]
}
```

fail
```json
{
    "status": 1,
    "msg": "Wrong permission."
}
```

------

