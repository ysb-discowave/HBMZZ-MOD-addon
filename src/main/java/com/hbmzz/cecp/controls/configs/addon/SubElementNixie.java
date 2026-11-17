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

package com.hbmzz.cecp.controls.configs.addon;

import com.hbmzz.cecp.GuiControlEdit;
import com.hbmzz.cecp.compat.CtrlRes;
import com.hbmzz.cecp.controls.configs.SubElementBaseConfig;
import com.hbmzz.cecp.types.DataValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

/** Config page for the nixie display: how many tubes. */
public class SubElementNixie extends SubElementBaseConfig {

    private static final int[] TRANSFORM = {132, 112, 88, 88};
    private int length;
    GuiSlider slideLength;

    public SubElementNixie(GuiControlEdit gui, Map<String, DataValue> map) {
        super(gui, map);
        this.length = (int) map.get("length").getNumber();
    }

    @Override
    public void fillConfigs(Map<String, DataValue> configs) {
        putFloatConfig(configs, "length", length);
    }

    @Override
    public void initGui() {
        int cX = gui.width / 2;
        slideLength = gui.addButton(new GuiSlider(gui.currentButtonId(), cX + 10, gui.getGuiTop() + 70, 75, 15,
                "位数 ", "", 0, 8, length, false, true));
        super.initGui();
    }

    @Override
    protected void drawScreen() {
        GlStateManager.disableLighting();
        Minecraft.getMinecraft().getTextureManager().bindTexture(CtrlRes.white);
        GlStateManager.enableLighting();
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        length = slideLength.getValueInt();
    }

    @Override
    public void enableButtons(boolean enable) {
        slideLength.visible = enable;
        slideLength.enabled = enable;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int[] getPreviewTransform() {
        return TRANSFORM;
    }
}
