/*
 * Copyright (c) 2021. Patryk Goworowski
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.patrykgoworowski.vico.core.dataset.line

import pl.patrykgoworowski.vico.core.component.Component
import pl.patrykgoworowski.vico.core.draw.DrawContext

internal fun Component.drawPoint(
    context: DrawContext,
    x: Float,
    y: Float,
    halfPointSize: Float,
) {
    draw(
        context = context,
        left = x - halfPointSize,
        top = y - halfPointSize,
        right = x + halfPointSize,
        bottom = y + halfPointSize,
    )
}
