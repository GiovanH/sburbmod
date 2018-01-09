package gio.sburbmod.alchemiter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiInventoryAlchemiter extends GuiContainer {

	// This is the resource location for the background image
	private static final ResourceLocation texture = new ResourceLocation("sburbmod", "textures/gui/alchemiter_bg.png");
	private TileAlchemiter tileEntity;

	public GuiInventoryAlchemiter(InventoryPlayer invPlayer, TileAlchemiter tileCruxtruder) {
		super(new ContainerAlchemiter(invPlayer, tileCruxtruder));

		// Set the width and height of the gui
		xSize = 176;
		ySize = 207;

		this.tileEntity = tileCruxtruder;
	}

	// some [x,y] coordinates of graphical elements
	final int COOK_BAR_XPOS = 33;
	final int COOK_BAR_YPOS = 10;
	final int COOK_BAR_ICON_U = 176; // texture position of white arrow icon
	final int COOK_BAR_ICON_V = 0;
	final int COOK_BAR_WIDTH = 55;
	final int COOK_BAR_HEIGHT = 110;

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y) {
		// Bind the image texture
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		// Draw the image
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		// get cook progress as a double between 0 and 1
		double cookProgress = tileEntity.fractionOfCookTimeComplete();
		// draw the cook progress bar
		int yOffset = (int) ((1 - cookProgress) * COOK_BAR_HEIGHT);
		drawTexturedModalRect(guiLeft + COOK_BAR_XPOS, guiTop + COOK_BAR_YPOS + yOffset, COOK_BAR_ICON_U,
				COOK_BAR_ICON_V + yOffset, COOK_BAR_WIDTH, COOK_BAR_HEIGHT - yOffset);
		drawTexturedModalRect(guiLeft + COOK_BAR_XPOS+COOK_BAR_WIDTH, guiTop + COOK_BAR_YPOS + yOffset, COOK_BAR_ICON_U,
				COOK_BAR_ICON_V + yOffset+COOK_BAR_HEIGHT, COOK_BAR_WIDTH, COOK_BAR_HEIGHT - yOffset);

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		final int LABEL_XPOS = 5;
		final int LABEL_YPOS = 5;
		fontRenderer.drawString(tileEntity.getDisplayName().getUnformattedText(), LABEL_XPOS, LABEL_YPOS,
				Color.darkGray.getRGB());

		List<String> hoveringText = new ArrayList<String>();

		// If the mouse is over the progress bar add the progress bar hovering text
//		if (isInRect(guiLeft + COOK_BAR_XPOS, guiTop + COOK_BAR_YPOS, COOK_BAR_WIDTH, COOK_BAR_HEIGHT, mouseX,
//				mouseY)) {
//			hoveringText.add("Progress:");
//			int cookPercentage = (int) (tileEntity.fractionOfCookTimeComplete() * 100);
//			hoveringText.add(cookPercentage + "%");
//		}
		
		// If hoveringText is not empty draw the hovering text
		if (!hoveringText.isEmpty()) {
			drawHoveringText(hoveringText, mouseX - guiLeft, mouseY - guiTop, fontRenderer);
		}

        this.renderHoveredToolTip(mouseX- guiLeft, mouseY- guiTop);
	}

	// Returns true if the given x,y coordinates are within the given rectangle
	public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY) {
		return ((mouseX >= x && mouseX <= x + xSize) && (mouseY >= y && mouseY <= y + ySize));
	}
}
