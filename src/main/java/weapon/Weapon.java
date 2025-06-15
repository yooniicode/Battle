package weapon;

import player.Player;

public interface Weapon {
    String getName();
    int getPower();

    // 무기의 특수한 공격 방식 정의
    void attackAction(Player attacker, Player target);
}
