package player;

import weapon.Weapon;

public class FirePoisonMage extends Player {

    public FirePoisonMage(String name, Weapon weapon, String imgFile1, String imgFile2) {
        super(name, 1100, 55, weapon, imgFile1, imgFile2);
        this.skillName = "파이어볼";
    }

    @Override
    public void attack(Player target) {
        System.out.println(name + " casts [포이즌 미스트]!");
        if (weapon != null) {
            weapon.attackAction(this, target);
        } else {
            target.receiveDamage(this.getPower());
        }
    }
}
