package buildcraft.test.lib.misc.data;

import org.junit.Assert;
import org.junit.Test;

import net.minecraft.nbt.NBTTagCompound;

import buildcraft.lib.misc.data.AverageLong;

public class AverageLongTester {
    @Test
    public void testNbt() {
        final long val = 0xDC_BA_98_76_54_32_10L;

        AverageLong avg = new AverageLong(5);
        AverageLong avg2 = new AverageLong(5);
        avg.tick(val);
        avg.tick(val);
        avg.tick(val);
        avg.tick(val);
        avg.tick(val);
        avg.tick(val);

        NBTTagCompound nbt = new NBTTagCompound();
        avg.writeToNbt(nbt, "test");
        avg2.readFromNbt(nbt, "test");
        Assert.assertEquals(avg.getAverageLong(), avg2.getAverageLong());
    }

    @Test
    public void testNbt2() {
        final long val =0xDC_BA_98_76_54_32_10L;
        AverageLong avg = new AverageLong(6);
        AverageLong avg2 = new AverageLong(6);
        avg.tick(val);
        avg.tick(val);
        avg.tick(val);
        avg.tick(val);
        avg.tick(val);
        avg.tick(val);
        avg.tick(val);
        NBTTagCompound nbt = new NBTTagCompound();
        avg.writeToNbt(nbt, "test");
        avg2.readFromNbt(nbt, "test");
        Assert.assertEquals(avg.getAverageLong(), avg2.getAverageLong());
    }

     @Test
    public void testNbtEmpty() {
       final long val =0xDC_BA_98_76_54_32_10L;
        AverageLong avg = new AverageLong(1);
        AverageLong avg2 = new AverageLong(1);
        avg.tick(val);
       
        NBTTagCompound nbt = new NBTTagCompound();
        avg.writeToNbt(nbt, "test");
        avg2.readFromNbt(nbt, "test");
        Assert.assertEquals(avg.getAverageLong(), avg2.getAverageLong());
    }
       @Test
    public void testNbtEmpty1() {
       final long val =0xDC_BA_98_76_54_32_10L;
        AverageLong avg = new AverageLong(1);
        AverageLong avg2 = new AverageLong(1);
        avg.tick(val);
        avg.tick(val);
       
        NBTTagCompound nbt = new NBTTagCompound();
        avg.writeToNbt(nbt, "test");
        avg2.readFromNbt(nbt, "test");
        Assert.assertEquals(avg.getAverageLong(), avg2.getAverageLong());
    }

}
