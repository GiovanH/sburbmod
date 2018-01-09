package gio.sburbmod.punchcard;

import java.util.List;

import javax.annotation.Nullable;

import gio.sburbmod.alchemy.SburbItemTooltip;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PunchCard extends Item {

	// private static final boolean DEBUG_COLOR = false;

	final int VARIANTS = 5;

	public int getVariants() {
		return VARIANTS;
	}

	public PunchCard() {
		this.setMaxStackSize(64);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.MISC); // the item will appear on the Miscellaneous tab in creative
	}

	// this.setDamage(stack, (stack.getTagCompound() != null &&
	// stack.getTagCompound().hasKey("Item") ? 1 : 0));

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (int i = 0; i <= VARIANTS; i++) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		// this.setDamage(stack, (stack.getTagCompound().hasKey("Item") ? 1 : 0));
		return stack.getMetadata() == 0 ? "item.sburbmod_punchcard" : "item.sburbmod_punchcard_punched";
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		super.addInformation(stack, worldIn, tooltip, flagIn);
		SburbItemTooltip.addInformation(stack, worldIn, tooltip, flagIn);
	}

}
