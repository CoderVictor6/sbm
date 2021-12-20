package demo.sbm.mapper;

import demo.sbm.entity.User;

public interface UserMapper {
    int insert(User record);

    int updateByPrimaryKey(User record);

    User findById(Long id);
}