package view;

import player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class SelectWindow extends JFrame {

    private Player selectedPlayer1;
    private Player selectedPlayer2;
    private String selectedMode;

    public SelectWindow(List<Player> players, Runnable onSelectionComplete) {
        setTitle("플레이어 & 모드 선택");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLayout(new GridLayout(0, 1));

        JLabel label = new JLabel("두 명의 플레이어를 선택하세요:");
        add(label);

        JComboBox<String> comboBox1 = new JComboBox<>();
        JComboBox<String> comboBox2 = new JComboBox<>();
        for (Player p : players) {
            comboBox1.addItem(p.getName());
            comboBox2.addItem(p.getName());
        }
        add(comboBox1);
        add(comboBox2);

        JLabel modeLabel = new JLabel("게임 모드를 선택하세요:");
        add(modeLabel);

        String[] modes = {"게임지휘모드", "랜덤전투 관전모드"};
        JComboBox<String> modeBox = new JComboBox<>(modes);
        add(modeBox);

        JButton selectButton = new JButton("선택 완료");
        selectButton.addActionListener((ActionEvent e) -> {
            int idx1 = comboBox1.getSelectedIndex();
            int idx2 = comboBox2.getSelectedIndex();
            if (idx1 == idx2) {
                JOptionPane.showMessageDialog(this, "서로 다른 플레이어를 선택하세요!");
                return;
            }

            selectedPlayer1 = players.get(idx1);
            selectedPlayer2 = players.get(idx2);
            selectedMode = (String) modeBox.getSelectedItem();

            onSelectionComplete.run(); // 선택 완료 후 콜백 실행
            dispose();
        });
        add(selectButton);

        setLocationRelativeTo(null); // 화면 중앙에
    }

    public Player getSelectedPlayer1() { return selectedPlayer1; }
    public Player getSelectedPlayer2() { return selectedPlayer2; }
    public String getSelectedMode() { return selectedMode; }
}
