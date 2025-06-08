<template>
  <div class="message-page">
    <h2>📨 받은 메시지함</h2>
    <div v-if="messages.length === 0" class="empty">
      받은 메시지가 없습니다.
    </div>

    <div class="message-list">
      <div class="message-card" v-for="msg in messages" :key="msg.messageId">
        <div class="message-header">
          <strong>보낸 사람:</strong> {{ msg.senderNickName }} 님
          <span class="date">{{ formatDate(msg.sentAt) }}</span>
        </div>
        <p class="content">{{ msg.content }}</p>
        <div class="footer">
          <span v-if="!msg.isRead" class="unread">안 읽음</span>
          <button v-if="!msg.isRead" @click="markAsRead(msg.messageId)">읽음 처리</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import axios from 'axios'

const messages = ref([])

const loadMessages = async () => {
  const userId = localStorage.getItem('userId')
  try {
    const res = await axios.get("/api/message/received/" + userId)
    messages.value = res.data
  } catch (err) {
    console.error('받은 메시지 불러오기 실패', err)
  }
}

const markAsRead = async (messageId) => {
  console.log("🟢 읽음 처리 시도 중: ", messageId)
  try {
    await axios.put(`/api/message/${messageId}/read`)
    const msg = messages.value.find(m => m.messageId === messageId)
    if (msg) msg.isRead = true  // ✅ 수동 갱신
  } catch (err) {
    console.error('❌ 읽음 처리 실패', err)
  }
}



const formatDate = (dt) => {
  return new Date(dt).toLocaleString()
}

onMounted(() => {
  loadMessages()
})
</script>

<style scoped>
.message-page {
  max-width: 800px;
  margin: 40px auto;
  padding: 20px;
  font-family: Arial, sans-serif;
}
.message-page h2 {
  margin-bottom: 20px;
  text-align: center;
}
.message-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}
.message-card {
  border: 1px solid #ccc;
  border-radius: 10px;
  padding: 15px 20px;
  background-color: #f9f9f9;
}
.message-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
  font-size: 0.95em;
  color: #333;
}
.content {
  font-size: 1em;
  margin-bottom: 10px;
  color: #222;
}
.footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.unread {
  color: red;
  font-weight: bold;
}
button {
  background-color: #eee;
  border: none;
  padding: 6px 14px;
  border-radius: 6px;
  cursor: pointer;
}
.empty {
  text-align: center;
  color: #888;
  margin-top: 60px;
  font-size: 1.1em;
}
</style>
