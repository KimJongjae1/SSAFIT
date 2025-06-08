import { createRouter, createWebHistory } from "vue-router";

// 화면 뷰
import Home from "../views/Home.vue";                    // ✅ 개인 영상 목록
import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import VideoDetail from "../views/VideoDetail.vue";
import YoutubeVideoList from "../views/YoutubeVideoList.vue"; // ✅ 유튜브 영상 목록
import Community from "../views/Community.vue";

// 영상 등록 뷰
import YoutubeRegister from "../views/YoutubeRegister.vue";
import PersonalRegister from "../views/PersonalRegister.vue";

// 마이페이지 관련
import Mypage from '../views/Mypage.vue';
import EditUser from '../views/EditUser.vue';
import ReceivedMessages from '../views/ReceivedMessages.vue'

const routes = [
  // 기본 홈 → 개인 영상
  { path: "/", component: Home },

  // 로그인 / 회원가입
  { path: "/login", component: Login },
  { path: "/register", component: Register },

  // 영상 상세 조회
  { path: "/videos/:id", component: VideoDetail },

  // 유튜브 영상 목록
  { path: "/videos/youtube", component: YoutubeVideoList },

  // 영상 등록
  { path: "/videos/register/youtube", component: YoutubeRegister },
  { path: "/videos/register/personal", component: PersonalRegister },

  // 마이페이지
  { path: "/mypage", name: "Mypage", component: Mypage },
  { path: "/edit", component: EditUser },
  { path: "/message/received", name: "ReceivedMessages", component: ReceivedMessages },

  // 커뮤니티
  { path: "/community", name: "Community", component: Community }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// ✅ 로그인 여부 체크 (비로그인 시 로그인 페이지로 리다이렉트)
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem("token");

  const publicPages = ["/login", "/register"];
  const authRequired = !publicPages.includes(to.path);

  if (authRequired && !token) {
    next("/login");
  } else {
    next();
  }
});

export default router;
