package com.atguigu.atguigu_mp;

import com.atguigu.atguigu_mp.entity.User;
import com.atguigu.atguigu_mp.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPlusApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void select() {
//        List<User> users = userMapper.selectList(null);
//        users.forEach(System.err::println);
//
//        List<Integer> i= new ArrayList<Integer>();
//        i.add(1);
//        i.add(3);
//        List<User> users1 = userMapper.selectBatchIds(i);
//        users1.forEach(System.err::println);


//        HashMap<String, Object> map = new HashMap<>();
//        map.put("name", "Helen");
//        map.put("age", 18);
//        List<User> users2 = userMapper.selectByMap(map);
//        users2.forEach(System.out::println);

//        Page<User> page = new Page<User>(2,2);
//        userMapper.selectPage(page,null);
//
//        page.getRecords().forEach(System.out::println);
//        System.out.println(page.getCurrent());
//        System.out.println(page.getPages());
//        System.out.println(page.getSize());
//        System.out.println(page.getTotal());
//        System.out.println(page.hasNext());
//        System.out.println(page.hasPrevious());


        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.notLike("name","唐");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void inster() {
        User user = new User();
        user.setName("大师");
        user.setAge(18);
        user.setEmail("dashi@163.com");
        //返回影响的行数，ID自动填写
        int insert = userMapper.insert(user);
    }

    @Test
    public void update() {
        User user = userMapper.selectById(1L);
        user.setName("唐三");
        userMapper.updateById(user);
    }

    @Test
    public void delete() {
        System.out.println(userMapper.deleteById(3L));
    }

    @Test
    public void queryWrapper() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("name");
        userMapper.selectList(wrapper).forEach(System.err::println);

    }

}
