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
import com.hbmzz.cecp.ControlEvent;
import com.hbmzz.cecp.ControlPanel;
import com.hbmzz.cecp.GuiControlEdit;
import com.hbmzz.cecp.compat.AddonRes;
import com.hbmzz.cecp.compat.IModelCustom;
import com.hbmzz.cecp.controls.ControlType;
import com.hbmzz.cecp.controls.configs.SubElementBaseConfig;
import com.hbmzz.cecp.controls.configs.addon.SubElementTextIndicator;
import com.hbmzz.cecp.types.DataValue;
import com.hbmzz.cecp.types.DataValueFloat;
import com.hbmzz.cecp.types.DataValueString;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Map;

/**
 * Illuminated plate with a stencilled caption, e.g. {@code A3-5}.
 *
 * <p>Ported from CE Addon's {@code TextIndicator}. The lit level ramps up and
 * down one step per tick; upstream syncs that number with a packet, here the
 * client derives it from the synced {@code isLit} flag.</p>
 */
public class TextIndicator extends Control {

    public static final int duration = 3;

    private float red = 255F;
    private float green = 0F;
    private float blue = 0F;
    private String text = "A3-5";

    public int current = 0;
    private long lastStep;

    public TextIndicator(String name, String registryName, ControlPanel panel) {
        super(name, registryName, panel);
        vars.put("isLit", new DataValueFloat(0));
        configMap.put("red", new DataValueFloat(red));
        configMap.put("green", new DataValueFloat(green));
        configMap.put("blue", new DataValueFloat(blue));
        configMap.put("text", new DataValueString(text));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public SubElementBaseConfig getConfigSubElement(GuiControlEdit gui, Map<String, DataValue> configs) {
        return new SubElementTextIndicator(gui, configs);
    }

    @Override
    protected void onConfigMapChanged() {
        for (Map.Entry<String, DataValue> entry : configMap.entrySet()) {
            switch (entry.getKey()) {
                case "red":
                    red = MathHelper.clamp(entry.getValue().getNumber(), 0F, 255F);
                    break;
                case "green":
                    green = MathHelper.clamp(entry.getValue().getNumber(), 0F, 255F);
                    break;
                case "blue":
                    blue = MathHelper.clamp(entry.getValue().getNumber(), 0F, 255F);
                    break;
                case "text":
                    text = entry.getValue().toString();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public ControlType getControlType() {
        return ControlType.INDICATOR;
    }

    @Override
    public float[] getSize() {
        return new float[]{4.5F, 1.8F, 0.25F};
    }

    /** One step towards the target light level per tick. */
    @SideOnly(Side.CLIENT)
    private void advanceVisual() {
        long now = System.currentTimeMillis();
        if (now - lastStep < 50L) {
            return;
        }
        lastStep = now;
        int target = getVar("isLit").getBoolean() ? duration : 0;
        if (current != target) {
            current += current < target ? 1 : -1;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void render() {
        advanceVisual();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.color(1F, 1F, 1F);
        IModelCustom mdl = getModel();
        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, 0F, posY);
        GlStateManager.scale(0.6666667F, 0.6666667F, 0.6666667F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(AddonRes.solid);
        GlStateManager.color(0.29F, 0.29F, 0.29F);
        mdl.renderAllExcept("Plane.001");
        float lX = OpenGlHelper.lastBrightnessX;
        float lY = OpenGlHelper.lastBrightnessY;
        Minecraft.getMinecraft().getTextureManager().bindTexture(AddonRes.text_indicator_light_tex);
        float offset = 20F;
        GlStateManager.color(
                red / (255F + offset) * 0.25F + offset / 255F,
                green / (255F + offset) * 0.25F + offset / 255F,
                blue / (255F + offset) * 0.25F + offset / 255F);
        offset = 80F;
        mdl.renderPart("Plane.001");
        GlStateManager.enableBlend();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
        GlStateManager.translate(0, 0.001F, 0);
        GlStateManager.color(
                red / (255F + offset) + offset / 255F,
                green / (255F + offset) + offset / 255F,
                blue / (255F + offset) + offset / 255F,
                (float) Math.pow((float) current / duration, 0.5));
        mdl.renderPart("Plane.001");
        GlStateManager.color(1F, 1F, 1F);
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lX, lY);
        GlStateManager.disableBlend();
        GlStateManager.translate(0, 0.376F, 0);
        GlStateManager.scale(0.1F, 0.1F, 0.1F);
        GlStateManager.rotate(90F, 1F, 0F, 0F);
        font.drawString(text, -font.getStringWidth(text) / 2, -3, 0x4A4A4A);
        GlStateManager.color(1F, 1F, 1F);
        GlStateManager.popMatrix();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderControl(float[] renderBox, Control selectedControl, GuiControlEdit gui) {
        float boxMinX = renderBox[0];
        float boxMinY = renderBox[1];
        float boxMaxX = renderBox[2];
        float boxMaxY = renderBox[3];
        super.renderControl(renderBox, selectedControl, gui);
        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        GlStateManager.pushMatrix();
        GlStateManager.translate((boxMinX + boxMaxX) / 2F, (boxMinY + boxMaxY) / 2F, 0F);
        GlStateManager.scale(0.075F, 0.075F, 0.075F);
        font.drawString(text, -font.getStringWidth(text) / 2, -3, 0x4A4A4A);
        GlStateManager.popMatrix();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IModelCustom getModel() {
        return AddonRes.text_indicator;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getGuiTexture() {
        return AddonRes.text_indicator_gui_tex;
    }

    @Override
    public Control newControl(ControlPanel panel) {
        return new TextIndicator(name, registryName, panel);
    }

    @Override
    public void populateDefaultNodes(List<ControlEvent> receiveEvents) {
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return null;
    }
}
