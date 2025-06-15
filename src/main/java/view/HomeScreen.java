package view;

import player.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import main.Main;

public class HomeScreen extends JFrame {
    public HomeScreen(List<Player> players) {
        setTitle("Maple Battle Simulator");

        // 이미지 로드
        ImageIcon icon = new ImageIcon("src/main/resources/img/landing.png");
        Image image = icon.getImage();

        int imageWidth = icon.getIconWidth();
        int imageHeight = icon.getIconHeight();

        // 텍스트 높이 추가
        int captionHeight = 40;

        // 프레임 사이즈: 이미지 높이 + 텍스트 높이
        setSize(imageWidth, imageHeight + captionHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // layered pane 사용하여 이미지 + 텍스트 겹치지 않게 표시
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(imageWidth, imageHeight + captionHeight));

        // 이미지 배경 패널
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, imageWidth, imageHeight, this);
            }
        };
        imagePanel.setBounds(0, 0, imageWidth, imageHeight);
        layeredPane.add(imagePanel, JLayeredPane.DEFAULT_LAYER);

        // 하단 텍스트 라벨 (기울임체, 중앙)
        JLabel caption = new JLabel("* 5초 후에 자동으로 넘어갑니다.", JLabel.CENTER);
        caption.setFont(new Font("SUITE Regular", Font.ITALIC, 14));
        caption.setForeground(Color.DARK_GRAY);
        caption.setBounds(0, imageHeight, imageWidth, captionHeight);
        layeredPane.add(caption, JLayeredPane.PALETTE_LAYER);

        // 레이아웃 설정
        setContentPane(layeredPane);
        pack();
        setVisible(true);

        // ⏳ 5초 후 자동 화면 전환
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    setVisible(false);
                    final SelectWindow[] swHolder = new SelectWindow[1];

                    swHolder[0] = new SelectWindow(players, () -> {
                        Player p1 = swHolder[0].getSelectedPlayer1();
                        Player p2 = swHolder[0].getSelectedPlayer2();
                        String mode = swHolder[0].getSelectedMode();

                        MyWin1 frame = new MyWin1(p1, p2);
                        frame.setVisible(true);

                        if ("게임지휘모드".equals(mode)) {
                            new Thread(() -> Main.consoleGame(p1, p2, frame)).start();
                        } else if ("랜덤전투 관전모드".equals(mode)) {
                            Main.randomSpectatorMode(p1, p2, frame);
                        } else {
                            System.err.println("❌ 알 수 없는 모드입니다: " + mode);
                        }

                    });

                    swHolder[0].setVisible(true);
                });
            }
        }, 5000);
    }
}
