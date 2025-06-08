<template>
  <div id="app">
    <!-- ✅ 상단 네비게이션 -->
    <header class="navbar">
      <div class="left">
        <router-link to="/" class="logo-link">
          <img src="/logo.png" alt="SSAFIT 로고" class="logo" />
        </router-link>

        <nav class="main-menu">
          <router-link to="/">개인 영상</router-link>
          <router-link to="/videos/youtube">유튜브 영상</router-link>
          <router-link to="/community">커뮤니티</router-link>
          <router-link to="/mypage">마이페이지</router-link>
        </nav>
      </div>

      <div class="auth">
        <router-link to="/register">회원가입</router-link>
        <span v-if="!isLogin"> | <router-link to="/login">로그인</router-link></span>
        <span v-else>
          | <a href="#" @click.prevent="logout">로그아웃</a>
          <div class="nickname">{{ nickName }} 회원님</div>
        </span>
      </div>
    </header>

    <!-- ✅ 페이지 본문 -->
    <main class="content">
      <router-view />
    </main>

    <!-- ✅ 푸터 추가 -->
    <footer class="footer">
      <p>© 2025 SSAFIT Corp. All rights reserved.</p>
      <p>Contact: support@ssafit.com | 사업자등록번호: 123-45-67890</p>
    </footer>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

export default {
  setup() {
    const isLogin = ref(false)
    const nickName = ref("")
    const router = useRouter()

    const checkLogin = () => {
      const token = localStorage.getItem('token')
      isLogin.value = !!token
      nickName.value = localStorage.getItem('nickName') || ""
    }

    const logout = () => {
      localStorage.removeItem('token')
      localStorage.removeItem('nickName')
      checkLogin()
      router.push('/login')
    }

    onMounted(() => {
      checkLogin()
      window.addEventListener('storage', checkLogin)
    })

    return {
      isLogin,
      nickName,
      logout
    }
  }
}
</script>

<style scoped>
#app {
  font-family: Arial, sans-serif;
  max-width: 1200px;
  margin: auto;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

/* ✅ 상단 바 */
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 20px;
  border-bottom: 1px solid #444;
  background-color: #373739; /* 배경 */
  color: #fff;
}

/* 왼쪽 */
.left {
  display: flex;
  align-items: center;
  gap: 40px;
}

.logo {
  height: 100px;
}
.logo-link {
  display: flex;
  align-items: center;
  text-decoration: none;
}

/* 메뉴 */
.main-menu {
  display: flex;
  gap: 20px;
}
.main-menu a {
  text-decoration: none;
  color: #fff;
  font-weight: bold;
}
.main-menu a:hover {
  color: #2a78c9;
}

/* 로그인 영역 */
.auth {
  font-size: 0.95rem;
  text-align: right;
}
.auth a {
  text-decoration: none;
  color: #ccc;
}
.auth a:hover {
  color: #2a78c9;
}
.nickname {
  display: block;
  margin-top: 4px;
  font-size: 0.9rem;
  color: #ccc;
}

/* ✅ 메인 컨텐츠 */
.content {
  flex: 1;
  padding-bottom: 60px; /* 푸터 공간 확보 */
}

/* ✅ 푸터 */
.footer {
  text-align: center;
  padding: 20px;
  background-color: #f5f5f5;
  font-size: 0.85rem;
  color: #666;
  border-top: 1px solid #ccc;
}
</style>
