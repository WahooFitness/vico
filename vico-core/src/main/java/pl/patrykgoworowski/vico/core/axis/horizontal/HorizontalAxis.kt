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

package pl.patrykgoworowski.vico.core.axis.horizontal

import pl.patrykgoworowski.vico.core.DEF_AXIS_COMPONENT
import pl.patrykgoworowski.vico.core.DEF_GUIDELINE_COMPONENT
import pl.patrykgoworowski.vico.core.DEF_LABEL_COMPONENT
import pl.patrykgoworowski.vico.core.DEF_TICK_COMPONENT
import pl.patrykgoworowski.vico.core.Dimens
import pl.patrykgoworowski.vico.core.axis.AxisPosition
import pl.patrykgoworowski.vico.core.axis.BaseLabeledAxisRenderer
import pl.patrykgoworowski.vico.core.axis.formatter.AxisValueFormatter
import pl.patrykgoworowski.vico.core.axis.formatter.DecimalFormatAxisValueFormatter
import pl.patrykgoworowski.vico.core.axis.model.DataSetModel
import pl.patrykgoworowski.vico.core.component.shape.LineComponent
import pl.patrykgoworowski.vico.core.component.text.TextComponent
import pl.patrykgoworowski.vico.core.component.text.VerticalPosition
import pl.patrykgoworowski.vico.core.dataset.draw.ChartDrawContext
import pl.patrykgoworowski.vico.core.dataset.insets.Insets
import pl.patrykgoworowski.vico.core.extension.half
import pl.patrykgoworowski.vico.core.extension.orZero
import pl.patrykgoworowski.vico.core.layout.MeasureContext
import kotlin.math.ceil

