package weapon;

import player.Player;

public class FirePoisonBook implements Weapon {
    private final String name = "마도서 - 불독";
    private final int power = 20;

    @Override
    public String getName() { return name; }
    @Override
    public int getPower() { return power; }

    @Override
    public void attackAction(Player attacker, Player target) {
        int damage = (int)((attacker.getPower() + power) * (Math.random() * 2 + 0.5)); // 0.5x ~ 2.5x 랜덤
        System.out.println("▶ 독 구름을 퍼트립니다...");
        target.receiveDamage(damage);
    }
}
