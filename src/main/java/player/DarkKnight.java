package player;

import weapon.Weapon;

public class DarkKnight extends Player {

    public DarkKnight(String name, Weapon weapon, String imgFile1, String imgFile2) {
        super(name, 1300, 40, weapon, imgFile1, imgFile2);
    }

    @Override
    public void attack(Player target) {
        System.out.println(name + " uses [피어스 사이클론]!");
        if (weapon != null) {
            weapon.attackAction(this, target);
        } else {
            target.receiveDamage(this.getPower());
        }
    }
}
