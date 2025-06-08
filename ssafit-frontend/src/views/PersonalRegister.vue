<template>
  <div class="home" style="padding: 20px;">

    <!-- 소개 영역 -->
    <section class="intro-box">
      <p>운동 영상을 직접 업로드하고<br />자신의 근성장을 기록해보세요!</p>
    </section>

    <!-- 등록 폼 -->
    <div class="register-box">
      <h2>📁 개인 영상 등록</h2>
      <form @submit.prevent="submit" enctype="multipart/form-data">
        <input v-model="title" type="text" placeholder="제목 입력" required />
        <textarea v-model="description" placeholder="설명 입력" rows="3" required></textarea>

        <div>
          <label>🎬 영상 파일</label>
          <input type="file" @change="onVideoChange" accept="video/*" required />
        </div>

        <div>
          <label>🖼 썸네일 이미지</label>
          <input type="file" @change="onThumbnailChange" accept="image/*" required />
        </div>

        <button type="submit">업로드</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()

const title = ref('')
const description = ref('')
const videoFile = ref(null)
const thumbnailFile = ref(null)

const onVideoChange = (e) => {
  videoFile.value = e.target.files[0]
}

const onThumbnailChange = (e) => {
  thumbnailFile.value = e.target.files[0]
}

const submit = async () => {
  const token = localStorage.getItem("token")
  if (!token) return alert("로그인이 필요합니다.")

  if (!videoFile.value || !thumbnailFile.value) {
    return alert("모든 파일을 선택해주세요.")
  }

  const formData = new FormData()
  formData.append("title", title.value)
  formData.append("description", description.value)
  formData.append("video", videoFile.value)
  formData.append("thumbnail", thumbnailFile.value)

  try {
    await axios.post("http://localhost:8080/api/videos/personal", formData, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
    alert("업로드 성공!")
    router.push("/") // 홈으로 이동
  } catch (e) {
    console.error("업로드 실패:", e)
    alert("업로드 실패")
  }
}
</script>

<style scoped>
.home {
  font-family: Arial, sans-serif;
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
.intro-box {
  background-color: #2a78c9;
  color: white;
  text-align: center;
  padding: 20px;
  border-radius: 10px;
  margin-top: 20px;
  font-size: 18px;
  line-height: 1.5;
}

.register-box {
  background: #f9f9f9;
  max-width: 500px;
  margin: 40px auto;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.register-box input,
.register-box textarea {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 6px;
  box-sizing: border-box;
}

.register-box label {
  font-weight: bold;
  margin-bottom: 5px;
  display: block;
}

.register-box button {
  background-color: #2a78c9;
  color: white;
  border: none;
  padding: 12px;
  font-weight: bold;
  border-radius: 6px;
  cursor: pointer;
  transition: background 0.3s;
}
.register-box button:hover {
  background-color: #1a5fa1;
}
</style>
