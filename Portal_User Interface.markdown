[MENU]

#### 1.Login

**/user/login.do**  post(use post to request), open get method for testing

> request

```
username,password
```
> response

fail
```
{
    "status": 1,
    "msg": "Wrong Password"
}
```

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

-------

#### 2.Registration
**/user/register.do**

> request

```
username,password,email,phone,question,answer
```


> response

success
```
{
    "status": 0,
    "msg": "Verify successful"
}
```


fail
```
{
    "status": 1,
    "msg": "Username does not exist"
}
```

--------

#### 3.Check the validity of the username

**/user/check_valid.do**

/check_valid.do?str=admin&type=username ——check the username



> request

```
str,type
str could be username or email based on the type value

```

>response

success
```
{
    "status": 0,
    "msg": "Verify successful"
}

```

fail
```
{
    "status": 1,
    "msg": "User already exists"
}

```

-----------


#### 4.Get login user information
**/user/get_user_info.do**


> request

```
No parameters
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
    "msg": "The user is not logged in and cannot get current user information."
}

```

------

#### 5.Password Forgotten
**/user/forget_get_question.do**

localhost:8080/user/forget_get_question.do?username=yingzhe



> request

```
username
```
> response

success

```
{
    "status": 0,
    "data": "The question"
}
```

fail
```
{
    "status": 1,
    "msg": "The user has not set a password recovery question"
}
```

---------

#### 6.Answer submission
**/user/forget_check_answer.do**

localhost:8080/user/forget_check_answer.do?username=aaa&question=aa&answer=sss


> request

```
username,question,answer
```

> response

There is a token in the correct response, we will use this when changing the password. Pass that to the next interface



success

```
{
    "status": 0,
    "data": "531ef4b4-9663-4e6d-9a20-fb56367446a5"
}
```

fail

```
{
    "status": 1,
    "msg": "Wrong answer"
}
```

------

#### 7.Reset password for password forgotten
**/user/forget_reset_password.do**

localhost:8080/user/forget_reset_password.do?username=aaa&passwordNew=xxx&forgetToken=531ef4b4-9663-4e6d-9a20-fb56367446a5

> request

```
username,passwordNew,forgetToken
```

> response

success

```
{
    "status": 0,
    "msg": "Password has been updated"
}
```

fail
```
{
    "status": 1,
    "msg": "Changing operation is invalid"
}
```
或
```
{
    "status": 1,
    "msg": "Token has expired"
}
```

------
#### 8.Reset password for login status
**/user/reset_password.do**

> request

```
passwordOld,passwordNew

```

> response

success

```
{
    "status": 0,
    "msg": "Password has been updated"
}
```

fail
```
{
    "status": 1,
    "msg": "Old password wrong"
}
```

------
#### 9.Update personal information for login status
**/user/update_information.do**

> request

```
email,phone,question,answer
```

> response

success

```
{
    "status": 0,
    "msg": "Update personal information successfully"
}
```

fail
```
{
    "status": 1,
    "msg": "User not logged in"
}
```

------
#### 10.Get the details of the currently logged in user and force login
**/user/get_information.do**


> request

```
No parameters
```
> response

success
```
{
    "status": 0,
    "data": {
        "id": 1,
        "username": "admin",
        "password": "",
        "email": "admin@163.com",
        "phone": "13800138000",
        "question": "question",
        "answer": "answer",
        "role": 1,
        "createTime": 1478422605000,
        "updateTime": 1491305256000
    }
}
```

fail
```
{
    "status": 10,
    "msg": "The user is not logged in and cannot obtain current user information,status=10,force login"
}

```

------


#### 11.Log out
**/user/logout.do**

> request

```
No parameters
```

> response

success

```
{
    "status": 0,
    "msg": "Log out successfully"
}
```

fail
```
{
    "status": 1,
    "msg": "Server exception"
}
```

------



#### Template

**/user/.do**

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
