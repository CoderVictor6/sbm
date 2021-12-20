package demo.sbm.processor;

import demo.sbm.entity.User;
import demo.sbm.entity.User2;
import demo.sbm.mapper.UserMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class U2Processor implements ItemProcessor<User2, User> {
    private final UserMapper userMapper;

    public U2Processor(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User process(User2 u2) {
        User user = userMapper.findById(u2.getId());
        // 返回null的数据不会进入writer
        if (user == null)
            return null;
        // user.age = u2.age
        user.setAge(u2.getAge());
        return user;
    }
}
