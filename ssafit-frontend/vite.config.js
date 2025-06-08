import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      // ✅ API 요청 프록시 설정
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        // rewrite: path => path.replace(/^\/api/, '')
      },
      // ✅ 정적 파일 (mp4, jpg 등) 프록시 설정
      '/files': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
})
