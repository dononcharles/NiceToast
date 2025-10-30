package com.dononcharles.nicetoast

import org.junit.Test

class NiceToastTypeTest {

    @Test
    fun `getName for single word enum`() {
        // Verify getName() returns the correct string for enum entries with no underscores, such as 'INFO'.
        assert(NiceToastType.INFO.getName() == "INFO")
        assert(NiceToastType.SUCCESS.getName() == "SUCCESS")
    }

    @Test
    fun `getName for multi word enum`() {
        // Verify getName() replaces the first underscore with a space for enum entries like 'NO_INTERNET', expecting 'NO INTERNET'.
        assert(NiceToastType.NO_INTERNET.getName() == "NO INTERNET")
    }

    @Test
    fun `getName for all enum values`() {
        // Iterate through all enum constants and assert that getName() provides the expected formatted string for each.
        val expectedNames = mapOf(
            NiceToastType.INFO to "INFO",
            NiceToastType.SUCCESS to "SUCCESS",
            NiceToastType.WARNING to "WARNING",
            NiceToastType.ERROR to "ERROR",
            NiceToastType.DELETE to "DELETE",
            NiceToastType.NO_INTERNET to "NO INTERNET"
        )
        NiceToastType.entries.forEach {
            assert(it.getName() == expectedNames[it])
        }
    }

    @Test
    fun `values   content verification`() {
        // Check that the array returned by values() contains all the defined enum constants (INFO, SUCCESS, WARNING, ERROR, DELETE, NO_INTERNET).
        val values = NiceToastType.entries.toTypedArray()
        val expectedValues = arrayOf(
            NiceToastType.INFO,
            NiceToastType.SUCCESS,
            NiceToastType.WARNING,
            NiceToastType.ERROR,
            NiceToastType.DELETE,
            NiceToastType.NO_INTERNET
        )
        assert(values.contentEquals(expectedValues))
    }

    @Test
    fun `values   size check`() {
        // Verify that the length of the array returned by values() is equal to the total number of enum constants defined.
        assert(NiceToastType.entries.size == 6)
    }

    @Test
    fun `values  order verification`() {
        // Ensure the order of enums in the returned array matches the declaration order in the source code.
        val expectedOrder = arrayOf(
            NiceToastType.INFO,
            NiceToastType.SUCCESS,
            NiceToastType.WARNING,
            NiceToastType.ERROR,
            NiceToastType.DELETE,
            NiceToastType.NO_INTERNET
        )
        assert(NiceToastType.entries.toTypedArray().contentEquals(expectedOrder))
    }

    @Test
    fun `values  immutability check`() {
        // Although the returned array is mutable, test that modifying the array does not affect subsequent calls to values(), which should return a new array. [7, 8]
        val firstCall = NiceToastType.entries.toTypedArray()
        val firstCallOriginalFirstElement = firstCall[0]
        firstCall[0] = NiceToastType.DELETE // Modify the returned array

        val secondCall = NiceToastType.entries.toTypedArray()
        // Assert that the second call returns a fresh, unmodified array
        assert(secondCall[0] == firstCallOriginalFirstElement)
        // Assert that the two returned arrays are different instances
        assert(firstCall !== secondCall)
    }

    @Test
    fun `valueOf   with valid uppercase string`() {
        // Test valueOf() with a valid, uppercase string like 'SUCCESS' and assert it returns the corresponding enum constant NiceToastType.SUCCESS.
        assert(NiceToastType.valueOf("SUCCESS") == NiceToastType.SUCCESS)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `valueOf   with invalid string`() {
        // Verify that calling valueOf() with a string that does not match any enum constant, like 'INVALID', throws an IllegalArgumentException. [3, 6]
        NiceToastType.valueOf("INVALID")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `valueOf   with lowercase string`() {
        // Check if valueOf() with a lowercase string 'error' throws an IllegalArgumentException, as it is case-sensitive.
        NiceToastType.valueOf("error")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `valueOf   with empty string`() {
        // Test that calling valueOf() with an empty string throws an IllegalArgumentException.
        NiceToastType.valueOf("")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `valueOf   with string containing spaces`() {
        // Verify that valueOf('NO INTERNET') throws an IllegalArgumentException because it expects the exact enum constant name 'NO_INTERNET'.
        NiceToastType.valueOf("NO INTERNET")
    }

    @Test
    fun `getEntries   content verification`() {
        // Check that the EnumEntries list returned by getEntries() contains all defined enum constants.
        val entries = NiceToastType.entries
        assert(
            entries.containsAll(
                listOf(
                    NiceToastType.INFO,
                    NiceToastType.SUCCESS,
                    NiceToastType.WARNING,
                    NiceToastType.ERROR,
                    NiceToastType.DELETE,
                    NiceToastType.NO_INTERNET
                )
            )
        )
    }

    @Test
    fun `getEntries   size check`() {
        // Verify that the size of the list from getEntries() matches the number of enum constants.
        assert(NiceToastType.entries.size == 6)
    }

    @Test
    fun `getEntries   order consistency`() {
        // Ensure the order of entries in the list from getEntries() is consistent with the declaration order and ordinal values. [1]
        val expectedOrder = listOf(
            NiceToastType.INFO,
            NiceToastType.SUCCESS,
            NiceToastType.WARNING,
            NiceToastType.ERROR,
            NiceToastType.DELETE,
            NiceToastType.NO_INTERNET
        )
        assert(NiceToastType.entries == expectedOrder)
    }

    @Test
    fun `getEntries   performance and instance check`() {
        // Verify that multiple calls to getEntries() return the same list instance, confirming its performance advantage over values(). [3, 7]
        val firstCall = NiceToastType.entries
        val secondCall = NiceToastType.entries
        assert(firstCall === secondCall) // Check for same instance
    }


}