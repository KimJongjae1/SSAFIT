
package com.ssafy.ssafit.friend.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FollowDao {
    boolean exists(@Param("followerId") int followerId, @Param("followeeId") int followeeId);
    void insert(@Param("followerId") int followerId, @Param("followeeId") int followeeId);
    boolean isMutualFriend(@Param("userId1") int userId1, @Param("userId2") int userId2);
}
