import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.de1.AngryBirds.GameObjects.Pigs.Pig;
import org.junit.Test;
import static org.junit.Assert.*;

public class PigTests {

    @Test
    public void testPigConstructor() {
        World world = new World(new Vector2(0,-7), true);
        Pig pig = new Pig(world, 100, 760, 300);
        assertEquals(300, pig.getHealth());
        assertNotNull(pig.getWorld());
        assertEquals(100, pig.getxPos(),0.1);
        assertEquals(760, pig.getyPos(),0.1);
    }

    @Test
    public void testPigDamage() {
        World world = new World(new Vector2(0,-7), true);
        Pig pig = new Pig(world, 100, 760, 300);
        pig.Damage(100);
        assertEquals(200, pig.getHealth());
    }

    @Test
    public void testPigIsDestroyed() {
        World world = new World(new Vector2(0,-7), true);
        Pig pig = new Pig(world, 100, 760, 300);
        pig.Damage(300);
        assertTrue(pig.isDestroyed());
    }

    @Test
    public void testPigCreateBody() {
        World world = new World(new Vector2(0,-7), true);
        Pig pig = new Pig(world, 100, 760, 300);
        pig.testCreateBody(100, 760);
        assertNotNull(pig.getBody());
    }
}
