/*
 * HBMZZ addon - control panel content split out of HBMZZ.
 * Copyright (C) 2025-2026 Leafia, movblock (upstream NTM Cursed Addon)
 * Modifications Copyright (C) 2026 Yang Sibo (ysb-discowave)
 *
 * Ported from NTM Cursed Addon (CE Addon).
 *
 * This file is distributed under the PolyForm Shield License 1.0.0, not under
 * the GNU GPL.  See LICENSE-PolyForm.txt and NOTICE-hbmzz-addon.md in this
 * repository, or <https://polyformproject.org/licenses/shield/1.0.0>.
 * Do not relicense it under GPL/MIT or any other terms: the upstream license
 * forbids sublicensing and includes a noncompete clause.
 */

package com.hbmzz.cecp.controls.addon;

import com.hbmzz.cecp.Control;
import com.hbmzz.cecp.ControlPanel;
import com.hbmzz.cecp.compat.AddonRes;
import com.hbmzz.cecp.compat.IModelCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

/** Lever standing upright: 1 wide, 2 deep. */
public class LeverVertical extends LeverBase {

    public LeverVertical(String name, String registryName, ControlPanel panel) {
        super(name, registryName, panel);
    }

    @Override
    public float[] getSize() {
        return new float[]{1F, 2F, 1.25F};
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void render() {
        advanceVisual();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.color(1F, 1F, 1F);
        IModelCustom mdl = getModel();
        Minecraft.getMinecraft().getTextureManager().bindTexture(AddonRes.solid);
        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, 0F, posY);
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        GlStateManager.color(0.306F, 0.306F, 0.306F);
        mdl.renderPart("Base");
        GlStateManager.color(0.765F, 0.765F, 0.765F);
        mdl.renderPart("Base.001");
        GlStateManager.translate(0, 0.1, 0);
        GlStateManager.rotate(-50F * (curLevel / 50F - 1F), 1F, 0F, 0F);
        GlStateManager.translate(0, -0.1, 0);
        GlStateManager.color(0.533F, 0.533F, 0.533F);
        mdl.renderPart("Lever");
        GlStateManager.popMatrix();
        GlStateManager.shadeModel(GL11.GL_FLAT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IModelCustom getModel() {
        return AddonRes.lever;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getGuiTexture() {
        return AddonRes.lever_v_gui_tex;
    }

    @Override
    public Control newControl(ControlPanel panel) {
        return new LeverVertical(name, registryName, panel);
    }
}
