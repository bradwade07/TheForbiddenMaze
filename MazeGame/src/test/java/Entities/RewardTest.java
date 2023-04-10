package Entities;

import Map.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RewardTest {

    @Test
    void obtainReward() {
        Player p = new Player(EntityType.player, new Point(1,1));
        Reward r = new Reward(EntityType.reward, new Point(1,1),50);
        r.obtainReward(p);
        assertFalse(r.rewardStatus());
        assertEquals(150,p.getScore());
    }

    @Test
    void rewardStatus() {
        Reward r = new Reward(EntityType.reward, new Point(1,1),50);
        assertTrue(r.rewardStatus());
    }

    @Test
    void getScore() {
        Reward r = new Reward(EntityType.reward, new Point(1,1),50);
        assertEquals(50,r.getScore());
    }
}