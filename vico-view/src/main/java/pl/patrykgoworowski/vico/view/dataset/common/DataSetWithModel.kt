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

package pl.patrykgoworowski.vico.view.dataset.common

import pl.patrykgoworowski.vico.core.axis.model.MutableDataSetModel
import pl.patrykgoworowski.vico.core.dataset.draw.ChartDrawContext
import pl.patrykgoworowski.vico.core.dataset.entry.collection.EntryModel
import pl.patrykgoworowski.vico.core.dataset.renderer.DataSet
import pl.patrykgoworowski.vico.core.dataset.segment.SegmentProperties
import pl.patrykgoworowski.vico.core.layout.MeasureContext
import pl.patrykgoworowski.vico.core.marker.Marker
import pl.patrykgoworowski.vico.view.common.UpdateRequestListener

interface DataSetWithModel<Model : EntryModel> : DataSet<Model> {
    fun draw(
        context: ChartDrawContext,
        marker: Marker?,
    )

    fun addListener(listener: UpdateRequestListener)
    fun removeListener(listener: UpdateRequestListener)
    fun getEntriesModel(): Model
    fun getSegmentProperties(context: MeasureContext): SegmentProperties
    fun setToAxisModel(axisModel: MutableDataSetModel)
}
