<template>
  <div class="community">
    <h1>🏋 커뮤니티 게시판</h1>
    <p>여러분의 운동 이야기를 자유롭게 공유해주세요!</p>

    <!-- 게시글 리스트 -->
    <div class="post" v-for="post in pagedPosts" :key="post.id">
      <h3>{{ post.title }}</h3>
      <p class="author">작성자: {{ post.author }}</p>
      <p>{{ post.content }}</p>
    </div>

    <!-- 페이징 -->
    <div class="pagination">
      <button
        v-for="page in totalPages"
        :key="page"
        :class="{ active: page === currentPage }"
        @click="changePage(page)"
      >
        {{ page }}
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

// ✅ 더미 게시글 15개 (1~3페이지)
const posts = ref([
  {
    id: 1,
    title: '운동 루틴 어떻게 짜시나요?',
    author: '통영피바다',
    content: '헬린이인데 어떤 루틴으로 해야 할지 모르겠어요. 추천 부탁드립니다!'
  },
  {
    id: 2,
    title: '하루 1만보 걷기 효과',
    author: '리자몽',
    content: '3주째 도전 중인데 체력이 진짜 좋아진 것 같아요 💪'
  },
  {
    id: 3,
    title: '스쿼트 할 때 무릎 아픈 분?',
    author: '민짜이',
    content: '스쿼트 자세가 잘못된 건지 무릎이 아파요. 경험 공유해주세요.'
  },
  {
    id: 4,
    title: '단백질 보충제 추천 좀요!',
    author: '싸피돌고래',
    content: '요즘 단백질 보충제 너무 많아서 고민돼요. 입문자용 뭐가 좋을까요?'
  },
  {
    id: 5,
    title: '플랭크 몇 분씩 하시나요?',
    author: '오만원',
    content: '플랭크 오래 버티는 법 공유해주세요. 저는 1분도 힘드네요ㅠ'
  },
  {
    id: 6,
    title: '스트레칭 루틴 공유해요',
    author: '민수',
    content: '운동 전후 꼭 해줘야 하는 스트레칭! 제 루틴 공유합니다 :)'
  },
  {
    id: 7,
    title: '요가 시작하려는데 팁 있을까요?',
    author: '철수',
    content: '요가 매트 어떤 거 사야 할지, 수련 순서도 궁금합니다.'
  },
  {
    id: 8,
    title: '다이어트 중인데 닭가슴살 질려요...',
    author: '영희',
    content: '닭가슴살 말고 단백질 섭취 방법 추천해주실 분?'
  },
  {
    id: 9,
    title: '러닝화 추천 부탁드려요!',
    author: '민수',
    content: '장거리 뛸 때 무릎에 부담 안 가는 러닝화 있을까요?'
  },
  {
    id: 10,
    title: '홈트 할 때 필수 장비?',
    author: '철수',
    content: '홈트 입문자에게 추천할만한 도구가 있다면 알려주세요!'
  },
  {
    id: 11,
    title: '폼롤러 써보신 분 계세요?',
    author: '영희',
    content: '폼롤러가 진짜 근육통에 도움이 되는지 궁금해요.'
  },
  {
    id: 12,
    title: '식단 조절 어렵지 않나요?',
    author: '민수',
    content: '운동보다 식단이 더 힘들어요. 간편한 식단팁 있을까요?'
  },
  {
    id: 13,
    title: '팔굽혀펴기 하루 몇 개 하시나요?',
    author: '철수',
    content: '하루에 50개 도전 중인데 효과가 있는지 궁금합니다.'
  },
  {
    id: 14,
    title: '운동할 때 듣는 노래 공유해요!',
    author: '영희',
    content: '운동할 때 듣기 좋은 음악 추천해주세요 🎧'
  },
  {
    id: 15,
    title: '크런치 자세 질문!',
    author: '민수',
    content: '복근 운동 중에 목이 아픈데 자세가 잘못된 걸까요?'
  }
])

const currentPage = ref(1)
const pageSize = 5

const totalPages = computed(() => Math.ceil(posts.value.length / pageSize))

const pagedPosts = computed(() => {
  const start = (currentPage.value - 1) * pageSize
  return posts.value.slice(start, start + pageSize)
})

const changePage = (page) => {
  currentPage.value = page
}
</script>

<style scoped>
.community {
  max-width: 800px;
  margin: 40px auto;
  font-family: Arial, sans-serif;
}
.post {
  background-color: #f9f9f9;
  padding: 16px;
  border: 1px solid #ddd;
  margin-bottom: 20px;
  border-radius: 8px;
}
.post h3 {
  margin-bottom: 6px;
}
.author {
  font-size: 0.9rem;
  color: #555;
  margin-bottom: 10px;
}
.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
.pagination button {
  margin: 0 6px;
  padding: 6px 12px;
  background: #eee;
  border: 1px solid #ccc;
  border-radius: 4px;
  cursor: pointer;
}
.pagination button.active {
  background: black;
  color: white;
  font-weight: bold;
}
</style>
