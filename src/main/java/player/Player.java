package player;

import weapon.Weapon;

public abstract class Player {
    protected String name;
    protected int hp;
    protected int power;
    protected Weapon weapon;
    protected String skillName;

    private String imgFile1; 
    private String imgFile2;

    public Player(String name, int hp, int power) {
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.skillName = "강타";
    }

    public Player(String name, int hp, int power, Weapon weapon) {
        this(name, hp, power);
        this.weapon = weapon;
        this.skillName = "강타";
    }

    public Player(String name, int hp, int power, Weapon weapon, String imgFile1, String imgFile2) {
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.weapon = weapon;
        this.imgFile1 = imgFile1;
        this.imgFile2 = imgFile2;
        this.skillName = "강타";
    }

    
    // Getter
    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getPower() {
        return power;
    }
    
    public String getImgFile1() {
        return imgFile1;
    }

    public void setImgFile1(String imgFile1) {
        this.imgFile1 = imgFile1;
    }

    public String getImgFile2() {
        return imgFile2;
    }

    public void setImgFile2(String imgFile2) {
        this.imgFile2 = imgFile2;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void receiveDamage(int damage) {
        hp -= damage;
        if (hp < 0) hp = 0;
        System.out.println(name + " receives " + damage + " damage. [HP: " + hp + "]");
    }

    // 기본 공격
    public void attack(Player target) {
        if (weapon != null) {
            System.out.println(this.name + " attacks " + target.getName() + " with " + weapon.getName());
            weapon.attackAction(this, target);
        } else {
            System.out.println(this.name + " attacks " + target.getName() + " with bare hands.");
            target.receiveDamage(this.power);
        }
    }

    // 특정 무기 선택 공격
    public void attack(Player target, Weapon weapon) {
        System.out.println(this.name + " attacks " + target.getName() + " with " + weapon.getName());
        weapon.attackAction(this, target);
    }

    // 플레이어 상태 출력
    public void show() {
        System.out.println("👤 이름: " + name);
        System.out.println("❤ HP: " + hp);
        System.out.println("💪 공격력: " + power);
        System.out.println("🛠 장착 무기: " + (weapon != null ? weapon.getName() : "없음"));
        System.out.println("🛡 상태: " + (isAlive() ? "🟢 생존" : "❌ 사망"));
        System.out.println("------------------------------------");
    }

    // 무기 장착
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
        System.out.println(name + "이(가) " + weapon.getName() + "을(를) 장착했습니다.");
    }

    public String getSkillName() {
        return skillName;
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
