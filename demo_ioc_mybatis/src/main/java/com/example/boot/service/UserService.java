package com.example.boot.service;

import com.example.boot.entity.User;
import com.example.boot.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:UserService
 * Package:com.example.boot.service
 * Description:
 *
 * @Date:2021/12/1 16:52
 * @Author:qs@1.com
 */
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createUser(User user) {
        userMapper.insertSelective(user);
    }

    public void updateUser(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    public void findExample() {
        log.info("-----------------------按主键查询：where id=100------------------------");
        User user = userMapper.selectByPrimaryKey(100);
        log.info(user.toString());

        log.info("-----------------------查询：where sex=1------------------------");
        User sex = new User();
        sex.setSex((byte) 1);
        List<User> list = userMapper.select(sex);
        log.info("查询sex=1的条数，{}", list.size());

        log.info("-----------------------查询：where username=? and password=?------------------------");
        User user1 = new User();
        user1.setUsername("update100");
        user1.setPassword("update100");
        User obj = userMapper.selectOne(user1);
        log.info(obj.toString());

        /**
         * 复杂查询使用Example.Criteria
         */

        log.info("-----------------------Example.Criteria查询：where username=? and password=?------------------------");
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", "update100");
        criteria.andEqualTo("password", "update100");
        List<User> users = userMapper.selectByExample(example);
        log.info("Example.Criteria查询结果，{}", users.toString());

        log.info("-----------------------Example.Criteria模糊查询：where username like ?------------------------");
        example.clear();
        criteria = example.createCriteria();
        criteria.andLike("username", "%100%");
        users = userMapper.selectByExample(example);
        log.info("Example.Criteria模糊查询结果，{}", users.toString());

        log.info("-----------------------Example.Criteria排序：where username like ? order by id desc -----------------------");
        example.clear();
        example.setOrderByClause("id desc");
        criteria = example.createCriteria();
        criteria.andLike("username", "%100%");
        users = userMapper.selectByExample(example);
        log.info("Example.Criteria查询排序结果，{}", users.toString());

        log.info("-----------------------Example.Criteria in查询：where id in(1,2) -----------------------");
        example.clear();
        criteria = example.createCriteria();
        List ids = new ArrayList();
        ids.add(1);
        ids.add(2);
        criteria.andIn("id", ids);
        users = userMapper.selectByExample(example);
        log.info("Example.Criteria in查询结果，{}", users.toString());

        log.info("-----------------------分页查询1-----------------------");
        User user2 = new User();
        user2.setSex((byte) 1);
        int count = userMapper.selectCount(user2);
        log.info("分页例子：总条数{}", count);
        users = userMapper.selectByRowBounds(user2, new RowBounds(0, 10));
        for (User userModel : users) {
            log.info("分页例子：第一页{}", userModel.toString());
        }

        log.info("-----------------------Example.Criteria分页查询2-----------------------");
        example.clear();
        criteria = example.createCriteria();
        criteria.andEqualTo("sex", 1);
        count = userMapper.selectCountByExample(example);
        log.info("分页例子：总条数{}", count);
        users = userMapper.selectByExampleAndRowBounds(example, new RowBounds(1, 10));
        for (User userModel : users) {
            log.info("分页例子：第二页{}", userModel.toString());
        }


    }
}
