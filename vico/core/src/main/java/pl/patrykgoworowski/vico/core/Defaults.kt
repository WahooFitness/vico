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

package pl.patrykgoworowski.vico.core

public const val DEF_LABEL_LINE_COUNT: Int = 1
public const val DEF_LABEL_COUNT: Int = 99
public const val DEF_LABEL_SIZE: Float = 12f
public const val DEF_LABEL_SPACING: Float = 16f

public const val DEF_MARKER_TICK_SIZE: Float = 6f

public const val MAX_ZOOM: Float = 10f
public const val MIN_ZOOM: Float = 0.1f

public const val DEF_SHADOW_COLOR: Int = 0x8A000000.toInt()

public object Dimens {
    public const val AXIS_LABEL_HORIZONTAL_PADDING: Int = 4
    public const val AXIS_LABEL_MAX_LINES: Int = 1
    public const val AXIS_LABEL_VERTICAL_PADDING: Int = 2
    public const val AXIS_LABEL_SIZE: Int = 12

    public const val AXIS_GUIDELINE_WIDTH: Float = 1f
    public const val AXIS_LINE_WIDTH: Float = 1f
    public const val AXIS_TICK_LENGTH: Float = 4f

    public const val COLUMN_WIDTH: Float = 8f
    public const val COLUMN_INSIDE_SPACING: Float = 8f
    public const val COLUMN_OUTSIDE_SPACING: Float = 32f
    public const val COLUMN_ROUNDNESS_PERCENT: Int = 40

    public const val CUBIC_STRENGTH: Float = 1f

    public const val DASH_LENGTH: Float = 4f
    public const val DASH_GAP: Float = 2f

    public const val LINE_THICKNESS: Float = 2f

    public const val MARKER_INDICATOR_SIZE: Float = 36f
    public const val MARKER_HORIZONTAL_PADDING: Float = 8f
    public const val MARKER_VERTICAL_PADDING: Float = 4f

    public const val POINT_SIZE: Float = 16f
    public const val POINT_SPACING: Float = 16f

    public const val THRESHOLD_LINE_THICKNESS: Float = 2f

    public const val CHART_HEIGHT: Float = 200f
}

@Suppress("MagicNumber")
public interface Colors {
    public val axisLabelColor: Long
    public val axisGuidelineColor: Long
    public val axisLineColor: Long

    public val column1Color: Long
    public val column2Color: Long
    public val column3Color: Long

    public val lineColor: Long

    public object Light : Colors {
        override val axisLabelColor: Long = 0xDE000000
        override val axisGuidelineColor: Long = 0xFFAAAAAA
        override val axisLineColor: Long = 0xFF8A8A8A

        override val column1Color: Long = 0xFF787878
        override val column2Color: Long = 0xFF5A5A5A
        override val column3Color: Long = 0xFF383838

        override val lineColor: Long = 0xFF1A1A1A
    }

    public object Dark : Colors {
        override val axisLabelColor: Long = 0xFFFFFFFF
        override val axisGuidelineColor: Long = 0xFF424242
        override val axisLineColor: Long = 0xFF555555

        override val column1Color: Long = 0xFFCACACA
        override val column2Color: Long = 0xFFA8A8A8
        override val column3Color: Long = 0xFF888888

        override val lineColor: Long = 0xFFEFEFEF
    }
}

public object Alpha {
    public const val LINE_BACKGROUND_SHADER_START: Float = 0.5f
    public const val LINE_BACKGROUND_SHADER_END: Float = 0f
}