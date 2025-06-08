<script setup>
import { ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

const id = ref("");
const password = ref("");
const error = ref("");
const router = useRouter();

const login = async () => {
  console.log("✅ 로그인 시도됨"); // ✅ 여기에 콘솔 찍어서 동작 확인
  error.value = "";
  try {
    const res = await axios.post("http://localhost:8080/api/user/login", {
      id: id.value,
      password: password.value,
    });

    localStorage.setItem("token", res.data.token);
    localStorage.setItem("nickName", res.data.nickName) // ✅ 닉네임 저장
    localStorage.setItem("userId", res.data.userId);
    window.location.href = "/"  // 강제 새로고침

    router.push("/");
  } catch (e) {
    error.value = "로그인 실패! 아이디 또는 비밀번호를 확인하세요.";
    console.error(e);
  }
};
</script>

<template>
  <div class="auth-container">
    <img src="/logo2.png" alt="SSAFIT Logo" class="logo" />
    <form @submit.prevent="login" class="form">
      <input v-model="id" placeholder="아이디" autocomplete="username" required />
      <input v-model="password" type="password" placeholder="비밀번호" autocomplete="current-password" required />
      <button type="submit" class="btn">로그인</button>
    </form>
    <p class="footer-text">
      계정이 없으신가요?
      <router-link to="/register">회원가입</router-link>
    </p>
    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>

<style scoped>
.auth-container {
  max-width: 360px;
  margin: 80px auto;
  text-align: center;
  font-family: "Arial";
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
.footer-text {
  margin-top: 12px;
  font-size: 14px;
}
.error {
  color: red;
  margin-top: 8px;
}
</style>
