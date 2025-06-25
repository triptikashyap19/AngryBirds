import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.de1.AngryBirds.GameObjects.Blocks.Glass;
import com.de1.AngryBirds.GameObjects.Blocks.Stone;
import com.de1.AngryBirds.GameObjects.Blocks.Wood;
import org.junit.Test;
import static org.junit.Assert.*;

public class BlockTests {

    @Test
    public void testStoneBlockConstructor() {
        World world = new World(new Vector2(0,-7), true);
        Stone stone = new Stone(world, 100, 760, 300);
        assertEquals(300, stone.getHealth());
        assertNotNull(stone.getWorld());
        assertEquals(100, stone.getxPos(),0.1);
        assertEquals(760, stone.getyPos(),0.1);
    }

    @Test
    public void testGlassBlockConstructor() {
        World world = new World(new Vector2(0,-7), true);
        Glass glass = new Glass(world, 120, 236, 100);
        assertEquals(100, glass.getHealth());
        assertNotNull(glass.getWorld());
        assertEquals(120, glass.getxPos(),0.1);
        assertEquals(236, glass.getyPos(),0.1);
    }

    @Test
    public void testWoodBlockConstructor() {
        World world = new World(new Vector2(0,-7), true);
        Wood wood = new Wood(world, 450, 560, 200);
        assertEquals(200, wood.getHealth());
        assertNotNull(wood.getWorld());
        assertEquals(450, wood.getxPos(),0.1);
        assertEquals(560, wood.getyPos(),0.1);
    }

    @Test
    public void testStoneBlockDamage() {
        World world = new World(new Vector2(0,-7), true);
        Stone stone = new Stone(world, 100, 760, 300);
        stone.Damage(100);
        assertEquals(200, stone.getHealth());
    }

    @Test
    public void testisDestroyed() {
        World world = new World(new Vector2(0,-7), true);
        Stone stone = new Stone(world, 100, 760, 300);
        stone.Damage(300);
        assertTrue(stone.isDestroyed());
    }

}
