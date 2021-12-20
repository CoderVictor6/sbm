package demo.sbm.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import demo.sbm.entity.User2;

public interface User2Mapper {
    int insert(User2 record);
    List<User2> findAll();


}