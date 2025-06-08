
package com.ssafy.ssafit.friend.service;

import com.ssafy.ssafit.friend.dao.FriendDao;
import com.ssafy.ssafit.user.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendDao friendDao;

    @Override
    public boolean follow(int followerId, int followeeId) {
        return friendDao.insertFollow(followerId, followeeId) > 0;
    }

    @Override
    public boolean unfollow(int followerId, int followeeId) {
        return friendDao.deleteFollow(followerId, followeeId) > 0;
    }

    @Override
    public List<User> getMutualFriends(int userId) {
        return friendDao.selectMutualFriends(userId);
    }
}
