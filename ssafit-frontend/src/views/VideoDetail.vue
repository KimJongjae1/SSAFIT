<template>
  <div v-if="video" style="max-width:700px;margin:0 auto;">
    <h2 style="font-size:2rem; margin-bottom:0.7em;">{{ video.title }}</h2>

<div style="display:flex; justify-content:center; margin-bottom:1.5em;">
  <!-- 유튜브 영상이면 iframe -->
  <iframe
    v-if="video.youtubeId"
    width="640"
    height="360"
    :src="`https://www.youtube.com/embed/${video.youtubeId}`"
    frameborder="0"
    allowfullscreen
    style="border-radius: 8px;"
  ></iframe>

  <!-- 직접 업로드한 영상이면 video 태그 -->
  <video
    v-else-if="video.videoUrl"
    width="640"
    height="360"
    controls
    crossorigin="anonymous"
    style="border-radius: 8px;"
  >
    <source :src="video.videoUrl" type="video/mp4" />
    브라우저가 video 태그를 지원하지 않습니다.
  </video>
</div>

    <div style="margin-bottom: 1em;">
      <b>설명:</b> {{ video.description }}
    </div>

    <div style="margin-top: 2em;">
      <h3>리뷰</h3>

      <div style="margin-bottom: 1em;">
        <textarea
          v-model="newReview"
          placeholder="리뷰를 입력하세요"
          rows="3"
          style="width: 100%; padding: 8px; border-radius: 6px; border: 1px solid #ccc;"
        ></textarea>
        <button type="button" @click="submitReview" style="margin-top: 0.5em;">리뷰 등록</button>
      </div>

      <div v-if="reviews.length === 0">
        <p>아직 리뷰가 없습니다.</p>
      </div>

      <ul v-else style="padding-left: 0;">
        <li
          v-for="review in reviews"
          :key="review.reviewId"
          style="list-style: none; margin-bottom: 1em; padding: 16px; border: 1px solid #ddd; border-radius: 10px; background: #fff;"
        >
          <div v-if="editingReviewId === review.reviewId">
            <textarea v-model="editedContent" rows="3" style="width: 100%; padding: 8px;"></textarea>
            <div style="margin-top: 0.5em;">
              <button type="button" @click="updateReview(review.reviewId)">저장</button>
              <button type="button" @click="cancelEditing" style="margin-left: 6px;">취소</button>
            </div>
          </div>

          <div v-else>
            <div style="display: flex; justify-content: space-between;">
              <strong>👤 {{ review.nickName }}</strong>
              <div v-if="userId === review.userId">
                <button type="button" @click="startEditing(review)" style="color: blue;">수정</button>
                <button type="button" @click="deleteReview(review.reviewId)" style="color: red; margin-left: 6px;">삭제</button>
              </div>
            </div>
            <p style="margin-top: 8px;">{{ review.content }}</p>
          </div>
        </li>
      </ul>
    </div>

    <button type="button" @click="router.push('/')" style="margin-top:2em;">← 목록으로</button>
  </div>

  <div v-else>
    <p>영상을 불러오는 중...</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'

const route = useRoute()
const router = useRouter()

const video = ref(null)
const reviews = ref([])
const newReview = ref("")
const userId = ref(null)
const editingReviewId = ref(null)
const editedContent = ref("")

const extractUserIdFromToken = () => {
  const token = localStorage.getItem("token")
  if (!token) return null
  try {
    const payload = JSON.parse(atob(token.split('.')[1]))
    return payload.numericUserId
  } catch {
    return null
  }
}

onMounted(async () => {
  try {
    const res = await axios.get(`http://localhost:8080/api/videos/${route.params.id}`)
    video.value = res.data
    console.log("🎥 videoUrl:", video.value.videoUrl)
    await fetchReviews()
    userId.value = extractUserIdFromToken()
  } catch (err) {
    alert("데이터 로딩 실패")
    console.error(err)
  }
})

const fetchReviews = async () => {
  try {
    const res = await axios.get(`http://localhost:8080/api/reviews/video/${route.params.id}`)
    reviews.value = res.data
  } catch (err) {
    console.error("리뷰 조회 실패", err)
  }
}

const submitReview = async () => {
  const content = newReview.value.trim()
  console.log("✅ 입력된 리뷰 내용:", content)
  if (!content) return alert("리뷰를 입력하세요.")

  const token = localStorage.getItem("token")
  if (!token) return alert("로그인이 필요합니다.")

  try {
    await axios.post("http://localhost:8080/api/reviews", {
      videoId: route.params.id,
      content
    }, {
      headers: { Authorization: `Bearer ${token}` }
    })

    await fetchReviews() // 최신 상태 반영
    newReview.value = ""
  } catch (err) {
    alert("리뷰 등록 실패")
    console.error(err)
  }
}

const deleteReview = async (reviewId) => {
  if (!confirm("리뷰를 삭제하시겠습니까?")) return

  const token = localStorage.getItem("token")
  if (!token) return alert("로그인이 필요합니다.")

  try {
    await axios.delete(`http://localhost:8080/api/reviews/${reviewId}`, {
      headers: { Authorization: `Bearer ${token}` }
    })
    reviews.value = reviews.value.filter(r => r.reviewId !== reviewId)
  } catch (err) {
    alert("리뷰 삭제 실패")
    console.error(err)
  }
}

const startEditing = (review) => {
  editingReviewId.value = review.reviewId
  editedContent.value = review.contentS
}

const cancelEditing = () => {
  editingReviewId.value = null
  editedContent.value = ""
}

const updateReview = async (reviewId) => {
  const token = localStorage.getItem("token")
  if (!token) return alert("로그인이 필요합니다.")

  try {
    await axios.put(`http://localhost:8080/api/reviews/${reviewId}`, {
      content: editedContent.value
    }, {
      headers: { Authorization: `Bearer ${token}` }
    })

    const target = reviews.value.find(r => r.reviewId === reviewId)
    if (target) target.content = editedContent.value

    editingReviewId.value = null
    editedContent.value = ""
  } catch (err) {
    alert("리뷰 수정 실패")
    console.error(err)
  }
}
</script>
