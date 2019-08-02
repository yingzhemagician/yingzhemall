package YingzheGroup.yingzhemall.service;

import YingzheGroup.yingzhemall.common.ServerResponse;
import YingzheGroup.yingzhemall.pojo.User;

public interface IUserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str, String type);

    ServerResponse selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username, String question, String answer);
}
