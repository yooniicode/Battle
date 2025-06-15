package view;

import player.*;
import weapon.*;

import java.util.Arrays;
import java.util.List;

public class PlayerFactory {
    public static List<Player> createDefaultPlayers() {
        Weapon spear = new Spear();
        Weapon fireBook = new FirePoisonBook();
        Weapon bow = new Bow();

        Player p = new BowMaster("윤지", bow, "/img/yooni.png", "/img/yooni_dead.png");
        Player dk = new DarkKnight("다크나이트", spear, "/img/darkknight.png", "/img/darkknight_dead.png");
        Player mage = new FirePoisonMage("알감자", fireBook, "/img/mage.png", "/img/mage_dead.png");
        Player bm = new BowMaster("백제의후예", bow, "/img/bowmaster.png", "/img/bowmaster_dead.png");

        return Arrays.asList(p, dk, mage, bm);
    }
}
