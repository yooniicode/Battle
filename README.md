# 🧭 Maple Battle Simulator

> 자바 객체지향 설계를 기반으로 만든 RPG 스타일 전투 시뮬레이터입니다.  
> GUI(Swing) 기반 게임 지휘 모드와 랜덤 관전 모드를 함께 제공합니다.

---

## 📌 목차

1. [프로그램 목적](#1-프로그램-목적)  
2. [게임 시나리오](#2-게임-시나리오)  
3. [Player 클래스 구조](#3-player-클래스-구조)  
4. [무기 클래스 구조](#4-무기-클래스-구조)  
5. [직업군 클래스](#5-직업군-클래스)  
6. [전투 시스템](#6-전투-시스템)  
7. [GUI 구성 요소](#7-gui-구성-요소)  
8. [기능 요약](#8-기능-요약)  
9. [학습 및 구현 소감](#9-학습-및-구현-소감)  

---

## 1. 프로그램 목적

이 프로젝트는 객체지향 설계의 핵심 개념을 실습하기 위해 개발되었습니다.

- **추상화, 상속, 다형성**을 기반으로 한 Player 설계
- **무기 인터페이스 구조**로 다양한 무기 공격 구현
- **Java Swing GUI** 기반의 시각적 전투 시스템 제공
- 콘솔 기반 CLI 전투 모드도 제공

---

## 2. 게임 시나리오

메이플스토리 직업군을 모티브로 한 4명의 캐릭터가 서로 전투합니다.

- 캐릭터는 고유 능력치, 무기, 스킬 보유
- 전투 방식: **게임 지휘 모드**, **랜덤 관전 모드**, **콘솔 모드**
- 각 캐릭터는 `PlayerFactory`로 생성되며 이미지와 무기도 함께 주입

---

## 3. Player 클래스 구조

```java
public abstract class Player {
    String name;
    int hp;
    int power;
    Weapon weapon;
    String imgAlive, imgDead;

    public void attack(Player target) { ... }
    public void receiveDamage(int dmg) { ... }
    public boolean isAlive() { ... }
    public void setWeapon(Weapon w) { ... }
    public void show() { ... }
}
````

📌 주요 메서드

* `attack()` / `receiveDamage()` / `isAlive()`
* `getSkillName()` / `setDeadImage()` / `show()`

GUI에서 `imgAlive`, `imgDead`를 이용한 이미지 전환 기능 포함

---

## 4. 무기 클래스 구조

모든 무기는 `Weapon` 인터페이스를 구현하며, 다음 메서드를 포함합니다:

```java
interface Weapon {
    String getName();
    int getPower();
    void attackAction(Player attacker, Player target);
}
```

| 무기             | 계산식                          | 특징                 |
| -------------- | ---------------------------- | ------------------ |
| Spear          | `power + 30`                 | 근접형, DarkKnight 전용 |
| Bow            | `2연타 (power + 25) * 2`       | 지속 피해              |
| FirePoisonBook | `(power + 20) * 랜덤(0.5~2.5)` | 중독성 마법, 불확정 피해     |

---

## 5. 직업군 클래스

```java
public class BowMaster extends Player {
    public void attack(Player target) {
        System.out.println(name + " fires [애로우 레인]!");
        super.attack(target);
    }
}
```

| 직업             | HP   | Power | 무기             | 스킬            |
| -------------- | ---- | ----- | -------------- | ------------- |
| BowMaster      | 1200 | 70    | Bow            | 애로우 레인        |
| DarkKnight     | 1300 | 40    | Spear          | 피어스 사이클론      |
| FirePoisonMage | 1100 | 55    | FirePoisonBook | 포이즌 미스트       |
| 윤지             | 1200 | 70    | Bow            | BowMaster와 동일 |

---

## 6. 전투 시스템

### 🎮 콘솔 전투 (`consoleGame`)

* 플레이어 직접 선택, CLI 기반
* 클릭 챌린지를 통한 공격력 증가 시스템 포함

### 🪄 GUI 전투 (`windowGame`)

* GUI 구성: 이미지, HP bar, 공격/회복 버튼, 전투 로그 출력
* 턴제 전투: 유저 클릭 or 자동 턴 진행
* 캐릭터 사망 시 이미지 전환

### 👀 관전 모드 (Spectator)

* 자동 1:1 전투 진행
* 실시간 턴 전환 및 공격 표시
* GUI 화면에 전투 흐름을 로그로 표시

---

## 7. GUI 구성 요소

| 화면           | 설명                       |
| ------------ | ------------------------ |
| HomeScreen   | 인트로 로고 및 자동 전환           |
| SelectWindow | 캐릭터 2명 선택 + 모드 설정        |
| MyWin1       | 전투 UI, 이미지/HP/스킬/버튼 표시   |
| 결과 팝업        | 승자 출력 + 홈으로 돌아가기 버튼      |
| 이미지 처리       | 살아있는 이미지 vs 사망 이미지 자동 전환 |

---

## 8. 기능 요약

| 메서드                      | 설명                  |
| ------------------------ | ------------------- |
| `attack()`               | 무기 기반 공격            |
| `receiveDamage()`        | 피해 적용               |
| `setWeapon()`            | 무기 장착               |
| `getSkillName()`         | 캐릭터 스킬명 반환          |
| `launchClickChallenge()` | 콘솔 클릭 챌린지           |
| `performAttack()`        | 외부에서 공격 제어 (관전 모드용) |
| `setDeadImage()`         | 사망 이미지로 전환          |

---

## 9. 학습 및 구현 소감

* 객체지향 설계(OOP)의 **추상화/상속/다형성** 개념을 실질적으로 적용
* 공통 로직은 추상 클래스에, 특화 로직은 하위 클래스에 배치
* `PlayerFactory` 패턴으로 캐릭터 초기화 구조화
* GUI와 이미지 전환을 포함한 인터랙션 구현 경험
* 직접 코드를 설계하고 구조화하며 객체 간 관계에 대한 이해도 향상

> 🎯 향후 목표: AI 전투 시스템, 스킬 이펙트, BGM 및 애니메이션 도입

---

## 🎞️ 게임 화면

### 홈 & 선택 화면

![스크린샷 2025-06-15 195412](https://github.com/user-attachments/assets/2bd19006-3293-4729-890d-4a15043d3403)
![스크린샷 2025-06-15 195446](https://github.com/user-attachments/assets/fee30751-455f-4d5b-9508-1e07784c35ad)


### 게임 지휘 모드

![스크린샷 2025-06-15 195457](https://github.com/user-attachments/assets/f1fcd843-15b9-4740-b6e0-b632d3c6d0ce)
![스크린샷 2025-06-15 195535](https://github.com/user-attachments/assets/675ede48-dac0-4293-944b-3d7854d0c90e)
![스크린샷 2025-06-15 195619](https://github.com/user-attachments/assets/4cbd958d-9776-4c1b-867a-1b2379d5abfe)


### 랜덤 전투 관전 모드

![스크린샷 2025-06-15 195638](https://github.com/user-attachments/assets/8fa37c8f-109e-463d-9a21-a4f5aa5ded5a)
![스크린샷 2025-06-15 195707](https://github.com/user-attachments/assets/79715399-4f01-44d3-80cb-fcea15602d6c)


---

## 📈 UML 다이어그램

### 전체 클래스 구성
![main](https://github.com/user-attachments/assets/30216a64-2477-405f-bf87-9819e8d5c84e)
![players](https://github.com/user-attachments/assets/0effe1ad-33e7-4e9e-a6cf-0571c8a091c6)
![weapons](https://github.com/user-attachments/assets/f20fea17-397f-4036-b6b5-7747a5332a21)
![views](https://github.com/user-attachments/assets/92391763-d97e-47aa-a592-2f8367f0a061)

### 화면 구성
![fullumls](https://github.com/user-attachments/assets/d1dbe213-fbcc-4908-8f00-5476905b777f)


---

## 🔗 실행 방법

1. `Main.java` 실행 (GUI or 콘솔 모드 자동 진입)
2. 또는 `jar` 파일로 빌드하여 다음 명령으로 실행:

```bash
java -jar battleFinal.jar
```

필요하다면 이미지 경로를 `images/홈.png`처럼 실제 리포지토리 구조에 맞게 수정해줄 수도 있어.  
`.md`로 저장해서 `README.md`로 쓰면 완벽하게 적용될 거야!

원하면 GitHub 테마에 어울리는 커버 배너도 만들어줄 수 있어 :)
```
