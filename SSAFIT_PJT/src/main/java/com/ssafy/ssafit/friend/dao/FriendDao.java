
package com.ssafy.ssafit.friend.dao;

import com.ssafy.ssafit.user.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface FriendDao {
    int insertFollow(@Param("followerId") int followerId, @Param("followeeId") int followeeId);
    int deleteFollow(@Param("followerId") int followerId, @Param("followeeId") int followeeId);
    List<User> selectMutualFriends(@Param("userId") int userId);
}
