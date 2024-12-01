package de.linkel.aoc.utils.geometry.plain.continuous

import kotlin.math.max
import kotlin.math.min

class Algorithms {
    companion object {
        fun anySegmentsIntersect(segments: Iterable<Pair<PointD,PointD>>): Boolean {
            val events = segments
                .map { (a, b) ->
                    if (a.x < b.x) a to b
                    else if (b.x < a.x) b to a
                    else if (a.y < b.y) a to b
                    else if (b.y < a.y) b to a
                    else a to b
                }
                .flatMapIndexed { idx, (a, b) -> listOf(a to (idx to 0), b to (idx to 1)) }
                .sortedWith(compareBy({ it.first.x }, { it.first.y }))


            for (event in events) {

            }
            return false
            //TODO: vertical segments

        }
        private fun angleDirection(p: PointD, q: PointD, r: PointD) = (q - p).determinant(r - q)
        private fun onSegment(p: PointD, q: PointD, x: PointD): Boolean {
            val topright = max(p.x, q.x) to min(p.y, q.y)
            val botleft = min(p.x, q.x) to max(p.y, q.y)
            return x.x <= topright.first && x.y <= topright.second && botleft.first <= x.x && botleft.second <= x.y
        }

        fun segmentsIntersect(a: SegmentD, b: SegmentD): Boolean {
            val d1 = angleDirection(a.start, a.end, b.start)
            val d2 = angleDirection(a.start, a.end, b.end)
            if ((d1 == 0.0 && onSegment(a.start, a.end, b.start)) || (d2 == 0.0 && onSegment(a.start, a.end, b.end)))
                return true
            if ((d1 > 0.0 && d2 > 0.0) || (d1 < 0.0 && d2 < 0.0))
                return false
            val d3 = angleDirection(b.start, b.end, a.start)
            val d4 = angleDirection(b.start, b.end, a.end)
            if ((d3 == 0.0 && onSegment(b.start, b.end, a.start)) || (d4 == 0.0 && onSegment(b.start, b.end, a.end)))
                return true
            if ((d3 > 0.0 && d4 > 0.0) || (d3 < 0.0 && d4 < 0.0))
                return false
            if (d1 == 0.0 && d2 == 0.0 && d3 == 0.0 && d4 == 0.0)
                return false
            return true
        }
    }
}
