package YingzheGroup.yingzhemall.service.impl;

import YingzheGroup.yingzhemall.common.ServerResponse;
import YingzheGroup.yingzhemall.dao.UserMapper;
import YingzheGroup.yingzhemall.pojo.User;
import YingzheGroup.yingzhemall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("Username does not exist");
        }

        //todo Login password MD5

        User user = userMapper.selectLogin(username, password);
        if(user == null){
            return ServerResponse.createByErrorMessage("Wrong password");
        }

        user.setPassword(org.apache.commons.lang.StringUtils.EMPTY);
        return ServerResponse.createBySuccess("Login successfully", user);
    }
}
