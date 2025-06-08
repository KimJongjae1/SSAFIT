<template>
  <div class="home" style="padding: 20px;">


    <!-- 인트로 메시지 -->
    <section class="intro-box">
      <p>유튜브 영상을 등록하고<br />운동 정보를 공유해보세요!</p>
    </section>

    <!-- 등록 폼 -->
    <div class="register-box">
      <h2>📤 영상 등록</h2>
      <form @submit.prevent="submitVideo">
        <input v-model="title" placeholder="제목" required />
        <textarea v-model="description" placeholder="설명" rows="3" required />
        <input v-model="youtubeUrl" placeholder="유튜브 URL 입력 시 자동 처리" />
        <input v-model="thumbnail" placeholder="썸네일 URL" readonly />
        <input v-model="videoUrl" placeholder="영상 URL" readonly />
        <button type="submit">등록</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()
const title = ref('')
const description = ref('')
const youtubeUrl = ref('')
const youtubeId = ref('')
const thumbnail = ref('')
const videoUrl = ref('')

watch(youtubeUrl, (newUrl) => {
  const match = newUrl.match(/(?:v=|\/)([0-9A-Za-z_-]{11})/)
  if (match) {
    youtubeId.value = match[1]
    thumbnail.value = `https://img.youtube.com/vi/${youtubeId.value}/0.jpg`
    videoUrl.value = `https://www.youtube.com/embed/${youtubeId.value}`
  }
})

const submitVideo = async () => {
  const token = localStorage.getItem('token')
  try {
    await axios.post('http://localhost:8080/api/videos', {
      title: title.value,
      description: description.value,
      youtubeId: youtubeId.value,
      thumbnail: thumbnail.value,
      videoUrl: videoUrl.value,
    }, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
    alert('영상이 등록되었습니다!')
    router.push('/videos/youtube')
  } catch (err) {
    alert('등록 실패: ' + (err.response?.data || '서버 오류'))
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
  gap: 12px;
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
