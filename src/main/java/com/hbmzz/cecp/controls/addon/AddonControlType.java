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

import com.hbmzz.cecp.controls.ControlType;

/**
 * Control types the addon registers on top of CE's own list.
 *
 * <p>{@link ControlType#registerOrGet} is keyed by the same English names the
 * addon uses, so panels saved with it still resolve their control type.</p>
 */
public final class AddonControlType {

    public static final ControlType RBMK_DISPLAY = ControlType.registerOrGet("RBMK Display");
    public static final ControlType GRAPH = ControlType.registerOrGet("Graph");
    public static final ControlType LEVER = ControlType.registerOrGet("Lever");
    /**
     * The command terminal.
     *
     * <p>Its own family rather than a display: it is neither read-only nor a
     * knob, and the editor's menu groups by type, so it deserves the row.</p>
     */
    public static final ControlType TERMINAL = ControlType.registerOrGet("Terminal");

    /**
     * Forces this class - and therefore the three registrations above - to run.
     *
     * <p>Static fields are initialised on first <em>use</em>, and nothing uses
     * them during startup: {@code ControlRegistry.init()} builds the control
     * table by constructing control objects, and their {@code getControlType()}
     * is not called until something walks it. That "something" used to be the
     * console editor's type menu, which walked the type list at the same time -
     * so the very first walk appended three entries to the list it was
     * iterating, and the editor died of a ConcurrentModificationException
     * before it could draw.</p>
     *
     * <p>Calling this from {@code ControlRegistry.init()} makes the type list
     * complete before any GUI can read it. The menu is also fixed to iterate a
     * snapshot, because one lazy registry is all it takes and there may be more
     * later.</p>
     */
    public static void register() {
        // Touch every constant: a reference to one initialises the class.
        if (RBMK_DISPLAY == null || GRAPH == null || LEVER == null || TERMINAL == null) {
            throw new IllegalStateException("control type registration failed");
        }
    }

    private AddonControlType() {
    }
}
