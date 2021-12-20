package demo.sbm.processor;

import demo.sbm.entity.User;
import demo.sbm.entity.User1;
import demo.sbm.mapper.UserMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class U1Processor implements ItemProcessor<User1, User> {
    private final UserMapper userMapper;

    public U1Processor(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User process(User1 u1) {
        User user = userMapper.findById(u1.getId());
        // 返回null的数据不会进入writer
        if (user == null)
            return null;
        // user.name = u1.name
        user.setName(u1.getName());
        return user;
    }
}
