package hello.dao;

import hello.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{username}")
    User findUserByUsername(@Param("username") String username);

    @Select("insert into user(username, encrypted_password, created_at, updated_at) " +
            "values(#{username}, #{encryptedPassword}, now(), now())")
    void save(@Param("username") String username, @Param("encryptedPassword") String encryptedPassword);

    @Select("select * from user where id = #{id}")
    User getUserById(@Param("id") Integer id);
}
