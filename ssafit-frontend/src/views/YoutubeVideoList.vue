<template>
  <div class="home" style="padding: 20px;">

    <!-- 소개 영역 -->
    <section class="intro-box">
      <p>유튜브 영상을 등록하고<br />운동 정보를 공유해보세요!</p>
    </section>

    <!-- 상단 버튼 영역 -->
    <div style="display: flex; justify-content: space-between; align-items: center; margin: 30px 0 20px;">
      <h2>🎬 유튜브 영상 목록</h2>
      <div>
        <button @click="goToYoutubeRegister" style="margin-right: 10px;">🎬 유튜브 영상 등록</button>
        <button v-if="!isSelectionMode" @click="toggleSelectionMode" style="margin-right: 10px;">✅ 선택</button>
        <button v-if="isSelectionMode" @click="deleteSelectedVideos" style="margin-right: 10px; background-color: red; color: white;">🗑 삭제</button>
        <button v-if="isSelectionMode" @click="toggleSelectionMode">❌ 취소</button>
      </div>
    </div>

    <!-- 영상 목록 -->
    <div class="video-grid">
      <div class="video-card" v-for="video in videos" :key="video.videoId" style="position: relative;">
        <!-- 선택 체크박스 -->
        <input
          v-if="isSelectionMode"
          type="checkbox"
          style="position: absolute; top: 10px; left: 10px; transform: scale(1.3);"
          :checked="selectedVideos.has(video.videoId)"
          @click.stop="toggleSelection(video.videoId)"
        />
        <div @click="!isSelectionMode && router.push(`/videos/${video.videoId}`)" style="cursor: pointer;">
          <!-- 유튜브 영상 iframe -->
          <iframe
            v-if="video.youtubeId"
            :src="`https://www.youtube.com/embed/${video.youtubeId}`"
            width="100%"
            height="180"
            frameborder="0"
            allowfullscreen
            style="border-radius: 8px;"
          ></iframe>

          <div class="card-footer">
            <img src="/profile.png" class="user-icon" />
            <div class="desc">
              <strong>{{ video.title }}</strong>
              <p>{{ video.description }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const videos = ref([])
const selectedVideos = ref(new Set())
const isSelectionMode = ref(false)

const fetchVideos = async () => {
  const res = await axios.get('http://localhost:8080/api/videos?type=YOUTUBE')
  videos.value = res.data
}
onMounted(fetchVideos)

const toggleSelectionMode = () => {
  if (isSelectionMode.value) selectedVideos.value.clear()
  isSelectionMode.value = !isSelectionMode.value
}
const toggleSelection = (videoId) => {
  if (selectedVideos.value.has(videoId)) {
    selectedVideos.value.delete(videoId)
  } else {
    selectedVideos.value.add(videoId)
  }
}
const deleteSelectedVideos = async () => {
  if (selectedVideos.value.size === 0) {
    alert("삭제할 영상을 선택하세요.")
    return
  }
  const confirmed = confirm("선택한 영상을 삭제하시겠습니까?")
  if (!confirmed) return
  try {
    for (let videoId of selectedVideos.value) {
      await axios.delete(`http://localhost:8080/api/videos/${videoId}`)
    }
    alert("삭제 완료")
    selectedVideos.value.clear()
    isSelectionMode.value = false
    await fetchVideos()
  } catch (e) {
    alert("삭제 중 오류 발생")
    console.error(e)
  }
}
const goToYoutubeRegister = () => router.push('/videos/register/youtube')
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
.video-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr); /* ✅ 한 줄에 3개 */
  gap: 20px;
  margin-top: 30px;
}

.video-card {
  border: 1px solid #ccc;
  border-radius: 10px;
  overflow: hidden;
  width: 100%; /* ✅ grid 내부에서는 100%로 설정 */
  box-sizing: border-box;
}

.card-footer {
  display: flex;
  align-items: center;
  padding: 10px;
}
.user-icon {
  width: 40px;
  height: 40px;
  margin-right: 10px;
}
.desc p {
  margin: 0;
  font-size: 14px;
}
</style>
