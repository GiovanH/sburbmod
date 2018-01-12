package gio.sburbmod.alchtable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: GiovanH Date: 2018-01-03
 *
 */
@SideOnly(Side.CLIENT)
public class GuiInventoryTable extends GuiContainer {

	// This is the resource location for the background image
	private static final ResourceLocation texture = new ResourceLocation("sburbmod", "textures/gui/alchtable_bg.png");
	private static final int ABUTTON = 0;
	private static final int NBUTTON = 1;
	private TileTable tileEntity;

	GuiButton aButton, nButton;

	public GuiInventoryTable(InventoryPlayer invPlayer, TileTable tilePuncher) {
		super(new ContainerTable(invPlayer, tilePuncher));

		// Set the width and height of the gui
		xSize = 176;
		ySize = 207;

		this.tileEntity = tilePuncher;
	}

	// some [x,y] coordinates of graphical elements
	final int COOK_BAR_XPOS = 79;
	final int COOK_BAR_YPOS = 35;
	final int COOK_BAR_ICON_U = 176; // texture position of white arrow icon
	final int COOK_BAR_ICON_V = 14;
	final int COOK_BAR_WIDTH = 24;
	final int COOK_BAR_HEIGHT = 17;

	final int FLAME_XPOS = 98;
	final int FLAME_YPOS = 76;
	final int FLAME_ICON_U = 176; // texture position of flame icon
	final int FLAME_ICON_V = 16;
	final int FLAME_WIDTH = 16;
	final int FLAME_HEIGHT = 16;

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y) {
		// Bind the image texture
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		// Draw the image
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		// // get cook progress as a double between 0 and 1
		// double cookProgress = tileEntity.fractionOfCookTimeComplete();
		// // draw the cook progress bar
		// int width = (int) ((cookProgress) * COOK_BAR_WIDTH);
		// drawTexturedModalRect(guiLeft + COOK_BAR_XPOS, guiTop + COOK_BAR_YPOS,
		// COOK_BAR_ICON_U,
		// COOK_BAR_ICON_V, width, COOK_BAR_HEIGHT);

		// // get cook progress as a double between 0 and 1
		// double burnRemaining = tileEntity.fractionOfFuelRemaining();
		// yOffset = (int) ((1.0 - burnRemaining) * FLAME_HEIGHT);
		// drawTexturedModalRect(guiLeft + FLAME_XPOS, guiTop + FLAME_YPOS + yOffset,
		// FLAME_ICON_U, FLAME_ICON_V + yOffset,
		// FLAME_WIDTH, FLAME_HEIGHT - yOffset);
	}

	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		buttonList.add(aButton = new GuiButton(ABUTTON, this.guiLeft + 17, 53 + this.guiTop, 18, 18, "&&"));
		buttonList.add(nButton = new GuiButton(NBUTTON, this.guiLeft + 55, 53 + this.guiTop, 18, 18, "||"));
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
			PacketMessageToServer airstrikeMessageToServer = new PacketMessageToServer(this.tileEntity.getPos().toLong(), (short) button.id);
			StartupCommon.simpleNetworkWrapper.sendToServer(airstrikeMessageToServer);
	super.actionPerformed(button);

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		final int LABEL_XPOS = 5;
		final int LABEL_YPOS = 5;
		fontRenderer.drawString(tileEntity.getDisplayName().getUnformattedText(), LABEL_XPOS, LABEL_YPOS,
				Color.darkGray.getRGB());

		aButton.drawButtonForegroundLayer(mouseX + guiLeft, mouseY + guiTop);

		List<String> hoveringText = new ArrayList<String>();

		// If the mouse is over one of the burn time indicator add the burn time
		// indicator hovering text
		// for (int i = 0; i < tileEntity.FUEL_SLOTS_COUNT; ++i) {
		// if (isInRect(guiLeft + FLAME_XPOS + FLAME_X_SPACING * i, guiTop + FLAME_YPOS,
		// FLAME_WIDTH, FLAME_HEIGHT, mouseX, mouseY)) {
		// hoveringText.add("Fuel Time:");
		// hoveringText.add(tileEntity.secondsOfFuelRemaining(i) + "s");
		// }
		// }
		// If hoveringText is not empty draw the hovering text
		if (!hoveringText.isEmpty()) {
			drawHoveringText(hoveringText, mouseX - guiLeft, mouseY - guiTop, fontRenderer);
		}

		this.renderHoveredToolTip(mouseX - guiLeft, mouseY - guiTop);
		// // You must re bind the texture and reset the colour if you still need to use
		// it after drawing a string
		// Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		// GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

	}

	// Returns true if the given x,y coordinates are within the given rectangle
	public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY) {
		return ((mouseX >= x && mouseX <= x + xSize) && (mouseY >= y && mouseY <= y + ySize));
	}
}
