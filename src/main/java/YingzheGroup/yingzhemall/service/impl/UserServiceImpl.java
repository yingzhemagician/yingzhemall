package YingzheGroup.yingzhemall.service.impl;

import YingzheGroup.yingzhemall.common.Const;
import YingzheGroup.yingzhemall.common.ServerResponse;
import YingzheGroup.yingzhemall.common.TokenCache;
import YingzheGroup.yingzhemall.dao.UserMapper;
import YingzheGroup.yingzhemall.pojo.User;
import YingzheGroup.yingzhemall.service.IUserService;
import YingzheGroup.yingzhemall.util.MD5Util;
import net.sf.jsqlparser.schema.Server;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.tools.jstat.Token;

import java.util.UUID;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("Username does not exist.");
        }

        //todo Login password MD5
        String md5Password = MD5Util.MD5EncodeUtf8(password);

        User user = userMapper.selectLogin(username, md5Password);
        if(user == null){
            return ServerResponse.createByErrorMessage("Wrong password.");
        }

        user.setPassword(org.apache.commons.lang.StringUtils.EMPTY);
        return ServerResponse.createBySuccess("Login successfully.", user);
    }

    public ServerResponse<String> register(User user){
        ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if(!validResponse.isSuccess()){
            return validResponse;
        }

        validResponse = this.checkValid(user.getPassword(), Const.EMAIL);
        if(!validResponse.isSuccess()){
            return validResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);

        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("Register fail.");
        }

        return ServerResponse.createBySuccessMessage("Registered successfully.");
    }

    public ServerResponse<String> checkValid(String str, String type){
        if(org.apache.commons.lang.StringUtils.isNotBlank(type)){
            //start checking
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if(resultCount > 0){
                    return ServerResponse.createByErrorMessage("Username already exist.");
                }
            }

            if(Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if(resultCount > 0){
                    return ServerResponse.createByErrorMessage("Email already exist.");
                }
            }

        }else{
            return ServerResponse.createByErrorMessage("Parameters wrong.");
        }
        return  ServerResponse.createBySuccessMessage("Checking successfully.");
    }

    public ServerResponse selectQuestion(String username){

        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if(validResponse.isSuccess()){
            return ServerResponse.createByErrorMessage("User does not exist.");
        }

        String question = userMapper.selectQuestionByUsername(username);
        if(org.apache.commons.lang.StringUtils.isNotBlank(question)){
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("The question is blank.");
    }

    public ServerResponse<String> checkAnswer(String username, String question, String answer){
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if(resultCount > 0){
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey("token_" + username, forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("Wrong answer of the question.");
    }

}
