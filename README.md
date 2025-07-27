# 링크레터 – 티스토리든 벨로그든, 새 글이 올라오면 바로 알려드려요

<img width="768" height="512" alt="LinkLetter 대표 이미지" src="https://github.com/user-attachments/assets/89a0a18a-9946-400b-93c1-2a4023e1841e" />

여러분의 관심사, 한곳에서 만나보세요

인터넷을 돌아다니다 보면 팔로우하고 싶은 블로그와 콘텐츠가 너무 많아, 정작 놓치는 정보가 많으시죠?

링크레터는 흩어져 있는 블로그를을 한곳으로 모아,
좋아하는 크리에이터의 새 글을 실시간 알림으로 신속하고 편리하게 전달해드립니다.

이제는 일일이 방문하지 않아도,
원하는 소식을 빠짐없이 받아보세요!

---

### 팔로잉 피드
내가 팔로우한 블로거의 최신 글을 한눈에 모아볼 수 있어요.

| Android | iOS |
|:--:|:--:|
| <img src="https://github.com/user-attachments/assets/c3667c17-9530-47a2-a301-328e7b585a9c" width="280" alt="Android 피드 화면" /> | <img src="https://github.com/user-attachments/assets/a0836fbc-8386-484f-bb84-3ad1e6dfe9c9" width="280" alt="iOS 피드 화면" /> |
| <img src="https://github.com/user-attachments/assets/27be1dbf-b39f-41df-b272-0aa3af347c01" width="280" alt="Android 피드 상세 화면" /> | <img src="https://github.com/user-attachments/assets/402afeb2-71a9-4729-99ce-ebaacb77dd04" width="280" alt="iOS 피드 상세 화면" /> |

---

### 알림 기능
관심 있는 블로거를 팔로우하면, 새로운 글이 올라올 때마다 알림을 받아볼 수 있습니다.

| Android | iOS |
|:--:|:--:|
| <img src="https://github.com/user-attachments/assets/ed6ae072-4792-4016-9315-5df6f0ebe7e6" width="280" alt="Android 알림 화면" /> | <img src="https://github.com/user-attachments/assets/5b61bac1-c4b7-469a-8f94-4b4a53063ae8" width="280" alt="iOS 알림 화면" /> |
| <img src="https://github.com/user-attachments/assets/3e5461bf-b104-4aff-84b2-2d4e2ca8c3a7" width="280" alt="Android 알림 상세 화면" /> | <img src="https://github.com/user-attachments/assets/aae2ad68-97db-4df9-a86f-9d1c222e451e" width="280" alt="iOS 알림 상세 화면" /> |

---

### 블로거 관리
내가 팔로우한 블로거 목록을 한눈에 확인할 수 있습니다.

| Android | iOS |
|:--:|:--:|
| <img src="https://github.com/user-attachments/assets/0efff2c1-23c7-4da8-819f-198324348f95" width="280" alt="Android 블로거 관리 화면" /> | <img src="https://github.com/user-attachments/assets/48d3f98d-f4c2-4a81-a18c-4b91717d01c0" width="280" alt="iOS 블로거 관리 화면" /> |

---

이제, 링크레터와 함께
매일매일 새로운 인사이트를 만나보세요! 🚀

## 기술 스택

- Kotlin Multiplatform
- Jetpack Compose / Compose Multiplatform
- Kotlin Coroutines
- Koin
- Coil
- Ktor
- Room
- DataStore
- kotlinx.serialization
- ksoup
- moko-permissions
- Gradle KSP, Detekt, Spotless

## 프로젝트 구조

```
linkletter-client/
  ├── core/         # 핵심 로직 및 공통 모듈
  ├── feature/      # 각 기능별 모듈 (피드, 설정 등)
  ├── composeApp/   # 멀티플랫폼 앱 진입점
  ├── iosApp/       # iOS 전용 코드 및 리소스
  └── build-logic/  # 빌드 설정 및 스크립트
```
