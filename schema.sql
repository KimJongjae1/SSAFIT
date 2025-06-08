DROP DATABASE IF EXISTS ssafit_db;
CREATE DATABASE ssafit_db CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE ssafit_db;

CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    id VARCHAR(30) NOT NULL,           -- 아이디
    password VARCHAR(30) NOT NULL,     -- 비밀번호
    name VARCHAR(20) NOT NULL,         -- 이름
    email VARCHAR(30) NOT NULL,        -- 이메일
    nick_name VARCHAR(20) NOT NULL,    -- 닉네임
    phone VARCHAR(20)                  -- 휴대폰 번호 (NULL 허용)
);

-- 영상 테이블
CREATE TABLE video (
    video_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    youtube_id VARCHAR(50),             -- 유튜브 영상이면 값, 개인 영상이면 NULL
    thumbnail VARCHAR(255),             -- 썸네일 url (유튜브/직접업로드 모두)
    video_url VARCHAR(255) NOT NULL,    -- 영상 재생 url (유튜브 임베드 or 내가 업로드한 mp4 url)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    type VARCHAR(10),
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

-- 리뷰 테이블
CREATE TABLE review (
    review_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(1000) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    video_id BIGINT NOT NULL,
    user_id INT NOT NULL,
    nick_name VARCHAR(100),
    FOREIGN KEY (video_id) REFERENCES video(video_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

-- 팔로우 테이블
CREATE TABLE IF NOT EXISTS follow (
    follower_id INT NOT NULL,   -- 친구 추가한 사람 (나)
    followee_id INT NOT NULL,   -- 친구로 추가된 대상
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (follower_id, followee_id),
    FOREIGN KEY (follower_id) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (followee_id) REFERENCES user(user_id) ON DELETE CASCADE
);

CREATE TABLE message (
    message_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id INT NOT NULL,           -- 보내는 사용자 ID
    receiver_id INT NOT NULL,         -- 받는 사용자 ID
    content TEXT NOT NULL,            -- 메시지 내용
    sent_at DATETIME DEFAULT CURRENT_TIMESTAMP,  -- 보낸 시각
    is_read BOOLEAN DEFAULT FALSE,    -- 읽음 여부
    FOREIGN KEY (sender_id) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES user(user_id) ON DELETE CASCADE
);


-- 사용자 예시
INSERT INTO user (id, password, name, email, nick_name) VALUES
('hong123', 'pass123', '홍길동', 'hong@example.com', '홍깅이'),
('kim456', 'pass456', '김철수', 'kim@example.com', '철킴');

-- 영상 예시
INSERT INTO video (title, description, youtube_id, thumbnail, video_url, user_id)
VALUES
(
  'Epic Cinematic Music Mix',
  '에픽 시네마틱 음악 모음 영상입니다.',
  'F-Jd4kI6rdM',
  'https://img.youtube.com/vi/F-Jd4kI6rdM/0.jpg',
  'https://www.youtube.com/watch?v=F-Jd4kI6rdM',
  1
),
(
  '멜로디가 좋은 음악 모음',
  '감성 멜로디 음악 모음 유튜브 영상입니다.',
  '3EzqF-VP8Yk',
  'https://img.youtube.com/vi/3EzqF-VP8Yk/0.jpg',
  'https://www.youtube.com/watch?v=3EzqF-VP8Yk',
  2
);


-- 리뷰 예시
INSERT INTO review (content, video_id, user_id) VALUES
('정말 힘들었지만 효과 만점!', 1, 1),
('하체 운동 루틴 너무 좋아요!', 1, 2),
('복근 터지는 줄 알았어요', 2, 1),
('진짜 따라하면서 힘들었지만 성취감이 최고네요.', 2, 2);

-- 팔로우 예시
INSERT INTO follow (follower_id, followee_id) VALUES
(1, 2), -- 1번 회원이 2번 회원을 팔로우
(2, 1); -- 2번 회원이 1번 회원을 팔로우

select * from user;
select * from follow;
