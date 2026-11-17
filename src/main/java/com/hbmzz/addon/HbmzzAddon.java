/*
 * hbmzz:addon - control panel content split out of HBMZZ.
 *
 * This file is part of hbmzz:addon, which is licensed under the PolyForm
 * Shield License 1.0.0.  See LICENSE-PolyForm.txt and NOTICE-hbmzz-addon.md.
 */

package com.hbmzz.addon;

import com.hbmzz.cecp.controls.addon.AddonControlType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

/**
 * Mod container for the addon.
 *
 * <p>The addon is a soft dependency of the root mod: {@code after:hbmzz} means "load
 * after hbmzz if it is present, do not refuse to start if it is not".  The reverse
 * direction is deliberately not expressed - the root mod must keep starting without
 * the addon, otherwise the two jars stop being separable.</p>
 */
@Mod(modid = HbmzzAddon.MODID, name = HbmzzAddon.NAME, version = HbmzzAddon.VERSION,
        dependencies = "after:hbmzz")
public class HbmzzAddon {

    public static final String MODID = "hbmzzaddon";
    public static final String NAME = "HBMZZ Addon";
    public static final String VERSION = "0.1.0";

    @Mod.Instance(MODID)
    public static HbmzzAddon instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        // The control types live in this jar now, so the root mod's ControlRegistry can no
        // longer touch them. Registering here - before any GUI is built - keeps the type
        // list complete when the console editor first walks it; left lazy, the walk would
        // append to the list it is iterating. registerOrGet is keyed by name, so a second
        // call from anywhere is a no-op.
        AddonControlType.register();
        logger.info("hbmzz:addon {} - control types registered", VERSION);
    }
}
