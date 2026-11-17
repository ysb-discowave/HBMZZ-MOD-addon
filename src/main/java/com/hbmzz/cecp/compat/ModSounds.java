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

package com.hbmzz.cecp.compat;

import net.minecraft.util.SoundEvent;

/**
 * Looks up a sound that lives in another mod's static field.
 *
 * <p>The addon's controls reference sounds on {@code HBMSoundHandler} and
 * {@code LeafiaSoundEvents} directly. Those are compile-time dependencies this
 * mod deliberately does not have - a plain CE install has the first but not the
 * second, and neither is guaranteed - so the field is resolved by name and a
 * missing sound degrades to silence instead of a crash.</p>
 */
public final class ModSounds {

    public static SoundEvent find(String owner, String field) {
        if (owner == null || field == null) {
            return null;
        }
        try {
            Class<?> type = Class.forName(owner, true, ModSounds.class.getClassLoader());
            Object value = type.getField(field).get(null);
            return value instanceof SoundEvent ? (SoundEvent) value : null;
        } catch (Throwable missing) {
            return null;
        }
    }

    private ModSounds() {
    }
}
