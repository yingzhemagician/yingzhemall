package YingzheGroup.yingzhemall.service;

import YingzheGroup.yingzhemall.common.ServerResponse;
import YingzheGroup.yingzhemall.pojo.User;

public interface IUserService {

    ServerResponse<User> login(String username, String password);
}
