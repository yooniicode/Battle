package main;

import player.*;
import view.*;
import view.PlayerFactory;
import weapon.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<Player> allPlayers = PlayerFactory.createDefaultPlayers();

        EventQueue.invokeLater(() -> {
            new HomeScreen(allPlayers).setVisible(true);
        });
    }

    public static void consoleGame(Player p1, Player p2, MyWin1 frame) {
        List<Player> players = Arrays.asList(p1, p2);
        System.out.println("\n===== 🍁 Maple Battle Simulator (콘솔 모드) 🍁 =====");

        // Step 1: 사용자 캐릭터 선택
        System.out.println("\n👤 조작할 캐릭터를 선택하세요:");
        System.out.println("[0] " + p1.getName());
        System.out.println("[1] " + p2.getName());
        System.out.print("선택: ");
        int userIndex = getUserChoice(players, false);
        Player user = players.get(userIndex);

        int currentTurn = 0;

        while (p1.isAlive() && p2.isAlive()) {
            Player attacker = players.get(currentTurn);
            Player target = players.get(1 - currentTurn);

            if (attacker == user) {
                System.out.println("\n💥 당신의 턴입니다! 버튼을 눌러 공격력을 높이세요!");
                int totalDamage = launchClickChallenge();
                target.receiveDamage(totalDamage);
            } else {
                System.out.println("\n🤖 컴퓨터의 턴입니다!");
                attacker.attack(target);
            }

            // GUI 반영
            SwingUtilities.invokeLater(() -> frame.performAttack(attacker, target));

            if (!target.isAlive()) {
                System.out.println("💀 " + target.getName() + " is defeated!");
                break;
            }

            currentTurn = 1 - currentTurn;
        }

        System.out.println("\n🏆 게임 종료! 최종 생존자: " + (p1.isAlive() ? p1.getName() : p2.getName()));

        SwingUtilities.invokeLater(() -> {
            JFrame resultFrame = new JFrame("게임 결과");
            resultFrame.setSize(300, 180);
            resultFrame.setLayout(new BorderLayout());
            resultFrame.setLocationRelativeTo(null);
            resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            String winner = p1.isAlive() ? p1.getName() : p2.getName();

            JLabel resultLabel = new JLabel("🏆 승자: " + winner, SwingConstants.CENTER);
            resultLabel.setFont(new Font("SUITE Regular", Font.PLAIN, 18));
            resultFrame.add(resultLabel, BorderLayout.CENTER);

            JButton homeButton = new JButton("🏠 홈화면으로 돌아가기");
            homeButton.setFont(new Font("SUITE Regular", Font.PLAIN, 14));
            resultFrame.add(homeButton, BorderLayout.SOUTH);

            homeButton.addActionListener(e -> {
                resultFrame.dispose(); // 결과 팝업
                frame.dispose(); // 전투 화면
                // 홈 화면 열기
                List<Player> newPlayers = PlayerFactory.createDefaultPlayers();
                new HomeScreen(newPlayers).setVisible(true);
            });

            resultFrame.setVisible(true);
        });

    }

    public static void randomSpectatorMode(Player p1, Player p2, MyWin1 frame) {
        List<Player> players = Arrays.asList(p1, p2);
        int[] currentTurn = {0}; // 턴 관리 변수 (0 또는 1)

        new Thread(() -> {
            while (p1.isAlive() && p2.isAlive()) {
                Player attacker = players.get(currentTurn[0]);
                Player target = players.get(1 - currentTurn[0]); // 반대 플레이어

                System.out.println(attacker.getName() + " attacks " + target.getName() + "!");

                // GUI 반영
                SwingUtilities.invokeLater(() -> frame.performAttack(attacker, target));

                // 공격 후 턴 변경
                currentTurn[0] = 1 - currentTurn[0];

                if (!target.isAlive()) {
                    System.out.println("💀 " + target.getName() + " is defeated!");
                    break;
                }

                try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
            }

            System.out.println("\n🏆 게임 종료! 최종 생존자: " + (p1.isAlive() ? p1.getName() : p2.getName()));
        }).start();
    }

    // 사용자 입력 (종료 옵션 포함)
    private static int getUserChoice(List<Player> players, boolean mustBeAlive) {
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("exit")) {
                System.out.println("게임을 종료합니다.");
                System.exit(0);
            }

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 0 && choice < players.size()) {
                    if (!mustBeAlive || players.get(choice).isAlive()) {
                        return choice;
                    } else {
                        System.out.println("❌ 사망한 플레이어는 선택할 수 없습니다.");
                    }
                } else {
                    System.out.println("❌ 잘못된 번호입니다.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ 숫자를 입력하세요. (종료: q 또는 exit)");
            }
        }
    }

    public static int launchClickChallenge() {
        final int[] clickCount = {0};

        // 창 구성
        JFrame frame = new JFrame("💥 공격 준비");
        frame.setAlwaysOnTop(true);
        frame.setSize(300, 200);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JLabel countdownLabel = new JLabel("3", SwingConstants.CENTER);
        countdownLabel.setFont(new Font("SUITE Regular", Font.BOLD, 40));
        frame.add(countdownLabel, BorderLayout.NORTH);

        JButton clickBtn = new JButton("클릭!");
        clickBtn.setFont(new Font("SUITE Regular", Font.BOLD, 24));
        clickBtn.setEnabled(false); // 처음엔 비활성화
        frame.add(clickBtn, BorderLayout.CENTER);

        clickBtn.addActionListener(e -> clickCount[0]++);

        frame.setVisible(true);

        // 카운트다운 진행
        Timer countdownTimer = new Timer();
        final int[] timeLeft = {3};

        countdownTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (timeLeft[0] > 1) {
                    timeLeft[0]--;
                    countdownLabel.setText(String.valueOf(timeLeft[0]));
                } else {
                    countdownLabel.setText("시작!");
                    clickBtn.setEnabled(true);
                    countdownTimer.cancel();

                    // 2초 후 종료
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            frame.dispose();
                        }
                    }, 2000);
                }
            }
        }, 1000, 1000); // 1초마다 실행

        // 대기: 창 닫힐 때까지 block (SwingUtilities.invokeAndWait 방지용)
        while (frame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }

        int damage = clickCount[0] * 3;
        System.out.println("🧮 클릭 수: " + clickCount[0] + " → 총 데미지: " + damage);
        return damage;
    }

}
