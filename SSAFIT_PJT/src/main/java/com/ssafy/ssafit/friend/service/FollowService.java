
package com.ssafy.ssafit.friend.service;

public interface FollowService {
    boolean follow(int followerId, int followeeId);
    boolean isMutualFriend(int userId1, int userId2);

}
