package view;

import player.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class MyWin1 extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTextField textField;
    private JLabel lblPlayer1Image;
    private JLabel lblPlayer2Image;
    private JButton btnAttack1, btnHeal1, btnAttack2, btnHeal2;
    private JProgressBar hpBar1, hpBar2;

    private Player player1;
    private Player player2;
    private int currentTurn = 0; // 0: player1 턴, 1: player2 턴
    // 멤버 변수 추가
    private JLabel lblPlayer1Hp, lblPlayer1Skill, lblPlayer1Weapon;
    private JLabel lblPlayer2Hp, lblPlayer2Skill, lblPlayer2Weapon;


    public MyWin1(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;

        setTitle("🍁 Maple Battle Simulator 🍁");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);

        initContentPane();
        initCharacterImages();
        initHpBars();
        initCharacterInfoPanels();
        initButtons();
        initLogField();
        updateTurnButtons(); // 처음에 player1 턴부터 시작
    }

    private void initContentPane() {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);
    }

    private void initCharacterInfoPanels() {
        // Player 1 정보
        lblPlayer1Hp = createInfoLabel(50, 290 + 100, "체력: " + player1.getHp());
        lblPlayer1Skill = createInfoLabel(50, 320 + 100, "스킬: " + player1.getSkillName());
        lblPlayer1Weapon = createInfoLabel(50, 350 + 100, "무기: " + player1.getWeapon().getName());

        // Player 2 정보
        lblPlayer2Hp = createInfoLabel(519, 290 + 100, "체력: " + player2.getHp());
        lblPlayer2Skill = createInfoLabel(519, 320 + 100, "스킬: " + player2.getSkillName());
        lblPlayer2Weapon = createInfoLabel(519, 350 + 100, "무기: " + player2.getWeapon().getName());

        contentPane.add(lblPlayer1Hp);
        contentPane.add(lblPlayer1Skill);
        contentPane.add(lblPlayer1Weapon);

        contentPane.add(lblPlayer2Hp);
        contentPane.add(lblPlayer2Skill);
        contentPane.add(lblPlayer2Weapon);
    }

    private JLabel createInfoLabel(int x, int y, String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SUITE SemiBold", Font.PLAIN, 14));
        label.setBounds(x, y, 250, 20);
        return label;
    }


    private void initCharacterImages() {
        lblPlayer1Image = createImageLabel(50, 40, player1.getImgFile1());
        lblPlayer2Image = createImageLabel(519, 40, player2.getImgFile1());

        contentPane.add(lblPlayer1Image);
        contentPane.add(lblPlayer2Image);
    }

    private JLabel createImageLabel(int x, int y, String path) {
        JLabel label = new JLabel();
        label.setBounds(x, y, 214, 218);
        setCharacterImage(label, path);
        return label;
    }

    private void initHpBars() {
        hpBar1 = createHpBar(50, 268, player1.getHp(), new Color(51, 102, 153));
        hpBar2 = createHpBar(519, 268, player2.getHp(), new Color(153, 51, 51));

        contentPane.add(hpBar1);
        contentPane.add(hpBar2);
    }

    private JProgressBar createHpBar(int x, int y, int max, Color color) {
        JProgressBar bar = new JProgressBar(0, max);
        bar.setValue(max);
        bar.setForeground(color);
        bar.setBounds(x, y, 214, 14);
        return bar;
    }

    private void initLogField() {
        textField = new JTextField();
        textField.setEditable(false);
        textField.setBounds(10, 500, 766, 60);
        contentPane.add(textField);
        textField.setColumns(10);
    }

    private void initButtons() {
        btnAttack1 = createButton("👊 공격하기", 50, 290, new Color(0, 51, 153), () -> {
            handleAttack(player1, player2, lblPlayer2Image, hpBar2);
            switchTurn();
        });
        btnHeal1 = createButton("💖 힐링하기", 50, 336, new Color(0, 51, 153), () -> {
            handleHeal(player1, hpBar1);
            switchTurn();
        });

        btnAttack2 = createButton("👊 공격하기", 519, 290, new Color(153, 51, 51), () -> {
            handleAttack(player2, player1, lblPlayer1Image, hpBar1);
            switchTurn();
        });
        btnHeal2 = createButton("💖 힐링하기", 519, 336, new Color(153, 51, 51), () -> {
            handleHeal(player2, hpBar2);
            switchTurn();
        });

        contentPane.add(btnAttack1);
        contentPane.add(btnHeal1);
        contentPane.add(btnAttack2);
        contentPane.add(btnHeal2);
    }

    private void switchTurn() {
        if (!player1.isAlive() || !player2.isAlive()) return;
        currentTurn = 1 - currentTurn;
        updateTurnButtons();
    }

    private void updateTurnButtons() {
        btnAttack1.setEnabled(currentTurn == 0 && player1.isAlive());
        btnHeal1.setEnabled(currentTurn == 0 && player1.isAlive());
        btnAttack2.setEnabled(currentTurn == 1 && player2.isAlive());
        btnHeal2.setEnabled(currentTurn == 1 && player2.isAlive());

        if (player1.isAlive() && player2.isAlive()) {
            textField.setText((currentTurn == 0 ? player1.getName() : player2.getName()) + "의 턴입니다!");
        }
    }

    private JButton createButton(String text, int x, int y, Color color, Runnable action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SUITE SemiBold", Font.PLAIN, 18));
        btn.setForeground(color);
        btn.setBounds(x, y, 214, 40);
        btn.addActionListener(e -> action.run());
        return btn;
    }

    private void handleAttack(Player attacker, Player target, JLabel targetImgLabel, JProgressBar targetHpBar) {
        if (!attacker.isAlive() || !target.isAlive()) return;

        attacker.attack(target);
        targetHpBar.setValue(target.getHp());
        updateHpLabels();
        textField.setText(attacker.getName() + "이(가) " + target.getName() + "을(를) 공격했습니다!");

        if (!target.isAlive()) {
            setDeadImage(target);
            textField.setText("💀 " + target.getName() + " is defeated!");
            disableButtons();
            return;
        }
    }

    private void handleHeal(Player player, JProgressBar hpBar) {
        if (!player.isAlive()) return;
        int oldHp = player.getHp();
        player.receiveDamage(-10); // 회복
        hpBar.setValue(player.getHp());
        updateHpLabels();
        textField.setText(player.getName() + "이(가) 체력을 회복했습니다! (" + oldHp + " → " + player.getHp() + ")");
    }

    private void updateHpLabels() {
        lblPlayer1Hp.setText("체력: " + player1.getHp());
        lblPlayer2Hp.setText("체력: " + player2.getHp());
    }


    private void setCharacterImage(JLabel label, String resourcePath) {
        try {
            ImageIcon icon = new ImageIcon(MyWin1.class.getResource(resourcePath));
            Image scaled = icon.getImage().getScaledInstance(214, 218, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            System.err.println("이미지 로딩 실패: " + resourcePath);
        }
    }

    private void disableButtons() {
        btnAttack1.setEnabled(false);
        btnHeal1.setEnabled(false);
        btnAttack2.setEnabled(false);
        btnHeal2.setEnabled(false);
    }

    public void performAttack(Player attacker, Player target) {
        if (!attacker.isAlive() || !target.isAlive()) return;

        attacker.attack(target);

        if (attacker == player1 && target == player2) {
            hpBar2.setValue(player2.getHp());
        } else if (attacker == player2 && target == player1) {
            hpBar1.setValue(player1.getHp());
        }

        textField.setText(attacker.getName() + "이(가) " + target.getName() + "을(를) 공격했습니다!");

        if (!target.isAlive()) {
            setDeadImage(target);
            textField.setText("💀 " + target.getName() + " is defeated!");
            disableButtons();
        }
    }

    private void setDeadImage(Player target) {
        if (target == player1) setCharacterImage(lblPlayer1Image, player1.getImgFile2());
        if (target == player2) setCharacterImage(lblPlayer2Image, player2.getImgFile2());
    }

}
