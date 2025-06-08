
package com.ssafy.ssafit.friend.service;

import com.ssafy.ssafit.user.dto.User;
import java.util.List;

public interface FriendService {
    boolean follow(int followerId, int followeeId);
    boolean unfollow(int followerId, int followeeId);
    List<User> getMutualFriends(int userId);
}
