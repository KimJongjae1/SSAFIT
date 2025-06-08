
<template>
  <div class="edit-user">
    <header class="navbar">
      <img src="/logo.png" class="logo" alt="SSAFIT Logo" />
      <nav>
        <a href="/">홈</a>
        <a href="/mypage">마이페이지</a>
      </nav>
    </header>

    <section class="edit-box">
      <h2>회원정보 수정</h2>
      <form @submit.prevent="updateUser">
        <div class="input-group">
          <label>새 비밀번호</label>
          <input v-model="password" type="password" placeholder="새 비밀번호" />
        </div>
        <div class="input-group">
          <label>이메일</label>
          <input v-model="email" :placeholder="originEmail || '이메일'" />
        </div>
        <div class="input-group">
          <label>닉네임</label>
          <input v-model="nickName" :placeholder="originNickName || '닉네임'" />
        </div>
        <div class="input-group">
          <label>전화번호</label>
          <input v-model="phone" :placeholder="originPhone || '010-xxxx-xxxx'" />
        </div>
        <button type="submit" class="btn">수정하기</button>
      </form>
      <p v-if="message" class="msg">{{ message }}</p>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const password = ref('')
const email = ref('')
const nickName = ref('')
const phone = ref('')

const originEmail = ref('')
const originNickName = ref('')
const originPhone = ref('')

const message = ref('')
const router = useRouter()

const updateUser = async () => {
  const token = localStorage.getItem('token')
  try {
    await axios.put('http://localhost:8080/api/user/update', {
      password: password.value,
      email: safe(email.value, originEmail.value),
      nickName: safe(nickName.value, originNickName.value),
      phone: safe(phone.value, originPhone.value)
    }, {
      headers: { Authorization: `Bearer ${token}` }
    })
    message.value = '수정이 완료되었습니다.'
    setTimeout(() => {
      router.push('/mypage')
    }, 1000)
  } catch (e) {
    console.error('수정 실패', e)
    message.value = '수정에 실패했습니다.'
  }
}

const safe = (val, fallback) => {
  return val && val.trim() !== '' ? val.trim() : fallback
}

onMounted(async () => {
  try {
    const token = localStorage.getItem('token')
    const res = await axios.get('http://localhost:8080/api/user/mypage', {
      headers: { Authorization: `Bearer ${token}` }
    })
    originEmail.value = res.data.email
    originNickName.value = res.data.nickName
    originPhone.value = res.data.phone
  } catch (e) {
    console.error('회원정보 조회 실패', e)
  }
})
</script>

<style scoped>
.edit-user {
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
.logo {
  width: 100px;
}
.navbar nav a {
  margin-right: 20px;
  color: black;
  font-weight: bold;
  text-decoration: none;
}
.edit-box {
  max-width: 500px;
  margin: 40px auto;
  padding: 20px;
  border: 1px solid #aaa;
  border-radius: 10px;
  background-color: #fdfdfd;
}
.edit-box h2 {
  text-align: center;
  margin-bottom: 20px;
}
.input-group {
  margin-bottom: 15px;
}
.input-group label {
  display: block;
  margin-bottom: 6px;
  font-weight: bold;
}
.input-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
}
.btn {
  width: 100%;
  padding: 12px;
  background-color: black;
  color: white;
  font-weight: bold;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  margin-top: 10px;
}
.msg {
  margin-top: 15px;
  color: green;
  text-align: center;
}
</style>
