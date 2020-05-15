package buildcraft.test.lib.inventory;

import org.junit.Assert;
import org.junit.Test;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.common.ForgeDirection;

import buildcraft.api.inventory.IItemTransactor;

import buildcraft.lib.tile.item.ItemHandlerSimple;
import buildcraft.lib.tile.item.StackInsertionFunction;

import buildcraft.test.VanillaSetupBaseTester;

public class ItemTransactorTester extends VanillaSetupBaseTester {
    private IInventory fakeInventory;
    private ItemStack fakeStack;
    private ItemStack injectStack;
    
    @Test
    public void emptyInventory_dontAdd(){

        TransactorSimple split = new TransactorSimple(fakeInventory);
        injectStack.stackSize = 64;

        when(fakeInventory.getSizeInventory()).thenReturn(1);
        when(fakeInventory.getStackInSlot(0)).thenReturn(null);

        cut.inject(injectStack, ForgeDirection.UNKOWN, false);
        verify(fakeInventory, never()).setInventorySlotContents(eq(0), (ItemStack) anyObject());
    }

	@Test
	public void emptyInventory_doAdd(){

        TransactorSimple cut = new TransactorSimple(fakeInventory);
		injectStack.stackSize = 64;

		when(fakeInventory.getSizeInventory()).thenReturn(1);
		when(fakeInventory.getStackInSlot(0)).thenReturn(null);
		when(fakeInventory.isStackValidForSlot(anyInt(), (ItemStack)anyObject())).thenReturn(true);
		
		cut.inject(injectStack, ForgeDirection.UNKNOWN, true);
		verify(fakeInventory).setInventorySlotContents(eq(0), (ItemStack) anyObject());
	}
    
    @Test
	public void fullInventory_returnSuccess(){

        TransactorSimple cut = new TransactorSimple(fakeInventory);

		when(fakeInventory.getSizeInventory()).thenReturn(0);
		when(fakeInventory.getStackInSlot(0)).thenReturn(fakeStack);
        
        int actual = cut.inject(injectStack, ForgeDirection.UNKNOWN, false);
		assertEquals(0, actual);
	}
    
    @Test
	public void fullInventory_returnFail(){

        TransactorSimple cut = new TransactorSimple(fakeInventory);

		when(fakeInventory.getSizeInventory()).thenReturn(0);
		when(fakeInventory.getStackInSlot(0)).thenReturn(fakeStack);
        
        int actual = cut.inject(injectStack, ForgeDirection.UNKNOWN, false);
		assertEquals(1, actual);
	}

	@Test
	public void halfFullInventory_returnFail(){

        TransactorSimple cut = new TransactorSimple(fakeInventory);
        ItemStack itemStack = mock(ItemStack.class);
        injectStack.stackSize = 64;
        itemStack.stackSize = 63;

		when(itemStack.isItemEqual((ItemStack) anyObject())).thenReturn(true);
		when(itemStack.getMaxStackSize()).thenReturn(64);		
		when(ItemStack.areItemStackTagsEqual((ItemStack)anyObject(), (ItemStack)anyObject())).thenReturn(true);
		when(fakeInventory.getSizeInventory()).thenReturn(1);
		when(fakeInventory.getStackInSlot(0)).thenReturn(itemStack);
        
        int actual = cut.inject(injectStack, ForgeDirection.UNKNOWN, false);
		assertEquals(1, actual);
    }
    
	@Test
	public void halfFullInventory_returnSuccess(){

        TransactorSimple cut = new TransactorSimple(fakeInventory);
        ItemStack itemStack = mock(ItemStack.class);
        injectStack.stackSize = 64;
        itemStack.stackSize = 63;

		when(itemStack.isItemEqual((ItemStack) anyObject())).thenReturn(true);
		when(itemStack.getMaxStackSize()).thenReturn(64);		
		when(ItemStack.areItemStackTagsEqual((ItemStack)anyObject(), (ItemStack)anyObject())).thenReturn(true);
		when(fakeInventory.getSizeInventory()).thenReturn(1);
		when(fakeInventory.getStackInSlot(0)).thenReturn(itemStack);
        
        int actual = cut.inject(injectStack, ForgeDirection.UNKNOWN, false);
		assertEquals(0, actual);
    }

    @Test
    public void testSimpleMoving() {
        IItemTransactor trans = new ItemHandlerSimple(2, null);

        Assert.assertTrue(trans.extract(null, 1, 1, false).isEmpty());

        ItemStack insert = new ItemStack(Items.APPLE);
        ItemStack leftOver = trans.insert(insert.copy(), false, false);

        Assert.assertTrue(leftOver.isEmpty());

        ItemStack extracted = trans.extract(null, 1, 1, false);

        Assert.assertTrue(ItemStack.areItemStacksEqual(insert, extracted));

        extracted = trans.extract(null, 1, 1, false);

        Assert.assertTrue(extracted.isEmpty());
    }

    @Test
    public void testLimitedInventory() {
        IItemTransactor limited = new ItemHandlerSimple(2, (i, s) -> true, StackInsertionFunction.getInsertionFunction(4), null);

        ItemStack toInsert = new ItemStack(Items.APPLE, 9);
        ItemStack toInsertCopy = toInsert.copy();
        ItemStack supposedLeftOver = new ItemStack(Items.APPLE);

        ItemStack actuallyLeftOver = limited.insert(toInsert, false, false);

        Assert.assertTrue(ItemStack.areItemStacksEqual(toInsert, toInsertCopy));
        Assert.assertTrue(ItemStack.areItemStacksEqual(supposedLeftOver, actuallyLeftOver));
    }
    @Test
    public void inject_inventoryHasNoSlots_returns0(){
        when(fakeInventory.getSizeInventory()).thenReturn(0);

        TransactorSimple cut = new TransactorSimple(fakeInventory);
        int actual = cut.inject(injectStack, ForgeDirection.UNKNOWN, false);

        assertEquals(0, actual);
    }

    @Test
    public void inject_inventoryIsEmptyDontAdd_returns64(){
        when(fakeInventory.getSizeInventory()).thenReturn(1);
        when(fakeInventory.getStackInSlot(0)).thenReturn(null);
        when(fakeInventory.isStackValidForSlot(anyInt(), (ItemStack)anyObject())).thenReturn(true);
        injectStack.stackSize = 64;

        TransactorSimple cut = new TransactorSimple(fakeInventory);
        int actual = cut.inject(injectStack, ForgeDirection.UNKNOWN, false);

        assertEquals(64, actual);
    }

    @Test
    public void inject_inventoryIsEmptyDoAdd_returns64(){
        when(fakeInventory.getSizeInventory()).thenReturn(1);
        when(fakeInventory.getStackInSlot(0)).thenReturn(null);
        when(fakeInventory.isStackValidForSlot(anyInt(), (ItemStack)anyObject())).thenReturn(true);
        injectStack.stackSize = 64;

        TransactorSimple cut = new TransactorSimple(fakeInventory);
        int actual = cut.inject(injectStack, ForgeDirection.UNKNOWN, true);

        assertEquals(64, actual);
    }
}
