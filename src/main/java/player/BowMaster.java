package player;

import weapon.Weapon;

public class BowMaster extends Player {

    public BowMaster(String name, Weapon weapon, String imgFile1, String imgFile2) {
        super(name, 1200, 70, weapon, imgFile1, imgFile2);
        this.skillName = "애로우 레인";
    }

    @Override
    public void attack(Player target) {
        System.out.println(name + " fires [애로우 레인]!");
        if (weapon != null) {
            weapon.attackAction(this, target);
        } else {
            target.receiveDamage(this.getPower());
        }
    }
}
