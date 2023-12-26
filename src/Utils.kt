import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun <T> T.println(): T { println(this); return this }
inline fun <T>checkEquals(expected: T, actual: T) {
    if (expected != actual) {
        val message = "Check failed. Expected $expected, got $actual"
        throw IllegalStateException(message)
    }
}

fun Long.lcm(other: Long): Long {
    return (this * other) / this.gcd(other)
}

tailrec fun Long.gcd(other: Long): Long {
    return if(other == 0L) this else other.gcd(this % other)
}
