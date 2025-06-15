package weapon;

import player.Player;

public class Spear implements Weapon {
    private final String name = "창";
    private final int power = 30;

    @Override
    public String getName() { return name; }
    @Override
    public int getPower() { return power; }

    @Override
    public void attackAction(Player attacker, Player target) {
        int damage = attacker.getPower() + power;
        System.out.println("▶ " + attacker.getName() + " 찌르기 공격! (창)");
        target.receiveDamage(damage);
    }
}
