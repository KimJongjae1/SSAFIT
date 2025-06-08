<template>
  <div class="mypage">
    <section class="profile-box" v-if="!error">
      <div class="profile-info">
        <img src="/profile.png" class="profile-img" alt="Profile" />
        <div class="info-text">
          <h2>{{ name || "회원님" }}</h2>
          <p>📧 이메일: {{ email }}</p>
          <p>📞 헤드폰: {{ phone }}</p>
        </div>
      </div>
      <button class="edit-btn" @click="$router.push('/edit')">회원정보 수정</button>
    </section>

    <section v-else class="error">{{ error }}</section>

    <section class="friends-box">
      <router-link to="/message/received">📨 받은 메시지함</router-link>
      <h3>친구 목록</h3>
      <div class="friend-list">
        <div class="friend-item" v-for="friend in mutualFriends" :key="friend.userId">
          <img src="/profile.png" class="friend-img" />
          <span>{{ friend.nickName }} 님</span>
          <button class="chat-btn" @click="openMessageModal(friend.userId)">메세지 보내기</button>
          <button class="chat-btn" @click="unfollow(friend.userId)">삭제</button>
        </div>
      </div>

      <div class="friend-add-box">
        <h4>친구 추가</h4>
        <input v-model="targetId" type="text" placeholder="추가할 유저 ID" />
        <button @click="follow">추가</button>
        <p v-if="message" class="msg">{{ message }}</p>
      </div>
    </section>

    <!-- 메시지 작성 목록 -->
    <div v-if="showModal" class="message-modal">
      <div class="modal-content">
        <h3>메시지 보내기</h3>
        <textarea v-model="newMessageContent" placeholder="메시지를 입력하세요..."></textarea>
        <div class="modal-actions">
          <button @click="sendMessage">보내기</button>
          <button @click="closeMessageModal">닫기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const name = ref('')
const email = ref('')
const phone = ref('')
const error = ref('')

const mutualFriends = ref([])
const targetId = ref('')
const message = ref('')

const showModal = ref(false)
const newMessageContent = ref('')
const receiverId = ref(null)

const openMessageModal = (id) => {
  receiverId.value = id
  showModal.value = true
}

const closeMessageModal = () => {
  showModal.value = false
  newMessageContent.value = ''
}

const sendMessage = async () => {
  const senderId = localStorage.getItem('userId')
  console.log('📤 메시지 전송 시도:', {
    senderId: senderId,
    receiverId: receiverId.value,
    content: newMessageContent.value
  })

  if (!newMessageContent.value.trim()) return

  try {
    await axios.post("/api/message", {
      senderId: senderId,
      receiverId: receiverId.value,
      content: newMessageContent.value
    })
    alert('메시지가 전송되었습니다.')
    closeMessageModal()
  } catch (err) {
    console.error('❌ 메시지 전송 실패', err)
    alert('메시지 전송에 실패했습니다.')
  }
}


const loadUserInfo = async () => {
  const token = localStorage.getItem('token')
  try {
    const res = await axios.get('http://localhost:8080/api/user/mypage', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
    name.value = res.data.name
    email.value = res.data.email
    phone.value = res.data.phone
  } catch (e) {
    error.value = '회원 정보를 불러올 수 없습니다.'
  }
}

const loadMutualFriends = async () => {
  const token = localStorage.getItem('token')
  try {
    const res = await axios.get('http://localhost:8080/api/friend/mutual', {
      headers: { Authorization: `Bearer ${token}` }
    })
    mutualFriends.value = res.data
  } catch (e) {
    message.value = '친구 목록을 불러올 수 없습니다.'
  }
}

const follow = async () => {
  const token = localStorage.getItem('token')
  try {
    await axios.post(`http://localhost:8080/api/follow/by-id/${targetId.value}`, null, {
      headers: { Authorization: `Bearer ${token}` }
    })
    message.value = '친구 추가 완료'
    loadMutualFriends()
  } catch {
    message.value = '친구 추가 실패'
  }
}

const unfollow = async (followeeId) => {
  const token = localStorage.getItem('token')
  try {
    await axios.post('http://localhost:8080/api/friend/unfollow', { followeeId }, {
      headers: { Authorization: `Bearer ${token}` }
    })
    message.value = '삭제 완료'
    loadMutualFriends()
  } catch {
    message.value = '삭제 실패'
  }
}

onMounted(() => {
  loadUserInfo()
  loadMutualFriends()
})
</script>

<style scoped>
.mypage {
  font-family: Arial, sans-serif;
  padding: 20px;
}
.navbar {
  display: flex;
  align-items: center;
  gap: 30px;
  border-bottom: 1px solid #ccc;
  padding-bottom: 10px;
}
.navbar .logo {
  width: 100px;
}
.navbar nav a {
  margin-right: 20px;
  color: black;
  font-weight: bold;
  text-decoration: none;
}
.profile-box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border: 1px solid #aaa;
  border-radius: 10px;
  padding: 20px;
  margin: 30px 0;
}
.profile-info {
  display: flex;
  align-items: center;
}
.profile-img {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  margin-right: 20px;
}
.edit-btn {
  background-color: #fff;
  border: 1px solid #666;
  padding: 8px 16px;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
}
.friends-box h3 {
  margin-bottom: 20px;
}
.friend-list {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}
.friend-item {
  display: flex;
  align-items: center;
  gap: 12px;
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 12px 20px;
  width: 45%;
}
.friend-img {
  width: 40px;
  height: 40px;
}
.chat-btn {
  margin-left: auto;
  background-color: #eee;
  border: none;
  padding: 6px 14px;
  border-radius: 6px;
  cursor: pointer;
}
.friend-add-box {
  margin-top: 30px;
}
.friend-add-box input {
  padding: 8px;
  margin-right: 10px;
}
.friend-add-box button {
  padding: 8px 16px;
}
.msg {
  margin-top: 10px;
  color: green;
}
.error {
  color: red;
  text-align: center;
  font-weight: bold;
  margin-top: 40px;
}
.message-modal {
  position: fixed;
  top: 30%;
  left: 35%;
  background: white;
  padding: 20px;
  border: 2px solid #ccc;
  border-radius: 8px;
  z-index: 999;
  width: 400px;
}
.modal-content textarea {
  width: 100%;
  height: 100px;
  margin-top: 10px;
}
.modal-actions {
  margin-top: 10px;
  display: flex;
  justify-content: flex-end;
}
.modal-actions button {
  margin-left: 10px;
}
</style>
