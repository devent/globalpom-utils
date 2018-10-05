package com.anrisoftware.globalpom.core.mnemonic

import static java.awt.event.KeyEvent.*
import static javax.swing.KeyStroke.*

import com.google.inject.ProvisionException

[
    [string: "a", code: getKeyStroke(VK_A, 0), ex: null],
    [string: "VK_A,ALT_DOWN_MASK,CTRL_DOWN_MASK", code: getKeyStroke(VK_A, ALT_DOWN_MASK|CTRL_DOWN_MASK), ex: null],
    [string: "vk_a,alt_down_mask,ctrl_down_mask", code: getKeyStroke(VK_A, ALT_DOWN_MASK|CTRL_DOWN_MASK), ex: null],
    [string: "a,ALT_DOWN_MASK,CTRL_DOWN_MASK", code: getKeyStroke(VK_A, ALT_DOWN_MASK|CTRL_DOWN_MASK), ex: null],
    [string: "a,alt_down_mask,ctrl_down_mask", code: getKeyStroke(VK_A, ALT_DOWN_MASK|CTRL_DOWN_MASK), ex: null],
    [string: "", code: null, valid: false, ex: null],
    [string: "SOME", code: null, valid: false, ex: IllegalArgumentException],
    [string: null, code: null, valid: false, ex: ProvisionException],
    [string: "a,SOME", code: null, valid: true, ex: IllegalArgumentException],
    [string: "alt shift X", code: getKeyStroke(VK_X, ALT_MASK | SHIFT_MASK), ex: null],
]

