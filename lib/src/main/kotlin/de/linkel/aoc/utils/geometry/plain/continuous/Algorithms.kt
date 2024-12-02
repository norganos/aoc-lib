package de.linkel.aoc.utils.geometry.plain.continuous

import java.util.*

class Algorithms {
    companion object {
        private fun sweepLine(segments: Iterable<SegmentD>, all: Boolean): Set<Set<SegmentD>> {
            data class OpenSegment(
                val segment: SegmentD,
                val idx: Int,
                val y: Double = segment.start.y
            )
            val comparator = compareBy(
                { openSegment: OpenSegment -> openSegment.y },
                { openSegment: OpenSegment -> openSegment.idx },
            )
            data class Event(
                val eventPoint: PointD,
                val isStart: Boolean,
                val segment: SegmentD,
                val originalSegment: SegmentD,
                val idx: Int
            )
            // sweep line algorithm
            val events = segments
                // transform our segments into point pairs where the first point is left of the other one
                .map { seg ->
                    seg to if (seg.start.x < seg.end.x) seg
                    else if (seg.end.x < seg.start.x) seg.turnAround()
                    else if (seg.start.y < seg.end.y) seg
                    else if (seg.end.y < seg.start.y) seg.turnAround()
                    else seg
                }
                .toSet()
                //.flatMapIndexed { idx, (a, b) -> listOf(Event(a to (idx to 0), b to (idx to 1)) }
                .flatMapIndexed { idx, (origSeg, normalSeg) ->
                    listOf(
                        Event(eventPoint = normalSeg.start, isStart = true, segment = normalSeg, originalSegment = origSeg, idx = idx),
                        Event(eventPoint = normalSeg.end, isStart = false, segment = normalSeg, originalSegment = origSeg, idx = idx)
                    )
                }
                .sortedWith(compareBy({ it.eventPoint.x }, { it.eventPoint.y }))

            val openSegments = TreeMap<OpenSegment, SegmentD>(comparator)
            val result = mutableSetOf<Set<SegmentD>>()
            for (event in events) {
                val checkSegment = if (event.isStart) {
                    OpenSegment(event.segment, idx = event.idx)
                        .also {
                            openSegments[it] = event.originalSegment
                        }
                } else {
                    OpenSegment(event.segment, idx = event.idx)
                        .also {
                            openSegments.remove(it)
                        }
                        .copy(y = event.segment.end.y)
                }
                val intersections = if (checkSegment.segment.isVertical)
                        openSegments.entries
                            .toList()
                            .filter { it.key.segment != event.segment}
                    else
                        listOfNotNull(openSegments.higherEntry(checkSegment), openSegments.lowerEntry(checkSegment))
                            .filter {
                                it.key.segment.intersects(event.segment)
                            }
                for (intersection in intersections) {
                    result.add(setOf(event.originalSegment, intersection.value))
                    if (!all) {
                        break
                    }
                }
            }
            return result
        }
        fun segmentIntersections(segments: Iterable<SegmentD>): Set<Set<SegmentD>> {
            return sweepLine(segments, true)
        }
        fun anySegmentsIntersect(segments: Iterable<SegmentD>): Boolean {
            return sweepLine(segments, false).isNotEmpty()
        }
    }
}
