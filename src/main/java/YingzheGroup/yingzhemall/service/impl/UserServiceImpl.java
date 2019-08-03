package YingzheGroup.yingzhemall.service.impl;

import YingzheGroup.yingzhemall.common.Const;
import YingzheGroup.yingzhemall.common.ServerResponse;
import YingzheGroup.yingzhemall.common.TokenCache;
import YingzheGroup.yingzhemall.dao.UserMapper;
import YingzheGroup.yingzhemall.pojo.User;
import YingzheGroup.yingzhemall.service.IUserService;
import YingzheGroup.yingzhemall.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("Wrong answer of the question.");
    }

    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken){
        if(org.apache.commons.lang3.StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("Error, the token is necessary");
        }
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if(validResponse.isSuccess()){
            return ServerResponse.createByErrorMessage("User does not exist");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if(org.apache.commons.lang3.StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMessage("Invalid token or expired token");
        }

        if(org.apache.commons.lang3.StringUtils.equals(forgetToken, token)){
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUsername(username, md5Password);

            if(rowCount > 0){
                return ServerResponse.createBySuccessMessage("Updated password successfully");
            }
        }else{
            return ServerResponse.createByErrorMessage("Wrong token, please get the reset token again.");
        }

        return ServerResponse.createByErrorMessage("Updated password failed");
    }

    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user){
        //防止横向越权，需要校验一下这个用户的旧密码，一定要指定是这个用户，因为我们会查询一个count(1)，如果不指定id，那么结果就是true(count>0)
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("Old password wrong.");
        }

        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        //updateByPrimaryKeySelective ----to be reviewed
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount > 0){
            return ServerResponse.createBySuccessMessage("Updated password successfully");
        }
        return ServerResponse.createByErrorMessage("Updated password failed");
    }

    public ServerResponse<User> updateInformation(User user){
        //username cannot be updated
        //email should be checked. Check if the new email is existing, the existing email cannot belong to current user

        int resultCount = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
        if(resultCount > 0){
            return ServerResponse.createByErrorMessage("Email is already existing, please change the email.");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount > 0){
            return ServerResponse.createBySuccess("Update user info successfully.", updateUser);
        }
        return ServerResponse.createByErrorMessage("Update user info failed");
    }

    public ServerResponse<User> getInformation(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            return ServerResponse.createByErrorMessage("Cannot find this user");
        }
        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }



}
