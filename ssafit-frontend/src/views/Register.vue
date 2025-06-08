
<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const id = ref('')
const password = ref('')
const name = ref('')
const email = ref('')
const nickName = ref('')
const phone = ref('')
const error = ref('')
const router = useRouter()

const register = async () => {
  error.value = ''
  try {
    await axios.post('http://localhost:8080/api/user/signup', {
      id: id.value,
      password: password.value,
      name: name.value,
      email: email.value,
      nickName: nickName.value,
      phone: phone.value
    })
    alert('회원가입이 완료되었습니다! 이제 로그인하세요.')
    router.push('/login')
  } catch (e) {
    error.value = e.response?.data || '회원가입 실패'
  }
}
</script>

<template>
  <div class="auth-container">
    <img src="/logo2.png" alt="SSAFIT Logo" class="logo" />
    <form @submit.prevent="register" class="form">
      <input v-model="id" placeholder="아이디" required />
      <input v-model="password" type="password" placeholder="비밀번호" required />
      <input v-model="name" placeholder="이름" required />
      <input v-model="email" type="email" placeholder="이메일" required />
      <input v-model="nickName" placeholder="닉네임" required />
      <input v-model="phone" placeholder="휴대전화 번호" required />
      <button type="submit" class="btn">회원가입</button>
    </form>
    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>

<style scoped>
.auth-container {
  max-width: 380px;
  margin: 80px auto;
  text-align: center;
  font-family: 'Arial';
}
.logo {
  width: 120px;
  margin-bottom: 24px;
}
.form input {
  width: 100%;
  padding: 10px;
  margin: 6px 0;
  border: 1px solid #ccc;
  border-radius: 4px;
}
.btn {
  width: 100%;
  padding: 12px;
  margin-top: 10px;
  background-color: black;
  color: white;
  font-weight: bold;
  border: none;
  border-radius: 4px;
}
.error {
  color: red;
  margin-top: 8px;
}
</style>
