package demo.sbm.mapper;
import java.util.List;

import demo.sbm.entity.User1;

public interface User1Mapper {
    int insert(User1 record);
    List<User1> findAll();
}