package de.linkel.aoc.utils

import kotlin.math.max
import kotlin.math.min

class Geometry {
    companion object {
        private operator fun Pair<Float, Float>.plus(other: Pair<Float, Float>) = (this.first + other.first) to (this.second + other.second)
        private operator fun Pair<Float, Float>.minus(other: Pair<Float, Float>) = (this.first - other.first) to (this.second - other.second)

        private fun det(a: Pair<Float, Float>, b: Pair<Float, Float>)
            = a.first * b.second - b.first * a.second

        // > 0 gegen uhrzeigersinn, < 0 bei uhrzeigersinn
        private fun dir(p: Pair<Float, Float>, q: Pair<Float, Float>, r: Pair<Float, Float>)
            = det(q - p, r - q)

        // we use screen geometry logic: y positive means down
        private fun onSegment(p: Pair<Float, Float>, q: Pair<Float, Float>, x: Pair<Float, Float>): Boolean {
            val topright = max(p.first, q.first) to min(p.second, q.second)
            val botleft = min(p.first, q.first) to max(p.second, q.second)
            return x.first <= topright.first && x.second <= topright.second && botleft.first <= x.first && botleft.second <= x.second
        }
        
        fun intersect(p: Pair<Float, Float>, q: Pair<Float, Float>, r: Pair<Float, Float>, s: Pair<Float, Float>): Boolean {
            val d1 = dir(p, q, r)
            val d2 = dir(p, q, s)
            if ((d1 == 0f && onSegment(p, q, r)) || (d2 == 0f && onSegment(p, q, s)))
                return true
            if ((d1 > 0f && d2 > 0f) || (d1 < 0f && d2 < 0f))
                return false
            val d3 = dir(r, s, p)
            val d4 = dir(r, s, q)
            if ((d3 == 0f && onSegment(r, s, p)) || (d4 == 0f && onSegment(r, s, q)))
                return true
            if ((d3 > 0f && d4 > 0f) || (d3 < 0f && d4 < 0f))
                return false
            if (d1 == 0f && d2 == 0f && d3 == 0f && d4 == 0f)
                return false
            return true
        }
    }
}
