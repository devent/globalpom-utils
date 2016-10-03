/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anrisoftware.globalpom.mnemonic

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

