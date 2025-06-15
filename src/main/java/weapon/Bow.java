package weapon;

import player.Player;

public class Bow implements Weapon {
    private final String name = "활";
    private final int power = 25;

    @Override
    public String getName() { return name; }
    @Override
    public int getPower() { return power; }

    @Override
    public void attackAction(Player attacker, Player target) {
        System.out.println("▶ 애로우 레인 발사!");
        int totalDamage = attacker.getPower() + power;

        // 활은 2연타 효과!
        target.receiveDamage(totalDamage / 2);
        target.receiveDamage(totalDamage / 2);
    }
}
