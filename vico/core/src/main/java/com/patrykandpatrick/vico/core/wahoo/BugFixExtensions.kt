/*
 * Copyright 2024 by Patryk Goworowski and Patrick Michalik.
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

package com.patrykandpatrick.vico.core.wahoo

import com.patrykandpatrick.vico.core.cartesian.CartesianDrawingContext
import com.patrykandpatrick.vico.core.common.length

public fun Float.setNaNto0(): Float = if (this.isNaN()) 0.0f else this

public fun CartesianDrawingContext.getVerticalGuidelineValues(
  visibleXRange: ClosedFloatingPointRange<Double>
): List<Double> {
  val quarterLength = visibleXRange.length / 4
  val guideline1 = visibleXRange.start + quarterLength
  val guideline2 = visibleXRange.start + (quarterLength * 2)
  val guideline3 = visibleXRange.start + (quarterLength * 3)

  return listOf(guideline1, guideline2, guideline3)
}