class HorizontalAxis<Position : AxisPosition.Horizontal>(
    override val position: Position,
    label: TextComponent?,
    axis: LineComponent?,
    tick: LineComponent?,
    guideline: LineComponent?,
    override var tickLengthDp: Float,
    override var valueFormatter: AxisValueFormatter,
) : BaseLabeledAxisRenderer<Position>(label, axis, tick, guideline) {

    private val AxisPosition.Horizontal.textVerticalPosition: VerticalPosition
        get() = if (isBottom) VerticalPosition.Top else VerticalPosition.Bottom

    var tickType: TickType = TickType.Minor

    override fun drawBehindDataSet(context: ChartDrawContext) = with(context) {
        val scrollX = context.horizontalScroll
        val clipRestoreCount = canvas.save()

        canvas.clipRect(
            bounds.left - if (tickType == TickType.Minor) tickThickness.half else 0f,
            minOf(bounds.top, dataSetBounds.top),
            bounds.right + if (tickType == TickType.Minor) tickThickness.half else 0f,
            maxOf(bounds.bottom, dataSetBounds.bottom)
        )

        val entryLength = getEntryLength(segmentProperties.segmentWidth)
        val tickCount = tickType.getTickCount(entryLength)
        val tickDrawStep = segmentProperties.segmentWidth
        val scrollAdjustment = (scrollX / tickDrawStep).toInt()
        var textDrawCenter =
            bounds.left + tickDrawStep.half - scrollX + (tickDrawStep * scrollAdjustment)
        var tickDrawCenter =
            tickType.getTickDrawCenter(scrollX, tickDrawStep, scrollAdjustment, textDrawCenter)

        val guidelineTop = dataSetBounds.top
        val guidelineBottom = dataSetBounds.bottom

        for (index in 0 until tickCount) {
            guideline?.run {
                setParentBounds(bounds)
                takeIf {
                    it.fitsInVertical(
                        context = context,
                        top = guidelineTop,
                        bottom = guidelineBottom,
                        centerX = tickDrawCenter,
                        boundingBox = dataSetBounds
                    )
                }?.drawVertical(
                    context = context,
                    top = guidelineTop,
                    bottom = guidelineBottom,
                    centerX = tickDrawCenter
                )
            }

            tickDrawCenter += tickDrawStep
            textDrawCenter += tickDrawStep
        }

        if (clipRestoreCount >= 0) canvas.restoreToCount(clipRestoreCount)
    }

    override fun drawAboveDataSet(context: ChartDrawContext) = with(context) {
        val tickMarkTop = if (position.isBottom) bounds.top else bounds.bottom - tickLength
        val tickMarkBottom = tickMarkTop + axisThickness + tickLength
        val scrollX = horizontalScroll
        val clipRestoreCount = canvas.save()
        val step = dataSetModel.entryModel.step

        canvas.clipRect(
            bounds.left - if (tickType == TickType.Minor) tickThickness.half else 0f,
            minOf(bounds.top, dataSetBounds.top),
            bounds.right + if (tickType == TickType.Minor) tickThickness.half else 0f,
            maxOf(bounds.bottom, dataSetBounds.bottom)
        )

        val entryLength = getEntryLength(segmentProperties.segmentWidth)
        val tickCount = tickType.getTickCount(entryLength)
        val tickDrawStep = segmentProperties.segmentWidth
        val scrollAdjustment = (scrollX / tickDrawStep).toInt()
        var textDrawCenter =
            bounds.left + tickDrawStep.half - scrollX + (tickDrawStep * scrollAdjustment)
        var tickDrawCenter =
            tickType.getTickDrawCenter(scrollX, tickDrawStep, scrollAdjustment, textDrawCenter)

        val textY = if (position.isBottom) tickMarkBottom else tickMarkTop

        var valueIndex: Float = dataSetModel.minX + scrollAdjustment * step

        for (index in 0 until tickCount) {
            tick?.setParentBounds(bounds)
            tick?.drawVertical(
                context = context,
                top = tickMarkTop,
                bottom = tickMarkBottom,
                centerX = tickDrawCenter
            )

            if (index < entryLength) {
                label?.background?.setParentBounds(bounds)
                label?.drawText(
                    context = context,
                    text = valueFormatter.formatValue(valueIndex, index, context.dataSetModel),
                    textX = textDrawCenter,
                    textY = textY,
                    verticalPosition = position.textVerticalPosition,
                    width = tickDrawStep.toInt(),
                )

                valueIndex += step
            }
            tickDrawCenter += tickDrawStep
            textDrawCenter += tickDrawStep
        }

        axis?.run {
            setParentBounds(bounds)
            drawHorizontal(
                context = context,
                left = dataSetBounds.left,
                right = dataSetBounds.right,
                centerY = ((if (position is AxisPosition.Horizontal.Bottom) bounds.top
                else bounds.bottom) + axisThickness.half)
            )
        }

        label?.clearLayoutCache()

        if (clipRestoreCount >= 0) canvas.restoreToCount(clipRestoreCount)
    }

    private fun getEntryLength(segmentWidth: Float) =
        ceil(bounds.width() / segmentWidth).toInt() + 1

    private fun TickType.getTickCount(entryLength: Int) = when (this) {
        TickType.Minor -> entryLength + 1
        TickType.Major -> entryLength
    }

    private fun TickType.getTickDrawCenter(
        scrollX: Float,
        tickDrawStep: Float,
        scrollAdjustment: Int,
        textDrawCenter: Float,
    ) = when (this) {
        TickType.Minor -> bounds.left - scrollX + (tickDrawStep * scrollAdjustment)
        TickType.Major -> textDrawCenter
    }

    override fun getVerticalInsets(
        context: MeasureContext,
        dataSetModel: DataSetModel,
        outInsets: Insets
    ) = with(context) {
        with(outInsets) {
            setHorizontal(
                if (tickType == TickType.Minor) tickThickness.half
                else 0f
            )
            top = if (position.isTop) getDesiredHeight(context).toFloat() else 0f
            bottom = if (position.isBottom) getDesiredHeight(context).toFloat() else 0f
        }
    }

    override fun getDesiredHeight(context: MeasureContext) = with(context) {
        ((if (position.isBottom) axisThickness else 0f) +
                tickLength +
                label?.getHeight(context = this).orZero).toInt()
    }

    override fun getDesiredWidth(context: MeasureContext, labels: List<String>): Float = 0f

    enum class TickType {
        Minor, Major
    }
}

fun topAxis(
    label: TextComponent? = DEF_LABEL_COMPONENT,
    axis: LineComponent? = DEF_AXIS_COMPONENT,
    tick: LineComponent? = DEF_TICK_COMPONENT,
    tickLengthDp: Float = Dimens.AXIS_TICK_LENGTH,
    guideline: LineComponent? = DEF_GUIDELINE_COMPONENT,
    valueFormatter: AxisValueFormatter = DecimalFormatAxisValueFormatter(),
): HorizontalAxis<AxisPosition.Horizontal.Top> = HorizontalAxis(
    position = AxisPosition.Horizontal.Top,
    label = label,
    axis = axis,
    tick = tick,
    guideline = guideline,
    valueFormatter = valueFormatter,
    tickLengthDp = tickLengthDp,
)

fun bottomAxis(
    label: TextComponent? = DEF_LABEL_COMPONENT,
    axis: LineComponent? = DEF_AXIS_COMPONENT,
    tick: LineComponent? = DEF_TICK_COMPONENT,
    tickLengthDp: Float = Dimens.AXIS_TICK_LENGTH,
    guideline: LineComponent? = DEF_GUIDELINE_COMPONENT,
    valueFormatter: AxisValueFormatter = DecimalFormatAxisValueFormatter(),
): HorizontalAxis<AxisPosition.Horizontal.Bottom> = HorizontalAxis(
    position = AxisPosition.Horizontal.Bottom,
    label = label,
    axis = axis,
    tick = tick,
    guideline = guideline,
    valueFormatter = valueFormatter,
    tickLengthDp = tickLengthDp,
)
