package com.ssafy.ssafit.friend.service;

import com.ssafy.ssafit.friend.dao.FollowDao;
// import com.ssafy.ssafit.friend.dao.ChatRoomDao; // ❌ 삭제
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowDao followDao;

    // ❌ 이미 제거된 DAO 참조 → 주석 또는 삭제
    // @Autowired
    // private ChatRoomDao chatRoomDao;

    @Override
    public boolean follow(int followerId, int followeeId) {
        // 이미 팔로우 상태인지 확인
        if (followDao.exists(followerId, followeeId)) {
            return false;
        }

        // 팔로우 추가
        followDao.insert(followerId, followeeId);

        // ❌ 채팅방 생성 로직도 함께 제거 (우리는 ChatRestController에서 처리함)
        // int user1 = Math.min(followerId, followeeId);
        // int user2 = Math.max(followerId, followeeId);
        // if (!chatRoomDao.exists(user1, user2)) {
        //     chatRoomDao.createRoom(user1, user2);
        // }

        return true;
    }

    @Override
    public boolean isMutualFriend(int userId1, int userId2) {
        return followDao.isMutualFriend(userId1, userId2);
    }
}
