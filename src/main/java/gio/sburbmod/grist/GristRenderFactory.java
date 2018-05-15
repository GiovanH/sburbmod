package gio.sburbmod.grist;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class GristRenderFactory implements IRenderFactory<EntityGristPickup> {

	@Override
	public Render<EntityGristPickup> createRenderFor(RenderManager manager) {
		// TODO Auto-generated method stub
		return new RenderGristPickup(manager);
	}

}
